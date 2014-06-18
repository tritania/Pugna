/*
 * Copyright 2014 Erik Wilson <erikwilson@magnorum.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.tritania.pugna;

/*Start Imports*/
import org.bukkit.permissions.PermissibleBase;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.Material;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

import org.tritania.pugna.Pugna;
import org.tritania.pugna.util.Message;
import org.tritania.pugna.wrappers.*;

public class Stats
{

    public Pugna pg;

    public Stats(Pugna pg)
    {
        this.pg = pg;
    }


    public void setBountyBoard(Player player, Player contractor, Reward reward)
    {
        ScoreboardManager mang = Bukkit.getScoreboardManager();
        Scoreboard board = mang.getNewScoreboard();

        Objective objective = board.registerNewObjective("Stats", "dummy");

        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName(ChatColor.BLUE +  "Bounty");

        Score bounty = objective.getScore(Bukkit.getOfflinePlayer("New Bounty!"));
        Score cont = objective.getScore(Bukkit.getOfflinePlayer("From: " + contractor.getPlayerListName())); //might be too long
        Score value = objective.getScore(Bukkit.getOfflinePlayer("Reward: " + reward.getItem()));

        bounty.setScore(1);
        cont.setScore(1);
        value.setScore(reward.getAmount());

        player.setScoreboard(board);
        scoreTimer(player);
    }

    public void setTeamBoard(Player player, PugnaTeam team)
    {
        ScoreboardManager mang = Bukkit.getScoreboardManager();
        Scoreboard board = mang.getNewScoreboard();

        Objective objective = board.registerNewObjective("Stats", "dummy");

        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName(ChatColor.BLUE +  "Team Stats");
    }

    public void setPlayerBoard(Player player)
    {
        ScoreboardManager mang = Bukkit.getScoreboardManager();
        Scoreboard board = mang.getNewScoreboard();

        Objective objective = board.registerNewObjective("Stats", "dummy");

        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName(ChatColor.BLUE +  "Your Stats");

        Score score = objective.getScore(Bukkit.getOfflinePlayer("Score: "));
        Score kills = objective.getScore(Bukkit.getOfflinePlayer("Kills: " ));
        Score deaths = objective.getScore(Bukkit.getOfflinePlayer("Deaths: " ));
        Score teamRank = objective.getScore(Bukkit.getOfflinePlayer("Team Rank: "));

        PugnaPlayer play = pg.track.getPlayerData(player);

        score.setScore(play.getScore());
        kills.setScore(play.getKills());
        deaths.setScore(play.getDeaths());

        player.setScoreboard(board);
        scoreTimer(player);
    }

    public void removeBoard(Player player)
    {
        ScoreboardManager manger = Bukkit.getScoreboardManager();
        Scoreboard board = manger.getNewScoreboard();

        player.setScoreboard(board);
    }

    public void scoreTimer(final Player player)
    {
        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                PugnaPlayer play = pg.track.getPlayerData(player);
                if (!play.getBoard())
                {
                    removeBoard(player);
                }
            }
        }.runTaskLater(pg, 1200); //may add config value in the future
    }
}

