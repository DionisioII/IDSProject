# Overview

Il progetto ha come obiettivo la realizzazione di una app per Android di notifica emergenze ed evacuazione indoor all'interno dell'università di Ancona. Il compito della applicazione sarà di allertare l’utente in caso di emergenza e guidarlo, attraverso un percorso illustrato su una mappa, verso il più vicino punto di raccolta; essa avrà la funzionalità di navigatore ossia guiderà l’utente dal punto in cui si trova verso l'uscita più sicura mostrando sempre il percorso su una mappa.
L'applicazione, inoltre, svolgerà la funzionalità di navigatore anche in caso di non emergenza indicando il percorso più breve tra due punti di interesse scelti dall'utente.

Il percorso è calcolato tramite l'algoritmo di dijkstra; In caso di funzionamento normale o offline il peso dei percorsi dipende esclusivamente dalla distanza fisica.

Al primo avvio viene chiesto di registrarsi con nome utente e password; a registrazione effettuata l'applicazione inizia a comunicare con un server che segnala la presenza di eventuali emergenze all'interno dell'edificio.
In caso di emergenza il server cambia dinamicamente i pesi degli archi segnalando i percorsi impraticabili o affollati.
