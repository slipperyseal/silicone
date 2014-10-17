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

import net.catchpole.silicone.lang.Throw;
import net.catchpole.silicone.lang.Values;

import javax.xml.bind.DatatypeConverter;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;

public class Mapper {
    private Values values = new Values();

    public void setValues(Object object, Map<String,String> map) {
        try {
            for (Method method : object.getClass().getMethods()) {
                Class[] paramTypes = method.getParameterTypes();
                if (method.getName().startsWith("set") && paramTypes.length == 1) {
                    String setterName = method.getName().substring(3);
                    for (String key : map.keySet()) {
                        if (key.equalsIgnoreCase(setterName)) {
                            method.invoke(object,
                                    this.getObjectValue(paramTypes[0], map.get(key)));
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw Throw.unchecked(e);
        }
    }

    public boolean hasSetter(Class clazz, String property) {
        try {
            for (Method method : clazz.getMethods()) {
                Class[] paramTypes = method.getParameterTypes();
                if (method.getName().startsWith("set") && paramTypes.length == 1) {
                    String setterName = method.getName().substring(3);
                    if (property.equalsIgnoreCase(setterName)) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            throw Throw.unchecked(e);
        }
        return false;
    }

    public <T> T getObjectValue(Class<T> clazz, String value) {
        if (value == null) {
            return null;
        }
        if (clazz.equals(String.class)) {
            return (T)value;
        }
        try {
            if (clazz.equals(Date.class)) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                return (T)simpleDateFormat.parse(value);
            }
            if (clazz.equals(Integer.class)) {
                return (T)new Integer(Integer.parseInt(value));
            }
            if (clazz.equals(Long.class)) {
                return (T)new Long(Long.parseLong(value));
            }
            if (clazz.equals(Double.class)) {
                return (T)new Double(Double.parseDouble(value));
            }
            if (clazz.equals(Boolean.class)) {
                return (T)new Boolean(Boolean.parseBoolean(value));
            }
            if (clazz.isArray()) {
                return (T)DatatypeConverter.parseHexBinary(value);
            }
            if (clazz.isArray() && clazz.getComponentType().equals(Byte.TYPE)) {
                return (T)values.hexToBytes(value);
            }
        } catch (Exception e) {
            throw Throw.unchecked(e);
        }
        throw new UnsupportedOperationException(clazz.getName());
    }

    public String getStringValue(Object value) {
        if (value == null) {
            return null;
        }
        Class clazz = value.getClass();
        if (!clazz.isArray() && clazz.equals(String.class)) {
            return (String)value;
        }
        if (!clazz.isArray() && clazz.equals(Date.class)) {
            try {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                return simpleDateFormat.format(value);
            } catch (Exception e) {
                throw Throw.unchecked(e);
            }
        }
        if (clazz.isArray() && clazz.getComponentType().equals(Byte.TYPE)) {
            return values.toHexString((byte[])value);
        }
        return String.valueOf(value);
    }
}
