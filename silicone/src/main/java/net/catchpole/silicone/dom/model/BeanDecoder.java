package net.catchpole.silicone.dom.model;

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

import net.catchpole.silicone.lang.reflect.MethodProfile;
import net.catchpole.silicone.lang.reflect.Reflection;

import java.lang.reflect.Method;
import java.util.*;

public class BeanDecoder {
    private Map<String,List<MethodProfile>> methodProfileMap = new HashMap<String, List<MethodProfile>>();

    public Map decode(Object bean) {
        if (bean == null || bean instanceof String) {
            return new HashMap();
        }

        if (bean instanceof Map) {
            return (Map) bean;
        }

        if (bean instanceof Iterable) {
            Map map = new LinkedHashMap();
            int x = 0;
            for (Object item : (Iterable) bean) {
                map.put(Integer.toString(x++), item);
            }
            return map;
        }

        return getValues(bean);
    }

    private Map<String, Object> getValues(Object bean) {
        Map<String, Object> map = new HashMap<String, Object>();

        for (MethodProfile methodProfile : getMethodProfiles(bean.getClass())) {
            Method method = methodProfile.getMethod();

            if (methodProfile.getTotalParams() == 0 && !methodProfile.isStatic() && methodProfile.isGetMethod()) {
                try {
                    map.put(methodProfile.getPropertyName(), method.invoke(bean));
                } catch (Throwable t) {
                    // should not happen as Invocation Target and Access should be OK
                    throw new RuntimeException(method.toString(), t);
                }
            }
        }
        return map;
    }

    private List<MethodProfile> getMethodProfiles(Class clazz) {
        String key = clazz.getName();
        synchronized (methodProfileMap) {
            List<MethodProfile> methodProfileList = methodProfileMap.get(key);
            if (methodProfileList == null) {
                Method[] methodArray = new Reflection().getNonObjectMethods(clazz);
                methodProfileList = new ArrayList<MethodProfile>();
                for (Method method : methodArray) {
                    methodProfileList.add(new MethodProfile(method));
                }
                methodProfileMap.put(key, methodProfileList);
            }
            return methodProfileList;
        }
    }

    public Object getObject(String name, Object bean) {
        Map<String,Object> map = getValues(bean);
        return map.get(name);
    }
}
