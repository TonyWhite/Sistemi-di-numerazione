/**
 * La finestra principale
 * 
 * Autore: Antonio Bianco
 */
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Font;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.net.URL;
import javax.swing.Action;
import javax.swing.BoxLayout;
import javax.swing.ComboBoxEditor;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerListModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.text.DefaultEditorKit;
import java.util.HashMap;
public class Finestra extends JFrame implements ActionListener, WindowListener, ChangeListener, Runnable
{
    // Componenti grafici
    JButton btnConverti;
    JButton btnInverti;
    JButton btnEsci;
    JComboBox cbbDa;
    JComboBox cbbA;
    JProgressBar progress;
    JTextArea txtDa;
    JTextArea txtA;
    JPopupMenu menuDa, menuA;
    
    // Componenti grafici delle opzioni
    JComboBox cbbTemi;
    JComboBox cbbFont;
    JCheckBox ckbGrassetto;
    JCheckBox ckbCorsivo;
    JSpinner spnDimensioneFont;
    JTextArea txtAnteprima;
    
    // Stato del programma
    boolean conversione = false;
    /**
     * Costruttore
     */
    public Finestra()
    {
        super("Sistemi di numerazione");
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(this);
        this.impostaIcona("img/128/binary.png");
        
        // Inizializzazione dei font
        //font = new Font(Opzioni.FONT_NAME, Opzioni.FONT_STILE, Opzioni.FONT_SIZE);
        Font font = new Font(Opzioni.FONT_NAME, Opzioni.FONT_STILE, Opzioni.FONT_SIZE);
        
        // Inizializzazione della lista dei combo box
        String[] listacodifiche = new String[37];
        listacodifiche[0] = "TESTO";
        for (int i=1; i<listacodifiche.length; i++)
        {
            String voce = i+"";
            switch (i)
            {
                case 2:  voce += " (BIN)";
                         break;
                case 8:  voce += " (OCT)";
                         break;
                case 10: voce += " (DEC)";
                         break;
                case 16: voce += " (HEX)";
                         break;
            }
            listacodifiche[i] = voce;
        }
        
        /****************************
         * Creazione dei componenti *
         ****************************/
        
        // Creazione dei CheckBox
        ckbGrassetto = new JCheckBox("Grassetto");
        if ((Opzioni.FONT_STILE==Font.BOLD)||(Opzioni.FONT_STILE==Font.BOLD+Font.ITALIC)) ckbGrassetto.setSelected(true);   // Seleziona il componente solo se il testo è grassetto
        ckbGrassetto.addActionListener(this);
        
        ckbCorsivo = new JCheckBox("Corsivo");
        if ((Opzioni.FONT_STILE==Font.ITALIC)||(Opzioni.FONT_STILE==Font.BOLD+Font.ITALIC)) ckbCorsivo.setSelected(true);   // Seleziona il componente solo se il testo è corsivo
        ckbCorsivo.addActionListener(this);
        
        // Creazione dei combo box
        cbbDa = new JComboBox(listacodifiche);
        //cbbDa.setEditable(true);
        cbbDa.addActionListener(this);
        
        cbbA = new JComboBox(listacodifiche);
        //cbbA.setEditable(true);
        cbbA.addActionListener(this);
        
        cbbTemi = new JComboBox(Opzioni.TEMI);
        cbbTemi.setSelectedItem(Opzioni.TEMA_CORRENTE);     // Seleziona il tema corrente
        cbbTemi.addActionListener(this);
        
        cbbFont = new JComboBox(Opzioni.LISTA_FONT);
        cbbFont.setSelectedItem(Opzioni.FONT_NAME);         // Seleziona il font corrente
        cbbFont.setToolTipText(Opzioni.FONT_NAME);          // Imposta il tooltip
        System.out.println("cbbFont.getPrototypeDisplayValue() = " + cbbFont.getPrototypeDisplayValue());
        cbbFont.setPrototypeDisplayValue("1234567890123456789012345");  // Imposta la larghezza del combobox a 25 caratteri
        cbbFont.addActionListener(this);
        
        // Inizializzazione dello spinner per la dimensione dei font
        String[] dimensioniCaratteri = new String[100];                                             // Array delle dimensioni da utilizzare
        for (int i=0; i<100; i++) dimensioniCaratteri[i] = (i+1) + "";                              // Si utilizzano dimensioni di carattere da 1 a 100
        SpinnerListModel modelloDimensioneCaratteri = new SpinnerListModel(dimensioniCaratteri);    // Si crea il modello di Spinner con le dimensioni di caratteri
        spnDimensioneFont = new JSpinner(modelloDimensioneCaratteri);                               // Si crea lo spinner con le dimensioni di caratteri da utilizzare
        {
            // Formatta il componente
            JComponent editor = spnDimensioneFont.getEditor();
            if (editor instanceof DefaultEditor)
            {
                ((DefaultEditor)editor).getTextField().setColumns(3);                               // Imposta una larghezza per 3 caratteri
                ((DefaultEditor)editor).getTextField().setHorizontalAlignment(JTextField.RIGHT);    // Allineamento del testo a destra
            }
        }
        // Visualizza la dimensione del font corrente
        spnDimensioneFont.setValue(Opzioni.FONT_SIZE+"");
        spnDimensioneFont.setToolTipText("Dimensione del font");
        spnDimensioneFont.addChangeListener(this);
        
        // Inizializzazione dei bottoni
        btnConverti = new JButton("Converti");
        btnConverti.setToolTipText("Converte nella base specificata");
        btnConverti.addActionListener(this);
        
        btnInverti = new JButton("Inverti");
        btnInverti.setToolTipText("Inverti i risultati nelle aree di testo");
        btnInverti.addActionListener(this);
        
        btnEsci = new JButton("Esci");
        btnEsci.setToolTipText("Esce dall'applicazione");
        btnEsci.addActionListener(this);
        
        // Inizializzazione della ProgressBar
        progress = new JProgressBar(SwingConstants.HORIZONTAL);
        
        // Aree di testo con scrolling e testo monospace
        txtDa = new JTextArea();
        txtDa.setFont(font);
        txtDa.setEditable(true);
        txtDa.setAutoscrolls(true);
        txtDa.setTabSize(2);
        txtDa.setLineWrap(true);
        txtDa.setWrapStyleWord(true);
        {
            /**
             * Crea le azioni copia/taglia/incolla per il componente
             * Crea il menu delle azioni
             * http://docs.oracle.com/javase/tutorial/uiswing/components/generaltext.html#commands
             */
            HashMap<Object, Action> mappaAzioni = new HashMap<Object, Action>();
            Action[] azioni = txtDa.getActions();
            for (int i = 0; i < azioni.length; i++) mappaAzioni.put(azioni[i].getValue(Action.NAME), azioni[i]);
            menuDa = new JPopupMenu(); // Crea il menu per questo componente
            menuDa.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.GRAY)); // Crea un bordo nero intorno al menu
            {
                JMenuItem menuTaglia = new JMenuItem(mappaAzioni.get(DefaultEditorKit.cutAction));
                menuTaglia.setText("Taglia");
                menuDa.add(menuTaglia);
                
                JMenuItem menuCopia = new JMenuItem(mappaAzioni.get(DefaultEditorKit.copyAction));
                menuCopia.setText("Copia");
                menuDa.add(menuCopia);
                
                JMenuItem menuIncolla = new JMenuItem(mappaAzioni.get(DefaultEditorKit.pasteAction));
                menuIncolla.setText("Incolla");
                menuDa.add(menuIncolla);
                
                menuDa.addSeparator();
                
                JMenuItem menuSelezionaTutto = new JMenuItem(mappaAzioni.get(DefaultEditorKit.selectAllAction));
                menuSelezionaTutto.setText("Seleziona tutto");
                menuDa.add(menuSelezionaTutto);
            }
            txtDa.setComponentPopupMenu(menuDa);
        }
        JScrollPane scrollDa = new JScrollPane(txtDa);
        
        txtA = new JTextArea();
        txtA.setFont(font);
        txtA.setEditable(true);
        txtA.setAutoscrolls(true);
        txtA.setTabSize(2);
        txtA.setLineWrap(true);
        txtA.setWrapStyleWord(true);
        {
            /**
             * Crea le azioni copia/taglia/incolla per il componente
             * Crea il menu delle azioni
             * http://docs.oracle.com/javase/tutorial/uiswing/components/generaltext.html#commands
             */
            HashMap<Object, Action> mappaAzioni = new HashMap<Object, Action>();
            Action[] azioni = txtA.getActions();
            for (int i = 0; i < azioni.length; i++) mappaAzioni.put(azioni[i].getValue(Action.NAME), azioni[i]);
            menuA = new JPopupMenu(); // Crea il menu per questo componente
            menuA.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.GRAY)); // Crea un bordo nero intorno al menu
            {
                JMenuItem menuTaglia = new JMenuItem(mappaAzioni.get(DefaultEditorKit.cutAction));
                menuTaglia.setText("Taglia");
                menuA.add(menuTaglia);
                
                JMenuItem menuCopia = new JMenuItem(mappaAzioni.get(DefaultEditorKit.copyAction));
                menuCopia.setText("Copia");
                menuA.add(menuCopia);
                
                JMenuItem menuIncolla = new JMenuItem(mappaAzioni.get(DefaultEditorKit.pasteAction));
                menuIncolla.setText("Incolla");
                menuA.add(menuIncolla);
                
                menuA.addSeparator();
                
                JMenuItem menuSelezionaTutto = new JMenuItem(mappaAzioni.get(DefaultEditorKit.selectAllAction));
                menuSelezionaTutto.setText("Seleziona tutto");
                menuA.add(menuSelezionaTutto);
            }
            txtA.setComponentPopupMenu(menuA);
        }
        JScrollPane scrollA = new JScrollPane(txtA);
        
        txtAnteprima = new JTextArea("123 456 789 ABCDEFGHIJKLMNOPQRSTUVWXYZ\n000 111 .,' abcdefghijklmnopqrstuvwxyz", 2, 39);
        txtAnteprima.setFont(font);
        txtAnteprima.setEditable(false);
        txtAnteprima.setAutoscrolls(true);
        txtAnteprima.setTabSize(2);
        txtAnteprima.setLineWrap(true);
        txtAnteprima.setWrapStyleWord(true);
        
        
        
        /************************************************
         * Posizionamento dei componenti nella finestra *
         ************************************************/
        JTabbedPane pannelloTab = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.WRAP_TAB_LAYOUT);
        pannelloTab.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1)); // Elimina l'artefatto grafico con un artefatto grafico. ESTIQAATZI.
        this.add(pannelloTab);
        JPanel pnlTabPrincipale = new JPanel(new BorderLayout());
        pannelloTab.addTab("Codifica", pnlTabPrincipale);
        
        JPanel pnlGenerale = new JPanel(new BorderLayout()); // Pannello generale: comandi e barra di avanzamento
        pnlTabPrincipale.add(pnlGenerale, BorderLayout.CENTER);
        
        // Barra di avanzamento
        pnlTabPrincipale.add(progress, BorderLayout.SOUTH);
        
        // Aree di testo
        JPanel pnlAreeTesto = new JPanel(new GridLayout(1, 2));
        pnlAreeTesto.add(scrollDa);
        pnlAreeTesto.add(scrollA);
        pnlGenerale.add(pnlAreeTesto, BorderLayout.CENTER);
        
        // Pannello dei controlli
        JPanel pnlControlli = new JPanel(new BorderLayout());
        pnlGenerale.add(pnlControlli, BorderLayout.SOUTH);
        
        // Controlli orientati a sinistra
        {
            JPanel pnlComandi = new JPanel(new FlowLayout());
            pnlComandi.add(btnConverti);
            pnlComandi.add(new JLabel("da"));
            pnlComandi.add(cbbDa);
            pnlComandi.add(new JLabel("a"));
            pnlComandi.add(cbbA);
            pnlControlli.add(pnlComandi, BorderLayout.WEST);
        }
        
        // Controlli orientati a destra
        {
            JPanel pnlComandi = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            pnlComandi.add(btnInverti);
            pnlComandi.add(btnEsci);
            pnlControlli.add(pnlComandi, BorderLayout.EAST);
        }
        
        
        /******************************************
         * Posiziona i componenti nel secondo Tab *
         ******************************************/
        JPanel pnlTabOpzioni = new JPanel(new BorderLayout());
        pannelloTab.addTab("Opzioni", pnlTabOpzioni);
        
        JPanel pnlTabOpzioniSuperiore = new JPanel();
        pnlTabOpzioniSuperiore.setLayout(new BoxLayout(pnlTabOpzioniSuperiore, BoxLayout.Y_AXIS));
        {
            JPanel pnlTmp = new JPanel(new FlowLayout(FlowLayout.LEFT));   // Pannello di convenienza: serve a contenere le dimensioni del pannello pnlTabOpzioni; il layout allinea i componenti a sinistra
            pnlTmp.add(pnlTabOpzioniSuperiore);
            pnlTabOpzioni.add(pnlTmp, BorderLayout.NORTH);
            //pannelloTab.addTab("Opzioni", pnlTmp);
        }
        
        // Scelta del tema
        {
            JPanel pnlTema = new JPanel(new FlowLayout(FlowLayout.LEFT));
            pnlTema.add(new JLabel("Tema grafico"));
            pnlTema.add(cbbTemi);
            pnlTabOpzioniSuperiore.add(pnlTema);
        }
        
        // Scelta del font
        {
            JPanel pnlFont = new JPanel(new FlowLayout(FlowLayout.LEFT));
            pnlFont.add(new JLabel("Font"));        // Nome del font
            pnlFont.add(cbbFont);
            pnlFont.add(ckbGrassetto);              // Stile grassetto
            pnlFont.add(ckbCorsivo);                // Stile corsivo
            pnlFont.add(spnDimensioneFont);         // Dimensione del font
            pnlTabOpzioniSuperiore.add(pnlFont);
        }
        
        // Area per l'anteprima del testo
        pnlTabOpzioni.add(txtAnteprima, BorderLayout.CENTER);
        
        
        /*****************************************************
         * Crea il tab con le informazioni dell'applicazione *
         *****************************************************/
        JPanel pnlTabInfo = new JPanel(new BorderLayout());
        pannelloTab.addTab("Info", pnlTabInfo);
        
        {
            JPanel pnlCentrato = new JPanel();  // Pannello centrale
            pnlCentrato.setLayout(new BoxLayout(pnlCentrato, BoxLayout.Y_AXIS));
            
            JLabel lblLogoProgramma = new JLabel(creaIcona("img/128/binary.png"), JLabel.CENTER);
            JPanel pnlLogo = new JPanel();
            pnlLogo.add(lblLogoProgramma);
            JLabel lblTitolo = new JLabel("Sistemi Di Numerazione", JLabel.CENTER);
            lblTitolo.setFont(lblTitolo.getFont().deriveFont(Font.BOLD));   // Applica lo stile grassetto
            JPanel pnlTitolo = new JPanel();
            pnlTitolo.add(lblTitolo);
            JLabel lblVersione = new JLabel("1.5", JLabel.CENTER);
            JPanel pnlVersione = new JPanel();
            pnlVersione.add(lblVersione);
            JLabel lblDescrizione_1 = new JLabel("Il programma permette di convertire numeri interi positivi tra basi numeriche differenti.", JLabel.CENTER);
            JPanel pnlDescrizione_1 = new JPanel();
            pnlDescrizione_1.add(lblDescrizione_1);
            JLabel lblDescrizione_2 = new JLabel("È possibile codificare il testo in ASCII rappresentato in diverse basi numeriche.", JLabel.CENTER);
            JPanel pnlDescrizione_2 = new JPanel();
            pnlDescrizione_2.add(lblDescrizione_2);
            URLJLabel lblWebPage = new URLJLabel("www.antoniobianco.altervista.org/java/SistemiDiNumerazione", JLabel.CENTER, "http://www.antoniobianco.altervista.org/java/SistemiDiNumerazione/");
            JPanel pnlWebPage = new JPanel();
            pnlWebPage.add(lblWebPage);
            JLabel lblDiritti = new JLabel("Copyleft © 2011-2012 Antonio Bianco", JLabel.CENTER);
            JPanel pnlDiritti = new JPanel();
            pnlDiritti.add(lblDiritti);
            URLJLabel lblLicenza = new URLJLabel(creaIcona("img/128/gplv3.png"), JLabel.CENTER, "http://www.gnu.org/licenses/gpl.html");
            JPanel pnlLicenza = new JPanel();
            pnlLicenza.add(lblLicenza);
            
            pnlCentrato.add(pnlLogo);
            pnlCentrato.add(pnlTitolo);
            pnlCentrato.add(pnlVersione);
            pnlCentrato.add(pnlDescrizione_1);
            pnlCentrato.add(pnlDescrizione_2);
            pnlCentrato.add(pnlWebPage);
            pnlCentrato.add(pnlDiritti);
            pnlCentrato.add(pnlLicenza);
            
            JPanel pnlCentratoPack = new JPanel();  // Pannello di convenienza per contenere le dimensioni
            pnlCentratoPack.add(pnlCentrato);
            pnlTabInfo.add(pnlCentratoPack, BorderLayout.CENTER);
        }
        
        Dimension dimensioni = Toolkit.getDefaultToolkit().getScreenSize();
        setSize((int)(dimensioni.getWidth()/100*50), (int)(dimensioni.getHeight()/100*50));
        setLocation((int)(dimensioni.getWidth()/2-getWidth()/2), (int)(dimensioni.getHeight()/2-getHeight()/2));
        this.setVisible(true);
        txtDa.requestFocus();   // Setta il focus alla prima area di testo
    }
    
    /**
     * Avvia o ferma la conversione
     */
    private void converti()
    {
        conversione= !conversione;
    }
    
    public void run()
    {
        while(true) // Mantiene il thread attivo
        {
            try
            {
                Thread.sleep(50);
            }
            catch(Exception e){}
            if(conversione) // Si muove solo quando inizia una conversione (es.: fronte di salita)
            {
                // Avviso sui numeri in base 1
                if (cbbA.getSelectedIndex()==1)
                {
                    if (JOptionPane.YES_OPTION!=JOptionPane.showConfirmDialog(this,"ATTENZIONE!\n\nL'usoimpropriopuòesserenocivotenerefuoridallaportatadeinabbini\nVuoi continuare ugualmente?", this.getTitle(), JOptionPane.YES_NO_OPTION))
                    {
                        conversione = false;
                    }
                    //System.out.println("ATTENZIONE! L'usoimpropriopuòesserenocivotenerefuoridallaportatadeinabbini.");
                }
                
                // Preparazione alla conversione: viene eseguito una sola volta
                txtDa.setEditable(false);
                txtA.setEditable(false);
                btnConverti.setText("Stop");
                btnConverti.setToolTipText("Ferma la conversione");
                btnInverti.setEnabled(false);
                btnEsci.setEnabled(false);
                cbbDa.setEnabled(false);
                cbbA.setEnabled(false);
                progress.setIndeterminate(true);
                
                // Cicli di conversione
                while(conversione)
                {
                    try
                    {
                        if (txtDa.getText().length()>0) // Converti solo se la lunghezza della stringa da convertire è maggiore di zero
                        {
                            int base_da = cbbDa.getSelectedIndex();
                            int base_a = cbbA.getSelectedIndex();
                            /**
                             * Converti per:
                             * TESTO → TESTO: prendi per il culo
                             * TESTO → NUMERO: converte ciascun carattere in ascii al valore specificato nella base
                             * NUMERO → TESTO: codifica ogni numero nel carattere corrispondente
                             * NUMERO → NUMERO: facile
                             */
                            txtA.setText("");
                            if ((base_da==0)&&(base_a==0)) // TESTO → TESTO: prendi per il culo
                            {
                                JOptionPane.showMessageDialog(this, "GUARDA...\nIo te lo scrivo lo stesso il risultato,\nMa tu promettimi di fare qualcosa per quei neuroni diversamente grigi che hai, OK?", this.getTitle(), JOptionPane.INFORMATION_MESSAGE);
                                txtA.setText(txtDa.getText());
                                progress.setIndeterminate(false);
                            }
                            else if ((base_da==0)&&(base_a>0)) // TESTO → NUMERO: converte ciascun carattere in ascii al valore specificato nella base
                            {
                                String testo = txtDa.getText();
                                String risultato = "";
                                progress.setIndeterminate(false);
                                progress.setMaximum(testo.length());
                                for (int i=0; (i<testo.length())&&(conversione); i++)
                                {
                                    progress.setValue(i);
                                    if (base_a==1)
                                    {
                                        // converte in base 10 e poi in base 1
                                        int intero = Integer.parseInt(Conversioni.charToBase(testo.charAt(i), 10));
                                        long avanzamento = 0;
                                        long avanzamentoDaVisualizzare = 0;
                                        int stepAvanzamento = 50;
                                        for (int j=0; (j<=intero)&&(conversione); j++) // Per il ciclo alta tensione..
                                        {
                                            risultato += "0";
                                            avanzamento++;
                                            if (avanzamento>=avanzamentoDaVisualizzare)
                                            {
                                                txtA.setText(avanzamento + "/" + intero);
                                                avanzamentoDaVisualizzare = avanzamento + stepAvanzamento;
                                                if (avanzamentoDaVisualizzare>intero) avanzamentoDaVisualizzare=intero;
                                            }
                                            Thread.sleep(1);
                                        }
                                        if (conversione)    // Se è finito bene
                                        {
                                            risultato += " ";
                                        }
                                        else                // Se è stato interrotto
                                        {
                                            risultato = "";
                                            JOptionPane.showMessageDialog(this, "La conversione è stata annullata dall'utente.", this.getTitle(), JOptionPane.INFORMATION_MESSAGE);
                                        }
                                    }
                                    else
                                    {
                                        risultato += Conversioni.charToBase(testo.charAt(i), base_a) + " ";
                                    }
                                    Thread.sleep(1);
                                }
                                risultato = risultato.trim(); // Toglie gli spazi iniziali e finali
                                txtA.setText(risultato);
                                progress.setValue(0);
                            }
                            else if ((base_da>0)&&(base_a==0)) // NUMERO → TESTO: codifica ogni numero nel carattere corrispondente
                            {
                                String[] numeri = txtDa.getText().split(" ");
                                String risultato = "";
                                progress.setIndeterminate(false);
                                progress.setMaximum(numeri.length);
                                for (int i=0; (i<numeri.length)&&(conversione); i++)
                                {
                                    String tmp = Conversioni.baseToDec(numeri[i], base_da);
                                    if (tmp.startsWith(Conversioni.ERRORE))
                                    {
                                        conversione = false;
                                        JOptionPane.showMessageDialog(this, "Il numero da convertire non è espresso in base " + base_da, this.getTitle(), JOptionPane.WARNING_MESSAGE);
                                    }
                                    else
                                    {
                                        int ascii = new Integer(tmp);
                                        char[] carattere = Character.toChars(ascii);
                                        risultato += carattere[0] + "";
                                        progress.setValue(i);
                                    }
                                    Thread.sleep(1);
                                }
                                risultato = risultato.trim(); // Toglie gli spazi iniziali e finali
                                txtA.setText(risultato);
                                progress.setValue(0);
                            }
                            else if ((base_da>0)&&(base_a>0)) // NUMERO → NUMERO: facile
                            {
                                String[] numeri = txtDa.getText().split(" ");
                                String risultato = "";
                                progress.setIndeterminate(false);
                                progress.setMaximum(numeri.length);
                                for (int i=0; (i<numeri.length)&&(conversione); i++)
                                {
                                    String tmp = Conversioni.baseToDec(numeri[i], base_da);
                                    if (tmp.startsWith(Conversioni.ERRORE))
                                    {
                                        conversione = false;
                                        JOptionPane.showMessageDialog(this, "Il numero da convertire non è espresso in base " + base_da, this.getTitle(), JOptionPane.WARNING_MESSAGE);
                                    }
                                    else
                                    {
                                        progress.setValue(i);
                                        if (base_a==1)
                                        {
                                            Long intero = new Long(tmp); // Istruzione con pericolo di overflow
                                            if (intero > Conversioni.MAX_DEC)
                                            {
                                                conversione = false;
                                                JOptionPane.showMessageDialog(this, "Overflow:\nIl numero da convertire è maggiore di " + Conversioni.MAX_DEC, this.getTitle(), JOptionPane.WARNING_MESSAGE);
                                            }
                                            else
                                            {
                                                long avanzamento = 0;
                                                long avanzamentoDaVisualizzare = 0;
                                                int stepAvanzamento = 50;
                                                for (int j=0; (j<=intero)&&(conversione); j++) // Per il ciclo alta tensione...
                                                {
                                                    risultato += "0";
                                                    avanzamento++;
                                                    if (avanzamento>=avanzamentoDaVisualizzare)
                                                    {
                                                        txtA.setText(avanzamento + "/" + intero);
                                                        avanzamentoDaVisualizzare = avanzamento + stepAvanzamento;
                                                        if (avanzamentoDaVisualizzare>intero) avanzamentoDaVisualizzare=intero;
                                                    }
                                                    Thread.sleep(1);
                                                }
                                                if (conversione)    // Se è finito bene
                                                {
                                                    risultato += " ";
                                                }
                                                else                // Se è stato interrotto
                                                {
                                                    risultato = "";
                                                    JOptionPane.showMessageDialog(this, "La conversione è stata annullata dall'utente.", this.getTitle(), JOptionPane.INFORMATION_MESSAGE);
                                                }
                                            }
                                        }
                                        else
                                        {
                                            risultato += Conversioni.decToBase(tmp, base_a) + " ";
                                        }
                                    }
                                    Thread.sleep(1);
                                }
                                risultato = risultato.trim(); // Toglie gli spazi iniziali e finali
                                txtA.setText(risultato);
                                progress.setValue(0);
                            }
                        }
                        else
                        {
                            JOptionPane.showMessageDialog(this, "Non credi che manchi qualcosa?", this.getTitle(), JOptionPane.INFORMATION_MESSAGE);
                            txtDa.setText("Inserisci in questa casella il testo o il numero da convertire.");
                        }
                        conversione = false;
                    }
                    catch(Exception e)
                    {
                        // Qualsiasi errore di conversione
                        conversione = false;
                    }
                }
                // Fine della conversione (es.: fronte di discesa)
                progress.setIndeterminate(false);
                txtDa.setEditable(true);
                txtA.setEditable(true);
                btnConverti.setText("Converti");
                btnConverti.setToolTipText("Converte nella base specificata");
                btnInverti.setEnabled(true);
                btnEsci.setEnabled(true);
                cbbDa.setEnabled(true);
                cbbA.setEnabled(true);
            }
        }
    }
    
    /**
     * Aggiorna il font delle caselle di testo
     */
    private void aggiornaFont()
    {
        Font font = new Font(Opzioni.FONT_NAME, Opzioni.FONT_STILE, Opzioni.FONT_SIZE);
        txtDa.setFont(font);
        txtA.setFont(font);
        txtAnteprima.setFont(font);
    }
    
    /**
     * Esce dal programma
     */
    private void esci()
    {
        if (conversione) return;
        if (JOptionPane.YES_OPTION==JOptionPane.showConfirmDialog(this,"Vuoi uscire dall'inutility?", this.getTitle(), JOptionPane.YES_NO_OPTION))
        {
            SistemiDiNumerazione.salvaOpzioni();
            System.exit(0);
        }
    }
    
    /**
     * Crea un'icona generica per qualsiasi componente
     */
    private ImageIcon creaIcona(String path)
    {
        URL imgURL = Finestra.class.getResource(path);
        if (imgURL != null)
        {
            return new ImageIcon(imgURL);
        }
        else
        {
            System.err.println("Impossibile trovare l'immagine " + path);
            return null;
        }
    }
    
    /**
     * Imposta l'icona per la finestra corrente
     */
    private void impostaIcona(String path)
    {
        URL imgURL = Finestra.class.getResource(path);
        if (imgURL != null)
        {
            setIconImage(Toolkit.getDefaultToolkit().getImage(imgURL));
        }
        else
        {
            System.err.println("Impossibile trovare " + imgURL.getPath() + "\nFile: " + imgURL.getFile());
        }
    }
    
    /**
     * Ascoltatore dei bottoni
     */
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource().equals((Object)btnConverti))
        {
            converti();
        }
        else if (e.getSource().equals((Object)btnInverti))
        {
            String tmp = txtDa.getText();
            txtDa.setText(txtA.getText());
            txtA.setText(tmp);
            
            int indice = cbbDa.getSelectedIndex();
            cbbDa.setSelectedIndex(cbbA.getSelectedIndex());
            cbbA.setSelectedIndex(indice);
        }
        else if (e.getSource().equals((Object)cbbDa))
        {
            //int scelta = cbbDa.getSelectedIndex();
            //if (scelta==1)
            //{
                // Barra di stato con Warning
                //JOptionPane.showMessageDialog(this, "ATTENZIONE!\n\nL'usoimpropriopuòesserenocivotenerefuoridallaportatadeinabbini", this.getTitle(), JOptionPane.WARNING_MESSAGE);
            //}
        }
        else if (e.getSource().equals((Object)cbbA))
        {
            //int scelta = cbbA.getSelectedIndex();
            //if (scelta==1)
            //{
                // Barra di stato con Warning
                //JOptionPane.showMessageDialog(this, "ATTENZIONE!\n\nL'usoimpropriopuòesserenocivotenerefuoridallaportatadeinabbini", this.getTitle(), JOptionPane.WARNING_MESSAGE);
            //}
        }
        else if (e.getSource().equals((Object)cbbTemi))
        {
            // Aggiorna il tema corrente
            Opzioni.TEMA_CORRENTE = (String)cbbTemi.getSelectedItem();
            SistemiDiNumerazione.cambiaTema();
            SwingUtilities.updateComponentTreeUI(menuDa);   // Aggiorna il tema dei menu
            SwingUtilities.updateComponentTreeUI(menuA);
            /*
             * bug di Java6: JSpinner perde l'allineamento quando cambia Tema
             * CORRETTO
             */
            JComponent editor = spnDimensioneFont.getEditor();
            if (editor instanceof DefaultEditor) ((DefaultEditor)editor).getTextField().setHorizontalAlignment(JTextField.RIGHT);    // Allineamento del testo a destra
        }
        else if (e.getSource().equals((Object)cbbFont))
        {
            // Aggiorna il font corrente
            Opzioni.FONT_NAME = (String)cbbFont.getSelectedItem();
            cbbFont.setToolTipText(Opzioni.FONT_NAME);
            aggiornaFont();
        }
        else if ((e.getSource().equals((Object)ckbGrassetto))||(e.getSource().equals((Object)ckbCorsivo)))
        {
            // Aggiorna lo stile del font corrente
            Opzioni.FONT_STILE = 0;
            if (ckbGrassetto.isSelected())
            {
                Opzioni.FONT_STILE = Font.BOLD;
            }
            if (ckbCorsivo.isSelected())
            {
                Opzioni.FONT_STILE += Font.ITALIC;
            }
            aggiornaFont();
        }
        else if (e.getSource().equals((Object)btnEsci))
        {
            esci();
        }
        else
        {
            System.out.println("DEBUG: Nessun'azione associata al componente.");
        }
    }
    
    /**
     * Ascoltatore del JSpinner
     */
    public void stateChanged(ChangeEvent e)
    {
        if (e.getSource().equals((Object)spnDimensioneFont))
        {
            try
            {
                Opzioni.FONT_SIZE = Integer.parseInt((String)spnDimensioneFont.getValue());
                aggiornaFont();
            }
            catch(Exception exc){}
        }
    }
    
    /**
     * Ascoltatore della finestra
     */
    public void windowActivated(WindowEvent e) {}
    public void windowClosed(WindowEvent e) {}
    public void windowClosing(WindowEvent e)
    {
        esci();
    }
    public void windowDeactivated(WindowEvent e) {}
    public void windowDeiconified(WindowEvent e) {}
    public void windowIconified(WindowEvent e) {}
    public void windowOpened(WindowEvent e) {}
}