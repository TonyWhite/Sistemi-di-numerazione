/**
 * Avvia la finestra con il tema grafico indicato in "options.txt" oppure passandoglielo come parametro
 * 
 * Antonio Bianco
 */

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.net.URL;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
public class SistemiDiNumerazione
{
    /*
     * Avvia l'applicazione con il tema grafico scelto dell'utente
     */
    public static String ABS_PATH;
    final static String OPZIONI = "options.txt";
    static String PATH_OPZIONI;
    private static String tema;
    
    public static void main(String args[])
    {
        ABS_PATH = getAbsolutePath(); // Definisce il percorso assoluto dell'applicazione
        PATH_OPZIONI = ABS_PATH + OPZIONI;
        
        String lef = "";
        int cont = 0;
        
        boolean argomento = false;
        // Decide se prendere il nome del tema dalle opzioni i dall'argomento di avvio
        if (args.length>0)
        {
            argomento = true;
            tema = args[0];
        }
        else
        {
            tema = leggiOpzioni();
        }
        // Applica il tema scelto
        try
        {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels())
            {
                lef += info.getName() + "\n";
                cont++;
                if (tema.equals(info.getName()))
                {
                    UIManager.setLookAndFeel(info.getClassName());
                }
            }
        }
        catch (Exception e)
        {
            System.err.println(e.getMessage());
        }
        // Visualizza la finestra del programma
        Finestra f = new Finestra();
        new Thread(f).start();
    }
    
    /**
     * Legge il tema salvato nel file delle opzioni
     */
    private static String leggiOpzioni()
    {
        String tema = "Nimbus";
        if (new File(PATH_OPZIONI).exists())
        {
            // Il file esiste
            // Si sceglie il tema salvato
            String errore = "";
            try
            {
                errore = "Impossibile leggere il file " + OPZIONI;
                FileReader streamOpzioni = new FileReader(PATH_OPZIONI);
                BufferedReader bufferOpzioni = new BufferedReader(streamOpzioni);
                errore = "Impossibile leggere il contenuto del file " + OPZIONI;
                tema = bufferOpzioni.readLine();
            }
            catch(Exception e)
            {
                System.out.println("ERRORE: " + errore);
            }
        }
        else
        {
            // Il file non esiste
            // Si sceglie il tema di default: Nimbus
            System.out.println("Il file " + OPZIONI + " non esiste.");
        }
        return tema;
    }
    
    /**
     * Ritorna il percorso assoluto dell'applicazione
     */
    private static String getAbsolutePath()
    {
        URL fileURL = SistemiDiNumerazione.class.getResource("/");
        if (fileURL == null)
        {
            return null;
        }
        else
        {
            return fileURL.getPath();
        }
    }
}