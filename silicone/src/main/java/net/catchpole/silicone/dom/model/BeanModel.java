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

import net.catchpole.silicone.dom.model.iterator.ModelIteratorResolver;
import net.catchpole.silicone.lang.SingleIterator;

import java.util.Iterator;
import java.util.Map;

public class BeanModel implements Model {
    private final BeanDecoder beanDecoder;
    private final String name;
    private final Object bean;

    public BeanModel(BeanDecoder beanDecoder, Object bean) {
        this.beanDecoder = beanDecoder;
        this.name = null;
        this.bean = bean;
    }

    public BeanModel(BeanDecoder beanDecoder, String name, Object bean) {
        this.beanDecoder = beanDecoder;
        this.name = name;
        this.bean = bean;
    }

    public String getName() {
        return name;
    }

    public Class getType() {
        return bean == null ? null : bean.getClass();
    }

    public Iterator getValues() {
        return new SingleIterator(bean);
    }

    public Iterator<Model> iterator() {
        if (bean instanceof Iterable) {
            return new ModelIteratorResolver<Object>(((Iterable) bean).iterator()) {
                public Model resolve(Object key) {
                    return new BeanModel(beanDecoder, key);
                }
            };
        }

        final Map map = beanDecoder.decode(bean);

        return new ModelIteratorResolver<Object>(map.keySet().iterator()) {
            public Model resolve(Object key) {
                return new BeanModel(beanDecoder, key.toString(), map.get(key));
            }
        };
    }

    public String toString() {
        return this.getClass().getSimpleName() + ' ' + name + ' ' + bean.getClass().getName();
    }
}
