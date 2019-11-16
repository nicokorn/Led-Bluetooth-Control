/**
 *  FileDemo1
 *  Ausgabe auf eine Datei: Beim Klick auf den Save-Button wird der in ein 
 *  Textfeld eingegebene Text in eine Datei testout.txt geschrieben (der
 *  Dateiname ist fest eingestellt).
 *  
 *  @author huhp, bkrt 
 *  @version 01-MAY-2013
 */
import java.io.*;
import java.awt.*;
import java.awt.event.*;

public class FileDemo1 extends Frame implements WindowListener, ActionListener 
{

    private TextArea inputTextArea;
    private Button saveButton;
    private PrintWriter outFile;

    /**
     *  main-Methode zum Starten des Programms
     */
    public static void main (String [] args) 
    {
        FileDemo1 demo = new FileDemo1();
        demo.setSize(300,400);
        demo.setVisible(true);
    }

    /**
     *  Konstruktor: GUI initialisieren
     */
    public FileDemo1 () 
    {
        // Button zum Speichern
        saveButton = new Button("save");
        add (saveButton, BorderLayout.NORTH);
        saveButton.addActionListener(this);
        
        // Textfeld fuer Daten, die gespeichert werden sollen 
        inputTextArea = new TextArea(10,50);
        add (inputTextArea, BorderLayout.CENTER);
        
        // zum Schliessen des Fensters
        addWindowListener(this); 
    }

    /**
     *  Button-Ereignis behandeln: Text in Datei speichern
     */
    public void actionPerformed (ActionEvent evt) 
    {
        if (evt.getSource() == saveButton ) {
            try {
                // Datei oeffnen, Text schreiben, Datei wieder schliessen
                outFile = new PrintWriter(new FileWriter("testout.txt"), true);
                outFile.print( inputTextArea.getText() );
                outFile.close();
            }
            catch (IOException e) {
                // Fehler aufgetreten
                System.err.println("File Error: " + e.toString() );
                System.exit(1);
            }
        }
    }

    /**
     *  WindowListener-Methoden
     */
    public void windowClosing (WindowEvent e) {
        System.exit(0);
    }
    public void windowIconified (WindowEvent e) {}
    public void windowOpened (WindowEvent e) { }
    public void windowClosed (WindowEvent e) { }
    public void windowDeiconified (WindowEvent e) { }
    public void windowActivated (WindowEvent e) { }
    public void windowDeactivated (WindowEvent e) { }

}
