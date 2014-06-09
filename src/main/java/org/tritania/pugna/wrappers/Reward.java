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
import org.bukkit.inventory.ItemStack;

public class Reward implements Serializable
{
	private UUID contractor;
	private ItemStack reward;
	
	public Reward(UUID contractor, ItemStack reward)
	{
		contractor = contractor;
		reward = reward;
	}
	
	public ItemStack getReward()
	{
		return reward;
	}
	
	public UUID getContractor()
	{
		return contractor;
	}
}
