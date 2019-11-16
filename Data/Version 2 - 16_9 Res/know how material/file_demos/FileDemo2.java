/**
 *  FileDemo2
 *  Einlesen des Inhalts einer Datei. Der Dateiname kann in einem Eingabefeld
 *  spezifiziert werden (mit Vorteil liegt die Datei im gleichen Verzeichnis 
 *  wie die Applikation). Der Dateiinhalt wird in einer TextArea angezeigt.
 *  
 *  @author huhp, bkrt 
 *  @version 01-MAY-2013
 */
import java.io.*;
import java.awt.*;
import java.awt.event.*;

public class FileDemo2 extends Frame implements WindowListener, ActionListener 
{

    private TextArea inputTextArea;
    private Button loadButton;
    private BufferedReader inFile;
    private TextField nameField;

    /**
     *  main-Methode zum Starten des Programms
     */
    public static void main (String [] args) 
    {
        FileDemo2 demo = new FileDemo2();
        demo.setSize(300,400);
        demo.setVisible(true);
    }

    /**
     *  Konstruktor: GUI initialisieren
     */
    public FileDemo2 () 
    {
        // Panel fuer Button und Textfeld
        Panel top = new Panel();
        
        // Load-Button
        loadButton = new Button("load");
        top.add(loadButton);
        loadButton.addActionListener(this);
        
        // Eingabefeld fuer Dateinamen
        nameField = new TextField(20);
        top.add(nameField);
        nameField.addActionListener(this);
        
        add ("North", top);
        
        // Textbereich zur Anzeige der geladenen Daten
        inputTextArea = new TextArea("",10,50);
        add ("Center", inputTextArea);

        // zum Schliessen des Fensters
        addWindowListener(this);
    }

    /**
     *  Button-Ereignis behandeln: Text aus Datei laden
     */
    public void actionPerformed (ActionEvent evt) 
    {
        String fileName;
        String line;
        if (evt.getSource() == loadButton) {
            fileName = nameField.getText();
            try {
                // Datei oeffnen und zeilenweise einlesen
                inFile = new BufferedReader(new FileReader(fileName));
                inputTextArea.setText("");
                while( ( line = inFile.readLine() ) != null) {
                    inputTextArea.append(line+"\n");
                }
                inFile.close();
            }
            catch (IOException e) {
                System.err.println("Error in file " + fileName + ": " + e.toString() );
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




