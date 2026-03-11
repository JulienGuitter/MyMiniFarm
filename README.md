# MyMiniFarm

MyMiniFarm est un jeu de gestion de ferme développé en Java 17 avec JavaFX. Ce projet a été réalisé dans le cadre d'un cours de Java. Il s'inspire de jeux comme Stardew Valley et propose une expérience de gestion agricole, de production et d'organisation de la ferme.

> **Note :** Ce projet a été réalisé pour un cours et ne sera pas maintenu.

## Sommaire
- [Présentation](#présentation)
- [Fonctionnalités](#fonctionnalités)
- [Installation](#installation)
- [Utilisation](#utilisation)
- [Documentation technique](#documentation-technique)
- [Changer la vitesse du temps](#changer-la-vitesse-du-temps)
- [Crédits](#crédits)
- [Licence](#licence)

## Présentation
MyMiniFarm vous place dans la peau d'un fermier devant gérer ses cultures, ses machines et son inventaire. Le jeu propose une interface moderne et intuitive, avec des graphismes pixel art et une ambiance sonore immersive.

## Fonctionnalités
- Gestion de cultures
- Système d'inventaire et de shop
- Machines de transformation et conteneurs
- Interface utilisateur complète (menu, inventaire, paramètres, etc.)
- Contrôles personnalisables (clavier, souris)
- Ambiance sonore dynamique

## Installation
1. **Prérequis** :
   - Java 17 ou supérieur (avec JavaFX)
   - Gradle (inclus dans le projet via `gradlew`)
2. **Clonage du projet** :
   ```zsh
   git clone <url-du-repo>
   cd MyMiniFarm/MyMiniFarm
   ```
3. **Compilation et exécution** :
   ```zsh
   ./gradlew run
   ```

## Utilisation
- Lancez le jeu et créez votre partie.
- Utilisez les menus pour naviguer, gérer votre ferme, acheter/vendre des produits, etc.
- Les contrôles sont configurables dans le menu paramètres.

## Documentation technique
- Le projet suit une architecture MVC (Model-View-Controller).
- Le code source principal se trouve dans `src/main/java/fr/eseo/e3a/myminifarm/`.
- Un diagramme de classes UML est disponible dans `docs/MyMiniFarm.puml`.
- Les ressources (textures, musiques) sont dans `src/main/resources/fr/eseo/e3a/myminifarm/`.
- Les tests unitaires sont dans `src/test/java/fr/eseo/e3a/myminifarm/`.

## Changer la vitesse du temps
La vitesse d'écoulement du temps dans le jeu peut être modifiée dans le fichier :
- `MyMiniFarm/src/main/java/fr/eseo/e3a/myminifarm/controller/GameLoop.java`

Modifiez la valeur de la constante suivante (plus la valeur est grande, plus le temps passe vite) :
```java
public static final int TIME_RATIO = 60; // Par défaut
```

## Crédits
- **Textures** :
  - Certaines textures ont été générées par GPT.
  - D'autres textures proviennent du jeu Stardew Valley (usage non commercial, hommage).
- **Musiques** :
  - Les musiques du jeu ont été générées avec Suno.
- **Développement** :
  - Projet réalisé par Julien et Maxence.

## Licence
Ce projet est distribué sous licence MIT. Les ressources issues de Stardew Valley restent la propriété de leurs auteurs respectifs et sont utilisées à des fins d'apprentissage et de démonstration uniquement.

---

