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

import org.tritania.pugna.Pugna;
import org.tritania.pugna.util.*;
import org.tritania.pugna.wrappers.*;

public class PugnaPlayerTracker
{
    public Pugna pg;
    private PugnaPlayer emperor;
    private HashMap<UUID, PugnaPlayer> players = new HashMap<UUID, PugnaPlayer>();

    public PugnaPlayerTracker(Pugna pg)
    {
        this.pg = pg;
        emperor = pg.storage.loadEmperor();
    }

    public String getEmperorName()
    {
        if (emperor == null || emperor.getName() == null || emperor.getName().isEmpty())
        {
            return "Their currently is no emperor!";
        }
        else
        {
            return emperor.getName() + " is the current emperor";
        }
    }

    public void checkEmperor(PugnaPlayer player)
    {
        System.out.println(player.getScore() + " --- " + emperor.getScore());
        if (emperor == null || player.getScore() > emperor.getScore())
        {
            emperor = player;
        }
    }

    public void startTracking(Player player)
    {
        pg.storage.check(player); //check for player file
        PugnaPlayer track = new PugnaPlayer(player.getDisplayName());
        players.put(player.getUniqueId(), track);
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
        Player[] playersSave = Bukkit.getOnlinePlayers();
        for (Player play : playersSave)
        {
            pg.storage.savePlayer(play);
            players.remove(play.getUniqueId());
        }
        pg.storage.saveEmperor(emperor);
    }

    public void loadPlayers()
    {
        Player[] playersSave = Bukkit.getOnlinePlayers();
        for (Player play : playersSave)
        {
            if (pg.storage.check(play))
            {
                PugnaPlayer loaded = pg.storage.loadPlayer(play);
                players.put(play.getUniqueId(), loaded);
            }
        }
    }
}
