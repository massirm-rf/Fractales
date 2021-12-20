import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;
import java.util.Set;

public class Test {

	public static void main(String[] args) {
		Fenetre f = new Fenetre();
		f.setVisible(true);
		
	}
	
	/*public static void afficher(int N,int d) {
		System.out.println("P("+N+","+d+") = "+ paradoxeProba(N,d) + " ");
	}
	public static double paradoxeProba(int N,int d) {
		//soit c la probabilité que toutes les personnes aient un anniversaires deffirents 
		double c = 1.0;
		
		for(int i = 1 ;i<=N;i++) {
			c = c/d * ( (d-i+1) );
		}
		return 1 - c ;
	}
	
	public static void AlgoSimulation(int N,int nbClasse) {
		Random rnd  = new Random();
		ArrayList<ArrayList<Integer>> ecole  = new ArrayList<ArrayList<Integer>>();
		
		for(int i = 0;i<nbClasse;i++) {
			ArrayList<Integer> classe = new ArrayList<Integer>();
			for(int j =0;j<N;j++) {
			classe.add(rnd.nextInt(365)+1);
			}
			ecole.add(classe);		
		}
		//System.out.println(classe);
		int cpt = 0;
		for(ArrayList<Integer> classe : ecole) {
			Set<Integer> set = new HashSet<Integer>(classe);
			if(set.size()<classe.size()) 
				cpt ++;
		}
		System.out.println(cpt+"/"+nbClasse+" satisfont le paradoxe des anniversaires");
	}
	
	/*public static void AlgoSimulation2(int N,int d) {
		Random rnd  = new Random();
		ArrayList<Integer> classe = new ArrayList<Integer>();
		for(int i =0;i<N;i++) {
			classe.add(rnd.nextInt(d)+1);
		}
		//System.out.println(classe);
		for(int e : classe) {
			if(Collections.frequency(classe, e) > 1) {
				return true;
			}
		}
		return false;
	}*/

}
