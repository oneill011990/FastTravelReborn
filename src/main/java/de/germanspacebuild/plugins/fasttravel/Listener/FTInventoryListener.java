/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2011-2016 CraftyCreeper, minebot.net, oneill011990
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
import de.germanspacebuild.plugins.fasttravel.menu.SignMenu;
import de.germanspacebuild.plugins.fasttravel.util.FastTravelUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by oneill011990 on 14.04.2016.
 */
public class FTInventoryListener implements Listener {

    private FastTravel plugin;
    private SignMenu menu;

    public FTInventoryListener(FastTravel plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Inventory inv = event.getInventory();
        int slot = event.getRawSlot();
        boolean isTravelInv = false;

        List<SignMenu> menus = SignMenu.getMenus();
        List<Inventory> inventories = new ArrayList<Inventory>();

        isTravelInv = FastTravelUtil.isFastTravelMenu(event.getInventory());

        if (!isTravelInv) {
            return;
        }

        if (event.getCursor() == null) {

        }


        if (slot <= 44 && event.getCurrentItem().getType() == Material.BEACON) {
            menu.travel(event.getCurrentItem().getItemMeta().getDisplayName());
        } else if (slot == 45) {
            menu.goBack();
        } else if (slot == 53) {
            menu.goNext();
        }


    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventoryPickup(InventoryPickupItemEvent event) {
        List<SignMenu> menus = SignMenu.getMenus();
        List<Inventory> inventories = new ArrayList<Inventory>();
        boolean isTravelInv = false;

        isTravelInv = FastTravelUtil.isFastTravelMenu(event.getInventory());

        if (!isTravelInv) {
            event.setCancelled(true);
        }
    }


}
