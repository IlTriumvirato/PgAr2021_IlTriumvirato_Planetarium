package arnaldo.anno2021.triumvirato.planetarium;

import java.util.ArrayList;
import java.util.Objects;

public class SistemaStellare {
	private String nomeSistema;
	private ArrayList<CorpoCeleste> array_corpi;

	
	public SistemaStellare(String nomeSistema) {
		this.nomeSistema=nomeSistema;
		array_corpi = new ArrayList<CorpoCeleste>();
	}
	
	public CorpoCeleste getStella() {
		return array_corpi.get(0);
		//il corpo in posizione 0 è sempre la stella, perché con la rimozione della stella si rimuovono tutti gli altri e la stella deve essere inserita per prima
	}
	
	
	
	public ArrayList<CorpoCeleste> getCorpi() {
		return array_corpi;
	}
	
	public void setCorpi(ArrayList<CorpoCeleste> array_corpi) {
		this.array_corpi = array_corpi;
	}
	
	public void addCorpo(CorpoCeleste aggiunto) {
		array_corpi.add(aggiunto);
	}
	


	public CorpoCeleste getById(String identificatore) {
		
		for(CorpoCeleste elemento:array_corpi) {
			if(elemento.getId().equals(identificatore)) {
				return elemento;
			}
		}
				
				
		return null;
	}
	
	public boolean isPresente(String identificatore) {
		return !Objects.isNull(this.getById(identificatore));
	}
	


	public Punto getCentroDiMassa() {
		double x_finale=0;
		double y_finale=0;
		double massa_totale=0;
		
		for(CorpoCeleste elemento:array_corpi) {
			//possibilità, fare elemento.getContributo()
			x_finale+=elemento.getMassa()*elemento.getPosizione().getX();
			y_finale+=elemento.getMassa()*elemento.getPosizione().getY();
			massa_totale+=elemento.getMassa();
		}
		
		x_finale/=massa_totale;
		y_finale/=massa_totale;
		
		
		return new Punto(x_finale,y_finale);
	}
	
	public Punto coordinateRispettoCentroDiMassa(String nomeCorpo) {
		return coordinateRispettoCentroDiMassa(getById(nomeCorpo).getPosizione());
	}
	
	public Punto coordinateRispettoCentroDiMassa(Punto posizione) {
				
		return posizione.getPosizioneRelativa(getCentroDiMassa());
	}
	
	public String getDescrizioneCoordinateOrbita(String nomeCorpo) {
		Punto posizioneRelativa;
		String ritorno="errore di inserimento";
		
		if(getById(nomeCorpo) instanceof Stella) {
			
			ritorno="Il corpo scelto è la stella e non orbita attorno ad altri corpi all'interno del sistema";
			
		}else if(getById(nomeCorpo) instanceof Pianeta){
			
			posizioneRelativa=((Pianeta)getById(nomeCorpo)).getPosizioneOrbita();
			ritorno="Posizione del pianeta rispetto alla stella: "+posizioneRelativa.toString();
			
		}else if(getById(nomeCorpo) instanceof Luna){
			
			posizioneRelativa=((Luna)getById(nomeCorpo)).getPosizioneOrbita();
			ritorno="Posizione della luna rispetto al pianeta "+((Luna)getById(nomeCorpo)).getCentroOrbita().getId()+": "+posizioneRelativa.toString();
			
		}
		
		return ritorno;
	}
	
	public void rimuoviCorpo(String identificativo) {
		CorpoCeleste daRimuovere=getById(identificativo);
		if(!Objects.isNull(daRimuovere)) {
			
			
			if(daRimuovere instanceof Stella) {
				
				//questo perché c'è una sola stella e tutti i pianeti orbitano intorno alla stella, con le loro lune
				array_corpi = new ArrayList<CorpoCeleste>();
				
			}else if(daRimuovere instanceof Pianeta) {

				
				for(int i=0;i<array_corpi.size();i++){
					
					if(array_corpi.get(i) instanceof Luna) {
						
						if(((Luna)array_corpi.get(i)).getCentroOrbita().getId().equals(identificativo)){
							array_corpi.remove(i);	
						}
					}

				}

			}
			
			array_corpi.remove(daRimuovere);

			
			
			
		}
	}

