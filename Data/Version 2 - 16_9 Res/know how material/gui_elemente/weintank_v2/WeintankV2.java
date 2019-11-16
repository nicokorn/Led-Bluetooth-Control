/**
 * Applet WeintankV2: Controller (und teilweise View) im MVC-Beispiel
 * 
 * @author huhp
 * @author bkrt (etwas angepasst)
 * @version 14-APR-2011
 */
import java.awt.*;
import java.applet.*;
import java.awt.event.*;
import java.beans.*;

public class WeintankV2 extends Applet implements ActionListener, AdjustmentListener, PropertyChangeListener 
{
    /**
     * Instanzenvariablen
     */
    private Button plusB, minusB, fillB, emptyB, startConsB;
    private TextField standT;
    private Scrollbar adjustS;

    private int visibleScrollbar = 20;
    private int fuellstand = 60;        // Anfangs-Fuellstand in Liter
    private int maxFuellstand = 150;    // max. Fuellstand in Liter

    private Tank tank;
    private TankView tankView;
    private Consumer cons; 
    
    /**
     * Applet initialisieren
     */
    public void init () 
    {
        // GUI-Elemente in separater Methode erzeugen
        createGUI();
        // Model anlegen
        tank = new Tank(fuellstand, maxFuellstand);
        // View anlegen
        tankView = new TankView(tank, 50, 150);
        add(tankView);
        // Beim Tank als PropertyChangeListener anmelden, damit Textfeld 
        // und Scrollbar bei Aenderungen aktualisiert werden
        tank.addPropertyChangeListener(this);
    }

    /**
     *  GUI anlegen
     */
    private void createGUI ()
    {
        // Buttons
        plusB = new Button(" + 1l");
        add(plusB);
        plusB.addActionListener(this);
        minusB = new Button(" - 1l");
        add(minusB);
        minusB.addActionListener(this);
        // Label
        Label lab = new Label(" FŸllstand in Liter ");
        add(lab);
        // TextField mit Fuellstand
        standT = new TextField(5);
        add(standT);
        standT.addActionListener(this);
        standT.setText(""+fuellstand);
        // Buttons
        fillB = new Button("FŸllen");
        add(fillB);
        fillB.addActionListener(this);
        emptyB = new Button("Leeren");
        add(emptyB);
        emptyB.addActionListener(this);
        // Button zum Starten des Consumers
        startConsB = new Button("Start Consumer");
        add(startConsB);
        startConsB.addActionListener(this);
        // Scrollbar
        adjustS = new Scrollbar(Scrollbar.HORIZONTAL, fuellstand+visibleScrollbar, 0,
            visibleScrollbar, maxFuellstand+visibleScrollbar+1);
        add(adjustS);
        adjustS.setPreferredSize(new Dimension(150, 20));
        adjustS.addAdjustmentListener(this);
    }

    /**
     *  paint braucht es gar nicht, da das GUI in init() mit der Methode
     *  createGUI() und in TankView beim Initialisieren des Canvas angelegt
     *  wird
     */
    //     public void paint (Graphics g) {
    //     }

    /**
     *  Event Handler: Button wurde betaetigt oder Textfeld geaendert
     */
    public void actionPerformed (ActionEvent e)
    {
        
        // Beeinflussen des Fuellstands im Model: Dadurch wird auch ein 
        // propertyChange-Event ausgeloest, was die Listener sowohl in 
        // dieser Klasse als auch in TankView veranlasst, die Darstellung
        // anzupassen
        
        // Tank fuellen
        if (e.getSource() == fillB){
            tank.fuellen();
        }
        // Tank leeren und alten Fuellstand in fuellstand speichern (diese 
        // Variable wird nicht mehr benutzt, es ist nicht der aktuelle 
        // Fuellstand, der ist ja im Model)
        if (e.getSource() == emptyB){
            fuellstand = tank.leeren();
        }
        // Fuellstand um 1 erhoehen
        if (e.getSource() == plusB){
            tank.fuellstandAendernUm(1);
        }
        // Fuellstand um 1 vermindern
        if (e.getSource() == minusB){
            tank.fuellstandAendernUm(-1);
        }
        // Textfeld geandert
        if (e.getSource() == standT){
            int stand = Integer.parseInt(standT.getText());
            tank.setFuellstand(stand);
        }
        
        // Starten des automatischen Consumers, der in regelmaessigen 
        // Abstaenden dem Tank etwas entnimmt; dies wird nicht durch
        // GUI-Elemente ausgeloest, sondern spricht direkt das Model
        // an; dabei wird auch das propertyChange-Event ausgeloest
        if (e.getSource() == startConsB){
            cons = new Consumer(tank, 5, 2);
        }
        
        // Das Aufdatieren von Textzeile, Scrollbar und TankView ist 
        // in dieser Version nicht mehr noetig, da dies im Event-Handler
        // nach einem propertyChange geschieht
        //         standT.setText("" + tank.getFuellstand()); 
        //         adjustS.setValue(tank.getFuellstand() + visibleScrollbar);
        //         repaint();
        //         tankView.repaint();
    }

    /**
     *  Event Handler: Scrollbar wurde geaendert
     */
    public void adjustmentValueChanged (AdjustmentEvent e)
    {
        fuellstand = e.getValue()-visibleScrollbar;
        tank.setFuellstand(fuellstand);
        // Das Aufdatieren von Textzeile und TankView ist in dieser 
        // Version nicht mehr noetig, da dies im Event-Handler nach 
        // einem propertyChange geschieht
        //         standT.setText("" + fuellstand); 

        //         repaint();
        //         tankView.repaint();
    }

    /**
     * Event Handler: 
     * Die TankView ist Listener des Models und wird ueber diese Methode 
     * ueber Aenderungen am Model informiert; wenn dies geschieht, muessen 
     * das Textfeld und die Scrollbar aktualisiert werden
     */
    public void propertyChange (PropertyChangeEvent e)
    {
        if (e.getPropertyName().equals(tank.FUELLSTAND_PROPERTY)){
            standT.setText("" + tank.getFuellstand()); 
            adjustS.setValue(tank.getFuellstand() + visibleScrollbar);
        }
        repaint();
    }
}
