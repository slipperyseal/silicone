package net.catchpole.silicone.lang.reflect;

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

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Reflection {
    public Reflection() {
    }

    public boolean methodsMatch(Method testMethod, Method[] methods) {
        for (Method method : methods) {
            if (methodsMatch(testMethod, method)) {
                return true;
            }
        }
        return false;
    }

    public boolean methodsMatch(Method method1, Method method2) {
        if (!method1.getName().equals(method2.getName())) {
            return false;
        }
        if (!method1.getReturnType().equals(method2.getReturnType())) {
            return false;
        }
        if (!Arrays.equals(method1.getParameterTypes(), method2.getParameterTypes())) {
            return false;
        }
        return true;
    }

    public Class[] getParameterTypes(Class clazz, Class parametizedClass) {
        for (Type type : clazz.getGenericInterfaces()) {
            if (type instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType) type;
                if (parameterizedType.getRawType().equals(parametizedClass)) {
                    Type[] paramTypes = parameterizedType.getActualTypeArguments();
                    Class[] classes = new Class[paramTypes.length];
                    System.arraycopy(paramTypes, 0, classes, 0, paramTypes.length);
                    return classes;
                }
            }
        }
        return new Class[0];
    }

    /**
     * Returns all methods for a class except those which are inheritied from
     * the Object class such as hashcode, toString etc.
     */
    public Method[] getNonObjectMethods(Class clazz) {
        List<Method> list = new ArrayList<Method>();
        for (Method method : clazz.getMethods()) {
            if (!methodsMatch(method, Object.class.getMethods())) {
                list.add(method);
            }
        }
        return list.toArray(new Method[list.size()]);
    }

    public <T extends Object> T findAnnotation(Class clazz, Class<T> annotationType) {
        for (Annotation annotation : clazz.getAnnotations()) {
            if (annotation.annotationType().equals(annotationType)) {
                return (T)annotation;
            }
        }
        return null;
    }

    public  <T extends Object> T findAnnotation(Method method, Class<T> annotationType) {
        for (Annotation annotation : method.getAnnotations()) {
            if (annotation.annotationType().equals(annotationType)) {
                return (T)annotation;
            }
        }
        return null;
    }
}
