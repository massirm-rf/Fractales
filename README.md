## Fractales Generator : 

Fractal Generator est une application qui permet de creer facilement vos fractales (ensembles de Julia et Mandelbrot). Vous en avez peut-etre deja entendu parler.


## Fonctionalités :

- Une page de connexion qui permet à l'utilisateur de s'identifier directement avec un username pour acceder à la page de fractal Generator.
- Une interface graphique jolie et bien soignée.
- Generer l'ensemble de julia à partir d'une constante saisie par l'utilisateur.
- Generer l'ensemble de Mandelbrot
- L'application propose à l'utilisateur de choisir la couleur de la fractale.
- Possibilité de sauvegarder la fractale generée dans un repertoire choisie par l'utilisateur.
- L'application propose à l'utilisateur de creéer sa liste de fractales favoris.
- Une Base De Données sous le nom de "UsersList" qui nous permet de sauvegarder une liste des utilisateurs ainsi que les listes de fractales sauvegardées (favoris)  par chaque utilisateur.
- Possibilité de zoomer et dézoomer sur la fenetre en utilisant le bouton droit et le bouton gauche de la souris.
- Possibilité de naviguer (se déplacer) sur l'ensemble de la fractale en utilisant le bouton droit de la souris et en le gardant pressé durant tout le déplacement.
- Possibilité de se déconnecter de l'application et de revenir à la page de connexion.
- Une fenetre d'aide rattachée à la page d'accueil qui explique comment on peut se servir du programme.
- Possibilité de changer la couleur de la fractale à l'aide du Slider (en choisissant la couleur multicouleur uniquement).

Afin de faciliter l'utilisation de cette application, on a crée un script sous le nom de "launch.sh" qui se trouve dans le répertoire : src.


## Comment utiliser Fractal Generator ?

# Executer l'application en graphique:
Pour lancer la partie graphique du programme, il suffit d'ouvrir le terminal et ensuite se placer dans le répertoire du projet "src" et ensuite lancer la commande: ./launch.sh

- La compilation sera faite automatiquement après le lancement de la commande citée ci-dessus, et ensuite l'execution automatique de la classe Main qui lancera le programme.
- A la femeture du programme, tous les fichiers avec lextension " .class " seront  directement supprimés de la machine de l'utilisateur.
