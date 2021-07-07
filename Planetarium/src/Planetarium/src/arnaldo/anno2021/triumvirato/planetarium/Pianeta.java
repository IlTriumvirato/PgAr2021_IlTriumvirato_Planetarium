package arnaldo.anno2021.triumvirato.planetarium;

import java.util.ArrayList;

public class Pianeta extends CorpoCeleste{
	private Stella centroOrbita;
	
	public Pianeta(String id, double massa, Punto posizione, Stella centroOrbita) {
		super(id, massa, posizione);
		this.id = id;
		this.massa = massa;
		this.posizione = posizione;
		this.centroOrbita=centroOrbita;
	}
	
	public Stella getCentroOrbita() {
		return centroOrbita;
	}

	public void setCentroOrbita(Stella centroOrbita) {
		this.centroOrbita = centroOrbita;
	}
	
	@Override
	public Punto getPosizioneOrbita() {
		return posizione.getPosizioneRelativa(centroOrbita.getPosizione());
	}
	
	@Override
	public String getPath() {
		return centroOrbita.getPath().substring(0,centroOrbita.getPath().length()-1)+">"+this.getId()+"]";
	}

	@Override
	public ArrayList<CorpoCeleste> getCorpiOrbitanti(ArrayList<CorpoCeleste> lista){
		
		ArrayList<CorpoCeleste> ritorno=new ArrayList<CorpoCeleste>();
		for(CorpoCeleste cc:lista) {
			if(cc instanceof Luna&&((Luna)cc).getCentroOrbita()==this){ //la condizione secondaria è superflua, giusto in caso si implementi in un programma che gestisce più stelle o sistemi stellari
				ritorno.add(cc);
			}
		}
		
		return ritorno;

	}
	
}
