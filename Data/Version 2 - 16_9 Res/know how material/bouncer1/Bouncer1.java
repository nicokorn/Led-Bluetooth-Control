/**
 *  Bouncer1
 *  Demo fuer Threads - in dieser Variante ohne Threads lässt sich
 *  die Animation nicht mit dem Button beenden. Waehrend die Schleife
 *  aktiv ist, reagiert der Button nicht.
 *  
 *  @version 14-MAY-2013
 */
import java.awt.*;
import java.applet.Applet;
import java.awt.event.*;

public class Bouncer1 extends Applet implements ActionListener 
{
    
    private Button start, stop;
    private Ball ball;

    /**
     *  Applet initialisieren: Zwei Buttons und ihre Listener anlegen
     */
    public void init () 
    {
        start = new Button("Start");
        add(start);
        start.addActionListener(this);

        stop = new Button("Stop");
        add(stop);
        stop.addActionListener(this);
    }

    /**
     *  Button-Ereignisse behandeln
     */
    public void actionPerformed (ActionEvent event) 
    {
        // Start-Button
        if (event.getSource() == start) {
            Graphics g = getGraphics();
            ball = new Ball(g, getBackground());
            // Ball anzeigen und Animation starten
            ball.display();
        }
        // Stop-Button
        else if (event.getSource() == stop) {
            ball.anhalten();
        }
    }
}