	@Override
	public String toString() {
		
		String ritorno="sistema: "+nomeSistema+"\n";
		
		for(CorpoCeleste cc:array_corpi) {
			
			if(cc instanceof Stella) {
				ritorno+="stella: "+cc.getId()+"\n";
				break;
			}
		}
		
		if(array_corpi.size()>=2) {
			ritorno+="pianeti:\n";
			for(CorpoCeleste cc:array_corpi) {
				
				if(cc instanceof Pianeta) {
					ritorno+="\t"+cc.getId()+"[lune: ";
					
					for(CorpoCeleste possibile_luna: array_corpi) {
						if(possibile_luna instanceof Luna&&((Luna)possibile_luna).getCentroOrbita()==cc) {
							ritorno+=possibile_luna.getId()+" ";
						}
					}
					
					ritorno+="]\n";
				}
			}
		}
		
		return ritorno;
	}

	
	
	public String getRotta(CorpoCeleste partenza, CorpoCeleste destinazione) {
		String ritorno="";
		
		
		//il corpo in posizione 0 è sempre la stella, perché con la rimozione della stella si rimuovono tutti gli altri e la stella deve essere inserita per prima
		
		if(partenza==destinazione) {
			ritorno="Non è necessario alcuno spostamento";
		}else{
			

			if(partenza instanceof Stella) {
				
				if(destinazione instanceof Pianeta) {
					
					ritorno=((Pianeta)destinazione).getPath();
					
					
				}else{ //destinazione instance of luna
					
					ritorno=((Luna)destinazione).getPath();			
				}
				
				
			}else if(partenza instanceof Pianeta){
				
				
				if(destinazione instanceof Stella) {
					ritorno="["+partenza.getId()+">"+destinazione.getId()+"]";
					
				}else if(destinazione instanceof Pianeta){
					ritorno="["+partenza.getId()+">"+getStella().getId()+">"+destinazione.getId()+"]";
					
				}else{ //destinazione instance of luna
					
					if(((Luna)destinazione).getCentroOrbita()==partenza) {
						ritorno="["+partenza.getId()+">"+destinazione.getId()+"]";
					}else {
						
						ritorno="["+partenza.getId()+">"+getStella().getId()+">"+((Luna)destinazione).getCentroOrbita().getId()+">"+destinazione.getId()+"]";
					}
				}
				
			}else if(partenza instanceof Luna) {
				
				if(destinazione instanceof Stella) {
					
					ritorno= "["+partenza.getId()+">"+((Luna)destinazione).getCentroOrbita().getId()+">"+destinazione.getId()+"]";
					
				}else if(destinazione instanceof Pianeta){
					
					if(((Luna)partenza).getCentroOrbita()==destinazione) {
						ritorno= "["+partenza.getId()+">"+destinazione.getId()+"]";
					}else {
						ritorno= "["+partenza.getId()+">"+((Luna)partenza).getCentroOrbita().getId()+">"+getStella().getId()+">"+destinazione.getId()+"]";				
						
					}
					
					
				}else { //destinazione instance of luna
					
					if(((Luna)partenza).getCentroOrbita()==((Luna)destinazione).getCentroOrbita()) {
						ritorno= "["+partenza.getId()+">"+((Luna)partenza).getCentroOrbita().getId()+">"+destinazione.getId()+"]";
					}else {
						ritorno= "["+partenza.getId()+">"+((Luna)partenza).getCentroOrbita().getId()+">"+getStella().getId()+">"+((Luna)destinazione).getCentroOrbita().getId()+">"+destinazione.getId()+"]";					
					}
					
				}
				
				
			}
		}
		
		return ritorno;
	}
	
	public double getDistanzaPath(String percorso){
		double distanza=0;
		percorso=percorso.substring(1, percorso.length()-1);
		String[] passi=percorso.split(">");
		
		for(int i=0;i<passi.length-1;i++) {
			if(this.isPresente(passi[i])&&this.isPresente(passi[i+1])) {
				distanza+=this.getById(passi[i]).distanzaDa(this.getById(passi[i+1]));
			}else {
				return -1;
			}
		}
		
		//returns -1 if at least one of the celestial bodies doesn't exist
		return distanza;
	}
	
	public String findPath(CorpoCeleste cc) {
		
		String ritorno="";
		if(cc instanceof Stella) {
			
			ritorno=((Stella)cc).getPath();
			
		}else if(cc instanceof Pianeta){
			
			ritorno=((Pianeta)cc).getPath();
			
		}else if(cc instanceof Luna){
			
			ritorno=((Luna)cc).getPath();
			
		}else {
			ritorno=cc.getPath();
		}
		
		return ritorno;
	}
	
	
	
