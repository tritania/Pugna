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
import org.bukkit.ChatColor;

import org.tritania.pugna.Pugna;
import org.tritania.pugna.wrappers.*;
import org.tritania.pugna.util.*;

public class PugnaTeam 
{
    
    private String teamName;
    private Player founder;
    private HashMap<UUID, PugnaPlayer> teammembers = new HashMap<UUID, PugnaPlayer>(); 
    
    public PugnaTeam(Player founder, String teamName, PugnaPlayer data)
    {
        founder = founder; 
        teamName = teamName;
        teammembers.put(founder.getUniqueId(), data);
    }
    
    public void addMember(Player player, PugnaPlayer data)
    {
        teammembers.put(player.getUniqueId(), data); 
        sendMessage(player.getDisplayName() + "has joined the team");
    }
    
    public void removeMember(Player player)
    {
        teammembers.remove(player.getUniqueId());
    }
    
    public void setNewFounder(Player player)
    {
        founder = player;
    }
    
    public boolean checkFounder(Player player)
    {
        return player.equals(founder);
    }
    
    public HashMap<UUID, PugnaPlayer> getPlayers()
    {
        return teammembers;
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
            player2.sendMessage(ChatColor.DARK_AQUA + "[Team Notification]" + message);
        }
    }
}
