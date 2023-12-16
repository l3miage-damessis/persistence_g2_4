# Iteration 4 


## But recherché
Le but de cette itération est d'adopter une approche dite par `composant` . L'interet de cette approche est d'avoir des brique logicielle réutilisable(par nous ou par d'autre développeurs), interchangeable, plus facilement maintenable et permettant aussi de pouvoir faire preuve d'abstraction et de mieux gerer les responsabilités.

## Réalisations
### Nouveau composant
Nous avons a cet effet crée un nouveau composant nommé `persistence_metier_g2_4`.Comme son nom l'indique ce nouveau composant nous permet d'isoler completement la logique metier.
Pour plus de détail sur ce composant cliquer ici `->` [Composant persistence_metier_g2_4](ComposantPersistenceMetier.md) 

Pour s'assurer de la fiabilité de ce composant qui est réutilisé nous avons implementé des test unitaire soigneusement élaboré et nous offrant un taux de couverture de plus de `90%` d'apres les relevés fourni par le module `jacoco` 

### Application principal
L'application principal garde le nom `persistence_g2_4` et ne contient plus que la vue et les commandes. 

On peut donc retrouver dans `persistence_g2_4` les classes 
- `App`, 
- `GUIHelper`, 
- `JDrawingFrame`, 
- `JDrawingPanel`, 
- `DrawTool` 

et pour les Command on a l'interface 
- `Command` 

ainsi que les classe 
- `AddShapeCommand` 
 
- `MoveCommand`.  

Elle ( `persistence_g2_4` ) utilise les different élement(classe) et fonctions proposé par `persistence_metier_g2_4` en faisant des imports.

Nous avons remanié les test de l'application principal pour prendre en compte cette nouvelle approche et avons rajouté de nouveau test.Nous en somme pour l'instant a un taux de couverture de `44 %`  d'après nos métrique sur `sonar` .

Eu égard au test automatique(unitaire et d'integration) que nous executé ainsi qu'au test utilisateur auxquels nous nous sommes soumis, nous considerons comme `stable` tout ce que nous avons obtenu jusqu'a ce niveau

## Remarque 

A cette étape nous avons détecté un léger bug qui fait dans certains cas (tout juste aprés un export et lors de l'augmentation de la taille de la fenetre) les shapes présent sur l'interface devienne temporairement invisible malgré qu'ils soient réélement présent et donc on doit réaliser une nouvelle action(tel que rajouter un nouveau shape) pour les rendre visibles.

Nous avons apppliquer un correctif qui rafraichit les shapes quand on modifie la taille de la fenetre ainsi que juste après chaque export ou tentative d'export avorté

## Modèles utilisé

## Nouveau diagramme de classe de l'application principal

<img title="a title" alt="Alt text" src="conception\image\Diagramme_composant_principal_persistence_g2_4.png">

## Diagramme de composant global