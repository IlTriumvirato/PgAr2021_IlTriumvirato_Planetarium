package arnaldo.anno2021.triumvirato.planetarium;

import java.util.Scanner;
import java.util.ArrayList;

public class PlanetariumMain {

	public static SistemaStellare sistema;
	final static String MESSAGGIO_INIZIALE="Benvenuto, inserisci il nome del sistema, sarà necessario inserire una stella per inserirvi poi gli altri pianeti e le lune";
	//fai un controllo sulla possibile quantità di input
	final static String MENU="\nScegli una delle seguenti opzioni:"
			+ "\n1-Inserisci una stella (se non già presente);"
			+ "\n2-Inserisci un pianeta;"
			+ "\n3-inserisci una luna;"
			+ "\n4-rimuovi un corpo celeste e tutti i corpi che gli orbitano attorno;"
			+ "\n5-controlla se un corpo celeste è presente;"
			+ "\n6-identifica il pianeta intorno al quale orbita una luna;"
			+ "\n7-visualizza le lune che orbitano attorno a un pianeta;"
			+ "\n8-trova il percorso di un corpo celeste;"
			+ "\n9-calcolo del centro di massa;"
			+ "\n10-visualizza le coordinate di un corpo celeste, in riferimento al centro di massa;"
			+ "\n11-visualizza le coordinate di un corpo celeste, in riferimento al corpo attorno al quale orbita;"
			+ "\n12-visualizza elenco contenuti del sistema;"
			+ "\n13-visualizza opzioni Extra;"
			+ "\n14-chiusura del programma.";
	final static String MENU_EXTRA="\nScegli una delle seguenti opzioni:"
			+ "\n1-Inserisci una stella (se non già presente);"
			+ "\n2-Inserisci un pianeta;"
			+ "\n3-inserisci una luna;"
			+ "\n4-rimuovi un corpo celeste e tutti i corpi che gli orbitano attorno;"
			+ "\n5-controlla se un corpo celeste è presente;"
			+ "\n6-identifica il pianeta intorno al quale orbita una luna;"
			+ "\n7-visualizza le lune che orbitano attorno a un pianeta;"
			+ "\n8-trova il percorso di un corpo celeste;"
			+ "\n9-calcolo del centro di massa;"
			+ "\n10-visualizza le coordinate di un corpo celeste, in riferimento al centro di massa;"
			+ "\n11-visualizza le coordinate di un corpo celeste, in riferimento al corpo attorno al quale orbita;"
			+ "\n12-visualizza elenco contenuti del sistema;"
			+ "\n13-nascondi opzioni Extra;"
			+ "\n14-chiusura del programma;"
			+ "\n15-trova la rotta da un corpo celeste ad un altro;"
			+ "\n16-individua eventuali collisioni.";
	final static int EXTRA_KEY=13;
	final static int INPUT_CHIUSURA=14;
	
	
	public static Stella inserisciStella() {
		
		String id;
		double massa;
		double coordinataX;
		double coordinataY;
		
		Scanner scan=new Scanner(System.in);
		
		System.out.println("Inserisci l'id della stella\n");
		id=scan.next();
		System.out.println("Inserisci la massa della stella\n");
		massa=scan.nextDouble();
		System.out.println("Inserisci la coordinata X assoluta della stella\n");
		coordinataX=scan.nextDouble();
		System.out.println("Inserisci la coordinata Y assoluta della stella\n");
		coordinataY=scan.nextDouble();
		
		return new Stella(id,massa,new Punto(coordinataX,coordinataY));
	}
	
