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
    public int deathChestTime;
    public String emperorTitle;
    public int combatTimer;
    public boolean flame;

    public Configuration(File file)
    {
        this.file = file;

        global = false;
        mapName = "World";
        deathChest = true;
        allowBounty = true;
        diffmob = true;
        deathChestTime = 6000;
        emperorTitle = "[EMPEROR]";
        combatTimer = 3600; // 3 minutes
        flame = true;
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
        emperorTitle  = getString("Use this title for the emperor chat system", emperorTitle);
        deathChest = getBoolean("Allow death chests", deathChest);
        allowBounty = getBoolean("Allow bounties", allowBounty);
        diffmob = getBoolean("Creates more difficult mobs", diffmob);
        deathChestTime = getInt("Amount of time to keep death chests around (20 = 1 second)", deathChestTime);
        combatTimer = getInt("Time between combat and teleport commands", combatTimer);
        flame = getBoolean("Allows flame arrows to light terrain on fire", flame);

        if (!file.exists())
            save();

    }

    public void save()
    {
        set("Use this plugin globally?", global);
        set("Use this plugin only on this map", mapName);
        set("Use this title for the emperor chat system", emperorTitle);
        set("Allow death chests", deathChest);
        set("Allow bounties", allowBounty);
        set("Creates more difficult mobs", diffmob);
        set("Amount of time to keep death chests around (20 = 1 second", deathChestTime);
        set("Time between combat and teleport commands", combatTimer);
        set("Allows flame arrows to light terrain on fire", flame);
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

