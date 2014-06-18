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

package org.tritania.pugna.wrappers;

import java.util.UUID;
import java.io.Serializable;
import java.util.ArrayList;

import org.bukkit.inventory.ItemStack;

public class DeathChest implements Serializable
{
    private UUID owner; //player that owns the chest
    private ArrayList<UUID> others; //players that have acess

    public DeathChest(UUID owner)
    {
        this.owner = owner;
        this.others = new ArrayList<UUID>();
    }

    public void addOther(UUID other)
    {
        others.add(other);
    }

    public boolean checkOwner(UUID maybe)
    {
        return owner.equals(maybe);
    }

    public boolean checkForAcess(UUID other)
    {
        if (others == null)
            return false;
        for (UUID entry : others)
        {
            if (entry.equals(other))
            {
                return true;
            }
        }
        return false;
    }
}
