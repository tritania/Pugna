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
	
    public  String SQLurl;
	public  String SQLpass;
	public  String SQLuser;
    
	public Configuration(File file)
	{
		this.file = file;
		
		SQLuser = "chrysus";
		SQLpass = "1234";
		SQLurl  = "jdbc:mysql://localhost:3306/chrysus";
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
		
		SQLuser = getString("user", SQLuser);
		SQLurl  = getString("url", SQLurl);
		SQLpass = getString("pass", SQLpass);
		
        
		if (!file.exists())
            save();
        
	}
	
	public void save() 
	{
		set("user", SQLuser);
		set("url", SQLurl);
		set("pass", SQLpass);
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

