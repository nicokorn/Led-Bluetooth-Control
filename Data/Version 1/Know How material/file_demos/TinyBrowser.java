/**
 *  TinyBrowser
 *  Dieses Beispiel zeigt, dass auch ein Stream ueber das Netzwerk geoeffnet und von
 *  diesem gelesen werden kann wie aus einer Datei. Die Konsolenapplikation fragt 
 *  nach der URL eines Webservers, verbindet zu diesem und zeigt den Inhalt der auf
 *  dem Server gespeicherten Datei an.
 *  
 *  @author huhp, bkrt 
 *  @version 01-MAY-2013
 */
import java.io.*;
import java.net.*;

public class TinyBrowser 
{

    private BufferedReader inStream, keyboard;

    /**
     *  main-Methode zum Starten des Programms
     */
    public static void main (String [] args) 
    {
        TinyBrowser aBrowser = new TinyBrowser();
        aBrowser.fetch();
    }

    /**
     *  Verbindung zum Server herstellen und Daten vom Server laden
     */
    private void fetch () 
    {
        String urlString = "";
        String line;
        keyboard = new BufferedReader(new InputStreamReader(System.in),1);
        
        try {
            // nach URL fragen
            urlString = prompt ("Type a URL address (e.g. http://java.sun.com/): ");
            
            // Verbindung zur URL herstellen und Reader erzeugen
            URL urlAddress = new URL(urlString);
            URLConnection link = urlAddress.openConnection();
            inStream = new BufferedReader(new InputStreamReader(link.getInputStream()));
            
            // Zeile um Zeile vom Netzwerk lesen und ausgeben
            while ((line = inStream.readLine()) != null) {
                System.out.print(line);
            }
        } 
        catch (MalformedURLException e) {
            System.err.println(urlString + e.toString());
            System.exit(2);
        }
        catch (IOException e) {
            System.err.println("Error in accessing URL: "+
                e.toString());
            System.exit(1);
        }
    } 

    /**
     *  Eingabeaufforderung ausgeben und Zeile einlesen
     *  
     *  @param  message     Eingabeaufforderung
     *  @return             eingelesener Text
     */
    private String prompt (String message) 
    {
        String reply = "";
        try {
            System.out.print(message);
            System.out.flush();
            reply = keyboard.readLine();
        }
        catch (IOException e) {
            System.out.println(" Keyboard input " + e.toString() );
            System.exit(2);
        }
        return reply;
    }
    
}