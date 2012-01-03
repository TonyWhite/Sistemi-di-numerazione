/**
 * Write a description of class Finestra here.
 * 
 * Antonio Bianco
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class Finestra extends JFrame implements ActionListener, WindowListener, Runnable
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
    Font monospace;
    
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
        
        // Inizializzazione dei font
        monospace = new Font(Font.MONOSPACED, Font.PLAIN, 12);
        
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
        
        // Creazione dei combo box
        cbbDa = new JComboBox(listacodifiche);
        //cbbDa.setEditable(true);
        cbbDa.addActionListener(this);
        
        cbbA = new JComboBox(listacodifiche);
        //cbbA.setEditable(true);
        cbbA.addActionListener(this);
        
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
        txtDa.setFont(monospace);
        txtDa.setEditable(true);
        txtDa.setAutoscrolls(true);
        txtDa.setTabSize(2);
        txtDa.setLineWrap(true);
        txtDa.setWrapStyleWord(true);
        JScrollPane scrollDa = new JScrollPane(txtDa);
        
        txtA = new JTextArea();
        txtA.setFont(monospace);
        txtA.setEditable(true);
        txtA.setAutoscrolls(true);
        txtA.setTabSize(2);
        txtA.setLineWrap(true);
        txtA.setWrapStyleWord(true);
        JScrollPane scrollA = new JScrollPane(txtA);
        
        /************************************************
         * Posizionamento dei componenti nella finestra *
         ************************************************/
        this.setLayout(new BorderLayout());
        JPanel pnlGenerale = new JPanel(new BorderLayout()); // Pannello generale: comandi e barra di avanzamento
        this.add(pnlGenerale, BorderLayout.CENTER);
        
        // Barra di avanzamento
        this.add(progress, BorderLayout.SOUTH);
        
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
        
        Dimension dimensioni = Toolkit.getDefaultToolkit().getScreenSize();
        setSize((int)(dimensioni.getWidth()/100*50), (int)(dimensioni.getHeight()/100*50));
        setLocation((int)(dimensioni.getWidth()/2-getWidth()/2), (int)(dimensioni.getHeight()/2-getHeight()/2));
        this.setVisible(true);
    }
    
    /**
     * Avvia o ferma la conversione
     */
    private void converti()
    {
        if (conversione) conversione=false;
        else conversione = true;
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
                        if (txtDa.getText().length()>0) // Converti solo se 
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
                                JOptionPane.showMessageDialog(this, "GUARDA...\n\nIo te lo scrivo lo stesso il risultato,\nMa tu promettimi di fare qualcosa per quei neuroni diversamente grigi che hai, OK?", this.getTitle(), JOptionPane.INFORMATION_MESSAGE);
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
                                    risultato += Conversioni.charToBase(testo.charAt(i), base_a) + " ";
                                    progress.setValue(i);
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
                                    int ascii = new Integer(tmp);
                                    char[] carattere = Character.toChars(ascii);
                                    risultato += carattere[0] + "";
                                    progress.setValue(i);
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
                                    }
                                    else
                                    {
                                        risultato += Conversioni.decToBase(tmp, base_a) + " ";
                                        progress.setValue(i);
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
                            txtDa.setText("Inserisci in questa casella il testo o numero da convertire");
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
     * Esce dal programma
     */
    private void esci()
    {
        if (conversione) return;
        if (JOptionPane.YES_OPTION==JOptionPane.showConfirmDialog(this,"Vuoi uscire dall'inutility?", this.getTitle(), JOptionPane.YES_NO_OPTION))
        {
            System.exit(0);
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
            int scelta = cbbDa.getSelectedIndex();
            if (scelta==1)
            {
                JOptionPane.showMessageDialog(this, "ATTENZIONE!\n\nL'usoimpropriopuòesserenocivotenerefuoridallaportatadeinabbini", this.getTitle(), JOptionPane.WARNING_MESSAGE);
            }
        }
        else if (e.getSource().equals((Object)cbbA))
        {
            int scelta = cbbA.getSelectedIndex();
            if (scelta==1)
            {
                JOptionPane.showMessageDialog(this, "ATTENZIONE!\n\nL'usoimpropriopuòesserenocivotenerefuoridallaportatadeinabbini", this.getTitle(), JOptionPane.WARNING_MESSAGE);
            }
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
