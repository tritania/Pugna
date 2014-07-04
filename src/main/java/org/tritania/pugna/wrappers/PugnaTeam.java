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

package org.tritania.pugna.wrappers;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.io.Serializable;

import org.bukkit.entity.Player;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import org.tritania.pugna.Pugna;
import org.tritania.pugna.wrappers.*;
import org.tritania.pugna.util.*;

public class PugnaTeam implements Serializable
{

    private String teamName;
    private UUID founder;
    private int kills;
    private int deaths;
    private PugnaScore score;
    private int members;
    private int onlineMembers;
    private HashMap<UUID, PugnaPlayer> teammembers = new HashMap<UUID, PugnaPlayer>();

    public PugnaTeam(Player founder, String teamName, PugnaPlayer data)
    {
        this.founder = founder.getUniqueId();
        this.teamName = teamName;
        this.teammembers.put(founder.getUniqueId(), data);
        this.members = 1;
        this.onlineMembers = 1;
        this.score = new PugnaScore();
    }

    public void addMember(Player player, PugnaPlayer data)
    {
        teammembers.put(player.getUniqueId(), data);
        sendMessage(player.getDisplayName() + ChatColor.DARK_AQUA + " has joined the team");
        data.setTeam(teamName);
        members++;
        onlineMembers++;
    }

    public void removeMember(Player player, PugnaPlayer play)
    {
        teammembers.remove(player.getUniqueId());
        play.removeTeam();
        members--;
        onlineMembers--;
    }

    public void setNewFounder(Player player)
    {
        founder = player.getUniqueId();
    }

    public boolean checkFounder(Player player)
    {
        if(player == null || founder == null)
        {
            return false;
        }
        else
        {
            return player.getUniqueId().equals(founder);
        }
    }

    public HashMap<UUID, PugnaPlayer> getMembers()
    {
        return teammembers;
    }

    public int getMembersI()
    {
        return members;
    }

    public void sendMessage(Player sender, String message)
    {
        for (Map.Entry<UUID, PugnaPlayer> entry : teammembers.entrySet())
        {
            UUID player = entry.getKey();
            Player player2 = Bukkit.getPlayer(player);
            player2.sendMessage(ChatColor.DARK_AQUA + "[Team " + sender.getDisplayName() + ChatColor.DARK_AQUA + "]" + message);
        }
    }

    public void sendMessage(String message)
    {
        for (Map.Entry<UUID, PugnaPlayer> entry : teammembers.entrySet())
        {
            UUID player = entry.getKey();
            Player player2 = Bukkit.getPlayer(player);
            player2.sendMessage(ChatColor.DARK_AQUA + "[Team Notification] " + message);
        }
    }

    public void setOffline()
    {
        onlineMembers--;
    }

    public void setOnline()
    {
        onlineMembers++;
    }

    public PugnaScore getScore()
    {
        return score;
    }
}
