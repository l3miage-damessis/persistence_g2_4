# Persistence_metier_g2_4

## Introduction

Persistence_metier_g2_4 est un composant java conçu pour abstraire la logique métier liée à la création et à la manipulation de formes. 
Il se compose de deux packages principaux : `shapes` et `persistence`. 

## Contenu

### Package `shapes`

Le package `shapes` contient la logique métier de la creation et manipulation de formes. Ce package contient une classe d'énumaration des type de forme, la classe abstraite `SimpleShape` qui sert de base pour les formes spécifiques et dont hérite la classes `Circle`, `Square`, `Triangle`, et `Cube`, et la classe `ShapeFactory` est un singleton pour controler la création des formes.Les diffentres classe de cette classe offre les services tel que :
- La creation des formes qui sont des objets java du type `Circle`,`Square`,`Triangle`,`Cube` via une fabrique la `ShapeFactory`
- Le dessin(representation graphique) de ces formes 
- Le deplacement de ses formes(en changeant leur coordonnées)
- Verifier si des coordonnées précise fournis sont a l'intérieur de la zone de la forme considéré
- Definir une forme comme selectionné(etant donné que pour bougé une forme dans l'interface il faut qu'il soit selectionné)
...

### Package `persistence`

Le package `persistence` contient la logique métier de permettant d'assurer la persistence(export et import) des données des shapes.Ce package contient l'interface visitable dont hérite les `SimpleShape`,  l'inteface `Visitor` qu'implente les classe `JsonVistor` et `XMlVistor` ainsi que la classe abstraite `ShapeWriter` dont hérite les classe `ShapeJsonWriter` et `ShapeXmlWriter` pour l'export et pour finir la classe abstraite `ShapeReader` dont hérite les classe `ShapeJsonReader` et `ShapeXmlReader` pour l'import qui servent a l'import et  sert de base pour les formes spécifiques (Circle, Square, Triangle, et Cube), et la classe `ShapeFactory` est un singleton pour créer des formes.Les diffentres classe de cette classe offre les service tel que :
- la representation des données des formes (type de la forme, son abscisse et son ordonnée) sous le format json et xml
- l'écriture des representation des formes dans des fichier xml et json fourni (bien sur en verifiant que les fichiers fournis sont valide)
-la lecture de representation des formes stocké dans des fichier xml et json fourni(bien sur en verifiant que les fichiers fournis sont valide) pour instancié ces formes.

## Installation

Pour intégrer `persistence_metier_g2_4` dans votre projet, suivez ces étapes :

1. Clonez le dépôt :

    ```bash
    git clone git@github.com:l3miage-damessis/persistence_g2_4.git
    cd persistence_g2_4
    cd persistence_metier_g2_4
    ```

2. Construisez et installez le projet :

    ```bash
    mvn clean install
    ```
    Cette commande devrait généré un jar du composant : `persistence_metier_g2_4-1.0.0.jar`

    Note : Si le mvn clean install échoue et que vous remarquer dans les log qu'il est fait mention de `SingleShape` alors vous aurrz besoin d'installer ce composant externe qui est utilisé par `persistence_metier`. Pour ce faire utiliser cette commande 

     ```bash
    mvn install:install-file -Dfile=lib/shape-1.0.1.jar -DgroupId=edu.uga.miage.m1 -DartifactId=edu.uga.singleshape -Dversion=1.0.1 -Dpackaging=jar -DgeneratePom=true
     ```
    
3. Ajouter le jar généré lors de la compilation dans votre projet

    - creer un repertoire lib

        en étant dans votre projet executer :

        ```bash
        mkdir lib
        ```
    - copier coller le jar dans le repertoire lib

4. Installer le fichier JAR local dans votre référentiel Maven local(Si vous utilisez maven)

    ```bash
    mvn install:install-file -Dfile=lib/persistence_metier_g2_4-1.0.0.jar -DgroupId=edu.uga.miage.m1 -DartifactId=persistence_metier_g2_4 -Dversion=1.0.0 -Dpackaging=jar -DgeneratePom=true
    ```
   
5. Rajouter la dépendance suivante du composant dans le pom.xml (Si vous utilisez maven) du projet

		<dependency>
			<groupId>edu.uga.miage.m1</groupId>
			<artifactId>persistence_metier_g2_4</artifactId>
			<version>1.0.0</version>
		</dependency>

## Utilisation

Une fons l'installation fini vous pourrez utilisé les classes et fonctions du composant `persistence_metier_g2_4` via a des imports

# Modele utilisé

##

<img title="a title" alt="Alt text" src="conception\image\Diagramme_de_classe_Composant_persistence_metier_g2_4.png">




