package com.lielamar.lielsutils.bukkit.gui;

import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public final class MenuEventHandler<T extends MenuData> implements Listener {

    /**
     * When a menu is registered, the events that occur when a click is made are being registered through this event handler
     */

    private final Menu<T> menu;

    public MenuEventHandler(Menu<T> menu) {
        this.menu = menu;
    }

    @org.bukkit.event.EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if(event.getClickedInventory() == null)
            return;

        if(event.getView().getTitle().equals(menu.getName())
                && event.getClickedInventory().getSize() == menu.getSize()
                && event.getClickedInventory().getHolder() == event.getWhoClicked()) {

            int slot = event.getSlot();

            if(menu.getElements().containsKey(slot)){
                event.setCancelled(true);
                MenuElement<T> element = menu.getElements().get(slot);

                element.execute(menu, event);
            }
        }
    }
}