package logger.handlers.handlerImpl;

import logger.Message;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathException;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class HTMLHandler extends AbstractHandler{
    private String fileName;
    private DocumentBuilder documentBuilder;
    private Document document;
    private Element container;
    private Transformer transformer;

    public HTMLHandler(String fileName, String xPath, String format, DateFormat dateFormat)
            throws IOException, ParserConfigurationException, SAXException,
            XPathException, TransformerConfigurationException {
        super(format);
        this.fileName = fileName;

        documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        document = documentBuilder.parse(fileName);
        XPathExpression expression = XPathFactory.newInstance().newXPath().compile(xPath);
        container = (Element) expression.evaluate(document, XPathConstants.NODE);

        //Check correctness of format string
        documentBuilder.parse(new ByteArrayInputStream(format.getBytes()));

        transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
    }

    public HTMLHandler(String fileName, String xPath, String format)
            throws IOException, ParserConfigurationException, SAXException,
            XPathException, TransformerConfigurationException{
        this(fileName, xPath, format, new SimpleDateFormat());
    }

    public HTMLHandler(String fileName, String xPath)
            throws IOException, ParserConfigurationException, SAXException, XPathException,
            TransformerConfigurationException{
        this(fileName, xPath,
                String.format("<pre>[%s] <b>%s</b>&#9;<i>%s</i>:&#9;%s</pre>",
                        Tag.DATE, Tag.SEVERITY, Tag.SENDER, Tag.CONTENT));
    }


    private Element parse(Message msg){
        try {
            String msgXML = format(msg);
            InputStream msgStream = new ByteArrayInputStream(msgXML.getBytes());
            Document tempDoc = documentBuilder.parse(msgStream);
            Element msgElement = tempDoc.getDocumentElement();
            return (Element) document.importNode(msgElement, true);
        }
        catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public void write(Message msg) {
        Element element = parse(msg);
        if (element != null) {
            container.appendChild(parse(msg));
            try {
                transformer.transform(
                        new DOMSource(document),
                        new StreamResult(fileName));
            }
            catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }
}

