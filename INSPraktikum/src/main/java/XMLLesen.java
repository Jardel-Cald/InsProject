
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author calderon
 */
public class XMLLesen {
    public static ArrayList<Feedback> feedbacks= new ArrayList<>();
    
    public static void main(String[] args) {

        // SAX-EventHandler erstellen
        DefaultHandler handler = new UserHandler();

        // Inhalt mit dem Default-Parser parsen
        SAXParser saxParser;
        for (File file : new File("C:\\xampp\\htdocs\\Auswertungen").listFiles()) {
            try {
                if(!file.getName().equals("Feedback.dtd")) {

                    System.out.println(file.getName());
                    SAXParserFactory saxFactory =SAXParserFactory.newInstance();
                    saxFactory.setValidating(true);
                    
                    saxParser = saxFactory.newSAXParser();
                    saxParser.parse(file, handler);
                    
                }

            } catch (ParserConfigurationException pe) {
                pe.printStackTrace();
            } catch (SAXException se) {
                System.out.println(se.getMessage());
            } catch (IOException ie) {
                ie.printStackTrace();
            }
        }
        XMLWriter xmlW = new XMLWriter();

        for(int i = 0; i < feedbacks.size(); i++) {

            xmlW.schreibeFeedback(feedbacks.get(i));
        }
        xmlW.speicherFeedback();
        
        
        //Durchnschnittsnote:
        System.out.println("Durchschnittsnote: " + feedbacks.stream()
                .mapToInt(a-> Integer.parseInt(a.gibWert("Aussehen_bewertung")))
                .sum()/(float) feedbacks.size());
        System.out.println("Wieder besuchen wollen diese Seite "
        + feedbacks.stream()
                .filter(a-> a.gibWert("Erneut_besuchen").equals("ja"))
                .count()/(float) feedbacks.size() * 100 + "%");
        System.out.println("Es möchten "
        + feedbacks.stream()
                .filter(a-> a.gibWert("Kopie_erstellen").equals("ja"))
                .count() + " eine Kopie an die Mailbox gesendet bekommen");
    }
    
}
