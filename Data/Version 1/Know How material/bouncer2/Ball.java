/**
 *  Ball
 *  Demo fuer Threads - Variante mit Threads
 *  
 *  @version 14-MAY-2013
 */
import java.awt.*;

class Ball extends Thread 
{

    private Graphics g;
    private Color bgColour;

    // Ball-Daten
    private int xChange = 14;
    private int yChange = 4;
    private int diameter= 20;
    
    // Spielfeld
    private int rectLeftX = 50, rectRightX = 350;
    private int rectTopY = 50, rectBottomY = 350;
    
    // Aktuelle Position
    private int x = rectLeftX + xChange;
    private int y = rectTopY + yChange;

    // Abbruch?
    private boolean weiterSo = true;

    /**
     *  Konstruktor
     */
    public Ball (Graphics graphics, Color bg) 
    {
        g = graphics;
        bgColour = bg;
    }

    /**
     *  run-Methode fuer den Thread
     */
    public void run () 
    {
        g.drawRect(rectLeftX, rectTopY, rectRightX-rectLeftX, rectBottomY-rectTopY);

        //  Schleife fuer die Animation des Balls
        while (weiterSo) {
            
            // auf alter Position loeschen
            g.setColor(bgColour);
            g.fillOval (x, y, diameter, diameter);
            
            // neue Position bestimmen
            if (x + xChange <= rectLeftX){
                xChange = -xChange;
            }
            if (x + diameter + xChange >= rectRightX){
                xChange = -xChange;
            }
            if (y + yChange <= rectTopY){
                yChange = -yChange;
            }
            if (y + diameter + yChange >= rectBottomY){
                yChange = -yChange;
            }
            x = x + xChange;
            y = y + yChange;
            
            // Ball auf neuer Position zeichnen
            g.setColor(Color.red);
            g.fillOval (x, y, diameter, diameter);
            
            // kurze Pause
            try {
                Thread.sleep(50);
            }
            catch (InterruptedException e) {
                g.drawString("sleep exception", 20, 20);
            }
        } // end while
    } // end run

    /**
     *  Animation anhalten
     */
    public void anhalten ()
    {
        weiterSo = false;
    }
    
} // end Ball
