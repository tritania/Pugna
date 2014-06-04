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

public class Death 
{
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
	}
	
	public void destroyDeathChest(Player player)
	{
		
	}
	
	public void checkPlayer(Player player)
	{
		
	}
	
}
