/**
 * La classe converte da un sistema decimale ad un sistema qualsiasi e viceversa
 * 
 * Autore: Antonio Bianco
 */
public class Conversioni implements Runnable
{
    public static final String ERRORE = "Err";
    public static final long MAX_DEC = 9999999999999990L; // Numero decimale più grande da convertire

    /**
     * Constructor for objects of class Conversioni
     */
    public Conversioni()
    {
        // Metodo vuoto
    }
    
    /**
     * Converte un carattere nel relativo codice ASCII nella base specificata
     * 
     * char carattere: il carattere da codificare
     * int base: la base di codifica
     * 
     * Esempio:
     * carattere = 'A'
     * base = 2
     * codice ASCII = 65
     * return: "1000001"
     */
    public static String charToBase(char carattere, int base)
    {
        return decToBase(((int)carattere)+"", base);
    }

    /**
     * Converte un numero in base 10 in un qualsiasi altra base
     * 
     * String num: numero in base 10
     * int base: la base nella quale convertire
     * return: la stringa che rappresenta il numero convertito
     * 
     * Nota 1: La conversione è assicurata solo per numeri interi positivi fino a 9999999999999990
     * Nota 2: base <= 36 perché le cifre rappresentate con le lettere dell'alfabeto sono solo maiuscole
     */
    public static String decToBase(String num, int base)
    {
        /**
         * Oltre 9999999999999990 il double non riesce a gestire con precisione tutti i decimali
         */
        String valore = ERRORE;
        try
        {
            if (numeroValido_privato(num, 10)) //Assicuriamoci che il numero inserito sia in base 10
            {
                valore = "";
                Long intero = new Long(num); // Istruzione con pericolo di overflow
                if (intero > MAX_DEC)
                {
                    valore = ERRORE + ": num deve essere < " + MAX_DEC;
                }
                else
                {
                    if (base==1) // Calcola per la base più inutile e pericolosa in assoluto
                    {
                        /**
                         * Con il massimo valore possibile 9'999'999'999'999'990 si consuma una quatara di RAM.
                         * Ogni carattere della stringa è rappresentato da 16 bit,
                         * quindi il consumo massimo sarebbe di (9'999'999'999'999'990+1)*2 byte
                         * che sarebbero 19'999'999'999'999'982 byte
                         * che sarebbero 19'073'486'328,1 MB
                         * che sarebbero 18'626'451,4923 GB
                         * che sarebbero cazzi amari espressi in potenze di 2
                         * E allora perché non ho messo un controllo per limitare lo spreco di memoria?
                         * 1) Per vedere chi è tanto http://lmgtfy.com/?q=persona+poco+avveduta+che+non+prevede+le+conseguenze+dei+propri+atti+per+insufficiente+intelligenza&l=1
                         * 2) Me lo ha chiesto Fabio (no, non è quello di Striscia)
                         */
                        /*for (int i=0; i<=intero; i++) // Per il ciclo alta tensione...
                        {
                            valore += "0";
                        }*/
                        valore = ERRORE;    // DEBUG: Questo metodo non è sicuro per compiere questa conversione
                    }
                    else // Calcola per tutte le altre basi
                    {
                        Long risultato;
                        Double decimale;
                        while(intero>0)
                        {
                            decimale = intero.doubleValue()/base;                  // In realtà la vera trappola non è l'overflow del Long, ma la notazione del double che perde precisione oltre un certo valore (definito in MAX_DEC)
                            intero = decimale.longValue();                         // Prendo la parte intera del numero
                            decimale = decimale - intero.doubleValue();           // Prendo la parte decimale del numero
                            risultato = Math.round(new Double(base*decimale));                             // Prendo il risultato
                            valore = valoreCifra(risultato.byteValue()) + valore;   // Aggiorno il risultato
                        }
                    }
                }
                
            }
            else
            {
                valore = ERRORE;
            }
        }
        catch(Exception e)
        {
            valore = ERRORE;
        }
        return valore;
    }
    
    /**
     * Ritorna la cifra in base 36 che rappresenta il valore in base decimale dato in input
     */
    private static char valoreCifra(byte valore)
    {
        if (valore<10) valore += 48;
        else valore += 55;
        return (char)valore;
    }
    
