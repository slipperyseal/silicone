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

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class MethodProfile {
    private final Method method;
    private final boolean isSetMethod;
    private final boolean isGetMethod;
    private final String propertyName;
    private final boolean isStatic;
    private final int totalParams;
    private final String toString;

    public MethodProfile(Method method) {
        this.method = method;
        this.isSetMethod = findIsSetMethod();
        this.isGetMethod = findIsGetMethod();
        this.propertyName = findPropertyName();
        this.isStatic =  Modifier.isStatic(method.getModifiers());
        Class[] params = method.getParameterTypes();
        this.totalParams = params == null ? 0 : params.length;
        this.toString = findToString();
    }

    public Method getMethod() {
        return method;
    }

    public boolean isSetMethod() {
        return isSetMethod;
    }

    public boolean isGetMethod() {
        return isGetMethod;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public boolean isStatic() {
        return isStatic;
    }

    public int getTotalParams() {
        return totalParams;
    }

    private boolean findIsGetMethod() {
        String name = method.getName();
        return (name.length() > 3 && name.startsWith("get") && Character.isUpperCase(name.charAt(3)))
                ||
                (name.length() > 2 && name.startsWith("is") && Character.isUpperCase(name.charAt(2)));
    }

    private boolean findIsSetMethod() {
        String name = method.getName();
        return (name.length() > 3 &&
                name.startsWith("set") &&
                (beginsPropertyName(name, 3)));
    }

    private String findPropertyName() {
        String name = method.getName();
        if ((name.startsWith("get") || name.startsWith("set")) && beginsPropertyName(name, 3)) {
            name = name.substring(3);
            if (name.length() > 0) {
                name = name.substring(0, 1).toLowerCase() + name.substring(1);
            }
        }
        if (name.startsWith("is") && beginsPropertyName(name, 2)) {
            name = name.substring(2);
            if (name.length() > 0) {
                name = name.substring(0, 1).toLowerCase() + name.substring(1);
            }
        }
        return name;
    }

    /**
     * Property names can't start with a lower case alpha character.
     * Example gettingHot() should not be a property while getTemperature() should.
     *
     * @param name
     * @param index
     * @return
     */
    private boolean beginsPropertyName(String name, int index) {
        if (index > name.length()) {
            return false;
        }
        char c = name.charAt(index);
        return (c < 'a') || (c > 'z');
    }

    private String findToString() {
        if (method == null) {
            return "null";
        }
        StringBuilder sb = new StringBuilder(64);
        sb.append(method.getReturnType().getSimpleName());
        sb.append('=');
        sb.append(method.getDeclaringClass().getSimpleName());
        sb.append('.');
        sb.append(method.getName());
        sb.append('(');

        Class[] paramClasses = method.getParameterTypes();
        for (int x = 0; x < paramClasses.length; x++) {
            sb.append(paramClasses[x].getSimpleName());
            if (x != 0) {
                sb.append(',');
            }
        }
        sb.append(')');
        return sb.toString();
    }

    public String toString() {
        return toString;
    }
}
