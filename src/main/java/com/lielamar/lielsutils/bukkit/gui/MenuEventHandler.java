package com.lielamar.lielsutils.bukkit.gui;

import com.lielamar.lielsutils.bukkit.gui.elements.Button;
import com.lielamar.lielsutils.bukkit.gui.elements.InputItem;
import org.bukkit.entity.Player;
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

                if(element instanceof Button){
                    menu.setData(event.getWhoClicked().getUniqueId(), ((Button<T>)element).click((Player) event.getWhoClicked(), event.getClick(),
                            menu.data.get(event.getWhoClicked().getUniqueId())));

                    if((event.getWhoClicked()).getOpenInventory().getTitle().equals(menu.getName()))
                        menu.update(menu.frame, (Player) event.getWhoClicked());
                } else if(element instanceof InputItem) {
                    menu.setData(event.getWhoClicked().getUniqueId(), ((InputItem<T>)element).click((Player) event.getWhoClicked(),event.getClick(),
                            menu.data.get(event.getWhoClicked().getUniqueId()), event.getCursor()));
                }
            }
        }
    }
}