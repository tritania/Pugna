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

import java.io.File;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import org.tritania.pugna.util.Log;
import org.tritania.pugna.util.Message;
import org.tritania.pugna.command.*;
import org.tritania.pugna.Configuration;
import org.tritania.pugna.PugnaListener;

public class Pugna extends JavaPlugin
{
	
	public Configuration config;
	public Combat com;
	public Bounties bt;
	public Death dt;
	public String datalocal;
	
	public void onLoad()
	{
		config = new Configuration(new File(getDataFolder(), "config.yml"));
	}
	
	public void onEnable()
	{
		PluginManager pm;
		Plugin p;
		
		Log.init(getLogger());
		Message.init(getDescription().getName());
		
		pm = getServer().getPluginManager();
		
		datalocal = getDataFolder().getAbsolutePath();
		
		config.load();
		dt.loadDeathChests();
		
		pm.registerEvents(new PugnaListener(this), this);
		
		com = new Combat(this);
		bt = new Bounties(this);
		dt = new Death(this);
		
		getCommand("pg").setExecutor(new Cpg(this));
	}
	
	public void onDisable()
	{
		dt.offloadDeathChests();
	}
}
