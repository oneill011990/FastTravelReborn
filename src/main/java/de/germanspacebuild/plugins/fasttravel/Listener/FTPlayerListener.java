/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2011-2015 CraftyCreeper, minebot.net, oneill011990
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package de.germanspacebuild.plugins.fasttravel.Listener;

import de.germanspacebuild.plugins.fasttravel.FastTravel;
import de.germanspacebuild.plugins.fasttravel.data.FastTravelDB;
import de.germanspacebuild.plugins.fasttravel.events.FastTravelFoundEvent;
import de.germanspacebuild.plugins.fasttravel.util.BlockUtil;
import de.germanspacebuild.plugins.fasttravel.util.FastTravelUtil;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Arrays;

/**
 * Created by oneill011990 on 04.03.2015.
 */
public class FTPlayerListener implements Listener {

    private FastTravel plugin;

    public FTPlayerListener(FastTravel plugin){
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onPlayerInteractEvent(PlayerInteractEvent event) {
        if (!Arrays.asList(BlockUtil.signBlocks).contains(event.getClickedBlock().getType())){
            return;
        } else if (!event.getPlayer().hasPermission(FastTravel.PERMS_BASE + "use")) {
            plugin.getIOManger().sendTranslation(event.getPlayer(), "Perms.Not");
            return;
        } else if (event.getAction() != Action.RIGHT_CLICK_AIR || event.getAction() != Action.RIGHT_CLICK_BLOCK){
            return;
        } else if (!FastTravelUtil.isFTSign(event.getClickedBlock())){
            return;
        }

        Sign sign = (Sign) event.getClickedBlock().getState();

        if (FastTravelDB.getSignsFor(event.getPlayer().getUniqueId()).contains(FastTravelDB.getSign(sign.getLine(1)))){
            plugin.getIOManger().sendTranslation(event.getPlayer(), "Sign.FoundAlready".replaceAll("%sign",
                    sign.getLine(1)));
            return;
        } else {
            plugin.getServer().getPluginManager().callEvent(new FastTravelFoundEvent(event.getPlayer(),
                    FastTravelDB.getSign(sign.getLine(1))));
        }

    }

}