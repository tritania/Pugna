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
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Player;

import org.tritania.pugna.Pugna;
import org.tritania.pugna.Inventory;
import org.tritania.pugna.util.*;
import org.tritania.pugna.wrappers.Reward;

public class Bounties
{
    public Pugna pg;
    public HashMap<UUID, Reward> bountyorder = new HashMap<UUID, Reward>();

    public Bounties(Pugna pg)
    {
        this.pg = pg;
    }

    public void createBounty(Player target, Player placer, ItemStack bounty)
    {
        UUID targeted = target.getUniqueId(); //need to make sure more then one can't be added + remove items from players inventory
        UUID contractor = placer.getUniqueId();
        Reward bountyreward = new Reward(contractor, bounty);
        bountyorder.put(targeted, bountyreward);
        pg.stats.setBountyBoard(target, placer, bountyreward);
    }

    public boolean removeBounty(Player target, Player placer) //remove (target was not killed)
    {

        UUID targeted = target.getUniqueId();
        UUID contractor = placer.getUniqueId();

        Iterator<Map.Entry<UUID, Reward>> iterator = bountyorder.entrySet().iterator();
        while(iterator.hasNext())
        {
            Map.Entry<UUID, Reward> entry = iterator.next();
            UUID targetID = entry.getKey();
            Reward reward = entry.getValue();
            UUID contractor2 = reward.getContractor();
            if (targetID == targeted && contractor2 == contractor)
            {
                Inventory.placeItems(placer, reward.getReward());
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    public boolean checkOutstanding(Player target, Player placer) //not allowing more then one bounty from the same player against the same player
    {
        UUID targeted = target.getUniqueId();
        UUID contractor = placer.getUniqueId();

        Iterator<Map.Entry<UUID, Reward>> iterator = bountyorder.entrySet().iterator();
        while(iterator.hasNext())
        {
            Map.Entry<UUID, Reward> entry = iterator.next();
            UUID targetID = entry.getKey();
            Reward reward = entry.getValue();
            UUID contractor2 = reward.getContractor();
            if (targetID == targeted && contractor2 == contractor)
            {
                return true;
            }
        }
        return false;
    }

    public boolean checkOutstanding(Player player) //used for player login events.
    {
        UUID id = player.getUniqueId();
        if (bountyorder.containsKey(id))
        {
            return true;
        }
        return false;
    }

    public void loadBounties()
    {
        bountyorder = pg.storage.loadData("bounty.data");
    }

    public void saveBounties()
    {
        pg.storage.saveData(bountyorder, "bounty.data");
    }

    public void cashOut(Player killer, Player killed)
    {
        UUID KilledId = killed.getUniqueId();
        Reward reward = bountyorder.get(KilledId);
        Inventory.placeItems(killer, reward.getReward()); // need to check for space in players inventory
    }
}
