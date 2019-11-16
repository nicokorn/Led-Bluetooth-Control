import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.lang.Object;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JSlider;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.io.*;


/**
 * Write a description of class LEDWallGUI here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class LEDWallGUI extends JFrame implements ItemListener, ActionListener, ChangeListener, WindowListener{
    //GUI
    
    //Container
    private static JPanel jTest;
    private static JPanel jMainPanel;
    private static JPanel jSetupPanel;
    private static JPanel jLEDPanel;
    private static JPanel jLEDPanelSouth;
    private static JPanel jMainPanelNorth;
    private static JPanelPicture jPanelPicture;
    private static JPanelLEDPreview jPanelLEDPreview;
    private static JPanel jMainPanelSouth;
    private static JTabbedPane tabpane; 
    private static BoxLayout bMainPanel;
    private static BoxLayout bLEDPanel;
    
    //Menu Bar
    private static JMenuBar jmbMenuleiste;
    private static JMenu jmAktion;
    private static JMenu jmHilfe;
    private static JMenuItem jmiEinstellungen;
    private static JMenuItem jmiBeenden;
    private static JMenuItem jmiUeberDasProgramm;
    
    //Elemente Main Panel/////////////////////////////////////////////////////////////////////////////
    //Buttons
    private static JButton btn_open_picture;        //öffnet Bild für LED Wall
    private static JButton btn_preview_led_wall;    //lädt Vorschau für LED Wall
    
    //Labels
    private static JLabel lbl_bild_einfuegen;       
    //Textfields
    //private static JTextField tf_polynomgrad;
    
    //File chooser
    private static JFileChooser chooser_open_picture;
    private static FileNameExtensionFilter filter;
    private static GridBagConstraints constraints;
    //////////////////////////////////////////////////////////////////////////////////////////////////////
    
    //Elemente LED Vorschau Panel/////////////////////////////////////////////////////////////////////////
    private static JButton btn_bigger_led;
    private static JButton btn_smaller_led;
    private static JButton btn_color_dance;
    private static boolean bol_color_dance = true;
    //////////////////////////////////////////////////////////////////////////////////////////////////////
    
    //Elemente Setup Panel////////////////////////////////////////////////////////////////////////////////
    //Dropdownliste für die LED Wall Auflösung
    private static JComboBox jResolutionCombo;
    private static String[] strArr_resolution = {"16x9","32x18","64x36","128x72","256x144","512x288"};
    private static String[] strArr_col_row;
    private static String str_combo_resolution;
    private static JLabel lbl_resolution;
    //Slider für die Segmentgrösse
    private static JSlider sliderSegmentSize;
    private static JLabel lbl_segment;
    //Checkbox für das beibehalten des Bildverhältnisses (veraltet)
    private static JCheckBox check_ratio;   //Checkbox für das beibehalten des Bildverhältnisses 
    private static JLabel lbl_ratio;
    //Dropdownliste für die Wahl der Refreshrate
    private static JComboBox jRefreshCombo;
    private static String[] strArr_refresh = {"25","50","75","100","200"};
    private static String str_combo_refresh;
    private static JLabel lbl_refresh;
    //Dropdownliste für die Wahl der Effekte
    private static JComboBox jEffectCombo;
    private static String[] strArr_effect = {"Color Dance","Color Wave"};
    private static String str_combo_effect;
    private static JLabel lbl_effect;
    
    //Buttons
    private static JButton btn_ok;
    //////////////////////////////////////////////////////////////////////////////////////////////////////
    
    //enum für states
    private static EnumStateFSM state;
        
    //picture
    private static Picture picture;
    
    /**
     * Konstruktor
     */
    public LEDWallGUI(){
        //Frame
        setTitle("LED Wall \u00a9 Nico Korn");
        //setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
        //getContentPane().setBackground(new Color(255,251,242)); //setBackground(Color.LIGHT_GRAY);
        setLocation(100,100);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        
        //Menuleiste
        jmbMenuleiste = new JMenuBar();
        //Aktion
        jmAktion = new JMenu("Aktion");
            jmiBeenden = new JMenuItem("Beenden");
            jmiBeenden.addActionListener(this);
            jmAktion.add(jmiBeenden);
        //jmbMenuleiste.setBackground(new Color(242,242,208));
        jmbMenuleiste.add(jmAktion);
        //Hilfe
        jmHilfe = new JMenu("Hilfe");
            jmiUeberDasProgramm = new JMenuItem("Über das Programm");
            jmiUeberDasProgramm.addActionListener(this);
            jmHilfe.add(jmiUeberDasProgramm);
        jmbMenuleiste.add(jmHilfe);
        //Menuleiste hinzufügen
        setJMenuBar(jmbMenuleiste);
        
        
        //register panels
        jMainPanel = new JPanel();
        jLEDPanel = new JPanel();
        jSetupPanel = new JPanel();
        bMainPanel = new BoxLayout(jMainPanel, BoxLayout.PAGE_AXIS);
        bLEDPanel = new BoxLayout(jLEDPanel, BoxLayout.PAGE_AXIS);
        jMainPanel.setLayout(bMainPanel);
        jLEDPanel.setLayout(bLEDPanel);
        jSetupPanel.setLayout(new GridBagLayout());
       
        //Constraints für GirdBagLayout
        constraints = new GridBagConstraints();
        //constraints.anchor = GridBagConstraints.CENTER;
        constraints.insets = new Insets(10, 10, 10, 10);
        
        /////////////////////////Main Panel///////////////////////////////////////////////////////
        //Panel für Buttons Oben
        jMainPanelNorth = new JPanel();                     //neues Panel
        jMainPanelNorth.setLayout(new GridBagLayout());     //GridBagLayout auf diesem Panel
        //open Picture Button
        btn_open_picture = new JButton("Bild Öffnen");
        //btn_open_picture.setBounds(240,10,235,25);
        btn_open_picture.addActionListener(this);
        constraints.gridx = 0;
        constraints.gridy = 0; 
        jMainPanelNorth.add(btn_open_picture,constraints);  //Button in das Panel einfügen
        jMainPanel.add(jMainPanelNorth,BorderLayout.NORTH); //Panel dem Frame hinzufügen und in dessen Layout einfügen
        
        //Panel mit Bildvorschau
        jPanelPicture = new JPanelPicture();
        jMainPanel.add(jPanelPicture,BorderLayout.CENTER);
        
        //Panel für Buttons Unten
        jMainPanelSouth = new JPanel();
        jMainPanelSouth.setLayout(new GridBagLayout());
        //vorschau der LED Wall Button
        btn_preview_led_wall = new JButton("Lade LED Wall Vorschau");
        //btn_preview_led_wall.setBounds(12,415,235,25);
        btn_preview_led_wall.setEnabled(false);
        btn_preview_led_wall.addActionListener(this);
        constraints.gridx = 0;
        constraints.gridy = 0; 
        //constraints.gridheight = 5;
        jMainPanelSouth.add(btn_preview_led_wall,constraints);
        jMainPanel.add(jMainPanelSouth,BorderLayout.SOUTH);
        ///////////////////////////////////////////////////////////////////////////////////////////
        
        /////////////////////////LED Panel///////////////////////////////////////////////////////
        //Panel
        jPanelLEDPreview = new JPanelLEDPreview();
        jLEDPanel.add(jPanelLEDPreview,BorderLayout.NORTH);
        
        jLEDPanelSouth = new JPanel();
        jLEDPanel.add(jLEDPanelSouth,BorderLayout.SOUTH);
        
        //Buttons
        //mit folgenden Buttons sollen die leds grösser und kleiner gemacht werden
        btn_smaller_led = new JButton("Kleinere LED's");
        btn_smaller_led.addActionListener(this);
        jLEDPanelSouth.add(btn_smaller_led,BorderLayout.SOUTH);
        btn_bigger_led = new JButton("Grössere LED's");
        btn_bigger_led.addActionListener(this);
        jLEDPanelSouth.add(btn_bigger_led,BorderLayout.SOUTH);
        btn_color_dance = new JButton("GO! Disco");
        btn_color_dance.addActionListener(this);
        jLEDPanelSouth.add(btn_color_dance,BorderLayout.SOUTH);
        /////////////////////////////////////////////////////////////////////////////////////////
        
        /////////////////////////Setup Panel///////////////////////////////////////////////////////
        //Panel für Einstellungen
        //Scrollbar für zum anpassen der Segmentgrössen
        sliderSegmentSize = new JSlider();
        sliderSegmentSize.setMinimum(0);
        sliderSegmentSize.setMaximum(100);
        sliderSegmentSize.setOrientation(JSlider.HORIZONTAL);
        sliderSegmentSize.setMajorTickSpacing(20);
        sliderSegmentSize.setMinorTickSpacing(20);
        sliderSegmentSize.setPaintLabels(true);
        sliderSegmentSize.setPaintLabels(true);
        sliderSegmentSize.addChangeListener(this);
        constraints.gridx = 5;
        constraints.gridy = 0; 
        jSetupPanel.add(sliderSegmentSize,constraints);
        lbl_segment = new JLabel("Segmentgrösse %");
        constraints.gridx = 4;
        constraints.gridy = 0; 
        jSetupPanel.add(lbl_segment,constraints);
        
        //Checkbox für das beibehalten des Bildverhältnisses
        check_ratio = new JCheckBox();
        check_ratio.setEnabled(false);
        constraints.gridx = 5;
        constraints.gridy = 1; 
        jSetupPanel.add(check_ratio,constraints);
        check_ratio.addItemListener(this);
        lbl_ratio = new JLabel("Bildverhältnis beibehalten");
        constraints.gridx = 4;
        constraints.gridy = 1; 
        jSetupPanel.add(lbl_ratio,constraints);
        
        //Checkbox für das aktivieren einer homogenen Gitterstruktur
        
        //Dropdownlisten mit Labels als Bezeichnungen
        //Anzahl Zeilen der LED Wand
        jResolutionCombo = new JComboBox(strArr_resolution);
        jResolutionCombo.addItemListener(this);
        constraints.gridx = 5;
        constraints.gridy = 2; 
        jSetupPanel.add(jResolutionCombo,constraints);
        lbl_resolution = new JLabel("LED Auflösung");
        constraints.gridx = 4;
        constraints.gridy = 2; 
        jSetupPanel.add(lbl_resolution,constraints);
        
        //Wahl der Refreshrate
        jRefreshCombo = new JComboBox(strArr_refresh);
        jRefreshCombo.addItemListener(this);
        constraints.gridx = 5;
        constraints.gridy = 3; 
        jSetupPanel.add(jRefreshCombo,constraints);
        lbl_refresh = new JLabel("FX Refreshperiode in ms");
        constraints.gridx = 4;
        constraints.gridy = 3; 
        jSetupPanel.add(lbl_refresh,constraints);
        
        //Wahl der Effekte
        jEffectCombo = new JComboBox(strArr_effect);
        jEffectCombo.addItemListener(this);
        constraints.gridx = 5;
        constraints.gridy = 4; 
        jSetupPanel.add(jEffectCombo,constraints);
        lbl_effect = new JLabel("LED Disco Effekte");
        constraints.gridx = 4;
        constraints.gridy = 4; 
        jSetupPanel.add(lbl_effect,constraints);
        
        //Buttons
        //Ok Button
        btn_ok = new JButton("Übernehmen");
        btn_ok.addActionListener(this);
        btn_ok.setEnabled(false);
        constraints.gridx = 5;
        constraints.gridy = 5; 
        jSetupPanel.add(btn_ok,constraints);
        /////////////////////////////////////////////////////////////////////////////////////////
    
        
        //Tabpane zusammensetzen
        tabpane = new JTabbedPane();
        tabpane.addTab("Bild einlesen", jMainPanel);
        tabpane.addTab("LED Wall Vorschau", jLEDPanel);
        tabpane.setEnabledAt(1, false);
        tabpane.addTab("Einstellungen", jSetupPanel);
        add(tabpane);
        
        pack();
        setLocationRelativeTo(null);
        //repaint();
        
        addWindowListener(this);
        
        setVisible(true);
    }
    
    /**
     * State Getter
    */
    public EnumStateFSM getStateGUI(){
        return state;
    }
    
    /**
     * State Setter
    */
    public void setStateGUI(EnumStateFSM state){
        this.state = state;
    }
    
    /**
     * Zeige Bild
    */
    public void showPicture(Picture picture){
        jPanelPicture.loadPicture(picture);
    }
    
    /**
     * get LED Resolution Rows
    */
    public int getResolutionRows(){
        str_combo_resolution = (String)jResolutionCombo.getSelectedItem();
        String[] strArr_col_row = str_combo_resolution.split("x");
        return Integer.parseInt(strArr_col_row[1]);
    }
    
    /**
     * get LED Resolution Cols
    */
    public int getResolutionCols(){
        str_combo_resolution = (String)jResolutionCombo.getSelectedItem();
        String[] strArr_col_row = str_combo_resolution.split("x");
        return Integer.parseInt(strArr_col_row[0]);
    }
    
    /**
     * set LED Resoltution
    */
    public void setResolution(String str_resolution){
        jResolutionCombo.setSelectedItem(str_resolution);
    }    
    
    /**
     * get refreshrate
    */
    public int getRefreshrate(){
        return Integer.parseInt((String)jRefreshCombo.getSelectedItem());
    }
    
    /**
     * set refreshrate
    */
    public void setRefreshrate(String str_refreshrate){
        jRefreshCombo.setSelectedItem(str_refreshrate);
    }
    
    /**
     * get effect
    */
    public String getEffect(){
        return (String)jEffectCombo.getSelectedItem();
    }
    
    /**
     * set effect
    */
    public void setEffect(String str_effect){
        jEffectCombo.setSelectedItem(str_effect);
    }
    
    /**
     * setze analyse Button enabled / disabled
    */
    public void setPreviewButtonAndTab(boolean set){
        btn_preview_led_wall.setEnabled(set);
        tabpane.setEnabledAt(1, set);
    }
    
    /**
     * setze übernehmen Button im Setup-Tab
    */
    public void setApplyButton(boolean set){
        btn_ok.setEnabled(set);
    }
    
    /**
     * setze bigger led button
    */
    public void setBiggerLed(boolean set){
        btn_bigger_led.setEnabled(set);
    }
    
    /**
     * setze smaller led button
    */
    public void setSmallerLed(boolean set){
        btn_smaller_led.setEnabled(set);
    }
    
    /**
     * öffne register
    */
    public void setTab(int tab){
        tabpane.setSelectedIndex(tab);
    }
    
    /**
     * setze segmentgrösse
    */
    public void setSegmentSize(int size){
        sliderSegmentSize.setValue(size);
    }
    
    /**
     * gebe segmentgrösse zurück
    */
    public int getSegmentSize(){
        return sliderSegmentSize.getValue();
    }
    
    /**
     * gebe ratio checkbox state zurück
    */
    public boolean getRatioState(){
        return check_ratio.isSelected();
    }
    
    /**
     * setze ratio checkbox
    */
    public void setRatioState(boolean set){
        check_ratio.setSelected(set);
    }
    
    /**
     * setze Disco / Color Change Button
    */
    public void stopColorDanceBtn(){
        btn_color_dance.setText("GO! Disco");
        bol_color_dance = true;
    }
    
    /**
     * setze stateChanged Listener für Register
    */
    public void enableTabListener(){
        tabpane.addChangeListener(this);
    }
    
     /**
     * ChangeListener
     */
    public void stateChanged(ChangeEvent s){
        //Listener für den Segmentgrössenslider
        if(s.getSource() == sliderSegmentSize){
            btn_ok.setEnabled(true);
        }
        //Listener für die Tabs
        if(s.getSource() == tabpane){
            System.out.println("Tab wurde gechanget");
            btn_color_dance.setText("GO! Disco");
            bol_color_dance = true;
            state = EnumStateFSM.COLOR_DANCE_STOP;
        }
    }
    
     /**
     * ItemListener
     */
    public void itemStateChanged(ItemEvent f){
        //Listener für Dropdownlisten
        if(f.getStateChange() == ItemEvent.SELECTED){
            btn_ok.setEnabled(true);
        }
        //Listener für die ratio checkbox
        if(f.getSource() == check_ratio){
            btn_ok.setEnabled(true);
        }
    }
    
    /**
     * ActionListener
     */
    public void actionPerformed(ActionEvent e){
        // Open a Picture
        if(e.getSource() == btn_open_picture){
            System.out.println("Openbutton wurde gedrückt");
            state = EnumStateFSM.OPEN_PICTURE;
        }
        //analyze the picture
        if(e.getSource() == btn_preview_led_wall){
            System.out.println("Previewbutton LED Wall wurde gedrückt");
            state = EnumStateFSM.PREVIEW_LED_WALL;
        }
        //übernehmen der Einstellungen
        if(e.getSource() == btn_ok){
            System.out.println("Übernehmen wurde gedrückt");
            btn_ok.setEnabled(false);
            state = EnumStateFSM.APPLYSETUP;
        }
        //LED's vergrössern
        if(e.getSource() == btn_bigger_led){
            System.out.println("LED's vergrössern Button gedrückt");
            state = EnumStateFSM.BIGGER_LED;
        }
        //LED's verkleinern
        if(e.getSource() == btn_smaller_led){
            System.out.println("LED's verkleinern Button gedrückt");
            state = EnumStateFSM.SMALLER_LED;
        }
        //Disco Modus starten/stoppen
        if(e.getSource() == btn_color_dance){
            System.out.println("Disco Modus Button gedrückt");
            if(bol_color_dance){
                btn_color_dance.setText("Stop Disco");
                bol_color_dance = false;
                state = EnumStateFSM.COLOR_DANCE_GO;
            }else{
                btn_color_dance.setText("GO! Disco");
                bol_color_dance = true;
                state = EnumStateFSM.COLOR_DANCE_STOP;
            }
        }
        //beenden
        if(e.getSource() == jmiBeenden){
            System.out.println("Beenden wurde gedrückt");
            state = EnumStateFSM.END;
        }
        //über das Programm
        if(e.getSource() == jmiUeberDasProgramm){
            System.out.println("Über das Programm wurde gedrückt");
            state = EnumStateFSM.INFO;
        }
    }
    
    /**
     * WindowListener Methoden
     */
    public void windowClosing(WindowEvent event){
        //setPreference();    //safe properties
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