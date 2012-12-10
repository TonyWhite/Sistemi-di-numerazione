/**
 * Questa classe conserva le informazioni che devono essere gestite da più classi
 * 
 * Autore: Antonio Bianco
 */
import java.awt.Font;
import java.util.HashMap;
import java.util.Vector;
public class Opzioni
{
    // Istanza di variabili
    public static Vector<String> TEMI = new Vector<String>();                       // Elenco dei temi disponibili
    public static HashMap<String, String> MAP_TEMI = new HashMap<String, String>(); // Hash Map dei temi(chiavi) e dei relativi nomi di classe(valori)
    public static Vector<String> LISTA_FONT = new Vector<String>();                 // Lista dei font installati nel sistema
    public static boolean FONT_PERSONALIZZATO = false;                              // Specifica se il font che si sta utilizzando è personalizzato o predefinito
    
    // Opzioni da caricare/salvare
    public static String TEMA_CORRENTE = "";                                        // Tema corrente
    public static String FONT_NAME = "";                                            // Font selezionato
    public static int FONT_STILE = 0;                                               // Stile del font selezionato
    public static int FONT_SIZE = 0;                                                // Altezza del font selezionato
    
    // Opzioni di default
    public static String TEMA_DEFAULT = "";                                         // Tema predefinito di sistema
    public static String FONT_DEFAULT_NAME = Font.MONOSPACED;                       // Font predefinito
    public static int FONT_DEFAULT_STILE = Font.PLAIN;                              // Stile del font predefinito
    public static int FONT_DEFAULT_SIZE = 12;                                       // Altezza del font predefinito
    
    /**
     * Costruttore della classe
     */
    public Opzioni()
    {
        // Assolutamente NIENTE
    }
}
