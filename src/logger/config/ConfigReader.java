package logger.config;

import logger.LogManager;
import logger.Logger;
import logger.filters.Filter;
import logger.handlers.Handler;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class ConfigReader {
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
            init();
        }
        catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
        readElements();
        return true;
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
        try {
            String name = XMLParser.getName(handlerNode);
            Object[] params = XMLParser.readParams(handlerNode);
            Class handlerClass = XMLParser.getClass(handlerNode);
            Handler handler = ReflexiveFactory.<Handler>createObject(handlerClass, params);
            logManager.addHandler(name, handler);
        }
        catch (Exception ex){
            System.err.println("Can not read handler " + XMLParser.getName(handlerNode));
            System.err.println("Cause: ");
            ex.printStackTrace();
        }
    }

    private void readFilter(Element filterNode) {
        try {
        String name = XMLParser.getName(filterNode);
        Object[] params = XMLParser.readParams(filterNode);
        Class filterClass = XMLParser.getClass(filterNode);
        Filter filter = ReflexiveFactory.<Filter>createObject(filterClass, params);
        logManager.addFilter(name, filter);
        }
        catch (Exception ex){
            System.err.println("Can not read filter: " + XMLParser.getName(filterNode));
            System.err.println("Cause: ");
            ex.printStackTrace();
        }
    }

    private void readLogger(Logger logger, Element node){
        NodeList handlerNameList = node.getElementsByTagName("handlerName");
        for (int i = 0; i < handlerNameList.getLength(); i++) {
            String handlerName = handlerNameList.item(i).getTextContent();
            Handler handler = logManager.getHandler(handlerName);
            logger.addHandler(handler);
        }
        Node filterNode = node.getElementsByTagName("filterName").item(0);
        if (filterNode != null) {
            String filterName = filterNode.getTextContent();
            Filter filter = logManager.getFilter(filterName);
            logger.setFilter(filter);
        }
    }

    private void readLogger(Element node){
        String name = XMLParser.getName(node);
        Logger logger = logManager.getLogger(name);
        readLogger(logger, node);
    }
}
