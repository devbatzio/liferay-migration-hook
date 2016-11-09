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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 *
 * @author bats-pc
 */
public class LibraryContentStructure implements IContentStructure {

    @Override
    public String generateContentStructure(Object... object) {

        ServiceContext st = (ServiceContext) object[0];

        String finalImagePath = (object[1] != null) ? object[1].toString() : "";
        String perilipsi = (object[2] != null) ? object[2].toString() : "";
        String periexomena = (object[3] != null) ? object[3].toString() : "";
        String ekdotis = (object[4] != null) ? object[4].toString() : "";
        String epitropi = (object[5] != null) ? object[5].toString() : "";
        String ekdosi_manager = (object[6] != null) ? object[6].toString() : "";
        String gram_ekdosi = (object[7] != null) ? object[7].toString() : "";
        String finalDocPath = (object[8] != null) ? object[8].toString() : "";

        StringWriter stringWriter = new StringWriter();

        XMLOutputFactory xMLOutputFactory = XMLOutputFactory.newInstance();
        XMLStreamWriter xMLStreamWriter;

        try {

            xMLStreamWriter = xMLOutputFactory.createXMLStreamWriter(stringWriter);
            //doc start
            xMLStreamWriter.writeStartDocument();
            //start of root element
            xMLStreamWriter.writeStartElement("root");

            xMLStreamWriter.writeAttribute("available-locales", "en_US");
            xMLStreamWriter.writeAttribute("default-locales", "en_US");

            //start of mag_image
            xMLStreamWriter.writeStartElement("dynamic-element");
            xMLStreamWriter.writeAttribute("name", "mag_image");
            xMLStreamWriter.writeAttribute("type", "document_library");

            xMLStreamWriter.writeAttribute("index-type", "keyword");
            xMLStreamWriter.writeAttribute("index", "0");
            //start of mag_image content
            xMLStreamWriter.writeStartElement("dynamic-content");
            xMLStreamWriter.writeAttribute("language-id", "en_US");
            xMLStreamWriter.writeCData(finalImagePath);
            //end of mag_image content
            xMLStreamWriter.writeEndElement();
            //end of mag_image element
            xMLStreamWriter.writeEndElement();

            //start of text_box Perilipsi element
            xMLStreamWriter.writeStartElement("dynamic-element");
            xMLStreamWriter.writeAttribute("name", "Perilipsi");
            xMLStreamWriter.writeAttribute("type", "text_box");
            xMLStreamWriter.writeAttribute("index-type", "keyword");
            xMLStreamWriter.writeAttribute("index", "0");
            //start of perilipsi content
            xMLStreamWriter.writeStartElement("dynamic-content");
            xMLStreamWriter.writeAttribute("language-id", "en_US");
            xMLStreamWriter.writeCData(perilipsi);

            //end of perilipsi content
            xMLStreamWriter.writeEndElement();
            //end of perilipsi element
            xMLStreamWriter.writeEndElement();

            //start of periexomena text_box element
            xMLStreamWriter.writeStartElement("dynamic-element");
            xMLStreamWriter.writeAttribute("name", "periexomena");
            xMLStreamWriter.writeAttribute("type", "text_box");
            xMLStreamWriter.writeAttribute("index-type", "keyword");
            xMLStreamWriter.writeAttribute("index", "0");
            //start of periexomena content
            xMLStreamWriter.writeStartElement("dynamic-content");
            xMLStreamWriter.writeAttribute("language-id", "en_US");
            xMLStreamWriter.writeCData(periexomena);

            //end of periexomena content
            xMLStreamWriter.writeEndElement();
            //end of periexomena element
            xMLStreamWriter.writeEndElement();

            //start of ekdotis element
            xMLStreamWriter.writeStartElement("dynamic-element");
            xMLStreamWriter.writeAttribute("name", "ekdotis");
            xMLStreamWriter.writeAttribute("type", "text");
            xMLStreamWriter.writeAttribute("index-type", "keyword");
            xMLStreamWriter.writeAttribute("index", "0");
            //start ok ekdotis content
            xMLStreamWriter.writeStartElement("dynamic-content");
            xMLStreamWriter.writeAttribute("language-id", "en_US");
            xMLStreamWriter.writeCData(ekdotis);

            //end of ekdotis content
            xMLStreamWriter.writeEndElement();
            //end of ekdotis element
            xMLStreamWriter.writeEndElement();

            //start of epitropi element
            xMLStreamWriter.writeStartElement("dynamic-element");
            xMLStreamWriter.writeAttribute("name", "epitropi");
            xMLStreamWriter.writeAttribute("type", "text");
            xMLStreamWriter.writeAttribute("index-type", "keyword");
            xMLStreamWriter.writeAttribute("index", "0");
            //start of epitropi content
            xMLStreamWriter.writeStartElement("dynamic-content");
            xMLStreamWriter.writeAttribute("language-id", "en_US");
            xMLStreamWriter.writeCData(epitropi);

            //end of epitropi content
            xMLStreamWriter.writeEndElement();
            //end of epitropi element
            xMLStreamWriter.writeEndElement();

            //start of ekdosi_manager element
            xMLStreamWriter.writeStartElement("dynamic-element");
            xMLStreamWriter.writeAttribute("name", "ekdosi_manager");
            xMLStreamWriter.writeAttribute("type", "text");
            xMLStreamWriter.writeAttribute("index-type", "keyword");
            xMLStreamWriter.writeAttribute("index", "0");
            //start of ekdosi_manager content
            xMLStreamWriter.writeStartElement("dynamic-content");
            xMLStreamWriter.writeAttribute("language-id", "en_US");
            xMLStreamWriter.writeCData(ekdosi_manager);

            //end of ekdosi_manager element
            xMLStreamWriter.writeEndElement();
            //end of ekdosi_manager element
            xMLStreamWriter.writeEndElement();

            //start of gram_ekdosi element    
            xMLStreamWriter.writeStartElement("dynamic-element");
            xMLStreamWriter.writeAttribute("name", "gram_ekdosi");
            xMLStreamWriter.writeAttribute("type", "text");
            xMLStreamWriter.writeAttribute("index-type", "keyword");
            xMLStreamWriter.writeAttribute("index", "0");
            //start of gram_ekdosi content
            xMLStreamWriter.writeStartElement("dynamic-content");
            xMLStreamWriter.writeAttribute("language-id", "en_US");
            xMLStreamWriter.writeCData(gram_ekdosi);

            //end of gram_ekdosi content
            xMLStreamWriter.writeEndElement();
            //end of gram_ekdosi element
            xMLStreamWriter.writeEndElement();

            //start of doc element
            xMLStreamWriter.writeStartElement("dynamic-element");
            xMLStreamWriter.writeAttribute("name", "Doc");
            xMLStreamWriter.writeAttribute("type", "document_library");
            xMLStreamWriter.writeAttribute("index-type", "keyword");
            xMLStreamWriter.writeAttribute("index", "0");

            //start of doc content
            xMLStreamWriter.writeStartElement("dynamic-content");
            xMLStreamWriter.writeAttribute("language-id", "en_US");
            xMLStreamWriter.writeCData(finalDocPath);

            //end of doc content
            xMLStreamWriter.writeEndElement();
            //end of doc element
            xMLStreamWriter.writeEndElement();

            //end of root elemenet
            xMLStreamWriter.writeEndElement();
            //end of xml doc
            xMLStreamWriter.writeEndDocument();

            xMLStreamWriter.flush();
            xMLStreamWriter.close();

            String xmlString = stringWriter.getBuffer().toString();

            stringWriter.close();

        } catch (XMLStreamException | IOException ex) {
            Logger.getLogger(LibraryContentStructure.class.getName()).log(Level.SEVERE, null, ex);
        }

        return stringWriter.toString();
    }

}
