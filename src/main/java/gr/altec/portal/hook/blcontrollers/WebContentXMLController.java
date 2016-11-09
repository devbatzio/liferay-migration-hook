/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.altec.portal.hook.blcontrollers;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 *
 * @author bats-pc
 */
public class WebContentXMLController {

    private StringWriter stringWriter;
    private XMLOutputFactory xmlOutputFactory;
    private XMLStreamWriter xmlStreamWriter;

    public void init() {
        try {
            stringWriter = new StringWriter();
            xmlOutputFactory = XMLOutputFactory.newInstance();
            xmlStreamWriter = xmlOutputFactory.createXMLStreamWriter(stringWriter);
        } catch (XMLStreamException ex) {
            Logger.getLogger(WebContentXMLController.class.getName()).log(Level.SEVERE, null, ex);
            destroy();
        }

    }

    public void destroy() {
        try {
            if (stringWriter != null) {
                stringWriter.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(WebContentXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            if (xmlStreamWriter != null) {
                xmlStreamWriter.close();
            }
        } catch (XMLStreamException ex) {
            Logger.getLogger(WebContentXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void writeStartDocument() throws XMLStreamException {
        xmlStreamWriter.writeStartDocument();
    }

    public void writeEndDocument() throws XMLStreamException {
        xmlStreamWriter.writeEndDocument();
    }

    public String writeElement(String elemName, Map<String, String> attrs, String cdata, boolean closeElem) throws XMLStreamException {
        String elemContent = null;

        xmlStreamWriter.writeStartDocument();
        xmlStreamWriter.writeStartElement(elemName);
        for (Map.Entry<String, String> entrySet : attrs.entrySet()) {
            String key = entrySet.getKey();
            String value = entrySet.getValue();
            xmlStreamWriter.writeAttribute(key, value);
        }

        xmlStreamWriter.writeEndElement();

        return elemContent;
    }

}
