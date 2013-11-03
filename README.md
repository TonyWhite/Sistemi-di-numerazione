# ULTIMA VERSIONE
SistemiDiNumerazione.jar (v1.5)

## REQUISITI
Java 6

## COSA FA
* Questa è una inutility.
* Puoi convertire numeri interi positivi da zero a 9999999999999990;
* Puoi utilizzare 36 basi di conversione;
* Puoi convertire il testo in ASCII con la voce “TESTO”.

## CARATTERISTICHE E LIMITAZIONI
* 36 basi di conversione;
* Numero minimo da convertire: zero;
* Numero massimo da convertire: 9999999999999990 (base 10) = 2QGPCKVNG1I (base 36);
* Codifica le stringhe di testo in UTF-16 in tutte le basi disponibili;
* Puoi cambiare il tema grafico;
* Puoi cambiare il font;
* I cambiamenti si applicano immediatamente e si salvano automaticamente all’uscita del programma.

Il programma permette di convertire numeri interi positivi tra basi numeriche differenti.<br>
È possibile codificare il testo in ASCII rappresentato in diverse basi numeriche.

## ATTENZIONE
* La base 1 è matematicamente corretta e siccome la matematica non è un’opinione, il consumo di RAM sul numero massimo da convertire ammonta a 18’626’451,4923 GB. Cazzi tuoi.
* Ringrazio スニャブロ per avermi convinto ad inserire la conversione in base 1.

## INFO
* Ho scelto solo 36 basi perché l’esadecimale usa “cifre dell’alfabeto” sia maiuscole che minuscole, quindi i caratteri alfabetici impiegati come cifre vanno da A-Z. Ho usato solo le lettere maiuscole:
* Il numero massimo è solo nove biliardi novecentonovantanove bilioni novecentonovantanove miliardi novecentonovantanove milioni novecentonovantanove mila novecentonovanta a causa dell’imprecisione del double.

## NOVITÀ E BUGFIX
### 03 novembre 2013
* Corretta la posizione del file di configurazione
* Creato un file di installazione per i sistemi Linux

### 24 agosto 2012
* Il programma ha un suo file delle opzioni dove salvare le preferenze dell’utente;
* Il tema può essere cambiato in runtime;
* Il font può essere cambiato in runtime;
* Corretto il posizionamento dei componenti all’interno del pannello delle opzioni;
* Aggiunti i bordi dei menu GTK+ (non si aggiornano in runtime);
* Aggiunti i checkbox per lo stile del testo;
* Corretti errori di runtime: cambio del tema sui menu;
* Aggiunto spinner per cambiare dimensione del font;
* Workaround per bug di Java6: JSpinner perde l’allineamento quando cambia Tema;

### 23 agosto 2012
* La conversione “numero > base 1″ è implementata nel thread: è possibile vederne lo stato e fermarla;

### 22 agosto 2012
* Aggiunti menu taglia/copia/incolla/seleziona tutto alle aree di testo;
* Aggiunto il messaggio di errore per la conversione “numero > testo” indicante la base non corretta;
* Aggiunta area di testo per anteprima alle modifiche al font.