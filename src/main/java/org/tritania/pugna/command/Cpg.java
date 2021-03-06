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

package org.tritania.pugna.command;

/*Start Imports*/
import org.bukkit.permissions.PermissibleBase;

import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import org.tritania.pugna.Pugna;
import org.tritania.pugna.util.Message;
/*End Imports*/

public class Cpg implements CommandExecutor
{
    public Pugna pg;

    public Cpg(Pugna pg)
    {
        this.pg = pg;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        Player player = (Player) sender;
        if (args[0].equals("reload"))
        {
            if (player.hasPermission("pugna.reload"))
            {
                pg.config.load();
                Message.info(sender, "Reloading the config");
            }
            else
            {
                Message.info(sender, "You don't have permisson for this!");
            }
        }
        else
        {
            Message.info(sender, command.getUsage());
            return true;
        }
        return true;
    }
}
