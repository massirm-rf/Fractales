
public interface Zoomable {
	
	//Getter de la hauteur de la fenetre
    public int height();

    //Getter de la largeur d'une fenetre
    public int width();

    //Méthode permettant de recentrer l'image dans le plan complexe et d'appliquer un facteur multiplicateur aux dimensions de la fenetre
    //Facteur < 1 -> zoom-in
    //Facteur > 1 -> zoom-out
    //Facteur = 1 -> recentrage
    public void zoom(Point center, double factor);

}
