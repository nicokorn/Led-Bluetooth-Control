/**
 *  Squares
 *  Multithreading Demo
 *  
 *  Zwei Threads starten, die (abwechselnd) eine neue Position bestimmen 
 *  und ein Rechteck zeichnen. Für die Position und das Zeichnen des
 *  Rechtecks wird auf ein gemeinsames Objekt count zugegriffen.
 *  
 *  @version 14-MAY-2013
 */
import java.applet.Applet;
import java.awt.*;

public class Squares extends Applet 
{

    private Counter1 counter1;
    private Counter2 counter2;

    /**
     *  Applet initialisieren: Die zwei Threads starten 
     */
    public void init ()
    {
        Graphics g = getGraphics();
        
        // gemeinsames Objekt count
        SharedNumber count = new SharedNumber(g);
        
        // zwei Threads erzeugen und starten
        counter1 = new Counter1(count);
        counter2 = new Counter2(count);
        counter1.start();
        
        // Verzoegerung vor dem Start von counter2
        /**/
        try{
            Thread.sleep(20);
        }
        catch (InterruptedException e){;}
        /**/
        counter2.start();
    }

    /**
     *  Legende ausgeben
     */
    public void paint (Graphics g)
    {
        g.setColor(Color.red);
        g.fillRect(200,10,20,20);
        g.drawString("Counter1", 240,25);
        
        g.setColor(Color.blue);
        g.fillRect(200,40,20,20);
        g.drawString("Counter2", 240,55);
    }
}