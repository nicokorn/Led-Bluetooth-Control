/**
 *  FileSearch
 *  Demonstriert die Suche in einer CSV-Datei mit Hilfe eines StringTokenizers.
 *  
 *  @author huhp, bkrt 
 *  @version 01-MAY-2013
 */
import java.io.*;
import java.awt.*;
import java.util.*; // StringTokenizer
import java.awt.event.*;

public class FileSearch extends Frame implements ActionListener, WindowListener 
{

    private BufferedReader inFile;
    private Button searchButton;
    private TextField result1Field, result2Field, personField;
    private TextField fileNameField, errorField;
    private String fileName;

    /**
     *  main-Methode zum Starten des Programms
     */
    public static void main (String [ ] args) 
    {
        FileSearch search = new FileSearch();
        search.setSize(400,400);
        search.setVisible(true);
    }

    /**
     *  Konstruktor: GUI initialisieren
     */
    public FileSearch () 
    {
        setLayout(new FlowLayout() );
        
        errorField= new TextField("Type the File name:");
        errorField.setEditable(false);
        add(errorField);
        
        fileNameField = new TextField(20);
        fileNameField.setText("");
        add(fileNameField);
        
        searchButton = new Button("Search");
        add(searchButton);
        searchButton.addActionListener(this);
        
        add(new Label("Type Name:"));
        personField = new TextField(20);
        personField.setText("");
        add(personField);
        
        add( new Label("Result1:"));
        result1Field = new TextField(5);
        result1Field.setEditable(false);
        add(result1Field);
        
        add (new Label("Result2:"));
        result2Field= new TextField(5);
        result2Field.setEditable(false);
        add(result2Field);
        
        this.addWindowListener(this);
    }

    /**
     *  Button-Ereignis behandeln: Text in Datei suchen
     */
    public void actionPerformed (ActionEvent evt) 
    {
        if (evt.getSource() == searchButton) {
            
            // Dateiname lesen und Datei oeffnen wenn moeglich
            fileName = fileNameField.getText();
            try {
                inFile = new BufferedReader(new FileReader(fileName));
            }
            catch (IOException e) {
                errorField.setText("Can't find file ");
                return;
            }
            
            // Text von eventuellem vorherigen Fehler ueberschreiben
            errorField.setText("Type the file name:");
            
            // Datei lesen
            try {
                String line;
                boolean found = false;
                while (( ( line = inFile.readLine() ) != null) && (! found)) {
                    
                    // Tokens getrennt durch Kommas, Leerzeichen
                    StringTokenizer tokens = new StringTokenizer(line," ,");
                    String nameInFile = tokens.nextToken();
                    
                    // Gesuchter Eintrag gefunden?
                    if (personField.getText().equals(nameInFile)) {
                        found = true;
                        result1Field.setText(tokens.nextToken() );
                        result2Field.setText(tokens.nextToken() );
                    }
                }
                inFile.close();
            }
            catch (IOException e) {
                System.err.println("Error reading file " + fileName+": " + e.toString() );
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

