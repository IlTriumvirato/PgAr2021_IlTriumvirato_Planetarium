package arnaldo.anno2021.triumvirato.planetarium;

import java.util.ArrayList;

public class CorpoCeleste {

	protected String id;
	protected double massa;
	protected Punto posizione;
	//le distanze sono espresse in unità astronomiche(150 000 000 km) e la massa in  massa_terra
	
	public CorpoCeleste(String id, double massa, Punto posizione) {
		this.id = id;
		this.massa = massa;
		this.posizione = posizione;
	}
	
	public String getId() {
		return id;
	}

	public double getMassa() {
		return massa;
	}

	public Punto getPosizione() {
		return posizione;
	}

	
	public void setId(String id) {
		this.id = id;
	}
	
	public void setMassa(double massa) {
		this.massa = massa;
	}
	
	public void setPosizione(Punto posizione) {
		this.posizione = posizione;
	}
	
	
	
	public String getPath() {
		return "["+id+"]";
	}
	
	public double distanzaDa(CorpoCeleste cc) {
		return posizione.getDistanceFrom(cc.posizione);
	}
	
	public ArrayList<CorpoCeleste> getCorpiOrbitanti(ArrayList<CorpoCeleste> lista){
		return null;
	}
	
	public Punto getPosizioneOrbita() {
		return new Punto(0,0);
	}
}
