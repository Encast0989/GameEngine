package club.encast.gameengine;

import club.encast.gameengine.util.Util;
import com.google.common.base.Joiner;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import java.util.ArrayList;
import java.util.List;

public class GScoreboard {

    private final int MAX_LINES = 16;
    private final int ANIMATE_DELAY = 5;
    private GameEngine engine;
    private Scoreboard scoreboard;
    private Objective objective;
    private List<String> scores = new ArrayList<>(MAX_LINES);

    public GScoreboard(GameEngine engine, String displayName, boolean animateName) {
        this.engine = engine;
        scoreboard = Bukkit.getServer().getScoreboardManager().getNewScoreboard();
        objective = scoreboard.registerNewObjective("core", "dummy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName(displayName);

        if(animateName) {
            String name = ChatColor.stripColor(displayName);
            new BukkitRunnable() {
                String[] chars = Util.toCharArrayAsString(name);
                int limit = name.toCharArray().length;
                int current = 0;
                @Override
                public void run() {
                    if(current < limit) {
                        chars[0] = "§f§l" + chars[0];
                        if(current + 1 < limit) {
                            chars[current] = "§e§l" + chars[current];
                            chars[current + 1] = "§6§l" + chars[current + 1];
                        }
                        setDisplayName(Joiner.on("").join(chars));
                        // Optimize this later, loops over and over again isn't good!
                        chars = Util.toCharArrayAsString(name);
                    } else if(current < limit + 20) { // This will make it stay on the same one for 5 seconds.
                        setDisplayName("§6§l" + name);
                    } else {
                        // This is -1 because the incrementer will make it to 0 and fully reset the cycle.
                        current = -1;
                    }
                    // Incrementer
                    current++;
                }
            }.runTaskTimer(engine.getPlugin(), 0, ANIMATE_DELAY);
        }
    }

    public Scoreboard getScoreboard() {
        return scoreboard;
    }

    public Objective getObjective() {
        return objective;
    }

    public List<String> getScores() {
        return scores;
    }

    public void setDisplayName(String displayName) {
        objective.setDisplayName(displayName);
    }

    public GScoreboard addLine(String text) {
        if(scores.size() <= MAX_LINES) {
            for(String s : scores) {
                Score score = objective.getScore(s);
                score.setScore(score.getScore() + 1);
            }
            scores.add(text);
            objective.getScore(text).setScore(0);
        }
        return this;
    }

    public void setLine(int line, String text) {
        if(scores.get(line - 1) != null) {
            if(!scores.get(line - 1).equals(text)) {
                scoreboard.resetScores(scores.get(line - 1));
                objective.getScore(text).setScore(scores.size() - line);
                scores.set(line - 1, text);
            }
        }
    }

    public void removeLine(int line) {
        for (int i = 0; i < scores.size(); i++) {
            if(i > (line - 1)) {
                Score score = objective.getScore(scores.get(i));
                score.setScore(score.getScore() - 1);
            }
        }
        scoreboard.resetScores(scores.get(line - 1));
        scores.remove(line - 1);
    }

    public void setScoreboard(Player p) {
        p.setScoreboard(scoreboard);
    }
}
