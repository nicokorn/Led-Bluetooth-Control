/**
 * Applet WeintankV0: Demo fuer Model-View-Controller-Einfuehrung
 * Variante 0: Alles in einer Klasse
 * 
 * @author huhp
 * @author bkrt (etwas angepasst)
 * @version 14-APR-2011
 */
import java.awt.*;
import java.applet.*;
import java.awt.event.*;

public class WeintankV0 extends Applet implements ActionListener, AdjustmentListener 
{
    private Button plusB, minusB, fillB, emptyB;
    private TextField standT;
    private Scrollbar adjustS;
    private int visibleScrollbar = 20;
    private int fuellStand = 60;        // aktueller Fuellstand in Liter
    private int maxFuellstand = 150;    // max. Fuellstand in Liter

    /**
     *  Initialisierung des Applet
     */
    public void init () 
    {
        createGUI();
    }

    /**
     *  Grafische Oberflaeche erzeugen
     */
    private void createGUI ()
    {
        plusB = new Button(" + 1l");
        add(plusB);
        plusB.addActionListener(this);

        minusB = new Button(" - 1l");
        add(minusB);
        minusB.addActionListener(this);

        Label lab = new Label(" Fuellstand in Liter ");
        add(lab);
        standT = new TextField(5);
        add(standT);
        standT.addActionListener(this);
        standT.setText(""+fuellStand);

        fillB = new Button("Fuellen");
        add(fillB);
        fillB.addActionListener(this);

        emptyB = new Button("Leeren");
        add(emptyB);
        emptyB.addActionListener(this);

        adjustS = new Scrollbar(Scrollbar.HORIZONTAL, fuellStand, 0,
            visibleScrollbar, maxFuellstand+visibleScrollbar+1);
        add(adjustS);
        adjustS.setPreferredSize(new Dimension(150, 20));
        adjustS.addAdjustmentListener(this);
    }

    /**
     *  Info ausgeben und Tank zeichnen
     */
    public void paint (Graphics g) 
    {
        int breite = 50, hoehe = 150;
        g.drawString("Max. Fuellstand in Liter = " + maxFuellstand, 50, 100);
        zeichneTank(g, getWidth()/2-breite/2, 100, breite, hoehe);
    }

    /**
     *  Zeichnet einen Tank mit breite 50 und hoehe 150 an der
     *  Position xPos, yPos
     *  Der max. Fuellstand ist 'maxStand',
     *  Der aktuelle Fuellstand 'fuellStand'
     */
    private void zeichneTank (Graphics g, int xPos, int yPos, int breite, int hoehe)
    {
        int fuellhoehe = 0;
        g.setColor(Color.black);
        g.drawRect(xPos, yPos, breite, hoehe);
        g.setColor(Color.red);
        fuellhoehe = hoehe*fuellStand/maxFuellstand;
        g.fillRect(xPos, yPos+(hoehe-fuellhoehe), breite, fuellhoehe);
    }

    /**
     *  Ereignisbehandlung: Buttons
     */
    public void actionPerformed (ActionEvent e)
    {
        if (e.getSource() == fillB){
            setzeFuellstand(maxFuellstand);
        }
        else if (e.getSource() == emptyB){
            setzeFuellstand(0);
        }
        else if (e.getSource() == plusB && fuellStand < maxFuellstand){
            erhoeheFuellstand(1);
        }
        else if (e.getSource() == minusB && fuellStand > 0){
            erhoeheFuellstand(-1);
        }
        else if (e.getSource() == standT){
            int stand = Integer.parseInt(standT.getText());
            if (stand >= 0 && stand <= maxFuellstand){
                fuellStand = stand;
                setzeFuellstand(stand);
            }
        }
        standT.setText("" + fuellStand); // Textzeile aufdatieren
        adjustS.setValue(fuellStand + visibleScrollbar);
        repaint();
    }

    /**
     *  Ereignisbehandlung: Scrollbar
     */
    public void adjustmentValueChanged (AdjustmentEvent e)
    {
        fuellStand = e.getValue()-visibleScrollbar;
        standT.setText("" + fuellStand); // Textzeile aufdatieren
        repaint();
    }

    /**
     *  Hilfsmethode zum Erhoehen des Fuellstands
     */
    private void erhoeheFuellstand (int erhoehung)
    {
        fuellStand = fuellStand + erhoehung;
    }

    /**
     *  Hilfsmethode zum Setzen des Fuellstands
     */
    private void setzeFuellstand (int fuellStand)
    {
        this.fuellStand = fuellStand;
    }
}