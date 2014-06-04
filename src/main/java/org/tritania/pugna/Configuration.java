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
import java.io.File;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import org.tritania.pugna.util.Log;
import org.tritania.pugna.Pugna;
/*End Imports*/

public class Configuration extends YamlConfiguration
{
	private File file;
	
	public  boolean global;
    public  String mapName;
	public  boolean deathChest;
	public  boolean allowBounty;
	public boolean diffmob;
    
	public Configuration(File file)
	{
		this.file = file;
		
		global = false;
		mapName = "World";
		deathChest = true;
		allowBounty = true;
		diffmob = true;
	}
	 
	public void load()
	{
		try 
		{
            super.load(file);
		} 
        catch (Exception e) 
		{
            Log.warning("Unable to load: %s", file.toString());
		}
		
		global = getBoolean("Use this plugin globally?", global);
		mapName  = getString("Use this plugin only on this map", mapName);
		deathChest = getBoolean("Allow death chests", deathChest);
		allowBounty = getBoolean("Allow bounties", allowBounty);
		diffmob = getBoolean("Creates more difficult mobs", diffmob);
		
        
		if (!file.exists())
            save();
        
	}
	
	public void save() 
	{
		set("Use this plugin globally?", global);
		set("Use this plugin only on this map", mapName);
		set("Allow death chests", deathChest);
		set("Allow bounties", allowBounty);
		set("Creates more difficult mobs", diffmob);
		try 
		{
			super.save(file);
        } 
        catch (Exception e) 
        {
			Log.warning("Unable to save: %s", file.toString());
        }
	}
}

