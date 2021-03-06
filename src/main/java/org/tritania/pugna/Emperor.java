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

import org.tritania.pugna.Pugna;
import org.tritania.pugna.wrappers.*;

public class Emperor
{
    public Pugna pg;
    private PugnaPlayer emperor;

    public Emperor(Pugna pg)
    {
        this.pg = pg;
        this.emperor = pg.storage.loadEmperor();
    }

    public void addPlayerScore(PugnaPlayer player, int amount)
    {
        player.getScore().changeScore(amount);
        checkEmperor(player);
    }

    public void removePlayerScore(PugnaPlayer player, int amount)
    {
        player.getScore().changeScore(amount);
        checkEmperor(player);
    }

    public void checkEmperor(PugnaPlayer player)
    {
        if (player.getScore().getTotalScore() > emperor.getScore().getTotalScore())
        {
            emperor = player;
        }
    }

    public void setEmperor(PugnaPlayer player)
    {
        emperor = player;
    }

    public String getEmperorName()
    {
        return emperor.getName();
    }

    public void offLoadEmperor()
    {
        pg.storage.saveEmperor(emperor);
    }
}
