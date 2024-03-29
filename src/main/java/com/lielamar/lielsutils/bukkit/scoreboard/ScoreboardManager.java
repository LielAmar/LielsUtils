package com.lielamar.lielsutils.bukkit.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class ScoreboardManager {

    private static final List<ChatColor> colors = Arrays.asList(ChatColor.values());

    private final Plugin plugin;

    private final String title;
    private final Map<UUID, CustomScoreboard> scoreboards;

    public ScoreboardManager(@NotNull Plugin plugin, @NotNull String title) {
        this.plugin = plugin;

        this.title = title;
        this.scoreboards = new HashMap<>();

        this.run();
    }

    /**
     * Injects a player. Sets their scoreboard
     *
     * @param player         Player to inject
     */
    public void injectPlayer(@NotNull Player player) {
        this.scoreboards.put(player.getUniqueId(), new CustomScoreboard(player, ChatColor.YELLOW + "" + ChatColor.BOLD + title));
    }

    /**
     * Injects a player. Sets their scoreboard
     *
     * @param player         Player to inject
     * @param scoreboard     Scoreboard to set
     */
    public void injectPlayer(@NotNull Player player, @NotNull Scoreboard scoreboard) {
        this.scoreboards.put(player.getUniqueId(), new CustomScoreboard(player, scoreboard, ChatColor.YELLOW + "" + ChatColor.BOLD + title));
    }

    /**
     * Ejects a player. Sets their scoreboard to null
     *
     * @param player         Player to eject
     */
    public void ejectPlayer(@NotNull Player player) {
        if(this.scoreboards.get(player.getUniqueId()) != null) {
            this.scoreboards.get(player.getUniqueId()).destroy();
            this.scoreboards.remove(player.getUniqueId());
        } else {
            for(Team team : player.getScoreboard().getTeams()) team.unregister();
            for(Objective obj : player.getScoreboard().getObjectives()) obj.unregister();
        }
    }

    /**
     * Returns a {@link CustomScoreboard} of a player
     *
     * @param player         Player to get the scoreboard of
     * @return               Attached scoreboard of the player
     */
    public @Nullable CustomScoreboard getScoreboard(@NotNull Player player) {
        return this.scoreboards.get(player.getUniqueId());
    }

    /**
     * Runs the scoreboard timer (updates scoreboard every 3ticks)
     */
    public void run() {
        new BukkitRunnable() {
            int effectIndex = 0;
            @Override
            public void run() {
                effectIndex++;
                if(effectIndex > title.length()+7)
                    effectIndex = 0;

                for(CustomScoreboard scoreboard : scoreboards.values()) {
                    String title = setupTitle(effectIndex);

                    scoreboard.updateTitle(title);
                }
            }
        }.runTaskTimer(plugin, 0L, 3L);
    }

    /**
     * Sets up the title
     *
     * @param index         The index of the title
     * @return              A designed title determined by index
     */
    public String setupTitle(int index) {
        if(index >= 0 && index < title.length())
            return ChatColor.BOLD + title.substring(0, index)
                    + ChatColor.GOLD + "" + ChatColor.BOLD + title.charAt(index)
                    + ChatColor.YELLOW + "" + ChatColor.BOLD + title.substring(index+1);

        if(index == (title.length())) return ChatColor.YELLOW + "" + ChatColor.BOLD + title;
        if(index == (1+title.length())) return ChatColor.WHITE + "" + ChatColor.BOLD + title;
        if(index == (2+title.length())) return ChatColor.YELLOW + "" + ChatColor.BOLD + title;
        if(index == (3+title.length())) return ChatColor.YELLOW + "" + ChatColor.BOLD + title;
        if(index == (4+title.length())) return ChatColor.YELLOW + "" + ChatColor.BOLD + title;

        return ChatColor.WHITE + "" + ChatColor.BOLD + title;
    }


    public static class CustomScoreboard {

        private Player player;

        private Scoreboard scoreboard;
        private Objective objective;
        private int lines;

        public CustomScoreboard(@NotNull Player player, @NotNull String title, @NotNull Line... lines) {
            this(player, Bukkit.getScoreboardManager().getNewScoreboard(), title, lines);
        }

        public CustomScoreboard(@NotNull Player player, @NotNull Scoreboard scoreboard, @NotNull String title, @NotNull Line... lines) {
            this.player = player;

            this.scoreboard = scoreboard;
            if(scoreboard.getObjective(player.getName() + "pg") != null)
                scoreboard.getObjective(player.getName() + "pg").unregister();
            this.objective = scoreboard.registerNewObjective(player.getName().substring(0,
                    Math.min(player.getName().length(), 14)) + "pg", "dummy");
            this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);
            this.lines = lines.length;

            this.updateTitle(title);
            this.updateLines(lines);
            this.display();
        }

        public Scoreboard getScoreboard() {
            return this.scoreboard;
        }

        public void setScoreboard(@NotNull Scoreboard scoreboard) {
            this.scoreboard = scoreboard;
            this.player.setScoreboard(scoreboard);
        }

        /**
         * Updates the scoreboard's lines
         *
         * @param lines        Lines to update
         */
        public void updateLines(@NotNull Line[] lines) {
            if(lines.length != this.lines) {
                for(String s : this.objective.getScoreboard().getEntries())
                    this.scoreboard.resetScores(s);
            }

            for(Line line : lines) {
                if(line.getTeam() != null) {
                    Team team = this.scoreboard.getTeam(line.getTeam());
                    if (team == null) team = this.scoreboard.registerNewTeam(line.getTeam());

                    team.setPrefix(line.getPrefix());
                    if(team.getEntries().size() == 0) team.addEntry(line.getEntry()); // Adding a fake entry if there isn't one (Fake entry = color code)
                    team.setSuffix(line.getSuffix());
                }
                this.objective.getScore(line.getEntry()).setScore(line.getLineNumber()); // Getting the line we just modified and updating it
            }

            this.lines = lines.length;
        }

        /**
         * Updates the scoreboard's title
         *
         * @param title        Title to update
         */
        public void updateTitle(@NotNull String title) {
            if(this.objective != null)
                this.objective.setDisplayName(title);
        }

        /**
         * Displays the scoreboard to the player
         */
        public void display() {
            this.player.setScoreboard(this.scoreboard);
        }

        /**
         * Destroys the scoreboard
         */
        public void destroy() {
            if(this.player == null || this.player.getScoreboard().getObjective(DisplaySlot.SIDEBAR) == null) return;

            this.player.getScoreboard().getObjective(DisplaySlot.SIDEBAR).unregister();

            this.player = null;

            this.scoreboard = null;
            this.objective = null;
        }
    }


    public static class Line {

        private final String team, prefix, entry, suffix;
        private final int lineNumber;

        public Line(String team, String prefix, String suffix, int lineNumber) {
            this.team = team;
            this.prefix = prefix;
            this.entry = colors.get(lineNumber).toString();
            this.suffix = suffix;
            this.lineNumber = lineNumber;
        }

        public String getTeam() { return this.team; }
        public String getPrefix() { return this.prefix; }
        public String getSuffix() { return this.suffix; }
        public String getEntry() { return this.entry; }
        public int getLineNumber() { return this.lineNumber; }
    }
}