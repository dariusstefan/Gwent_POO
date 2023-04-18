# GwentStone

### Packages

* **cards/**
    * **Card** - abstract class which contains general information about a card
    * **HeroCard** - abstract class which extends class Card; in addition, it has a health field and an ability method
    * **EnvironmentCard** - abstract class which extends class Card; in addition, it has an ability method
    * **MinionCard** - extends class Card; in addition, it has health and attack damage fields, and methods to attack another minion, to attack enemy hero and to use ability; this class also contains fields which tells if a minion is first row quality, if a minion is frozen and if a minion is a tank
    * **EmpressThorina** - extends HeroCard class; contains an implementation for the ability method
    * **LordRoyce** - extends HeroCard class; contains an implementation for the ability method
    * **GeneralKocioraw** - extends HeroCard class; contains an implementation for the ability method
    * **KingMudface** - extends HeroCard class; contains an implementation for the ability method
    * **Firestorm** - extends EnvironmentCard class; contains an implementation for the ability method
    * **Winterfell** - extends EnvironmentCard class; contains an implementation for the ability method
    * **HeartHound** - extends EnvironmentCard class; contains an implementation for the ability method
    * **Miraj** - extends MinionCard class; contains an implementation for the ability method
    * **TheCursedOne** - extends MinionCard class; contains an implementation for the ability method
    * **TheRipper** - extends MinionCard class; contains an implementation for the ability method
    * **Disciple** - extends MinionCard class; contains an implementation for the ability method
* **entities/**
    * **Game** - a class which models a game of GwentStone; it contains the game-board, the active player and the starting player; a game has rounds, each with 2 turns
    * **Player** - a class which models a player of GwentStone; a player has multiple decks, a deck which is used in the current game, a hand of cards and a hero; it contains also a mana to spend
* **interpreters/**
    * **CardInterpreter** - a utility class that contains only a static method which takes an input of a card and creates a card of the correct type based on its name
    * **Logger** - a class that is used to print specific information during a game; it contains a method which takes an input command and act based on that; if the command is not for debugging, a GameInterpreter is called
    * **GameInterpreter** - a class that is used to translate game commands into actions of players; it has one big method which switch to a specific method based on the command after it extracts the active player

### General description of my implementation
My game, player and cards classes are based on the input classes. <br/><br/>
Game class has a constructor which takes as argument a reference to a GameInput object. Player class contains a DecksInput field from which it extracts the deck of the current game. <br/><br/>
This deckInUsage is an arraylist of Cards which is made by adding elements returned by the CardInterpreter which creates one Card object from a CardInput object. <br/><br/>
In the Main class, in action method, an arraylist of games is made from the input, two player are created and a Logger is created as well. 
For each game, the initial state is set: decks for players, heroes, initial mana and heroes health, round to be first and the starting player.  
Next, for each command taken from the input the Logger interprets it. When a hero is killed the game ends, and it only accepts debug commands. This is the way for every game. <br/><br/>
An object of class Logger contains an object of class GameInterpreter. A GameInterpreter can be instanced only in Logger, so it doesn't need to be public. <br/><br/>
The CardInterpreter class is not meant to be instanced, so its constructor is private. <br/><br/>
Class Card is an abstract one because there is no card that is simply a card, cards are classified, but they share some qualities and functionalities. 
In the same context, classes EnvironmentCard and HeroCard are also abstract, but they extend class Card because they have more functionalities. 
MinionCard is not abstract because some minions don't have any special functionalities, so they are simply minions and this class must have instances. But this class is extended, 
and method useAbility (which does nothing in MinionCard) is overridden for Disciple, The Cursed One, Miraj and The Ripper minions. <br/><br/>
To use the JSON format of output and the putPOJO method, there is an abstract method in class Card which must return a deep-copied object of a card, because putPOJO takes the values 
at the end of execution for the reference passed as its parameter, and some objects change their fields as the execution continues. <br/><br/> 
This method have implementations in classes HeroCard, EnvironmentCard and MinionCard. For MinionCard a copy-constructor is used, but for the other two a copy abstract method is used because they (the classes) are abstract.
In their inheritors, these copy methods are implemented by returning a new object of that type with the same values for fields.

