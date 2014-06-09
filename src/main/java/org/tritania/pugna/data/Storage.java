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

package org.tritania.pugna.storage;

import java.util.HashMap;
import java.util.Iterator;
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
import java.util.ArrayList;

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

public class Storage implements Serializable
{
	public Pugna pg;

    public Storage(Pugna pg)
    {
		new File(pg.datalocal + "/playerdata").mkdirs();
        this.pg = pg;
    }
    
    public void saveData(HashMap toBeSaved, String fileName)
    {
		try
		{
			File data =  new File(pg.datalocal + "/" + fileName);
			FileOutputStream fos   = new FileOutputStream(data);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			
			oos.writeObject(toBeSaved);
			oos.flush();
			oos.close();
			fos.close();
			
		}
		catch(Exception ex)
		{
			Log.severe("  " + ex.getMessage());
		}	
	}
	
	public HashMap loadData(String fileName)
	{
		HashMap dataLoaded = new HashMap();
		try
		{
			File data            = new File(pg.datalocal + "/" + fileName);
			FileInputStream fis  = new FileInputStream(data);
			ObjectInputStream ois= new ObjectInputStream(fis);

			dataLoaded = (HashMap)ois.readObject();

			ois.close();
			fis.close();
		}
		catch(Exception ex)
		{
			Log.severe(" " + ex.getMessage());
		}
		return dataLoaded;
	}
	
	public void savePlayer(Player player)
	{
		PugnaPlayer play = pg.track.getPlayerData(player);
		String playerId = player.getUniqueId().toString();
		try
		{
			File data =  new File(pg.datalocal + "/playerdata/" + playerId);
			FileOutputStream fos   = new FileOutputStream(data);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			
			oos.writeObject(play);
			oos.flush();
			oos.close();
			fos.close();
		}
		catch(Exception ex)
		{
			Log.severe("  " + ex.getMessage());
		}
	}
	
	public boolean check(Player player)
	{
		File data = new File(pg.datalocal + "/playerdata/" + player.getUniqueId().toString());
		return data.exists();
	}
	
	public PugnaPlayer loadPlayer(Player player)
	{
		PugnaPlayer play = new PugnaPlayer();
		try
		{
			File data            = new File(pg.datalocal + "/playerdata/" + player.getUniqueId().toString());
			FileInputStream fis  = new FileInputStream(data);
			ObjectInputStream ois= new ObjectInputStream(fis);

			play = (PugnaPlayer)ois.readObject();

			ois.close();
			fis.close();
		}
		catch(Exception ex)
		{
			Log.severe(" " + ex.getMessage());
		}
		return play;
	}
}
