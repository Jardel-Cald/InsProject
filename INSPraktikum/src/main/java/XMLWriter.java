
import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;


public class XMLWriter {
    
    private DocumentBuilderFactory dbFactory;
    private DocumentBuilder dBuilder;
    private Document xmlDoc;
    private Element rootElement;
    
    public XMLWriter() {
        try {
            dbFactory = DocumentBuilderFactory.newInstance();
            dbFactory.setValidating(true);
            dBuilder = dbFactory.newDocumentBuilder();
            dBuilder.setErrorHandler(new ErrorHandler() {
                @Override
                public void warning(SAXParseException exception) throws SAXException {
                    exception.printStackTrace();
                }

                @Override
                public void error(SAXParseException exception) throws SAXException {
                    exception.printStackTrace();                
                }

                @Override
                public void fatalError(SAXParseException exception) throws SAXException {
                    exception.printStackTrace(); 
                }
            });
            xmlDoc = dBuilder.newDocument();
            
            rootElement = xmlDoc.createElement("feedbackdatenbank");
            xmlDoc.appendChild(rootElement);
            DOMImplementation domImpl = xmlDoc.getImplementation();
            DocumentType doctype = domImpl.createDocumentType("doctype",
                "feedbackdatenbank",
            "C:\\xampp\\htdocs\\auswertung.dtd");
            
            
            xmlDoc.appendChild(doctype);
        }catch(Exception e) {
            
        }       
    }
    
    public void schreibeFeedback(Feedback feedback) {
        Element feedbackRoot = xmlDoc.createElement("feedback");
        rootElement.appendChild(feedbackRoot);
        Element besucherElement = xmlDoc.createElement("besucher");
        feedbackRoot.appendChild(besucherElement);
        Attr attr = xmlDoc.createAttribute("anrede");
        String anrede = "";
        if(feedback.gibWert("Anrede").equals("Prof")) {
            anrede = "Professor";
        }else if(feedback.gibWert("Anrede").equals("Dr")) {
            anrede = "Doktor";
        } else {
            anrede = feedback.gibWert("Anrede");
        }
        attr.setValue(anrede);
        besucherElement.setAttributeNode(attr);
        
        attr = xmlDoc.createAttribute("vorname");
        attr.setValue(feedback.gibWert("Vorname"));
        besucherElement.setAttributeNode(attr);
        
        attr = xmlDoc.createAttribute("nachname");
        attr.setValue(feedback.gibWert("Nachname"));
        besucherElement.setAttributeNode(attr);
        
        Element alterElement = xmlDoc.createElement("alter");
        besucherElement.appendChild(alterElement);
        alterElement.appendChild(xmlDoc.createTextNode(feedback.gibWert("Alter")));
        
        //Kontakt
        Element kontaktElement = xmlDoc.createElement("kontakt");
        besucherElement.appendChild(kontaktElement);

        attr = xmlDoc.createAttribute("rueckfrage_erlaubt");
        attr.setValue(feedback.gibWert("Zurückmelden").equals("ja")?"true":"false");
        kontaktElement.setAttributeNode(attr);
        
        Element kontaktWertElement;
        if(!feedback.gibWert("E-Mail").isEmpty()) {
            kontaktWertElement = xmlDoc.createElement("emailadresse");
            kontaktElement.appendChild(kontaktWertElement);
            kontaktWertElement.appendChild(xmlDoc.createTextNode(feedback.gibWert("E-Mail")));
        }
        if(!feedback.gibWert("Webseite").isEmpty()) {
            kontaktWertElement = xmlDoc.createElement("website");
            kontaktElement.appendChild(kontaktWertElement);
            kontaktWertElement.appendChild(xmlDoc.createTextNode(feedback.gibWert("Webseite")));
        }
        if(!feedback.gibWert("Telefonnummer").isEmpty()) {
            kontaktWertElement = xmlDoc.createElement("telefonnummer");
            kontaktElement.appendChild(kontaktWertElement);
            kontaktWertElement.appendChild(xmlDoc.createTextNode(feedback.gibWert("Telefonnummer")));
        }
        
        //Bewertung
        Element bewertungElement = xmlDoc.createElement("bewertung");
        feedbackRoot.appendChild(bewertungElement);
        
        attr = xmlDoc.createAttribute("erneuter_besuch");
        attr.setValue(feedback.gibWert("Erneut_besuchen"));
        bewertungElement.setAttributeNode(attr);
        
        attr = xmlDoc.createAttribute("note_inhalt");
        String[] notenNamen = new String[]{"sehr_gut","gut","befriedigend",
            "ausreichend","mangelhaft","ungenuegend"};
        attr.setValue(notenNamen[Integer.parseInt(feedback.gibWert("Inhalt_bewertung"))- 1]);
        
        bewertungElement.setAttributeNode(attr);
        
        attr = xmlDoc.createAttribute("note_aussehen");        
        attr.setValue(feedback.gibWert("Aussehen_bewertung"));
        bewertungElement.setAttributeNode(attr);
        
        if(!feedback.gibWert("Verbesserungen").isEmpty()) {
            Element vorschlagElement = xmlDoc.createElement("vorschlag");
            vorschlagElement.appendChild(xmlDoc.createTextNode(feedback.gibWert("Verbesserungen")));
            bewertungElement.appendChild(vorschlagElement);

        }
        //info
        Element infoElement = xmlDoc.createElement("info");
        feedbackRoot.appendChild(infoElement);
        
        Element infoWertElement = xmlDoc.createElement("email-gesendet");
        infoWertElement.appendChild(xmlDoc.createTextNode(feedback.gibWert("Kopie_erstellen")));
        infoElement.appendChild(infoWertElement);
        
        infoWertElement = xmlDoc.createElement("datum");
        infoWertElement.appendChild(xmlDoc.createTextNode(feedback.gibWert("Datum")));
        infoElement.appendChild(infoWertElement);
        
        infoWertElement = xmlDoc.createElement("uhrzeit");
        infoWertElement.appendChild(xmlDoc.createTextNode(feedback.gibWert("Uhrzeit")));
        infoElement.appendChild(infoWertElement);       
//          <!ELEMENT entwicker_parser (#PCDATA)>
     
    }
    