	//abbiamo fatto un metodo unico perché in caso di modifiche, non rischiamo di doverle fare in due posti diversi, ed è stato infinitamente utile
	public static CorpoCeleste inserisciPianetaOLuna(char tipologia) {
		
		String idCentroOrbita;
		String id;
		double massa;
		double coordinataX;
		double coordinataY;
		
		Scanner scan=new Scanner(System.in);
		
		System.out.println("Inserisci l'id del corpo celeste");
		id=scan.next();
		
		while(sistema.isPresente(id)) {
			System.out.println("Esiste già un corpo con quel nome, non è possibile avere ambiguità");
			id=scan.next();
		}
		
		System.out.println("Inserisci l'id del corpo attorno al quale "+id+" orbita");
		idCentroOrbita=scan.next();
			
		//controllo sull'inserimento
		while(
			!sistema.isPresente(idCentroOrbita)
			||
			(tipologia=='p'&&!(sistema.getById(idCentroOrbita) instanceof Stella))
			||
			(tipologia=='l'&&!(sistema.getById(idCentroOrbita) instanceof Pianeta))
		) {
			
			if(!sistema.isPresente(idCentroOrbita)) {
				
				System.out.println("Il corpo inserito non è presente, inserirne un altro corpo attorno a cui "+id+" orbita");
			
			}else if(tipologia=='p' && !(sistema.getById(idCentroOrbita) instanceof Stella)) {
				
				System.out.println("Per definizione un pianeta orbita attorno a una stella, inserire una stella e non un altro corpo celeste");
			
			}else if(tipologia=='l' && !(sistema.getById(idCentroOrbita) instanceof Pianeta)) {
				
				System.out.println("Per definizione una luna orbita attorno a un pianeta, inserire un pianeta e non un altro corpo celeste");
			}
			
			idCentroOrbita=scan.next();
		}
		
		

		
		System.out.println("Inserisci la massa di "+id);
		massa=scan.nextDouble();
		System.out.println("Inserisci la coordinata X assoluta di "+id);
		coordinataX=scan.nextDouble();
		System.out.println("Inserisci la coordinata Y assoluta di "+id);
		coordinataY=scan.nextDouble();
		
		
		switch(tipologia) {
			case 'p':
				Stella stellaCentroOrbita=(Stella)sistema.getById(idCentroOrbita);
				return new Pianeta(id,massa,new Punto(coordinataX,coordinataY),stellaCentroOrbita);
			case 'l':
				Pianeta pianetaCentroOrbita=(Pianeta)sistema.getById(idCentroOrbita);
				return new Luna(id,massa,new Punto(coordinataX,coordinataY),pianetaCentroOrbita);
		}
		
		//won't happen, method always called with proper parameter
		return null;
		
	}
	
	public static void stampaLuneOrbitanti(String nomeCorpo) {
		
		
		if(sistema.isPresente(nomeCorpo) && sistema.getById(nomeCorpo) instanceof Pianeta) {
			
			String toPrint="Lune orbitanti attorno a "+nomeCorpo+": ";
			
			for(CorpoCeleste cc: ((Pianeta)sistema.getById(nomeCorpo)).getCorpiOrbitanti(sistema.getCorpi())) {
				toPrint+=(cc.getId()+"  ");
			};
			
			if(toPrint.equals("Lune orbitanti attorno a "+nomeCorpo+":  ")) {
				System.out.println("Attorno a questo pianeta non orbita nessuna luna");						
			}else {
				System.out.println(toPrint);
			}
			
			
		}else {
			System.out.println("Il corpo indicato non è presente o non è valido (attenzione, è case sensitive, controlla le maiuscole, verificare inoltre di aver inserito un pianeta e non un altro corpo celeste)");
		}
	}
	
