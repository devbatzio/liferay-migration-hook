/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.altec.portal.hook.structures;

import com.liferay.portal.service.ServiceContext;
import gr.altec.portal.hook.IContentStructure;
import java.io.IOException;
import java.io.StringWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 *
 * @author bats-pc
 */
public class InviteContentStructure implements IContentStructure {

    
    @Override
    public String generateContentStructure(Object... object) {

        ServiceContext st = (ServiceContext) object[0];
        String preInfo = (object[1] != null) ? object[1].toString() : "";
        String info = (object[2] != null) ? object[2].toString() : "";
        String dates = (object[3] != null) ? object[3].toString() : "";
        String doc_info = (object[4] != null) ? object[4].toString() : "";
        String inst = (object[5] != null) ? object[5].toString() : "";
        String docPath = (object[6] != null) ? object[6].toString() : "";

        StringWriter stringWriter = new StringWriter();
        try {

            XMLOutputFactory xMLOutputFactory = XMLOutputFactory.newInstance();
            XMLStreamWriter xMLStreamWriter = xMLOutputFactory.createXMLStreamWriter(stringWriter);

            xMLStreamWriter.writeStartDocument();
            xMLStreamWriter.writeStartElement("root");

            xMLStreamWriter.writeAttribute("available-locales", "en_US");
            xMLStreamWriter.writeAttribute("default-locales", "en_US");

            xMLStreamWriter.writeStartElement("dynamic-element");
            xMLStreamWriter.writeAttribute("name", "preInfo");
            xMLStreamWriter.writeAttribute("type", "text_area");
            xMLStreamWriter.writeAttribute("index-type", "keyword");
            xMLStreamWriter.writeAttribute("index", "0");

            xMLStreamWriter.writeStartElement("dynamic-content");
            xMLStreamWriter.writeAttribute("language-id", "en_US");
            xMLStreamWriter.writeCData(preInfo);
            //end of dynamic-content
            xMLStreamWriter.writeEndElement();
            //end of dynamic-element
            xMLStreamWriter.writeEndElement();

            xMLStreamWriter.writeStartElement("dynamic-element");
            xMLStreamWriter.writeAttribute("name", "info");
            xMLStreamWriter.writeAttribute("type", "text-area");
            xMLStreamWriter.writeAttribute("index-type", "keyword");
            xMLStreamWriter.writeAttribute("index", "0");

            xMLStreamWriter.writeStartElement("dynamic-content");
            xMLStreamWriter.writeAttribute("language-id", "en_US");
            xMLStreamWriter.writeCData(info);
            //end of dynamic-content
            xMLStreamWriter.writeEndElement();
            //end of dynamic-element
            xMLStreamWriter.writeEndElement();

            xMLStreamWriter.writeStartElement("dynamic-element");
            xMLStreamWriter.writeAttribute("name", "dates");
            xMLStreamWriter.writeAttribute("type", "text-area");
            xMLStreamWriter.writeAttribute("index-type", "keyword");
            xMLStreamWriter.writeAttribute("index", "0");

            xMLStreamWriter.writeStartElement("dynamic-content");
            xMLStreamWriter.writeAttribute("language-id", "en_US");
            xMLStreamWriter.writeCData(dates);
            //end of dynamic-content
            xMLStreamWriter.writeEndElement();
            //end of dynamic-element
            xMLStreamWriter.writeEndElement();

            xMLStreamWriter.writeStartElement("dynamic-element");
            xMLStreamWriter.writeAttribute("name", "doc_info");
            xMLStreamWriter.writeAttribute("type", "text-area");
            xMLStreamWriter.writeAttribute("index-type", "keyword");
            xMLStreamWriter.writeAttribute("index", "0");

            xMLStreamWriter.writeStartElement("dynamic-content");
            xMLStreamWriter.writeAttribute("language-id", "en_US");
            xMLStreamWriter.writeCData(doc_info);
            //end of dynamic-content
            xMLStreamWriter.writeEndElement();
            //end of dynamic-element
            xMLStreamWriter.writeEndElement();

            xMLStreamWriter.writeStartElement("dynamic-element");
            xMLStreamWriter.writeAttribute("name", "inst");
            xMLStreamWriter.writeAttribute("type", "text-area");
            xMLStreamWriter.writeAttribute("index-type", "keyword");
            xMLStreamWriter.writeAttribute("index", "0");

            xMLStreamWriter.writeStartElement("dynamic-content");
            xMLStreamWriter.writeAttribute("language-id", "en_US");
            xMLStreamWriter.writeCData(inst);
            //end of dynamic-content
            xMLStreamWriter.writeEndElement();
            //end of dynamic-element
            xMLStreamWriter.writeEndElement();

            xMLStreamWriter.writeStartElement("dynamic-element");
            xMLStreamWriter.writeAttribute("name", "document");
            xMLStreamWriter.writeAttribute("type", "document_library");
            xMLStreamWriter.writeAttribute("index-type", "keyword");
            xMLStreamWriter.writeAttribute("index", "0");

            xMLStreamWriter.writeStartElement("dynamic-content");
            xMLStreamWriter.writeAttribute("language-id", "en_US");
            xMLStreamWriter.writeCData(docPath);

            //end of image dynamic content
            xMLStreamWriter.writeEndElement();
            //end of image dynamic element
            xMLStreamWriter.writeEndElement();

            //end of root
            xMLStreamWriter.writeEndElement();

            xMLStreamWriter.writeEndDocument();

            xMLStreamWriter.flush();
            xMLStreamWriter.close();

            String xmlString = stringWriter.getBuffer().toString();

            stringWriter.close();

        } catch (XMLStreamException | IOException e) {
        }

        return stringWriter.toString();
    }

}
