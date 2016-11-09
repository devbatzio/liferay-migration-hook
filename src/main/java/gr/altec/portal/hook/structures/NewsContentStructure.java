/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.altec.portal.hook.structures;

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
public class NewsContentStructure implements IContentStructure {

    @Override
    public String generateContentStructure(Object... object) {

       
        String content = (object[0] != null) ? object[0].toString() : "";
        String docPath = "";
        String imagePath = (object[1] != null) ? object[1].toString() : "";

        StringWriter stringWriter = new StringWriter();

        XMLOutputFactory xMLOutputFactory = XMLOutputFactory.newInstance();
        XMLStreamWriter xMLStreamWriter;

        try {
            xMLStreamWriter = xMLOutputFactory.createXMLStreamWriter(stringWriter);
            //doc start
            xMLStreamWriter.writeStartDocument();
            //start of root element
            xMLStreamWriter.writeStartElement("root");

            //start of news_content element
            xMLStreamWriter.writeStartElement("dynamic-element");
            xMLStreamWriter.writeAttribute("name", "News_content");
            xMLStreamWriter.writeAttribute("type", "text_area");
            xMLStreamWriter.writeAttribute("index-type", "keyword");
            xMLStreamWriter.writeAttribute("index", "0");

            //start of news_content content
            xMLStreamWriter.writeStartElement("dynamic-content");
            xMLStreamWriter.writeAttribute("language-id", "en_US");
            xMLStreamWriter.writeCData(content);

            //end of news_content content
            xMLStreamWriter.writeEndElement();
            //end of news_content element
            xMLStreamWriter.writeEndElement();

            //start of document_news element
            xMLStreamWriter.writeStartElement("dynamic-element");
            xMLStreamWriter.writeAttribute("name", "document_news");
            xMLStreamWriter.writeAttribute("type", "document_library");
            xMLStreamWriter.writeAttribute("index-type", "keyword");
            xMLStreamWriter.writeAttribute("index", "0");

            //start of document_news content
            xMLStreamWriter.writeStartElement("dynamic-content");
            xMLStreamWriter.writeAttribute("language-id", "en_US");
            xMLStreamWriter.writeCData(docPath);

            //end of document_news content
            xMLStreamWriter.writeEndElement();
            //end of document_news element
            xMLStreamWriter.writeEndElement();

            //start of image_news element
            xMLStreamWriter.writeStartElement("dynamic-element");
            xMLStreamWriter.writeAttribute("name", "Image_News");
            xMLStreamWriter.writeAttribute("type", "document_library");
            xMLStreamWriter.writeAttribute("index-type", "keyword");
            xMLStreamWriter.writeAttribute("index", "0");

            //start of image_news content
            xMLStreamWriter.writeStartElement("dynamic-content");
            xMLStreamWriter.writeAttribute("language-id", "en_US");
            xMLStreamWriter.writeCData(imagePath);
            //end of image_news content
            xMLStreamWriter.writeEndDocument();
            //end of image_news element
            xMLStreamWriter.writeEndElement();

            //end of root element
            xMLStreamWriter.writeEndElement();
            //end of doc
            xMLStreamWriter.writeEndDocument();
            
            xMLStreamWriter.flush();
            xMLStreamWriter.close();

            stringWriter.close();
        }
        catch (XMLStreamException | IOException e) {
        }

        return stringWriter.toString();
    }

}