	public static int selezione(String modalita) {
		int selection;
		Scanner scan=new Scanner(System.in);
		
		if(modalita.equals("Base")) {
			System.out.println(MENU);
		}else if(modalita.equals("Extra")){
			System.out.println(MENU_EXTRA);			
		}

		selection=scan.nextInt();
		
		return selection;
	}
	
	
	public static void runMenu() {
		
		int selection;
		String modalita="Base";
		Scanner scan=new Scanner(System.in);
		
		do{
			//variabili ausiliarie
			String nomeCorpo="";
			String nomeDestinazione="";
			String percorso;
			
			selection=selezione(modalita);
			
			if(modalita.equals("Base")&&selection>=15&&selection<=16) {
				
				System.out.println("L'opzione inserita rientra nelle extra, è necessario abilitarle con l'opzione "+EXTRA_KEY+", se si desidera riutilizzarle");	
			
			}else{
			
			
				switch(selection) {
					//controllo esistenza centroOrbita
					case 1:
						if(sistema.getCorpi().size()>0) {
							//in questo caso vuol dire che la stella è già presente
							System.out.println("Non è possibile andare a gestire la presenza di più di una stella");
						}else {
							sistema.addCorpo(inserisciStella());
						}
					break;
					case 2:
						
						if(sistema.getCorpi().size()<=0) {
							//in questo caso vuol dire che sicuramente non è presente una stella, quindi che l'inserimento di un pianeta è impossibile
							System.out.println("Non è possibile inserire pianeti data l'assenza di stelle, inserire almeno una stella per poter inserire pianeti");
						}else {
							sistema.addCorpo(inserisciPianetaOLuna('p'));
						}
	
	
					break;
					case 3:
	
						if(sistema.getCorpi().size()<=1) {
							//in questo caso vuol dire che sicuramente non sono presenti pianeti, quindi che l'inserimento di una luna è impossibile
							System.out.println("Non è possibile inserire lune data l'assenza di pianeti, inserire almeno un pianeta per poter inserire una luna");
						}else {
							sistema.addCorpo(inserisciPianetaOLuna('l'));
						}
						
	
					break;
					case 4:
						
						System.out.println("Inserisci l'identificativo del corpo da rimuovere");
						nomeCorpo=scan.next();
						
						if(sistema.isPresente(nomeCorpo)){
							sistema.rimuoviCorpo(nomeCorpo);
						}else {
							System.out.println("Il corpo indicato non è presente (attenzione, è case sensitive, controlla le maiuscole)");
						}
						
					break;
					case 5:
						System.out.println("Inserisci l'identificativo del corpo da trovare");
						nomeCorpo=scan.next();
						
						if(sistema.isPresente(nomeCorpo)) {
							System.out.println("Il corpo indicato è presente");						
							
						}else {
							System.out.println("Il corpo indicato non è presente (attenzione, è case sensitive, controlla le maiuscole)");
						}
						
					break;
					case 6:
						
						System.out.println("Inserisci l'identificativo della luna di cui si vuole sapere il centro d'orbita");
						nomeCorpo=scan.next();
						
						if(sistema.isPresente(nomeCorpo)) {
							
							if(sistema.getById(nomeCorpo) instanceof Luna) {
								System.out.println(nomeCorpo+" orbita attorno a "+((Luna)sistema.getById(nomeCorpo)).getCentroOrbita().getId());
							}else {
								System.out.println("Il corpo inserito non è una luna, se si vuole sapere attorno a quale corpo orbita un pianeta, tutti i pianeti orbitano attorno alla stella "+sistema.getStella().getId());
							}

						}else {
							System.out.println("Il corpo indicato non è presente (attenzione, è case sensitive, controlla le maiuscole)");						
						}
	
					break;
					case 7:
						System.out.println("Inserisci l'identificativo del Pianeta di cui si vogliono conoscere le lune");
						nomeCorpo=scan.next();
						
						stampaLuneOrbitanti(nomeCorpo);
						
	
						
					break;
					case 8:
						System.out.println("Inserisci l'identificativo del corpo celeste di cui si vuole conoscere il percorso");
						nomeCorpo=scan.next();
						
						if(sistema.isPresente(nomeCorpo)) {
							percorso=sistema.findPath(sistema.getById(nomeCorpo));
							System.out.println(percorso+" e la distanza totale del percorso è "+sistema.getDistanzaPath(percorso));
							
						}else {
							
							System.out.println("Il corpo indicato non è presente (attenzione, è case sensitive, controlla le maiuscole)");
						
						}
						
					break;
					case 9:
						
						System.out.println("Il centro di massa è in "+sistema.getCentroDiMassa().toString());
						
					break;
					case 10:
						System.out.println("Inserisci l'identificativo del corpo di cui si vogliono sapere le coordinate rispetto al centro di massa");
						nomeCorpo=scan.next();
						
						if(sistema.isPresente(nomeCorpo)) {
							
							System.out.println(sistema.coordinateRispettoCentroDiMassa(nomeCorpo).toString());
							
						}else {
							
							System.out.println("Il corpo indicato non è presente (attenzione, è case sensitive, controlla le maiuscole)");
						}
						
					break;
					case 11:
						
						System.out.println("Inserisci l'identificativo del corpo di cui si vogliono sapere le coordinate rispetto al corpo attorno al quale orbita");
						nomeCorpo=scan.next();
						
						if(sistema.isPresente(nomeCorpo)) {
												
							System.out.println(sistema.getDescrizioneCoordinateOrbita(nomeCorpo));
							
						}else {
							
							System.out.println("Il corpo indicato non è presente (attenzione, è case sensitive, controlla le maiuscole)");
						}
					break;
					case 12:
						
						System.out.println(sistema.toString());
						
					break;
					case EXTRA_KEY:
						
						if(modalita.equals("Base")) {
							modalita="Extra";
						}else {
							modalita="Base";
						}
						
						
						
					break;
					case INPUT_CHIUSURA:
						System.out.println("Chiusura completata");
					break;
					case 15:
						
						System.out.println("Inserisci l'id del corpo celeste di partenza");
						nomeCorpo=scan.next();
	
						System.out.println("Inserisci l'id del corpo celeste destinazione");
						nomeDestinazione=scan.next();
						
						if(sistema.isPresente(nomeCorpo)&&sistema.isPresente(nomeDestinazione)) {
							percorso=sistema.getRotta(sistema.getById(nomeCorpo), sistema.getById(nomeDestinazione));
							System.out.println(percorso+" e la distanza totale è "+sistema.getDistanzaPath(percorso));
						}else{
							System.out.println("Uno o entrambi i corpi inseriti non sono presenti");
						}
						
					break;
					case 16:
						System.out.println(sistema.reportCollisioni());
					break;
					default:
						System.out.println("Input non previsto, inserire una delle opzioni date");
					break;
				}
			}
		}while(selection!=INPUT_CHIUSURA);
	}
	
	
	private static void inizializzazione() {
		
		Scanner scan=new Scanner(System.in);
		String nomeSistema;
		System.out.println(MESSAGGIO_INIZIALE);
		nomeSistema=scan.next();
		
		if(nomeSistema.equalsIgnoreCase("solare")||nomeSistema.equalsIgnoreCase("sistemasolare")) {
			String risposta="";
			System.out.println("Solo per il sistema solare, è già disponibile avere una lista approssimativa, desidera utilizzarla? Alternativamente è possibile procedere con l'inserimento manuale");
			risposta=scan.next();
			
			if(risposta.equalsIgnoreCase("si")||risposta.equalsIgnoreCase("sì")||risposta.equalsIgnoreCase("y")||risposta.equalsIgnoreCase("yes")) {
				sistemaSolare();
			}else {
				sistema=new SistemaStellare(nomeSistema);
				
				sistema.addCorpo(inserisciStella());
			}
		}else {
			sistema=new SistemaStellare(nomeSistema);
			
			sistema.addCorpo(inserisciStella());
		
		}
		
		
	}
	
