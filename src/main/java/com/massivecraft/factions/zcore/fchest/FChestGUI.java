package com.massivecraft.factions.zcore.fchest;

import com.github.stefvanschie.inventoryframework.Gui;
import com.github.stefvanschie.inventoryframework.GuiItem;
import com.github.stefvanschie.inventoryframework.pane.PaginatedPane;
import com.massivecraft.factions.SavageFactions;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class FChestGUI {

   private String title;
   private int rows;
   private boolean viewing;

   public FChestGUI(int rows, String title, boolean viewing) {
      this.rows = rows;
      this.title = title;
      this.viewing = viewing;
   }


   public void build(ItemStack[] items, HumanEntity humanEntity) {
      Gui gui = new Gui(SavageFactions.plugin, rows, title);
      // Paginated because I want some way to be able to scroll between them later.
      PaginatedPane pane = new PaginatedPane(0, 0, 9, rows);
      List<GuiItem> guiItems = new ArrayList<>();
      for (ItemStack itemStack : items) {
         if (viewing)
            guiItems.add(new GuiItem(itemStack, event -> event.setCancelled(true)));
         else
            guiItems.add(new GuiItem(itemStack, event -> {

            }))
      }
      pane.populateWithGuiItems(guiItems);
      gui.addPane(pane);
      gui.update();
      gui.show(humanEntity);

   }


}
