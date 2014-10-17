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
import net.catchpole.silicone.resource.ResourceSource;
import net.catchpole.silicone.resource.ResourceURIResolver;
import net.catchpole.silicone.servlet.Backing;

import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.io.OutputStream;

public class TemplateRender implements Render {
    private final boolean prod = false;
    private final BeanDecoder beanDecoder = new BeanDecoder();
    private final ModelDocumentBuilder modelDocumentBuilder;
    private final Templates transformer;

    public TemplateRender(ResourceSource resourceSource, String templatePath) {
        try {
            this.modelDocumentBuilder = new ModelDocumentBuilder(new XsdBuilder());

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            transformerFactory.setURIResolver(new ResourceURIResolver(resourceSource));
            this.transformer = transformerFactory.newTemplates(
                    new StreamSource(resourceSource.getResourceStream(templatePath)));
        } catch (Exception e) {
            throw Throw.unchecked(e);
        }
    }

    public void render(OutputStream os, Backing backing, RequestDetails requestDetails) throws IOException {
        this.render(os, (Object)backing, requestDetails);
    }

    public void render(OutputStream os, Object backing, RequestDetails requestDetails) throws IOException {
        try {
            ModelDocument document = modelDocumentBuilder.newDocument(new BeanModel(beanDecoder, backing));

            if (!prod) {
                new XmlTransform().printXML(document);
            }
            try {
                Transformer transformer = this.transformer.newTransformer();
                transformer.transform(new DOMSource(document), new StreamResult(os));
            } finally {
                os.flush();
                os.close();
            }
        } catch (Exception e) {
            throw Throw.unchecked(e);
        }
    }

    public String getContentType() {
        return "text/html";
    }
}
