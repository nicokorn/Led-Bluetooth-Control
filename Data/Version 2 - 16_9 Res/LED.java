import java.awt.Color;
import java.awt.Graphics;
import java.lang.Object;
import javax.swing.*;
import java.awt.*;


/**
 * Diese Klasse beinhaltet einen einzelne LED
 */
public class LED
{
    //varaiablen
    private static Graphics2D g2d;

    /**
     * Constructor for objects of class LED
     */
    public LED(int x, int y, int radius, int color, Graphics g)
    {
        // variablen instanzieren
        g2d = (Graphics2D) g;
        
        //LED instanzieren
        g.setColor(new Color(color));
        g.fillOval(x, y, radius, radius);
    }
}
