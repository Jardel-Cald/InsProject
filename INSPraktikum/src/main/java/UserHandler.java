
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author calderon
 */
public class UserHandler extends DefaultHandler {
    static final String neueZeile = System.getProperty("line.separator");
    private String letzterTag = "";
    private String stringBuffer = "";
    public Feedback feedback;

    // SAX DefaultHandler Methoden

    @Override
    public void startDocument() throws SAXException {
        feedback = new Feedback();
    }

    @Override
    public void endDocument() throws SAXException {
        XMLLesen.feedbacks.add(feedback);
    }

    // Starttag auslesen
    @Override
    public void startElement(String namespaceURI, String localName,
            String qName, Attributes attrs) throws SAXException {
        letzterTag = qName;

        // Atribute auslesen
        if (attrs != null) {
            for (int i = 0; i < attrs.getLength(); i++) {
                String aName = attrs.getValue(i);
                // System.out.println(aName);

            }
        }
    }

    @Override
    public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
        if (!stringBuffer.isEmpty()) {
            feedback.fuegeWerthinzu(letzterTag, stringBuffer);
            System.out.println(letzterTag + " " + stringBuffer);
        }
        stringBuffer = "";
    }

    @Override
    public void characters(char[] buf, int offset, int len) throws SAXException {
        String s = new String(buf, offset, len);

        if (!s.isBlank()) {
            stringBuffer = stringBuffer + s;
        }

    }

    @Override
    public void error(SAXParseException e) throws SAXException {
        throw new SAXException("XML stimmt mit DTD nicht überein");
    }
}