    public void speicherFeedback() {
        Element entwicklerElement = xmlDoc.createElement("entwicker_parser");
        entwicklerElement.appendChild(xmlDoc.createTextNode("SAX Parser"));
        rootElement.appendChild(entwicklerElement);

        try {
            
            TransformerFactory transformerFactory =
            TransformerFactory.newInstance();
            Transformer transformer;       
            transformer = transformerFactory.newTransformer();       
            DOMSource source = new DOMSource(xmlDoc);
            StreamResult result =
            new StreamResult(new File("C:\\xampp\\htdocs\\Gesammtauswertung.xml"));
            transformer.transform(source, result);
        } catch (Exception ex) {
            System.out.println("fehler");
        }
    }/*
    !ELEMENT feedbackdatenbank (feedback*, entwicker_parser)>
<!ELEMENT feedback (besucher,bewertung,info)>
<!ELEMENT besucher (alter,kontakt)>
<!ATTLIST besucher
anrede (Herr|Frau|Doktor|Professor) #IMPLIED
vorname CDATA #REQUIRED
nachname CDATA #REQUIRED
>
<!ELEMENT kontakt (emailadresse?,website?,telefonnummer?)>
<!ATTLIST kontakt
rueckfrage_erlaubt (true|false) #IMPLIED
>
<!ELEMENT alter (#PCDATA)>
<!ELEMENT emailadresse (#PCDATA)>
<!ELEMENT telefonnummer (#PCDATA)>
<!ELEMENT website (#PCDATA)>   
<!ELEMENT bewertung (vorschlag?)>
<!ATTLIST bewertung
erneuter_besuch (ja|nein) "ja"
    
    
note_inhalt (sehr_gut|gut|befriedigend|ausreichend|mangelhaft|ungenuegend) #IMPLIED
note_aussehen (1|2|3|4|5|6) #IMPLIED
>
<!ELEMENT vorschlag (#PCDATA)>
    
    
<!ELEMENT info (email-gesendet?,datum,uhrzeit)>
<!ELEMENT email-gesendet (#PCDATA)>
<!ELEMENT datum (#PCDATA)>
<!ELEMENT uhrzeit (#PCDATA)>
<!ELEMENT entwicker_parser (#PCDATA)>
    */
    
}
