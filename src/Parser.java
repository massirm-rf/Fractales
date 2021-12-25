
import java.util.LinkedList;
import java.util.Stack;

public class Parser {

	private Stack<String> fileString = new Stack<String>();
	private Stack<String> pile = new Stack<String>();
	private String nom;
	private String strActuel;
	private String coeff="";
	private LinkedList<String> listeFonc = new LinkedList<>();

	private final String[] nombres = {"1","2","3","4","5","6","7","8","9"};
	private final String[] operateurs = {"+","-","/","*","^"};
	private final String[] operandes = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};



	public Parser(String nomFunc) {
		this.nom = nomFunc;
	}


	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public Stack<String> getFileString() {
		return fileString;
	}


	public boolean containsNum(String a) {
		for(int i=0;i<nombres.length;i++) {
			if(a.equals(nombres[i])) return true;
		}
		return false;
	}

	public boolean containsOp(String a) {
		for(int i=0;i<operateurs.length;i++) {
			if(a.equals(operateurs[i])) return true;
		}
		return false;
	}

	public boolean containsOperandes(String a) {
		for(int i=0;i<operandes.length;i++) {
			if(a.equals(operandes[i])) return true;
		}
		return false;
	}

	private static boolean letterOrDigit(char c) {
		if(Character.isLetterOrDigit(c)) return true;
		return false;
	}

	public static int getPrecedence(String c) {
		if("+".equals(c) || "-".equals(c)) return 1 ;
		else if("*".equals(c)||"/".equals(c)) return 2;
		else if("^".equals(c)) return 3;
		return -1;
	}

	public int recupererFunc(int ind) {
		int i = ind;
		String str = "";

		while(i<nom.length() && (containsOperandes(""+nom.charAt(i))&& nom.charAt(i) != '(' && nom.charAt(i)!=')')) {
			str+=nom.charAt(i);
			i++;
		}

		strActuel = str;
		return i-1;
	}

	public int recupererNum(int ind) {
        int i = ind;
        String str = "";

        while(i<nom.length() && (Character.isDigit(nom.charAt(i)) || '.' == nom.charAt(i) ||nom.charAt(i)=='i') ) {
            str+=nom.charAt(i);
            i++;
        }
        strActuel =str;
        return i-1;
    }

	public Stack<String> inverserPile(Stack<String> p){
		Stack<String> res = new Stack<String>();

		while(!p.empty()) {
			res.push(p.pop());
		}
		return res;
	}

	public boolean associatif(String op1, String op2) {
		return true;
	}

	public int priorite(String a, String b) {

		if(a.equals(b)) return 0;

		else if((a.equals("+") && b.equals("-")) || (a.equals("-") && b.equals("+"))
				|| (a.equals("*") && b.equals("/")) || (a.equals("/") && b.equals("*"))) {

			return 0;

		}
		else if((a.equals("*") && (b.equals("-") || b.equals("+")))
				|| (a.equals("/") && (b.equals("-") || b.equals("+"))) || (a.equals("^")) ) {
			return 1;
		}
		else {
			return -1;
		}
	}

	public Complex apppliquerOperateur(Complex z1, Complex z2, String op)/* throws InputException*/ {
		switch (op) {
			case "+" : return Complex.somme(z1, z2);
			case "-" : return Complex.soustraction(z1, z2);
			case "*" : return Complex.multiplication(z1, z2);
			case "/" : return Complex.division(z1, z2);
			case "^" : {
				//System.out.println("on est la");
				int puissance = (int) z1.getReal();
				//System.out.println(puissance);
				Complex tmp =z2;
				for(int i =0;i<puissance-1;i++) {
					z2 = Complex.multiplication(z2, tmp);
				}
				return z2;
			}
			default :
				return Complex.of(0,0);
		}
	}

	public Complex calculer(Complex z) /*throws InputException */{
		//parsing2();
		@SuppressWarnings("unchecked")
		Stack<String> p = (Stack<String>)fileString.clone();
		Stack<Complex> save = new Stack<Complex>();
		
		while(!p.empty()) {
			//if(!save.isEmpty())System.out.println("save.peeak : "+save.peek());
			if(Character.isDigit(p.peek().charAt(0)) || (p.peek().charAt(0)=='-' && p.peek().length()>= 2 && Character.isDigit(p.peek().charAt(1)))) {
				save.push(Complex.stringToComplex(p.pop()));
				//System.out.print( " lààà");
			}else if(p.peek().equals("z")) {
				save.push(z);
				p.pop();
				//System.out.println( "else if ");
			}else if(containsOp(p.peek())) {
				
				Complex z1 = null,z2=null;
				if(!save.empty()) z1 = save.pop();
				if(!save.empty()) z2 = save.pop();
				/*System.out.print(z1 + " ");
				System.err.print(p.peek());
				System.out.println(" "+z2);*/
				save.push(apppliquerOperateur(z1, z2, p.pop()));
			}/*else {
				if(!p.isEmpty() && !save.isEmpty()) {
					save.push(appliquerFonction(save.pop(), p.pop()));
				}
				else {throw new InputException();}

			}*/
			
		}
		return save.pop();
	}

	public void parsing2() /*throws InputException*/{
		Stack<String> pile = new Stack<String>();
		int nbPar=0;
		for(int i=0;i<nom.length();i++) {
			char c =nom.charAt(i);
			if(letterOrDigit(c)) {
				if(Character.isLetter(c)) {
					i = recupererFunc(i);
					pile.push(strActuel);
					strActuel ="";
				}else {
					i =recupererNum(i);
					if(i+1<nom.length() && Character.isLetter(nom.charAt(i+1))){
						pile.push("*");
					}
					fileString.push(coeff+strActuel);
					strActuel = "";
				}
			} else if(c == '(') {
				nbPar+=1;
				pile.push(""+c);
			}else if(c == ')') {
				nbPar-=1;
				while(!pile.isEmpty() && !"(".equals(pile.peek()) ) {
					fileString.push(pile.pop());
				}
				if(!pile.isEmpty())pile.pop();
			}
			else {
				while(!pile.isEmpty() && (getPrecedence(""+c) <= getPrecedence(pile.peek()) || Character.isLetter(pile.peek().charAt(0)))) {
					fileString.push(pile.pop());
				}
				if(c!='-' )/*|| (i+1<nom.length() && !Character.isDigit(nom.charAt(i+1)))) */{
					pile.push(""+c);
					coeff = "";
				}
				else {
					//coeff = "-";
					fileString.push("-1");
					if(i!=0 && nom.charAt(i-1)!='(') pile.push("+");
					pile.push("*");
				}
			}
		}
		if (nbPar != 0 ){
			//throw new InputException();
		}
		while(!pile.isEmpty()) {
			if("(".equals(pile.peek())|| ")".equals(pile.peek())) {
				//throw new InputException();
			}
			fileString.push(pile.pop());
		}
		fileString = inverserPile(fileString);
	}

}