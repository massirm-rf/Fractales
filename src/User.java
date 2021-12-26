import java.io.Serializable;
import java.util.LinkedList;

public class User implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;

    private LinkedList<Julia> FractalsList ;/** filtre par defaut ( le meme que pour le zoomController) */

    public User(String name){
        this.name=name;
        FractalsList = new LinkedList<>();
    }

    public String getName(){
        return  name;
    }

    public LinkedList<Julia> getFractalList(){
        return  FractalsList;
    }

	/**
     * verifie si la une fonction est deja enregistree par l'utilisateur
     * @param FoncExpression
     * @return
     */
    public Boolean dejaSauvegarde(Julia julia){
        int n = 0;
        while(n<FractalsList.size()) {
            if(FractalsList.get(n).getC().toString().equals(julia.getC().toString())) return true;
            n++;
        }
        return false;
    }
    
    public void removeFractal(Julia julia) {
    	for(Julia j : FractalsList) {
    		if(j.getC().toString().equals(julia.getC().toString())) 
    			FractalsList.remove(j);
    	}
    }
}
