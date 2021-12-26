import java.io.*;
import java.util.LinkedList;

public class ListeUsers implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private LinkedList<User> liste = new LinkedList<User>() ;

    @SuppressWarnings("unchecked")
	public ListeUsers(){
        try{
            FileInputStream fis = new FileInputStream("../saves/usersList");
            ObjectInputStream ois = new ObjectInputStream(fis);
            liste=((LinkedList<User>) ois.readObject());
        }
        catch (IOException e) {
			e.printStackTrace();
		}
		catch(ClassNotFoundException e){
			e.printStackTrace();
		}
    }

    /**
     * ajout et sauvegarde d'un nouvel utilisateur
     * @param user
     */
    public void ajouter(User user) {
        liste.add(user);
        sauvegarder();
    }

    /**
     * verifier si l'utilisateur au nom 'name' existe dans notre BDD
     * @param name
     * @return
     */
    public boolean Existe(String name) {
        int n = 0;
        while(n<liste.size()) {
            if(liste.get(n).getName().equals(name)) return true;
            n++;
        }
        return false;
    }

    /**
     * sauvegarder de nouvelles informations ( utilisateur, fonctions..) sur la BDD
     */
    public void sauvegarder() {
		 try {
				FileOutputStream fos = new FileOutputStream("../saves/usersList");
				
				ObjectOutputStream oos = new ObjectOutputStream(fos);
				oos.writeObject(liste);
				oos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}	
	}

    /**
     *    recuperation de l'utilisateur 'name' avec ses information
     * @param name
     * @return
     */
    public User recupUser(String name) {
        int n = 0;
        while(n<liste.size()) {
            if(liste.get(n).getName().equalsIgnoreCase(name)) {
                return liste.get(n);
            }
            n++;
        }
        return null;
    }


    /**
     *    ajouter et sauvegarder une fonction dans la liste de fonctions de l'utilisateur au nom 'username'
     * @param Foncexpression
     * @param username
     */
    public void addFratalExpression(Julia j, String username){
        recupUser(username).getFractalList().add(j);
        sauvegarder();
    }

    public LinkedList<User> getListe(){
        return  liste;
    }
}