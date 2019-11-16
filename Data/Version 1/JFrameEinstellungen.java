import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.IOException;
import java.lang.Object;
import java.awt.Color;
import java.awt.Graphics;

/**
 * JFrame Klasse für die Generierung einer Einstellungsinstanz in Form eines Fenster
 */
public class JFrameEinstellungen extends JFrame implements ItemListener, ActionListener, WindowListener{
    //Variablen
    //Panel
    private static JPanel jEinstellungsPanel;
    private static GridBagConstraints constraints;
    
    //Dropdownliste
    private static JComboBox jZeilenCombo;
    private static JComboBox jSpaltenCombo;
    private static String[] strArr_zeilen = {"0","1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20"};
    private static String[] strArr_spalten = {"0","1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20"};
    private static JLabel lbl_zeilen;
    private static JLabel lbl_spalten;
    
    //Buttons
    private static JButton btn_ok;
    private static JButton btn_abbrechen;
    
    //enum für states
    private static EnumStateFSM state;

    /**
     * Constructor for objects of class JFrameEinstellungen
     */
    public JFrameEinstellungen(){
        //Frame
        setTitle("Einstellungen");
        setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
        setLocation(500,500);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        
        //Panel
        //Constraints für GirdBagLayout
        constraints = new GridBagConstraints();
        //constraints.anchor = GridBagConstraints.CENTER;
        constraints.insets = new Insets(10, 10, 10, 10);
        jEinstellungsPanel = new JPanel();
        jEinstellungsPanel.setLayout(new GridBagLayout());     //GridBagLayout auf diesem Panel
        
        //Dropdownlisten mit Labels als Bezeichnungen
        //Anzahl Zeilen der LED Wand
        jZeilenCombo = new JComboBox(strArr_zeilen);
        constraints.gridx = 5;
        constraints.gridy = 0; 
        jEinstellungsPanel.add(jZeilenCombo,constraints);
        lbl_zeilen = new JLabel("Anzahl LED Zeilen");
        constraints.gridx = 4;
        constraints.gridy = 0; 
        jEinstellungsPanel.add(lbl_zeilen,constraints);
        //Anzahlspalten der LED Wand
        jSpaltenCombo = new JComboBox(strArr_spalten);
        constraints.gridx = 5;
        constraints.gridy = 1; 
        jEinstellungsPanel.add(jSpaltenCombo,constraints);
        lbl_spalten = new JLabel("Anzahl LED Spalten");
        constraints.gridx = 4;
        constraints.gridy = 1; 
        jEinstellungsPanel.add(lbl_spalten,constraints);
        
        //Buttons
        //Ok Button
        btn_ok = new JButton("Ok");
        btn_ok.addActionListener(this);
        constraints.gridx = 4;
        constraints.gridy = 2; 
        jEinstellungsPanel.add(btn_ok,constraints);
        //Abbrechen Button
        btn_abbrechen = new JButton("Abbrechen");
        btn_abbrechen.addActionListener(this);
        constraints.gridx = 5;
        constraints.gridy = 2; 
        jEinstellungsPanel.add(btn_abbrechen,constraints);
        
        add(jEinstellungsPanel);
        pack();
        //setVisible(true);
    }
    
    /**
     * State Getter
    */
    public EnumStateFSM getStateSetup(){
        return state;
    }
    
    /**
     * State Setter
    */
    public void setStateSetup(EnumStateFSM state){
        this.state = state;
    }

     /**
     * ItemListener
     */
    public void itemStateChanged(ItemEvent f){
        //Listener for choosing a source
        //if(f.getSource() == jcSource){
        //}
    }
    
    /**
     * ActionListener
     */
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == btn_ok){
            state = EnumStateFSM.APPLYSETUP;
        }
        if(e.getSource() == btn_abbrechen){
            state = EnumStateFSM.DENYSETUP;
        }
    }
    
    /**
     * WindowListener Methoden
     */
    public void windowClosing(WindowEvent event){
        //setPreference();    //safe properties
        dispose();            //vanish window
        System.exit(0);       //end application
    }
    public void windowIconified(WindowEvent event){ 
    }
    public void windowOpened(WindowEvent event){
    }
    public void windowClosed(WindowEvent event){
    }
    public void windowActivated(WindowEvent event){
    }
    public void windowDeiconified(WindowEvent event){
    }
    public void windowDeactivated(WindowEvent event){
    }
    private void exitForm(WindowEvent event){
    }
}
