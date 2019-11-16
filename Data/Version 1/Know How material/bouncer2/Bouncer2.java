/**
 *  Bouncer2
 *  Demo fuer Threads - in dieser Variante mit Threads kann die 
 *  Animation mit dem Button beendet werden. Da die Schleife in
 *  einem separaten Thread laeuft, werden Button-Ereignisse auch
 *  waehrend der Animation behandelt.
 *  
 *  @version 14-MAY-2013
 */
import java.awt.*;
import java.applet.Applet;
import java.awt.event.*;

public class Bouncer2 extends Applet implements ActionListener 
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
            // Thread mit Animation starten
            ball.start();
        }
        // Stop-Button
        if (event.getSource() == stop) {
            ball.anhalten();
        }
    }
}