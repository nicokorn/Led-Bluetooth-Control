/**
 *  Counter1
 *  Multithreading Demo
 *  
 *  Klasse fuer den Thread, der die roten Quadrate erzeugt (im Prinzip
 *  koennten die beiden Threads auch aus der geichen Klasse erzeugt
 *  werden).
 *  
 *  @version 14-MAY-2013
 */
import java.awt.*;

class Counter1 extends Thread 
{
    
	// gemeinsam benutztes Zaehlerobjekt
    private SharedNumber count;

    /**
     *  Konstruktor: Zaehlerobjekt uebernehmen
     */
    public Counter1 (SharedNumber count)
    {
        this.count = count;
    }

    /**
     *  run-Methode fuer den Thread: In einer Schleife 50 mal nach je 
     *  einer kurzen Verzoegerung das counter-Objekt anweisen, den 
     *  Zaehler zu inkrementieren und ein neues Quadrat zu zeichnen 
     */
    public void run () 
    {
        for (int i = 1; i <= 50; i++) {
            try{
                Thread.sleep(40);
            }
            catch (InterruptedException e){;}
            count.increment(Color.red);
        }
    }
}