package arnaldo.anno2021.triumvirato.planetarium;

import java.util.ArrayList;

public class Stella extends CorpoCeleste{
	
	public Stella(String id, double massa, Punto posizione) {
		super(id, massa, posizione);
	}
	
	@Override
	public String getPath() {
		return "["+id+"]";
	}
	
	@Override
	public ArrayList<CorpoCeleste> getCorpiOrbitanti(ArrayList<CorpoCeleste> lista){
		
		ArrayList<CorpoCeleste> ritorno=new ArrayList<CorpoCeleste>();
		for(CorpoCeleste cc:lista) {
			if(cc instanceof Pianeta&&((Pianeta)cc).getCentroOrbita()==this){ //la condizione secondaria è superflua, giusto in caso si implementi in un programma che gestisce più stelle o sistemi stellari
				ritorno.add(cc);
			}
		}
		
		return null;

	}
}
