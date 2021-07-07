package arnaldo.anno2021.triumvirato.planetarium;


public class EsempioOtto {
	public static void main(String[] args) {
		for(int i = 0; i < 1500; i++) {
			System.out.println(doSomething(i));
		}
	}
 
	public static double doSomething(int i) {
		switch(i) {
			case 1254:
				System.out.println("Hey!");
			case 0:
				return -1;
		}
 
		return 2560/i;
	}
 
}
