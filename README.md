# Persistence_g2_4

## Auteurs
- ### Damessi Samuel
- ### Freund Galeano Matias

## Objectifs
Ce projet vise a mettre en pratique les notions de design pattern(patrons de conceptions) de qualité de code et de test.Vous verez tout du long aussi bien dans le code que dans les diagramme de classe fourni ici que nous avons eu a utiliser et adapter differents design pattern pour mettre en place les fonctionnalité voulu. Le code produit a été fait en essayant de respecter les bonnes pratiques de codage que nous avons pu surveiller grace a `sonar` et nous y avons rajouter des test `junit` d'integration pour s'assurer que le resultat obtenu correspondait aux attentes.

## Réalisations

### Existant 
Nous somme partie d'une interface permettant de selectionner des forme(Shape) dans une barre d'outil(toolbar) et de les posé dans un design canva. Une fois posé on ne peut plus agir sur la forme posé(on ne peut pas la deplacer, la supprimer...).

Pour consulter chacune des iterations cliquez dessus

### [Itération 1](Iteration1.md) 

### [Itération 2](Iteration2.md) 

### [Itération 3](Iteration3.md) 


### Inachévés de l'iteration 3 
L'itération 3 n'a pas été mené a bout. Il était envisagé a cette étape de rajouter une fonctionnalité de groupage de forme(Shape) et de pouvoir appliquer les autre fonctionnalité de deplacement, annulation(dégroupage), rexecution(regroupage), export, import sur sur cette forme composite(compositeShape).L'étape de conception a été réaliser et des implémentation on été fait mais n'étant pas arrivé a une version stable nous avons décidé de ne pas l'inclure dans la livrasion principal mais de mettre cette version là sur la branche feature\grouping et il est a noté que dans cette version certaines régressions sont a constaté par rapport a la version stable

## Fonctionnalité 
-Dessiner des forme dans un design canvas.
-Exporter en JSON ou en XML des données sur les shapes présent sur le design canvas.
-Importer des données précédemment exporté pour initialiser le design canvas.
-Faire mouvoir les formes present sur le design canvas.
-Annuler les actions de dessin et de mouvement avec "CTRL+Z" pour faire revenir le design canvas a un état antérieur a l'action annulée.
-Rejouer les actions annulée.

## Mise en place du projet en local

### Prérequis technique
- Java jdk version 8 ou supérieur
- Maven 3.6.3 ou supérieur
- IDE (Visual Studio Code, Eclipse, Netbeans, ...)
- StarUML (visualisation des diagrammes)
- Eventuellement un acces a sonar ou sonarlint(en local) pour obeserver l'état du projet 
Structure du projet
Le dossier src comprend tous les fichiers source du projet
Le dossier diagrams contiendra tous les diagrammes requis pour la modélisation du projet

## Installation
- Cloner le projet
- Importer le projet dans votre IDE
- Lancer la commande `mvn clean install` à la racine du projet
NB Pour toute modification a apporté pensez au préalable à exécuté ( avec la commande `mvn clean verify` )   la série de test déjà écris et éventuellement les complété ou modifié en fonction de vos modifications

## Utilisation 
- Lancer l'application via la classe Main situé dans le fichier `./src/main/java/edu/uga/miage/m1/polygons/gui/App.java`
- Une fois l'interface lancé la forme Square est sélectionné par défaut vous pouvez donc commencer par dessiner ou alors changer de Shape.
- Pour changer de forme(Shape) il suffit de cliquer sur la forme souhaité dans la barre d'outil.
- Pour dessiner une forme(Shape) il suffit de cliquer dans le design canvas.
- Pour déplacer une forme(Shape)  présente dans le design canvas il suffit de placer la souris au dessus de cette forme(Shape) exercer une pression de la souris déplacer le pointeur à l'endroit ou vous voulez déplacer cette forme(Shape) puis relâcher la pression de la souris 
- Pour annuler la dernière commande exécuté utilisé la combinaison CTRL+Z 
          Par exemple si la dernière action que vous avez faite est de déplacer une forme alors en utilisant CTRL+Z cette forme reviendra a sa position initiale
- Pour rejouer la dernière commande qui a été défaite utilisé la combinaison CTRL+Y 
          Par exemple si la aprés avoir ajouter une forme vous avez utilisez CTRL+Z en utilisant CTRL+Y(sans aucune autre action entre ces deux moment) la forme reviendra qui a disparu avec le CTRL+Z reviendra sur le design canva
- Pour importer des données allez dans le menu en haut a gauche de la fenetre vous aurez deux options import depuis fichier xml ou import depuis fichier json.Si aucune données cohérente n'a pu etre tiré du fichier fourni vous recevrez un message
- Pour export des données allez aussi dans le menu en haut a gauche de la fenetre vous aurez deux options export vers un fichier xml ou import vers fichier json.En fonction de votre choix un fichier sera crée que vous pourrez enregister a la destination de votre choix.Le status(echec ou succes) de cet export vous sera notifié par un message.Si aucune forme n'est présent sur le design canva au moment de la tentative d'export l'export ne pourra pas se faire et vous recevrez un messsage vous l'indiquant.
