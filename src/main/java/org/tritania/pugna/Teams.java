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

import org.tritania.pugna.Pugna;
import org.tritania.pugna.util.Message;
import org.tritania.pugna.wrappers.*;


public class Teams
{	
	public Pugna pg;
	private HashMap<String, Team> teamList = new HashMap<String, Team>();

    public Teams(Pugna pg)
    {
        this.pg = pg;
    }
    
    public void createTeam(Player player, String teamName, PugnaPlayer tracked)
    {
		//PugnaPlayer tracked = pg.track.getPlayerData(player);
        Team team = new Team(player, teamName, tracked);
		teamList.put(teamName, team);
		tracked.setTeam(teamName);
	}
	
	public void disbandTeam(String teamName)
	{
		teamList.remove(teamName);
	}
	
	public void sendChat(String teamName, String Message)
	{
        HashMap<UUID, PugnaPlayer> players = teamList.get(teamName).getPlayers();
	}
}
   
