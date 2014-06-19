package net.catchpole.silicone.action;

//   Copyright 2014 catchpole.net
//
//   Licensed under the Apache License, Version 2.0 (the "License");
//   you may not use this file except in compliance with the License.
//   You may obtain a copy of the License at
//
//       http://www.apache.org/licenses/LICENSE-2.0
//
//   Unless required by applicable law or agreed to in writing, software
//   distributed under the License is distributed on an "AS IS" BASIS,
//   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//   See the License for the specific language governing permissions and
//   limitations under the License.

import net.catchpole.silicone.action.annotation.AllowGetRequest;
import net.catchpole.silicone.action.annotation.SessionMarker;
import net.catchpole.silicone.lang.Throw;
import net.catchpole.silicone.lang.reflect.Mapper;
import net.catchpole.silicone.lang.reflect.Reflection;
import net.catchpole.silicone.servlet.RequestMap;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Actions {
    private final Map<String,ClassAction> actionClassMap = new HashMap<String,ClassAction>();
    private final Mapper mapper = new Mapper();
    private final Reflection reflection = new Reflection();
    private Action statelessAction;
    private Action sessionAction;
    private Action globalAction;
    private Class sessionClass;

    public void add(Action action) {
        Class[] genericsClasses = reflection.getParameterTypes(action.getClass(), Action.class);
        if (genericsClasses.length != 1) {
            throw new IllegalArgumentException("Unable to find generics parameter type for Action on class " + action.getClass());
        }
        Class beanClazz = genericsClasses[0];
        String actionName = beanClazz.getSimpleName().toLowerCase();
        if (actionClassMap.containsKey(actionName)) {
            throw new IllegalArgumentException("Action " + actionName + " already exists.");
        }

        boolean allowGet = reflection.findAnnotation(action.getClass(), AllowGetRequest.class) != null;
        if (allowGet && !mapper.hasSetter(beanClazz, actionName)) {
            throw new IllegalArgumentException("Beans for Actions that allow GET must have a property of the same name: " + actionName);
        }
        if (reflection.findAnnotation(beanClazz, SessionMarker.class) != null) {
            sessionClass = beanClazz;
        }

        actionClassMap.put(actionName, new ClassAction(beanClazz, action, allowGet));
    }

    public String perform(HttpServletRequest httpServletRequest, Artefacts artefacts) {
        ClassAction classAction = getClassAction(httpServletRequest);
        if (classAction == null) {
            return null;
        }
        try {
            Object actionBean = classAction.clazz.newInstance();
            mapper.setValues(actionBean, new RequestMap(httpServletRequest));

            // only perform if actions are in session
            if (artefacts.get(actionBean.getClass()) != null) {
                artefacts.set(actionBean);
                try {
                    classAction.action.perform(actionBean, artefacts);
                } catch (IllegalArgumentException iae) {
                    return iae.getMessage();
                }
            }
        } catch (Exception e) {
            throw Throw.unchecked(e);
        }
        return null;
    }

    private ClassAction getClassAction(HttpServletRequest httpServletRequest) {
        if (httpServletRequest.getMethod().equalsIgnoreCase("GET")) {
            for (Map.Entry<String,String[]> entry : (Set<Map.Entry<String,String[]>>)httpServletRequest.getParameterMap().entrySet()) {
                ClassAction classAction = actionClassMap.get(entry.getKey().toLowerCase());
                if (classAction != null && classAction.allowGet) {
                    return classAction;
                }
            }
        } else {
            String actionName = httpServletRequest.getParameter("action");
            if (actionName != null) {
                return actionClassMap.get(actionName.toLowerCase());
            }
        }
        return null;
    }

    public String perform(String actionName, Artefacts artefacts, InputStream inputStream) {
        try {
            ClassAction classAction = actionClassMap.get(actionName.toLowerCase());

            if (classAction != null) {
                Object actionBean = classAction.clazz.newInstance();
                if (actionBean instanceof Upload) {
                    ((Upload)actionBean).setInputStream(inputStream);
                    // only perform if actions are in session
                    if (artefacts.get(actionBean.getClass()) != null) {
                        artefacts.set(actionBean);
                        try {
                            classAction.action.perform(actionBean, artefacts);
                        } catch (IllegalArgumentException iae) {
                            return iae.getMessage();
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw Throw.unchecked(e);
        }
        return null;
    }

    public Action getStatelessAction() {
        return statelessAction;
    }

    public void setStatelessAction(Action statelessAction) {
        this.statelessAction = statelessAction;
    }

    public Action getSessionAction() {
        return sessionAction;
    }

    public void setSessionAction(Action sessionAction) {
        this.sessionAction = sessionAction;
    }

    public Action getGlobalAction() {
        return globalAction;
    }

    public void setGlobalAction(Action globalAction) {
        this.globalAction = globalAction;
    }

    public Class getSessionClass() {
        return sessionClass;
    }

    private class ClassAction {
        Class clazz;
        Action action;
        boolean allowGet;

        public ClassAction(Class clazz, Action action, boolean allowGet) {
            this.clazz = clazz;
            this.action = action;
            this.allowGet = allowGet;
        }
    }
}
