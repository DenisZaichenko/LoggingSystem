package logger.config;

import logger.LogManager;
import logger.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.io.IOException;

public class ConfigReader {
    private static final String XSD_FILE = "configSchema.xsd";
    private final String XML_FILE;
    private final LogManager logManager;
    private Document document;

    public static boolean load(String path, LogManager manager){
        return new ConfigReader(path, manager).loadConfig();
    }

    private ConfigReader(String xmlFile, LogManager logManager){
        XML_FILE = xmlFile;
        this.logManager = logManager;
    }

    private boolean loadConfig(){
        try {
            if (isValidConfig()) init();
            else return false;
        }
        catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
        readElements();
        return true;
    }

    private boolean isValidConfig() throws IOException {
        try {
            SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = sf.newSchema(new File(XSD_FILE));
            schema.newValidator().validate(new StreamSource(XML_FILE));
            return true;
        }
        catch (SAXException ex){
            return false;
        }
    }

    private void init() throws Exception {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        document = db.parse(XML_FILE);
    }

    private void readElements() {
        NodeList handlers = document.getElementsByTagName("handler");
        for (int i = 0; i < handlers.getLength(); i++)
            readHandler((Element) handlers.item(i));

        NodeList filters = document.getElementsByTagName("filter");
        for (int i = 0; i < filters.getLength(); i++)
            readFilter((Element) filters.item(i));

        Element rootLogger = (Element) document.getElementsByTagName("rootLogger").item(0);
        readLogger(logManager.getRootLogger(), rootLogger);

        NodeList loggers = document.getElementsByTagName("logger");
        for (int i = 0; i < loggers.getLength(); i++) {
            readLogger((Element) loggers.item(i));
        }
    }

    private void readHandler(Element handlerNode) {
        throw new UnsupportedOperationException("Not supported yet");
    }

    private void readFilter(Element filterNode) {
        throw new UnsupportedOperationException("Not supported yet");
    }

    private void readLogger(Logger logger, Element node){
        throw new UnsupportedOperationException("Not supported yet");
    }

    private void readLogger(Element node){
        throw new UnsupportedOperationException("Not supported yet");
    }
}
