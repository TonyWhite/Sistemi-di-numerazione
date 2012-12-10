/**
 * JLabel cliccabile con URL
 * 
 * Autore: Antonio Bianco
 * Copyright (c) 2012
 */
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.font.TextAttribute;
import java.net.URI;
import java.util.Hashtable;
import java.util.Map;
import javax.swing.Icon;
import javax.swing.JLabel;
public class URLJLabel extends JLabel implements MouseListener
{
    private String url;
    
    /**
     * Crea l'istanza di una URLJLabel senza immagine e nessun testo.
     */
    public URLJLabel()
    {
        super();
        setProprieta();
        addMouseListener(this);
    }
    
    /**
     * Crea l'istanza di una URLJLabel specificando l'immagine.
     */
    public URLJLabel(Icon image)
    {
        super(image);
        setProprieta();
        addMouseListener(this);
    }
    
    /**
     * Crea l'istanza di una URLJLabel specificando l'immagine e l'url.
     */
    public URLJLabel(Icon image, String url)
    {
        super(image);
        setProprieta();
        setUrl(url);
        addMouseListener(this);
    }
    
    /**
     * Crea l'istanza di una URLJLabel specificando l'immagine e l'allineamento orizzontale.
     */
    public URLJLabel(Icon image, int horizontalAlignment)
    {
        super(image, horizontalAlignment);
        setProprieta();
        addMouseListener(this);
    }
    
    /**
     * Crea l'istanza di una URLJLabel specificando l'immagine, l'allineamento orizzontale e l'url.
     */
    public URLJLabel(Icon image, int horizontalAlignment, String url)
    {
        super(image, horizontalAlignment);
        setProprieta();
        setUrl(url);
        addMouseListener(this);
    }
    
    /**
     * Crea l'istanza di una URLJLabel specificando il testo.
     */
    public URLJLabel(String text)
    {
        super(text);
        setProprieta();
        addMouseListener(this);
    }
    
    /**
     * Crea l'istanza di una URLJLabel specificando il testo e l'url.
     */
    public URLJLabel(String text, String url)
    {
        super(text);
        setProprieta();
        setUrl(url);
        addMouseListener(this);
    }
    
    /**
     * Crea l'istanza di una URLJLabel specificando il testo, l'immagine e l'allineamento orizzontale.
     */
    public URLJLabel(String text, Icon icon, int horizontalAlignment)
    {
        super(text, icon, horizontalAlignment);
        setProprieta();
        addMouseListener(this);
    }
    
    /**
     * Crea l'istanza di una URLJLabel specificando il testo, l'immagine e l'url.
     */
    public URLJLabel(String text, Icon icon, String url)
    {
        super(icon);
        setText(text);
        setProprieta();
        setUrl(url);
        addMouseListener(this);
    }
    
    /**
     * Crea l'istanza di una URLJLabel specificando il testo, l'immagine, l'allineamento orizzontale e l'url.
     */
    public URLJLabel(String text, Icon icon, int horizontalAlignment, String url)
    {
        super(text, icon, horizontalAlignment);
        setProprieta();
        setUrl(url);
        addMouseListener(this);
    }
    
    /**
     * Crea l'istanda di una URLJlabel specificando il testo e l'allineamento orizzontale.
     */
    public URLJLabel(String text, int horizontalAlignment)
    {
        super(text, horizontalAlignment);
        setProprieta();
        addMouseListener(this);
    }
    
    /**
     * Crea l'istanda di una URLJlabel specificando il testo, l'allineamento orizzontale e l'url.
     */
    public URLJLabel(String text, int horizontalAlignment, String url)
    {
        super(text, horizontalAlignment);
        setProprieta();
        setUrl(url);
        addMouseListener(this);
    }
    
    /** Imposta le proprietà del componente */
    private void setProprieta()
    {
        setForeground(Color.BLUE);                  // Testo blu
        setCursor(new Cursor(Cursor.HAND_CURSOR));  // Cursore mano
        Map<TextAttribute, Object> map = new Hashtable<TextAttribute, Object>();
        map.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        setFont(getFont().deriveFont(map));
    }
    
    /**
     * Ritorna l'url
     */
    public String getUrl()
    {
        return url;
    }
    
    /**
     * Imposta l'url
     */
    public void setUrl(String url)
    {
        this.url = url;
        setToolTipText(url);
    }
    
    /**
     * Avvia l'url come se il componente avesse ricevuto il click. Ritorna false se si verifica un errore.
     */
    public boolean click()
    {
        boolean errore = false;
        if (Desktop.isDesktopSupported())
        {
            try
            {
                errore = true;                                  // Controllo ridondante dell'errore
                Desktop.getDesktop().browse(new URI(url));      // Avvia l'url
                setForeground(Color.BLUE.darker());             // Testo blu scuro
                errore = false;
            }
            catch(Exception e)
            {
                errore = true;
            }
        }
        return errore;
    }
    
    /** Invoked when the mouse button has been clicked (pressed and released) on a component. */
    public void mouseClicked(MouseEvent e)
    {
        click();
    }
    
    /** Invoked when the mouse enters a component. */
    public void mouseEntered(MouseEvent e) {}
    
    /** Invoked when the mouse exits a component. */
    public void mouseExited(MouseEvent e) {}
    
    /** Invoked when a mouse button has been pressed on a component. */
    public void mousePressed(MouseEvent e) {}
    
    /** Invoked when a mouse button has been released on a component. */
    public void mouseReleased(MouseEvent e) {}
}