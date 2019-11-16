import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
//import java.util.prefs.Preferences;
//import java.util.Timer;
//import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.io.*;


/**
 * Typ:         Application
 * Name:        LEDWall.java
 * Autor:       Nico Korn
 * Datum:       09.05.2016
 * Projekt:     LED Wall
 * Version:     Beta
 * 
 * Beschreibung: Programm zur ansteuerung einer LED Wand
 * 
 */
public class LEDWallApp{
    //Deklarationen
    //enum für states
    //private static enum State{FAIL, INIT, RESET, ANALYZE, OPEN_IMAGE, WAIT};

    //GUI
    private static LEDWallGUI led_gui;
    
    //LED Wall
    private static JPanelLEDPreview led_wall_preview;
    private static int i_led_radius;
    
    //Bild (BufferedImage)
    private static Picture picture;
    private static int[][] arr_led_colors;
    
    //State Machine
    private static boolean bol_run; //FSM booelan
    private static EnumStateFSM state_from_gui;
    private static EnumStateFSM state_from_einstellungen;
    private static EnumStateFSM state;
    private static EnumStateFSM state_old;
    
    //Integer für LED Wand Auflösung
    private static int i_led_row;
    private static int i_led_col;
    private static int i_width;
    private static int i_height;
    
    //Integer für Segmentszie
    private static int i_segment_size;
    
    //boolean für das beibehalten des Bildverhältnisses
    private static boolean b_keep_ratio;
    
    public static void main(String[] args){
        //GUI initialisieren
        led_gui = new LEDWallGUI();
        
        //LED Wall Vorschau initialisieren
        led_wall_preview = new JPanelLEDPreview();
        
        //Picture initialisieren
        picture = new Picture(led_gui);
        
        //Setze While Schlaufe für FSM auf true
        bol_run = true;
        
        //Initialisiere den ersten state
        state = EnumStateFSM.INIT;
        
        //starte FSM
        while(bol_run){
            //Überprüfe nach Evemts aus dem LED Gui mithilfe einer Flankendetektion
            state_from_gui = led_gui.getStateGUI();
            if(state_from_gui != null){
                System.out.println("Action from GUI detected");
                state = state_from_gui;
                led_gui.setStateGUI(null);  //reset state in led_gui
            }

            //FSM
            if(state != state_old){ //Flankendetektion für FSM
                state_old = state;  //speichere aktuellen state wert unter old state
                //State FSM
                switch(state){
                    case INIT:              state_init();
                                            System.out.println("State: Init");
                                            state = EnumStateFSM.WAIT;
                    break;
                    
                    case RESET:             state_reset();
                                            System.out.println("State: Reset");
                                            state = EnumStateFSM.WAIT;
                    break;
                    
                    case OPEN_PICTURE:      state_open_picture();
                                            System.out.println("State: Open Picture");
                                            state = EnumStateFSM.WAIT;
                    break;
                    
                    case PREVIEW_LED_WALL:  state_preview_led_wall();
                                            System.out.println("State: Preview LED Wall");
                                            state = EnumStateFSM.WAIT;
                    break;
                    
                    case WAIT:              state_wait();
                                            System.out.println("State: Wait");
                                            state = EnumStateFSM.WAIT;
                    break;
                    
                    case APPLYSETUP:        state_applysetup();
                                            System.out.println("State: Setup übernommen\nLED Wand mit: "+i_led_row+" Zeilen und "+i_led_col+" Spalten");
                                            if(picture.get_picture() != null){
                                                state = EnumStateFSM.PREVIEW_LED_WALL;
                                                break;
                                            }
                                            state = EnumStateFSM.WAIT;
                    break;
                    
                    case DENYSETUP:         state_denysetup();
                                            System.out.println("State: Setup Abbrechen");
                                            state = EnumStateFSM.WAIT;
                    break;
                    
                    case END:               state_end();
                                            System.out.println("State: Ende");
                                            state = EnumStateFSM.WAIT;
                    break;
                    
                    case INFO:              state_info();
                                            System.out.println("State: Info");
                                            state = EnumStateFSM.WAIT;
                    break;
                    
                    case SMALLER_LED:       state_smaller_led();
                                            System.out.println("State: smaller led");
                                            state = EnumStateFSM.WAIT;
                    break;
                    
                    case BIGGER_LED:        state_bigger_led();
                                            System.out.println("State: bigger led");
                                            state = EnumStateFSM.WAIT;
                    break;
                    
                    default:                state_wait();
                                            System.out.println("Default Switch statement: Wall");
                }
            }
            waitProcessing(1);  //warte 1ms für das FSM Processing
        }
    }
    
