/**
 *  SharedNumber
 *  Multithreading Demo
 *  
 *  Gemeinsame Ressource fuer die beiden Threads
 *  
 *  @version 14-MAY-2013
 */
import java.awt.*;

class SharedNumber 
{
	// Zaehler
    private int n = 0;
    private Graphics g;

    /**
     *  Konstruktor: Graphics-Objekt uebernehmen
     */
    public SharedNumber (Graphics g) 
    {
        this.g = g;
    }

    /**
     *  Zaehler erhoehen und Quadrat in der als Parameter angegebenen
     *  Farbe zeichnen
     */
    public /*/ synchronized /**/ void increment (Color col) 
    {
        n = n + 10;
        /*/
        try{ // Pause, damit Thread sicher
             // hier unterbrochen wird
             // und das Problem auftritt
            Thread.sleep(1);
        }
        catch (InterruptedException e){;}
        /**/
        g.setColor(col);
        g.fillRect(n, n, 10, 10);
    }
}