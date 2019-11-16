/**
 * Applet WeintankV1: Demo fuer Model-View-Controller-Einfuehrung
 * Variante 1: Separate Model-Klasse Tank
 * 
 * @author huhp
 * @author bkrt (etwas angepasst)
 * @version 14-APR-2011
 */
import java.awt.*;
import java.applet.*;
import java.awt.event.*;

public class WeintankV1 extends Applet implements ActionListener, AdjustmentListener 
{
    private Button plusB, minusB, fillB, emptyB;
    private TextField standT;
    private Scrollbar adjustS;
    private int visibleScrollbar = 20;
    private int fuellstand = 60;        // aktueller Fuellstand in Liter
    private int maxFuellstand = 150;    // max. Fuellstand in Liter
    
    // Referenz aufs Model
    private Tank tank;
    
    /**
     *  Initialisierung des Applet:
     *  GUI anlegen und Tank-Objekt instanzieren
     */
    public void init () 
    {
        createGUI();
        tank = new Tank(fuellstand, maxFuellstand);
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
        standT.setText(""+fuellstand);
        
        fillB = new Button("Fuellen");
        add(fillB);
        fillB.addActionListener(this);
        
        emptyB = new Button("Leeren");
        add(emptyB);
        emptyB.addActionListener(this);
        
        adjustS = new Scrollbar(Scrollbar.HORIZONTAL, fuellstand, 0,
            visibleScrollbar, maxFuellstand+visibleScrollbar+1);
        add(adjustS);
        adjustS.setPreferredSize(new Dimension(150, 20));
        adjustS.addAdjustmentListener(this);
    }

    /**
     *  Info ausgeben und Tank zeichnen:
     *  Fuellstand wird nun vom Model geholt
     */
    public void paint (Graphics g) 
    {
        int breite = 50, hoehe = 150;
        g.drawString("Max. Fuellstand in Liter = " + tank.getMaxFuellstand(), 50, 100);
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
        fuellhoehe = hoehe*tank.getFuellstand()/tank.getMaxFuellstand();
        g.fillRect(xPos, yPos+(hoehe-fuellhoehe), breite, fuellhoehe);
    }

    /**
     *  Ereignisbehandlung: Buttons
     */
    public void actionPerformed (ActionEvent e)
    {
        if (e.getSource() == fillB){
            tank.fuellen();
        }
        else if (e.getSource() == emptyB){
            fuellstand = tank.leeren();
        }
        else if (e.getSource() == plusB){
            tank.fuellstandAendernUm(1);
        }
        else if (e.getSource() == minusB){
            tank.fuellstandAendernUm(-1);
        }
        else if (e.getSource() == standT){
            int stand = Integer.parseInt(standT.getText());
            tank.setFuellstand(stand);
        }
        standT.setText("" + tank.getFuellstand()); // Textzeile aufdatieren
        adjustS.setValue(tank.getFuellstand() + visibleScrollbar);
        repaint();
    }

    /**
     *  Ereignisbehandlung: Scrollbar
     */
    public void adjustmentValueChanged (AdjustmentEvent e)
    {
        fuellstand = e.getValue()-visibleScrollbar;
        tank.setFuellstand(fuellstand);
        standT.setText("" + fuellstand); // Textzeile aufdatieren
        repaint();
    }
}
