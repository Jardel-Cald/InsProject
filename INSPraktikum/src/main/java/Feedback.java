
import java.util.HashMap;
import java.util.Iterator;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author calderon
 */
public class Feedback {
    private HashMap<String, String> eingabeElemente;
    
    public Feedback() {
        eingabeElemente = new HashMap<>();
    }
    
    public void fuegeWerthinzu(String kategorie, String wert) {
        eingabeElemente.put(kategorie.stripTrailing(), wert);
    }
    public String gibWert(String kategorie) {
        return eingabeElemente.getOrDefault(kategorie, "");
    }
    @Override
    public String toString() {
        String ausgabe = "";
        Iterator<String> keyIt = eingabeElemente.keySet().iterator();
        while(keyIt.hasNext()) {
            String key = keyIt.next();
            ausgabe = ausgabe + key + ": " + eingabeElemente.get(key)+ "\n";
        }
        return ausgabe;

    }
    
    
}