	private static void sistemaSolare(){
		
		sistema=new SistemaStellare("solare");
		Stella sole= new Stella("Sole",332946,new Punto(0, 0));
		Pianeta mercurio=new Pianeta("Mercurio",0.055,new Punto(0.387, 0),sole);
		Pianeta venere=new Pianeta("Venere",0.815,new Punto(0.72 ,0),sole); //0.72
		Pianeta terra=new Pianeta("Terra",1,new Punto(1, 0),sole);
		Luna luna=new Luna("Luna",0.012,new Punto(1.002,0),terra);
		Pianeta marte=new Pianeta("Marte",0.107,new Punto(1.52, 0),sole);
		Pianeta giove=new Pianeta("Giove",317.938,new Punto(5.28, 0),sole); //4
		Luna satellite_io=new Luna("satellite_io",0.0151,new Punto(5.283,0),giove);
		Luna europa=new Luna("Europa",0.008,new Punto(5.32,0),giove);
		Luna ganimede=new Luna("Ganimede",0.025,new Punto(5.35,0),giove);
		Luna callisto=new Luna("Callisto",0.018,new Punto(5.41,0),giove); //5.41
		Pianeta saturno=new Pianeta("Saturno",95.181,new Punto(9.553, 0),sole);
		Luna lunaSaturno=new Luna("lunaSaturna",0.008,new Punto(9.563,0),saturno);
		Pianeta urano=new Pianeta("Urano",14.531,new Punto(19.19, 0),sole);
		Luna lunaUrano=new Luna("lunaUrano",0.008,new Punto(19.22,0),urano);
		Pianeta nettuno=new Pianeta("Nettuno",17.135 ,new Punto(30.061, 0),sole);
		Luna lunaNettuno=new Luna("lunaNettuno",0.008,new Punto(30.081,0),nettuno);
		sistema.addCorpo(sole);
		sistema.addCorpo(mercurio);
		sistema.addCorpo(venere);
		sistema.addCorpo(terra);
		sistema.addCorpo(luna);
		sistema.addCorpo(marte);
		sistema.addCorpo(giove);
		sistema.addCorpo(satellite_io);
		sistema.addCorpo(europa);
		sistema.addCorpo(ganimede);
		sistema.addCorpo(callisto);
		sistema.addCorpo(saturno);
		sistema.addCorpo(lunaSaturno);
		sistema.addCorpo(urano);
		sistema.addCorpo(lunaUrano);
		sistema.addCorpo(nettuno);
		sistema.addCorpo(lunaNettuno);
	}
	
	
	public static void main(String[] args) {
		inizializzazione();
		runMenu();
	}

}
