#! /bin/bash
# Definizione delle variabili
APPLICAZIONE="SistemiDiNumerazione.jar"
INSTALLAZIONE="/usr/share/sistemidinumerazione/"
BINARIO="/usr/bin/sistemidinumerazione"
ICONA="sistemidinumerazione.svg"
ICONE="/usr/share/pixmaps/"
VOCE_DI_MENU="sistemidinumerazione.desktop"
VOCI_DI_MENU="/usr/share/applications/"

# Sei root?
if [ `whoami` == "root" ]; then
	echo "root: ok"
	
	# L'aplicazione esiste?
	if [ -a $APPLICAZIONE ]; then
		echo "$APPLICAZIONE: ok"
		
		# L'icona esiste?
		if [ -a $ICONA ]; then
			echo "icon: ok"
			
			# La voce di menu esiste?
			if [ -a $VOCE_DI_MENU ]; then
				echo "menu item: ok"
				
				# Crea la cartella di installazione
				mkdir $INSTALLAZIONE
				# Copia il file nella cartella di installazione
				cp $APPLICAZIONE $INSTALLAZIONE
				
				# Crea l'eseguibile in /usr/bin
				echo "java -jar $INSTALLAZIONE/$APPLICAZIONE" > $BINARIO
				chmod +x $BINARIO
				
				# Crea l'icona
				cp $ICONA $ICONE
				
				# Crea la voce di menù
				cp $VOCE_DI_MENU $VOCI_DI_MENU
				
				echo "Sistemi di Numerazione: INSTALLED"
			else
				echo "menu item: no"
			fi
			
		else
			echo "icon: no"
		fi
	else
		echo "$APPLICAZIONE: no"
	fi
else
	echo "Non hai i permessi di root"
fi