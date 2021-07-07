package arnaldo.anno2021.triumvirato.planetarium;
public class EsempioSette {

	public static void main(String[] args) {
		//codice che permette di trovare i numeri primi tra i primi 100 numeri
 
		int num=0;
 
		for(int i=0; i<100; i++) {
			if(primo(i)==true) {
				num++;
 
			}
		}
 
 
		System.out.println(num);
	}
 
	private static boolean primo(int i) {
		if(i==0 || i==1) {
			return false; 
		}else {
			for(int j=2; j<i; j++ ) {
				if(i%j==0) {
					System.out.print(i+"_"+j+" ");
					return false;
				}
			}
		}
 
		return true;
	}
	
}
