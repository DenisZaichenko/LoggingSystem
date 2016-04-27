package logger.config;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;

public class XMLParser {
    public static Object parseObject(Element node) throws Exception {
        String textContent = node.getTextContent();
        switch (node.getNodeName()){
            case "string":  return textContent;
            case "boolean": return Boolean.parseBoolean(textContent);
            case "byte":    return Byte.parseByte(textContent);
            case "short":   return Short.parseShort(textContent);
            case "int":     return Integer.parseInt(textContent);
            case "long":    return Long.parseLong(textContent);
            case "float":   return Float.parseFloat(textContent);
            case "double":  return Double.parseDouble(textContent);
            case "object":
                Object [] params = readParams(node);
                Class c = getClass(node);
                return new Object();//TODO Допилить создание через рефлексию
            default: return null;
        }
    }

    public static Object[] readParams(Element node) throws Exception{
        ArrayList<Object> params = new ArrayList<Object>();
        NodeList child = node.getChildNodes();
        for (int i = 0; i < child.getLength(); i++)
            if (child.item(i).getNodeType() == Node.ELEMENT_NODE) {
                Element e = (Element) child.item(i);
                Object o = parseObject(e);
                params.add(o);
            }

        return params.toArray();
    }

    public static Class getClass(Element node) throws ClassNotFoundException {
        String className = node.getAttributes().getNamedItem("class").getTextContent();
        return Class.forName(className);
    }

    public static String getName(Element node) {
        return node.getAttributes().getNamedItem("name").getTextContent();
    }
}

