package net.catchpole.silicone.render;

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

import net.catchpole.silicone.action.RequestDetails;
import net.catchpole.silicone.dom.ModelDocument;
import net.catchpole.silicone.dom.ModelDocumentBuilder;
import net.catchpole.silicone.dom.XsdBuilder;
import net.catchpole.silicone.dom.model.BeanDecoder;
import net.catchpole.silicone.dom.model.BeanModel;
import net.catchpole.silicone.dom.transform.XmlTransform;
import net.catchpole.silicone.lang.Throw;
import net.catchpole.silicone.servlet.Backing;

import java.io.IOException;
import java.io.OutputStream;

public class XmlRender implements Render {
    private final Class clazz;
    private final BeanDecoder beanDecoder = new BeanDecoder();
    private final ModelDocumentBuilder modelDocumentBuilder;
    private final XmlTransform xmlTransform = new XmlTransform();

    public XmlRender(Class clazz) {
        this.clazz = clazz;
        try {
            this.modelDocumentBuilder = new ModelDocumentBuilder(new XsdBuilder());
        } catch (Exception e) {
            throw Throw.unchecked(e);
        }
    }

    public void render(OutputStream os, Backing backing, RequestDetails requestDetails) throws IOException {
        Object object = backing.getArtefacts().get(clazz);
        if (object == null) {
            throw new IllegalArgumentException();
        }
        try {
            ModelDocument document = modelDocumentBuilder.newDocument(new BeanModel(beanDecoder, object));

            xmlTransform.printXML(document, os);
        } catch (Exception e) {
            throw Throw.unchecked(e);
        }
    }

    public String getContentType() {
        return "application/xml";
    }
}