    /**
     * Converte un numero in base decimale
     * 
     * String num: numero da convertire
     * int base: base del numero da convertire
     * return: una stringa che rappresenza il numero num convertito in decimale
     * 
     * Nota 1: Il metodo controlla che il numero in input sia valido
     * Nota 2: La conversione è assicurata solo per numeri interi positivi fino a Long.MAX_VALUE
     * Nota 3: base<=36 perché le cifre rappresentate con le lettere dell'alfabeto sono solo maiuscole
     */
    public static String baseToDec(String num, int base)
    {
        String risultato = ERRORE;
        long risultato_num = 0;
        num = num.toUpperCase();
        if (numeroValido_privato(num, base))    // Numero valido
        {
            if (base==1)            // Se la base è 1
            {
                int numero = num.length();
                numero--;
                risultato = numero+"";
            }
            else if (base==10)      // Se la base è già decimale
            {
                try
                {
                    // non convertire niente
                    long numero = new Long(num); // Ma rendilo almeno leggibile
                    risultato = numero + "";
                }
                catch(Exception e)
                {
                    // Impossibile, tanto il numero è stato verificato prima, ed È decimale. In ogni caso...
                    risultato = ERRORE;
                }
            }
            else    // Per tutte le altre basi
            {
                risultato = "";
                double j=num.length(); // Esponente per il calcolo
                for (int i=0; i<num.length(); i++)
                {
                   j--;
                   byte cifra = (byte)num.charAt(i);
                   if (cifra<=57) cifra -= 48;
                   else cifra -= 55;
                   double conversione = cifra*(Math.pow(base, j));
                   risultato_num += conversione;
                }
                risultato = "" + risultato_num;
            }
        }
        else risultato = ERRORE;
        return risultato;
    }
    
    /**
     * Verifica se la cifra può essere espressa con la base indicata
     * 
     * String num: numero da verificare
     * int base: base per la quale verificare
     * return: ritorna true se il numero può essere espresso nella base indicata
     * 
     * Nota: il metodo è privato ed è più snello
     */
    private static boolean numeroValido_privato(String num, int base)
    {
        /**
         * Intervalli
         * 0...9 = 48...57
         * A...Z = 65...90
         * la differenza tra il codice ascii della cifra e della base è:
         * --> 47 quando la base è minore di 10
         * --> 54 quando la base è almeno 11 
         */
        boolean valido = true;
        
        if ((base>0)&&(base<=36)) // Verifica che la base scelta sia gestibile
        {
            for (int i=0; (i<num.length())&&(valido); i++)
            {
                byte ascii = (byte)num.charAt(i);
                
                if (ascii==48) // Lo zero è compreso in tutte le basi
                {
                    valido = true;
                }
                else
                {
                    // Verifica che la cifra sia rappresentabile nella base indicata
                    if (((ascii>48)&&(ascii<=57))||((ascii>=65)&&(ascii<=90)))
                    {
                        int differenza = 54;
                        if (base<=10) differenza = 47;
                        if ((ascii-base)<=differenza) valido = true;
                        else valido = false;
                    }
                    else valido = false;
                }
            }
        }
        else
        {
            valido = false;
        }
        return valido;
    }
    
    /**
     * Verifica se la cifra può essere espressa con la base indicata
     * 
     * String num: numero da verificare
     * int base: base per la quale verificare
     * return: ritorna true se il numero può essere espresso nella base indicata
     */
    public static boolean numeroValido(String num, int base)
    {
        /**
         * Intervalli
         * 0...9 = 48...57
         * A...Z = 65...90
         * la differenza tra il codice ascii della cifra e della base è:
         * --> 47 quando la base è minore di 10
         * --> 54 quando la base è almeno 11 
         */
        boolean valido = true;
        num = num.toUpperCase();
        if ((base>0)&&(base<=36)) // Verifica che la base scelta sia gestibile
        {
            for (int i=0; (i<num.length())&&(valido); i++)
            {
                byte ascii = (byte)num.charAt(i);
                
                if (ascii==48) // Lo zero è compreso in tutte le basi
                {
                    valido = true;
                }
                else
                {
                    // Verifica che la cifra sia rappresentabile nella base indicata
                    if (((ascii>48)&&(ascii<=57))||((ascii>=65)&&(ascii<=90)))
                    {
                        int differenza = 54;
                        if (base<=10) differenza = 47;
                        if ((ascii-base)<=differenza) valido = true;
                        else valido = false;
                    }
                    else valido = false;
                }
            }
        }
        else
        {
            valido = false;
        }
        return valido;
    }
    
    /**
     * Thread per salvaguardare la conversione più rischiosa: da decimale a base 1
     */
    public void run()
    {
        while(true)
        {
            try
            {
                
                Thread.sleep(100);
            }
            catch(Exception e){}
        }
    }
}
