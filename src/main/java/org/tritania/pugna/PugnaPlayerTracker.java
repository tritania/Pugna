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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.List;
import java.util.Collection;

import org.bukkit.permissions.PermissibleBase;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.Material;
import org.bukkit.scheduler.BukkitRunnable;

import org.tritania.pugna.Pugna;
import org.tritania.pugna.util.*;
import org.tritania.pugna.wrappers.*;
import org.tritania.pugna.util.Log;

public class PugnaPlayerTracker
{
    public Pugna pg;
    private HashMap<UUID, PugnaPlayer> players = new HashMap<UUID, PugnaPlayer>();

    public PugnaPlayerTracker(Pugna pg)
    {
        this.pg = pg;
    }

    public void startTracking(Player player)
    {
        if (pg.storage.check(player))
        {
            PugnaPlayer track = pg.storage.loadPlayer(player);
            players.put(player.getUniqueId(), track);
        }
        else
        {
            Log.info("Creating new config file for %s", player.getPlayerListName());
            PugnaPlayer track = new PugnaPlayer(player.getName());
            players.put(player.getUniqueId(), track);
        }
    }

    public void stopTracking(Player player)
    {
        players.remove(player.getUniqueId());
    }

    public boolean checkTracking(Player player)
    {
        if(players.containsKey(player.getUniqueId()))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public PugnaPlayer getPlayerData(Player player)
    {
        UUID playerId = player.getUniqueId();
        return players.get(playerId);
    }

    public void savePlayers()
    {
		Collection<? extends Player> playersSave = Bukkit.getOnlinePlayers();
        for (Iterator iterator = playersSave.iterator(); iterator.hasNext();) 
        {
			Player player = (Player) iterator.next();
            pg.storage.savePlayer(player);
            players.remove(player.getUniqueId());
		}
    }

    public void loadPlayers()
    {
        Collection<? extends Player> playersSave = Bukkit.getOnlinePlayers();
        for (Player play : playersSave)
        {
            if (pg.storage.check(play))
            {
                PugnaPlayer loaded = pg.storage.loadPlayer(play);
                players.put(play.getUniqueId(), loaded);
            }
        }
    }

    public void startCombatTimer(final PugnaPlayer player)
    {
        player.setCombatState(true);
        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                player.setCombatState(false);
            }
        }.runTaskLater(pg, pg.config.combatTimer);
    }

    public PugnaTeam getTeam (Player player)
    {
        PugnaPlayer play = pg.track.getPlayerData(player);
        if (play.getTeamState())
        {
            String teamName = play.getTeam();
            PugnaTeam team = pg.teams.getTeam(teamName);
            return team;
        }
        return null;
    }
}
