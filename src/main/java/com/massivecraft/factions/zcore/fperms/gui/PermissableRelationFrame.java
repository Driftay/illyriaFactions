package com.massivecraft.factions.zcore.fperms.gui;

import com.github.stefvanschie.inventoryframework.Gui;
import com.github.stefvanschie.inventoryframework.GuiItem;
import com.github.stefvanschie.inventoryframework.pane.PaginatedPane;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.SavageFactions;
import com.massivecraft.factions.struct.Relation;
import com.massivecraft.factions.struct.Role;
import com.massivecraft.factions.util.XMaterial;
import com.massivecraft.factions.zcore.fperms.Permissable;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class PermissableRelationFrame {

    private Gui gui;
    private ConfigurationSection section;

    public PermissableRelationFrame(Faction f) {
        section = SavageFactions.plugin.getConfig().getConfigurationSection("fperm-gui.relation");
        gui = new Gui(SavageFactions.plugin,
                section.getInt("rows", 3),
                SavageFactions.plugin.color(SavageFactions.plugin.getConfig().getString("fperm-gui.relation.name").replace("{faction}", f.getTag())));
    }

    public void buildGUI(FPlayer fplayer) {
        PaginatedPane pane = new PaginatedPane(0, 0, 9, gui.getRows());
        List<GuiItem> GUIItems = new ArrayList<>();
        ItemStack dumby = buildDummyItem();
        // Fill background of GUI with dumbyitem & replace GUI assets after
        for (int x = 0; x <= (gui.getRows() * 9) - 1; x++) GUIItems.add(new GuiItem(dumby, e -> e.setCancelled(true)));
        ConfigurationSection sec = SavageFactions.plugin.getConfig().getConfigurationSection("fperm-gui.relation");
        for (String key : sec.getConfigurationSection("slots").getKeys(false)) {
            if (sec.getInt("slots." + key) < 0) continue;
            GUIItems.set(sec.getInt("slots." + key), new GuiItem(buildAsset("fperm-gui.relation.materials." + key, key), e -> {
                e.setCancelled(true);
                // Closing and opening resets the cursor.
                // e.getWhoClicked().closeInventory();
                new PermissableActionFrame(fplayer.getFaction()).buildGUI(fplayer, getPermissable(key));
            }));
        }
        pane.populateWithGuiItems(GUIItems);
        gui.addPane(pane);
        gui.update();
        gui.show(fplayer.getPlayer());
    }

    private ItemStack buildAsset(String loc, String relation) {
        ItemStack item = XMaterial.matchXMaterial(SavageFactions.plugin.getConfig().getString(loc)).parseItem();
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(SavageFactions.plugin.color(SavageFactions.plugin.getConfig().getString("fperm-gui.relation.Placeholder-Item.Name").replace("{relation}", relation)));
            item.setItemMeta(meta);
        }
        return item;
    }

    private ItemStack buildDummyItem() {
        ConfigurationSection config = SavageFactions.plugin.getConfig().getConfigurationSection("fperm-gui.dummy-item");
        ItemStack item = XMaterial.matchXMaterial(config.getString("Type")).parseItem();
        ItemMeta meta = item.getItemMeta();
        // So u can set it to air.
        if (meta != null) {
            meta.setLore(SavageFactions.plugin.colorList(config.getStringList("Lore")));
            meta.setDisplayName(SavageFactions.plugin.color(config.getString("Name")));
            item.setItemMeta(meta);
        }
        return item;
    }

    private Permissable getPermissable(String name) {
        if (Role.fromString(name.toUpperCase()) != null) {
            return Role.fromString(name.toUpperCase());
        } else if (Relation.fromString(name.toUpperCase()) != null) {
            return Relation.fromString(name.toUpperCase());
        } else {
            return null;
        }
    }
}
