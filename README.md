# Persistence_g2_4

## Auteurs
- ### Damessi Samuel
- ### Freund Galeano Matias

## Introduction
Persistence_g2_4 est un projet d'outil permettant de representer des formes(shapes) sur une interface graphique 

## Objectifs
Ce projet s'inscrit dans une démarche visant à mettre en pratique les notions de design pattern (patrons de conception), de qualité de code, et de tests. Tout au long du développement, les auteurs ont utilisé et adapté différents design patterns, comme en témoignent le code et les diagrammes de classe fournis. L'objectif principal était de mettre en place les fonctionnalités souhaitées tout en respectant les bonnes pratiques de codage. Le code produit a été scruté à l'aide de l'outil `Sonar`, et des tests unitaire et d'intégration `JUnit` ont été ajoutés pour garantir que les résultats obtenus correspondent aux attentes.


## Réalisations

### Existant 
Nous somme partie d'une interface permettant de selectionner des forme(Shape) dans une barre d'outil(toolbar) et de les posé dans un design canva. Une fois posé on ne peut plus agir sur la forme posé(on ne peut pas la deplacer, la supprimer...).

Pour consulter chacune des iterations cliquez dessus

### [Itération 1](Iteration1.md) 

### [Itération 2](Iteration2.md) 

### [Itération 3](Iteration3.md) 

### [Itération 4](Iteration4.md) 


## Fonctionnalités 
-Dessiner des forme dans un design canvas.
-Exporter en JSON ou en XML des données sur les shapes présent sur le design canvas.
-Importer des données précédemment exporté pour initialiser le design canvas.
-Faire mouvoir les formes present sur le design canvas.
-Annuler les actions de dessin et de mouvement avec "CTRL+Z" pour faire revenir le design canvas a un état antérieur a l'action annulée.
-Rejouer les actions annulée.

## Mise en place du projet en local

### Prérequis technique
- Java jdk version 13 ou supérieur
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
