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
import java.util.Map;
import java.util.Properties;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
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

import org.tritania.pugna.Pugna;
import org.tritania.pugna.util.*;

public class Death implements Serializable 
{
	public HashMap<UUID, String> deathlocations = new HashMap<UUID, String>();
	
	public Pugna pg;

    public Death(Pugna pg)
    {
        this.pg = pg;
    }
    
    public void createDeathChest(Player player, List<ItemStack> drops) 
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
		deathlocations.put(playerId, local);
		
		CommandSender pc = (CommandSender) player;
		Message.info(pc, "You have 5 minutes to retrive your items, good luck!");
		
		final Block replace = chest;
		
		pg.getServer().getScheduler().scheduleSyncDelayedTask(pg, new Runnable() 
		{
			public void run() 
			{
			  replace.setType(Material.AIR);
			}
		}, 6000L);// 60 L == 3 sec, 20 ticks == 1 sec
	}
	
	//need to wipe all the chests after 5 mins
	
	public boolean checkPlayer(Location location, Player player)
	{
		String local = location.getWorld().getName() + "," + String.valueOf( location.getBlockX()) + "," + String.valueOf( location.getBlockY()) + "," + String.valueOf(location.getBlockZ());
		UUID playerId = player.getUniqueId();
		String match = deathlocations.get(playerId);
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
			for (Map.Entry<UUID, String> entry : deathlocations.entrySet())
			{
				if (entry.getValue().equals(local))
				{
					return false;
				}
			}
			return true;
		}
		
	}
	
	public void loadDeathChests()
    {
		try
		{
			File data            = new File(pg.datalocal + "/deathChests.data");
			FileInputStream fis  = new FileInputStream(data);
			ObjectInputStream ois= new ObjectInputStream(fis);

			deathlocations = (HashMap<UUID,String>)ois.readObject();

			ois.close();
			fis.close();
		}
		catch(Exception ex)
		{
			Log.severe(" " + ex.getMessage());
		}
	}
	
	public void offloadDeathChests()
	{
		try
		{
			File data =  new File(pg.datalocal + "/deathChests.data");
			FileOutputStream fos   = new FileOutputStream(data);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			
			oos.writeObject(deathlocations);
			oos.flush();
			oos.close();
			fos.close();
			
		}
		catch(Exception ex)
		{
			Log.severe("  " + ex.getMessage());
		}	
	}
	
}
