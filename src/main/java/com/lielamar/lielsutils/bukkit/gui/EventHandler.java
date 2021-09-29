package com.lielamar.lielsutils.bukkit.gui;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class EventHandler<T extends GuiData> implements Listener {

    private final Menu<T> menu;

    public EventHandler(Menu<T> menu) {
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
                UIElement<T> element = menu.getElements().get(slot);

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