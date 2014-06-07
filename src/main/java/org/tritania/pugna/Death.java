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
import org.bukkit.inventory.*;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.scheduler.BukkitRunnable;

import org.tritania.pugna.Pugna;
import org.tritania.pugna.util.*;

public class Death 
{
	private HashMap<String, UUID> deathlocations = new HashMap<String, UUID>();
	private HashMap<String, UUID> deathlocationspost = new HashMap<String, UUID>();
	
	public Pugna pg;

    public Death(Pugna pg)
    {
        this.pg = pg;
        if (pg.config.deathChest)
        {
			pg.getServer().getScheduler().runTaskLater(pg, new Runnable() 
			{
				public void run() 
				{
					destroyAll();
				}
			}, pg.config.deathChestTime);
		}
    }
    
    public void destroyAll() 
    {
		Iterator<Map.Entry<String, UUID>> iterator = deathlocationspost.entrySet().iterator();
        while(iterator.hasNext())
        {
            Map.Entry<String, UUID> entry = iterator.next();
            String[] ld = entry.getKey().split(",");
			Location location = new Location(Bukkit.getWorld(ld[0]),Double.parseDouble(ld[1]),Double.parseDouble(ld[2]),Double.parseDouble(ld[3]));
			Block toDestroy = location.getBlock();
			if(toDestroy.getType().equals(Material.CHEST)) 
			{
				toDestroy.setType(Material.AIR);
			}
			iterator.remove();
		}
	}
	
	public void removeChest(UUID playerID, Location local) 
	{
		
		String location = local.getWorld().getName() + "," + String.valueOf( local.getBlockX()) + "," + String.valueOf( local.getBlockY()) + "," + String.valueOf(local.getBlockZ());
		Iterator<Map.Entry<String, UUID>> iterator = deathlocations.entrySet().iterator();
        while(iterator.hasNext())
        {
            Map.Entry<String, UUID> entry = iterator.next();
            UUID IDdestroy = entry.getValue();
            String loc    = entry.getKey();
			if (IDdestroy.equals(playerID) && loc.equals(location))
			{
				Block toDestroy = local.getBlock();
				if(toDestroy.getType().equals(Material.CHEST)) 
				{
					toDestroy.setType(Material.AIR);
				}
				iterator.remove();
			}
		}
	}
	
	public void removePlayerChests(UUID playerID)
	{
		Iterator<Map.Entry<String, UUID>> iterator = deathlocations.entrySet().iterator();
        while(iterator.hasNext())
        {
            Map.Entry<String, UUID> entry = iterator.next();
            UUID IDdestroy = entry.getValue();
			if (IDdestroy.equals(playerID))
			{
				String[] ld = entry.getKey().split(",");
				Location location = new Location(Bukkit.getWorld(ld[0]),Double.parseDouble(ld[1]),Double.parseDouble(ld[2]),Double.parseDouble(ld[3]));
				removeChest(playerID, location);
			}
		}
	}
	
	public void changeOwnership(UUID current, Player postplayer)
	{
		UUID post = postplayer.getUniqueId();
		Iterator<Map.Entry<String, UUID>> iterator = deathlocations.entrySet().iterator();
        while(iterator.hasNext())
        {
            Map.Entry<String, UUID> entry = iterator.next();
            UUID preId = entry.getValue();
			if (preId.equals(current))
			{
				deathlocations.put(entry.getKey(), post);
				String[] ld = entry.getKey().split(",");
				Location location = new Location(Bukkit.getWorld(ld[0]),Double.parseDouble(ld[1]),Double.parseDouble(ld[2]),Double.parseDouble(ld[3]));
				deathChestTimer(postplayer, location);
			}
		}		
	}
    
    public void createDeathChest(Player player, List<ItemStack> drops) 
    {
		if (pg.config.deathChest)
		{
			Location death = player.getLocation();
			Block chest = death.getBlock();
			chest.setType(Material.CHEST);
			Chest chestp = (Chest) death.getBlock().getState();
			for (ItemStack tmp : drops)
			{
				chestp.getInventory().addItem(tmp);
			}
			
			UUID playerId = player.getUniqueId();
			String local = death.getWorld().getName() + "," + String.valueOf( death.getBlockX()) + "," + String.valueOf( death.getBlockY()) + "," + String.valueOf(death.getBlockZ());
			deathlocations.put(local, playerId);
			
			CommandSender pc = (CommandSender) player;
			Message.info(pc, "You have 5 minutes to retrive your items, good luck!");
			
			deathChestTimer(player, death);
		}
	}
	
	public void deathChestTimer(final Player player, final Location location)
	{
		new BukkitRunnable()
		{
			@Override
			public void run()
			{
				UUID playID = player.getUniqueId();
				removeChest(playID, location);
			}
		}.runTaskLater(pg, pg.config.deathChestTime);
	}
	
	
	
	public boolean checkPlayer(Location location, Player player)
	{
		String local = location.getWorld().getName() + "," + String.valueOf( location.getBlockX()) + "," + String.valueOf( location.getBlockY()) + "," + String.valueOf(location.getBlockZ());
		UUID playerId = player.getUniqueId();
		String match = null;
		for (Map.Entry<String, UUID> entry : deathlocations.entrySet())
		{
				if (entry.getValue().equals(playerId))
				{
					match = entry.getKey();
				}
		}
		if (player.hasPermission("pugna.chestoveride"))
		{
			return true;
		}
		else if (local.equals(match))
		{
			return true;
		}
		else
		{
			for (Map.Entry<String, UUID> entry : deathlocations.entrySet())
			{
				if (entry.getKey().equals(local))
				{
					return false;
				}
			}
			return true;
		}
		
	}
	
	public boolean checkBlock(Block block)
	{
		Location location = block.getLocation();
		String local = location.getWorld().getName() + "," + String.valueOf( location.getBlockX()) + "," + String.valueOf( location.getBlockY()) + "," + String.valueOf(location.getBlockZ());
		if(deathlocations.containsKey(local))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public void loadDeathChests()
    {
		deathlocationspost = pg.storage.loadData("deathChests.data");
	}
	
	public void offloadDeathChests()
	{
		pg.storage.saveData(deathlocations, "deathChests.data");	
	}
	
}
