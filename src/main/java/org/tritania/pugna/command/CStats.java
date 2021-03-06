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
import org.tritania.pugna.wrappers.*;
/*End Imports*/

public class CStats implements CommandExecutor
{
    public Pugna pg;

    public CStats(Pugna pg)
    {
        this.pg = pg;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        Player player = (Player) sender;
        if (args.length < 1)
        {
            Message.info(sender, command.getUsage());
            return true;
        }
        if (args[0].equals("remove"))
        {
            PugnaPlayer play = pg.track.getPlayerData(player);
            pg.stats.removeBoard(player);
            play.setBoard(false);
            return true;
        }
        else if (args[0].equals("keep"))
        {
            PugnaPlayer play = pg.track.getPlayerData(player);
            play.setBoard(true);
            return true;
        }
        else
        {
            Message.info(sender, command.getUsage());
        }
        return true;
    }
}
