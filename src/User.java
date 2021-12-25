import java.io.Serializable;
import java.util.LinkedList;

public class User implements Serializable {

    private String name;

    private LinkedList<String> FractalsList ;/** filtre par defaut ( le meme que pour le zoomController) */

    public User(String name){
        this.name=name;
        FractalsList = new LinkedList<>();
    }

    public String getName(){
        return  name;
    }

    public LinkedList<String> getListeFonctions(){
        return  FractalsList;
    }

    /**
     * verifie si la une fonction est deja enregistree par l'utilisateur
     * @param FoncExpression
     * @return
     */
    public Boolean dejaSauvegarde(String FoncExpression){
        int n = 0;
        while(n<FractalsList.size()) {
            if(FractalsList.get(n).equalsIgnoreCase(FoncExpression)) return true;
            n++;
        }
        return false;
    }
}
