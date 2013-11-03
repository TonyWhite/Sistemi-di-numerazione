#! /bin/bash
# Definizione delle variabili
APPLICAZIONE="SistemiDiNumerazione.jar"
INSTALLAZIONE="/usr/share/sistemidinumerazione/"
BINARIO="/usr/bin/sistemidinumerazione"
ICONA="/usr/share/pixmaps/sistemidinumerazione.svg"
VOCE_DI_MENU="/usr/share/applications/sistemidinumerazione.desktop"

# Sei root?
if [ `whoami` == "root" ]; then
	echo "root: ok"
	
	# Elimina la voce di menu
	if [ -a $VOCE_DI_MENU ]; then
		rm $VOCE_DI_MENU
	fi
	echo "$VOCE_DI_MENU: removed"
	
	# Elimina l'icona della voce di menu
	if [ -a $ICONA ]; then
		rm $ICONA
	fi
	echo "$ICONA: removed"
	
	# Elimina lo script da /usr/bin
	if [ -a $BINARIO ]; then
		rm $BINARIO
	fi
	echo "$BINARIO: removed"
	
	# Elimina l'applicazione installata
	if [ -a "$INSTALLAZIONE$APPLICAZIONE" ]; then
		rm "$INSTALLAZIONE$APPLICAZIONE"
	fi
	echo "$INSTALLAZIONE$APPLICAZIONE: removed"
	
	# Elimina la cartella dell'applicazione
	if [ -a "$INSTALLAZIONE" ]; then
		rmdir $INSTALLAZIONE
	fi
	echo "$INSTALLAZIONE folder: removed"
	echo "Sistemi di Numerazione: UNINSTALLED"
else
	echo "Non hai i permessi di root"
fi