     /**
     * init state
     */
    public static void state_init(){
        //initwerte
        i_led_row = 10;
        led_gui.setRowCount("10");
        i_led_col = 10;
        led_gui.setColCount("10");
        led_gui.setApplyButton(false);
        //lese Radiuswerte der LED's
        i_led_radius = 10;
        led_wall_preview.setLEDRadius(i_led_radius);
        led_gui.setSegmentSize(50);
        led_gui.setRatioState(false);
    }
    
     /**
     * reset state
     */
    public static void state_reset(){

    }
    
     /**
     * preview led wall state
     */
    public static void state_preview_led_wall(){
        try{
            i_segment_size = led_gui.getSegmentSize();
            arr_led_colors = picture.get_picture_color(i_led_col, i_led_row, i_segment_size);
            
            b_keep_ratio = led_gui.getRatioState();
            i_width = picture.get_picture_width();
            i_height = picture.get_picture_height();
            led_wall_preview.setLEDRaster(i_led_col, i_led_row, arr_led_colors, b_keep_ratio, i_width, i_height);
            waitProcessing(1);  //Kurze Wartezeit da sonst zweimal die led Vorschau gezeichnet wird
            led_gui.setTab(1);
            waitProcessing(1);  //Kurze Wartezeit da sonst zweimal die led Vorschau gezeichnet wird
        }catch (ArithmeticException ae) {
            JOptionPane.showMessageDialog(led_gui,
            "Segmentgrösse zu klein!",
            "Error",
            JOptionPane.ERROR_MESSAGE);
        }

    }
   
     /**
     * open picture state
     */
    public static void state_open_picture(){
        arr_led_colors = null;
        picture.open_picture();
        led_gui.showPicture(picture);
        if(picture.get_picture()!=null){
            led_gui.setPreviewButtonAndTab(true);
        }else{
            led_gui.setPreviewButtonAndTab(false);
        }
    }
    
     /**
     * wait state
     */
    public static void state_wait(){
        //led_gui.repaint();
        if(picture.get_picture()==null){
            led_gui.setPreviewButtonAndTab(false);
        }
    }
    
     /**
     * setup state
     */
    public static void state_setup(){
    }
    
     /**
     * apply setup state
     */
    public static void state_applysetup(){
        i_led_row = led_gui.getRowCount();
        System.out.println("Anzahl Reihen y: "+i_led_row);
        i_led_col = led_gui.getColCount();
        System.out.println("Anzahl Spalten x: "+i_led_col);
        i_segment_size = led_gui.getSegmentSize();
    }
    
     /**
     * deny setup state
     */
    public static void state_denysetup(){
    }
    
     /**
     * deny end
     */
    public static void state_end(){
        System.exit(0);
    }
    
     /**
     * Programminfo
     */
    public static void state_info(){
        JOptionPane.showMessageDialog(led_gui,
        "Autor: Nico Korn\nDatum: 10.05.2016",
        "Info",
        JOptionPane.INFORMATION_MESSAGE);
    }
    
     /**
     * LED's in der Vorschau verkleinern
     */
    public static void state_smaller_led(){
        if(i_led_radius > 2){   
            i_led_radius--;
            led_gui.setBiggerLed(true);
            led_wall_preview.setLEDRadius(i_led_radius);
        }
        
        if(i_led_radius == 2){
            led_gui.setSmallerLed(false);
        }
        
        led_gui.repaint();
    }
    
     /**
     * LED's in der Vorschau vergrössern
     */
    public static void state_bigger_led(){ 
        if(i_led_radius < 20){
            i_led_radius++;
            led_gui.setSmallerLed(true);
            led_wall_preview.setLEDRadius(i_led_radius);
        }
        
        if(i_led_radius == 20){
            led_gui.setBiggerLed(false);
        }
        
        led_gui.repaint();
    }
    
     /**
     * init fail
     */
    public static void fail(String str_fail){
        JOptionPane.showMessageDialog(led_gui,
        str_fail,
        "Error",
        JOptionPane.ERROR_MESSAGE);
    }
    
    private static void waitProcessing(int iWait){
        try {
            Thread.sleep(iWait);
        }catch(InterruptedException ie) {
        }
    }

}