	public String reportCollisioni() {

		String ritorno="Possibili collisioni:";
		double distanza_pianeta_stella;
		double distanza_pianeta_luna;
				
		double[] raggi_minori=new double[array_corpi.size()];
		double[] raggi_maggiori=new double[array_corpi.size()];
		
		raggi_minori[0]=0;
		raggi_maggiori[0]=0;
		//la stella è sempre in prima posizione perché viene inserita per prima e se qualcuno la rimuove, si rimuove tutto e non ce n'è mai più di una
		
		//inizializza tutti i raggi maggiori e minori dell'area in cui si può trovare il corpo. Per i pianeti è una distanza unica e quindi i raggi sono uguali, per le lune no, in quanto hanno una distanza minima e massima che possono avere dalla stella
		for(int i=1;i<array_corpi.size();i++) {
			
			if(array_corpi.get(i) instanceof Pianeta) {
				raggi_minori[i]=array_corpi.get(i).distanzaDa(getStella());
				raggi_maggiori[i]=raggi_minori[i];
				
			}else{// instanceof luna, perché parto da array_corpi.get(1)
				
				distanza_pianeta_stella=((Luna)array_corpi.get(i)).getCentroOrbita().distanzaDa(getStella());
				distanza_pianeta_luna=((Luna)array_corpi.get(i)).getCentroOrbita().distanzaDa(array_corpi.get(i));
				
				raggi_minori[i]=distanza_pianeta_stella-distanza_pianeta_luna;
				raggi_maggiori[i]=distanza_pianeta_stella+distanza_pianeta_luna;		
				
				if(raggi_minori[i]<=0) {
					raggi_minori[i]=0;
				}
			}
		}
		
		
		
		//confronta i raggi delle orbite per vedere quali arreee si sovrappongono
		
		for(int i=0;i<array_corpi.size();i++) {
			
			for(int j=i+1;j<array_corpi.size();j++) {
				
				if(
					(raggi_minori[i]>=raggi_minori[j]&&raggi_minori[i]<=raggi_maggiori[j])
					||
					(raggi_maggiori[i]>=raggi_minori[j]&&raggi_maggiori[i]<=raggi_maggiori[j])
					||
					(raggi_minori[j]>=raggi_minori[i]&&raggi_minori[j]<=raggi_maggiori[i])
					||
					(raggi_maggiori[j]>=raggi_minori[i]&&raggi_maggiori[j]<=raggi_maggiori[i])
				){
					
					//
					
					if(array_corpi.get(i) instanceof Luna) {
						
						if(array_corpi.get(j) instanceof Luna) {
							//se sono entrambe lune, deve controllare che non abbiano lo stesso centro d'orbita, perché in tal caso non possono collidere
							if(!(((Luna)array_corpi.get(i)).getCentroOrbita()==(((Luna)array_corpi.get(j)).getCentroOrbita()))){
								ritorno+="\n\ttra "+array_corpi.get(i).getId()+" e "+array_corpi.get(j).getId();
							}
						}else{
							//se la prima è una luna e il secondo è un pianeta, deve controllare che la luna non orbiti attorno al pianeta, perché in tal caso non può avvenire una collisione
							if(!(((Luna)array_corpi.get(i)).getCentroOrbita()==((Pianeta)array_corpi.get(j)))){
								ritorno+="\n\ttra "+array_corpi.get(i).getId()+" e "+array_corpi.get(j).getId();
							}
						}
						
					}else if(array_corpi.get(i) instanceof Pianeta){
						
						if(array_corpi.get(j) instanceof Luna) {
							//se il primo è un pianeta e il secondo è una luna, deve controllare che la luna non orbiti attorno al pianeta, perché in tal caso non può avvenire una collisione
							if(!(((Luna)array_corpi.get(j)).getCentroOrbita()==((Pianeta)array_corpi.get(i)))){
								ritorno+="\n\ttra "+array_corpi.get(i).getId()+" e "+array_corpi.get(j).getId();
							}

						}else {
							//se sono entrambi pianeti e sono arrivati fin qua, vuol dire che sono sulla stessa identica orbita e la collisione è possibile 
							ritorno+="\n\ttra "+array_corpi.get(i).getId()+" e "+array_corpi.get(j).getId();
						}
					
					}else { //questo è il caso array_corpi.get(i) instanceof Stella, in cui si arriva se il secondo corpo è un pianeta o una luna con raggio_minore 0
						ritorno+="\n\ttra "+array_corpi.get(i).getId()+" e "+array_corpi.get(j).getId();
					
					}
					
				}
			}
		}
		
		//in tal caso non ha aggiunto neanche una collisione, quindi gli si da un messaggio più appropriato
		if(ritorno.equals("Possibili collisioni:")) {
			return "Nessuna collisione possibile";
		}else {
			return ritorno+"\n";
		}
		
	}


}
