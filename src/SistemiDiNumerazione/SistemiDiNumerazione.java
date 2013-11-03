/**
 * Avvia il programma
 * 
 * Autore: Antonio Bianco
 */

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.URL;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.SwingUtilities;
import java.util.HashMap;
public class SistemiDiNumerazione
{
    /*
     * Avvia l'applicazione con il tema grafico scelto dell'utente
     */
    public static String ABS_PATH;
    final static String FILE_OPZIONI = "./.java/SistemiDiNumerazione/config.conf";
    final static String CARTELLA_OPZIONI = "./.java/SistemiDiNumerazione/";
    private static Finestra gui;
    
    public static void main(String args[])
    {
        ABS_PATH = getAbsolutePath(); // Definisce il percorso assoluto dell'applicazione
        boolean argomento = false;
        leggiOpzioni();
        // Applica il tema scelto
        // Carico la lista dei temi, vedo qual è il tema predefinito e nel frattempo vedo se il tema corrente è valido
        boolean temaCorrenteValido = false;
        try
        {
            String classeTemaPredefinito = UIManager.getSystemLookAndFeelClassName();
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels())
            {
                // Carico la lista dei temi disponibili
                Opzioni.MAP_TEMI.put(info.getName(), info.getClassName());
                Opzioni.TEMI.add(info.getName());
                
                // Identifico il tema predefinito
                if (classeTemaPredefinito.equals(info.getClassName()))  Opzioni.TEMA_DEFAULT = info.getName();
                
                // Vedo se il tema corrente è valido
                if (Opzioni.TEMA_CORRENTE.equals(info.getName())) temaCorrenteValido = true;
            }
            if (!temaCorrenteValido) Opzioni.TEMA_CORRENTE = Opzioni.TEMA_DEFAULT;  // Se il tema corrente non è valido, seleziona il tema di default
            UIManager.setLookAndFeel(Opzioni.MAP_TEMI.get(Opzioni.TEMA_CORRENTE));              // Applico il tema valido
        }
        catch (Exception e)
        {
            System.err.println("Ecchitelo!");
        }
        
        // Elenco i font di sistema
        String[] elencoFont = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        for (int i=0; i<elencoFont.length; i++)
        {
            Opzioni.LISTA_FONT.add(elencoFont[i]);
        }
        // Se il font delle opzioni non è stato trovato, usa il font di default: Font.MONOSPACED
        if (!Opzioni.LISTA_FONT.contains(Opzioni.FONT_NAME)) Opzioni.FONT_NAME = Opzioni.FONT_DEFAULT_NAME;
        
        // Se lo stile memorizzato nelle opzioni non è valido, prende lo stile di default: Font.PLAIN
        if ((Opzioni.FONT_STILE!=Font.PLAIN)&&(Opzioni.FONT_STILE!=Font.BOLD)&&(Opzioni.FONT_STILE!=Font.ITALIC)&&(Opzioni.FONT_STILE!=Font.BOLD+Font.ITALIC)) Opzioni.FONT_STILE = Font.PLAIN;
        
        // se la dimensione del font memorizzato nelle opzioni non è valido, prende la dimensione di default: 12
        if (Opzioni.FONT_SIZE==0) Opzioni.FONT_SIZE = Opzioni.FONT_DEFAULT_SIZE;
        
        // Visualizza la finestra del programma
        //JFrame.setDefaultLookAndFeelDecorated(true); // DEBUG: Funziona solo su Metal. Gli altri sono troppo checche.
        gui = new Finestra();
        new Thread(gui).start();
    }
    
    /**
     * Legge il tema salvato nel file delle opzioni.
     * Ritorna false se il file è corrotto o inesistente
     */
    private static void leggiOpzioni()
    {
        if (new File(FILE_OPZIONI).exists())
        {
            // Il file esiste
            // Si sceglie il tema salvato
            try
            {
                FileReader streamOpzioni = new FileReader(FILE_OPZIONI);
                BufferedReader bufferOpzioni = new BufferedReader(streamOpzioni);
                Opzioni.TEMA_CORRENTE = bufferOpzioni.readLine();
                Opzioni.FONT_NAME = bufferOpzioni.readLine();
                Opzioni.FONT_STILE = Integer.parseInt(bufferOpzioni.readLine());
                Opzioni.FONT_SIZE = Integer.parseInt(bufferOpzioni.readLine());
                bufferOpzioni.close();
                streamOpzioni.close();
            }
            catch(Exception e){}
        }
        else
        {
            File cartella = new File(CARTELLA_OPZIONI);
            if (!cartella.exists()) cartella.mkdirs();
        }
    }
    
    /**
     * Salva le opzioni nel file
     */
    public static void salvaOpzioni()
    {
        try
        {
            FileWriter fileOpzioni = new FileWriter(FILE_OPZIONI);
            PrintWriter fileOut = new PrintWriter(fileOpzioni);
            fileOut.println(Opzioni.TEMA_CORRENTE);
            fileOut.println(Opzioni.FONT_NAME);
            fileOut.println(Opzioni.FONT_STILE + "");
            fileOut.println(Opzioni.FONT_SIZE + "");
            fileOut.close();
            fileOpzioni.close();
        }
        catch(Exception e){}
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
    
    /**
     * Cambia il tema grafico in runtime
     */
    public static void cambiaTema()
    {
        try
        {
            UIManager.setLookAndFeel(Opzioni.MAP_TEMI.get(Opzioni.TEMA_CORRENTE));
            SwingUtilities.updateComponentTreeUI(gui);
        }
        catch (Exception e)
        {
            System.err.println("Cambia tema(): " + e.getMessage());
        }
    }
}