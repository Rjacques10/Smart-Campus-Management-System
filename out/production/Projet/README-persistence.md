# Étape 2 : Persistance avec SQLite + JDBC

## Ce qui a changé par rapport à la version console pure

- **`Database.java`** (nouveau) : ouvre la connexion vers `campus.db` (créé automatiquement)
  et crée les tables `students` et `courses` si elles n'existent pas encore.
- **`StudentRepository.java`** (nouveau) : toute la logique SQL (INSERT/SELECT/UPDATE/DELETE)
  pour les étudiants et leurs cours. C'est la seule classe qui écrit du SQL.
- **`Main.java`** (modifié) : n'utilise plus de `ArrayList<Student>` en mémoire.
  Chaque action (inscription, recherche, paiement, modification...) passe par
  `studentRepository` et va lire/écrire directement dans `campus.db`.
- **`Staff` reste en mémoire pour l'instant** — ce sera la prochaine étape,
  sur le même principe.

## Prérequis pour compiler et lancer

Il faut le pilote JDBC SQLite (Xerial) sur le classpath. Deux options :

**Option A — Ubuntu/Debian (comme dans cet environnement) :**
```bash
sudo apt-get install libxerial-sqlite-jdbc-java sqlite3
```
Le driver se trouve alors dans `/usr/share/java/sqlite-jdbc.jar`
(+ `/usr/share/java/slf4j-api.jar` et `/usr/share/java/slf4j-nop.jar`, requis en dépendance).

**Option B — Télécharger le .jar directement (si tu utilises IntelliJ, Maven, etc.) :**
Ajoute la dépendance Maven :
```xml
<dependency>
    <groupId>org.xerial</groupId>
    <artifactId>sqlite-jdbc</artifactId>
    <version>3.44.1.0</version>
</dependency>
```
Ou dans IntelliJ : `File > Project Structure > Libraries > +` puis ajoute le `.jar`
téléchargé depuis https://github.com/xerial/sqlite-jdbc/releases

## Compiler et lancer (ligne de commande)

```bash
javac -cp .:sqlite-jdbc.jar:slf4j-api.jar:slf4j-nop.jar -d out *.java
java  -cp out:sqlite-jdbc.jar:slf4j-api.jar:slf4j-nop.jar Main
```

Un fichier `campus.db` apparaîtra dans le dossier courant. C'est ta base de données —
tu peux l'inspecter avec `sqlite3 campus.db "SELECT * FROM students;"`.

## Testé et vérifié

- Inscription d'un étudiant + un cours + paiement partiel dans un premier lancement
- Fermeture complète du programme
- Relancement dans un **nouveau processus** : l'étudiant, son cours et son
  statut de paiement ("PARTIALLY PAID") sont bien retrouvés depuis la base.
