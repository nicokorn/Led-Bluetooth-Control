/**
 *  Finder
 *  Konsolenapplikation, die einen ueber die Tastatur eingegebenen Text in einer 
 *  Datei sucht. Der Dateiname wird beim Start der Applikation als Argument
 *  uebergeben.
 *  
 *  @author huhp, bkrt 
 *  @version 01-MAY-2013
 */
import java.io.*;

public class Finder 
{

    private String line1, line2, line3;
    private BufferedReader keyboard, inStream;

    /**
     *  main-Methode zum Starten des Programms
     */
    public static void main (String [] args) 
    {
        Finder aFind = new Finder();
        aFind.doSearch(args[0]);
    }

    /**
     *  Nach einem bestimmten String in der Datei suchen und wenn erfolgreich
     *  Zeile mit Zeile davor und danach anzeigen
     *  
     *  @param  fileName    Datei in der gesucht werden soll
     */
    private void doSearch (String fileName) 
    {
        // Lesen vom Standard Input
        keyboard = new BufferedReader(new InputStreamReader(System.in), 1);
        String wanted = prompt("Type string to find:");
        line1 = "";
        line2 = "";
        try {
            inStream = new BufferedReader(new FileReader(fileName));
            while ((line3 = inStream.readLine()) != null) {
                if ( line2.indexOf(wanted) >= 0 ) { 
                    displayLine(); 
                }
                // naechste Dreiergruppe
                line1 = line2;
                line2 = line3;
                // und line3 von der Datei laden...
            }
            // letzte Zeile testen
            line3 = "";  // null mit "" ersetzen
            if (line2.indexOf(wanted) >= 0) {
                displayLine();
            }
            inStream.close();
        }
        catch (IOException e) {
            System.err.println("Error in Finder: " + e.toString());
            System.exit(1);
        }
    }

    /**
     *  Die drei Zeilen mit Suchergebnis in der mittleren Zeile ausgeben
     */
    private void displayLine () 
    {
        System.out.println("<<------------ context:");
        System.out.println(line1);
        System.out.println(line2);
        System.out.println(line3);
        System.out.println(" ------------->>");
        System.out.println("");
    }

    /**
     *  Eingabeaufforderung ausgeben und Zeile einlesen
     *  
     *  @param  message     Eingabeaufforderung
     *  @return             eingelesener Text
     */
    private String prompt(String message) {
        String reply = "";
        try {
            System.out.print(message);
            System.out.flush();
            reply = keyboard.readLine();
        }
        catch (IOException e) {
            System.out.println("Keyboard input " + e.toString() );
            System.exit(2);
        }
        return reply;
    }
    
}