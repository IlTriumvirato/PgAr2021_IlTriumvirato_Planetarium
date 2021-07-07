package arnaldo.anno2021.triumvirato.planetarium;

public class Punto {
	private double x;
	private double y;
	
	public Punto(double x, double y) {
		this.x = x;
		this.y = y;
	}
		
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public void setY(double y) {
		this.y = y;
	}
	
	public void setX(double x) {
		this.x = x;
	}

	
	@Override
	public String toString() {
		return "("+x+","+y+")";
	}
	
	public double getDistanceFrom(Punto p) {
		double termine_orizzontale=Math.pow((x-p.x), 2);
		double termine_verticale=Math.pow((y-p.y), 2);
		
		return Math.sqrt(termine_orizzontale+termine_verticale);
	}
	
	public Punto getPosizioneRelativa(Punto riferimento) {
		return new Punto(x-riferimento.getX(),y-riferimento.getY());
	}
}
