package arnaldo.anno2021.triumvirato.planetarium;

public class Luna extends CorpoCeleste{
	private Pianeta centroOrbita;
	
	public Luna(String id, double massa, Punto posizione, Pianeta centroOrbita) {
		super(id, massa, posizione);
		this.id = id;
		this.massa = massa;
		this.posizione = posizione;
		this.centroOrbita=centroOrbita;
	}
	
	public Pianeta getCentroOrbita() {
		return centroOrbita;
	}
	
	public void setCentroOrbita(Pianeta centroOrbita) {
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
	
	
}
