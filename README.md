# GameEngine
A Minecraft state-based mini-game API for quick and easy development of mini-games.

*NOTE: GameEngine will be replaced by another API when it is ready for public release. All future updates 
       will be done on that API instead of this one.

## Examples
#### Getting Started
To create a game, you will need to create a class which inherits **GameEngine**.
```java
public class Game extends GameEngine {
       
    public Game() {
        super(Plugin pl, String internalName, String publicName);
    }
}
```

The Timer, Game Players, Spectators, etc. will be setup for you. Take a look at the GameEngine class for all available methods!

#### Creating GameStates
To create a GameState, create another class (LobbyState, DeathmatchState, etc.) which inherits **GameState**. You will need to implement three methods.
```java
public class LobbyState extends GameState {
    
    public LobbyState() {
       super(String internalName, String publicName, long timeLength, TimeUnit unit);
    }
    
    @Override
    public void onStateBegin(GameEngine engine) {
    }

    @Override
    public void onStateTick(GameEngine engine) {
    }

    @Override
    public void onStateEnd(GameEngine engine) {
    }
}
```

To register this GameState, use ```this.getGameStates().add(GameState gameState);``` to add a GameState to the game.
*NOTE: The first state that is run is the state you added in first:
```java
// Constructor
public Game {
    super(Plugin plugin, String internalName, String publicName);
    
    // LobbyState will be run first, then when the time left hits 0, StartState will be run.
    this.getGameStates().add(new LobbyState());
    this.getGameStates().add(new StartState());
}
```

You can also add a GameState that always ticks (even when the timer isn't running)! To do so, you would extend GameState as usual, but this time, instead of adding the GameState using ```this.getGameStates().add...```, you would use ```this.getTimer().setAlwaysTickState(GameState gameState);```
Ex.
```java
// Constructor
public Game {
    // Code
    
    // Adding a GameState that always ticks
    this.getTimer().setAlwaysTickState(GameState gameState);
}
```

#### Creating Scoreboards
GameEngine provides an easy way to create minimal flicker scoreboards. To create a new scoreboard, you can use the **GScoreboard** class.
Ex.
```java
public void createScoreboard(Player p) {
    GScoreboard scoreboard = new GScoreboard(GameEngine gameEngine, String displayName, boolean animate);
    scoreboard.addLine("Name: " + p.getName());
    scoreboard.setScoreboard(p);
    
    new BukkitRunnable() {
        int c = 0;
        @Override
        public void run() {
            if(c == 0) {
                scoreboard.setLine(1, "Name: §c" + p.getName());
                c = 1;
            } else {
                scoreboard.setLine(1, "Name: §b" + p.getName());
                c = 0;
            }
        }
    }.runTaskTimer(Plugin plugin, 0, 5);
}
```
*NOTE: Line numbers start with 1, not 0.
*NOTE: At this time, there is no way to change the colour of the display name animation. This will be changed in a future release.

**More examples coming soon!**
