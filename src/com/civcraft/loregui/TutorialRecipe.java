package com.civcraft.loregui;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.civcraft.config.ConfigIngredient;
import com.civcraft.lorestorage.LoreCraftableMaterial;
import com.civcraft.lorestorage.LoreGuiItem;
import com.civcraft.lorestorage.LoreGuiItemListener;
import com.civcraft.util.ItemManager;

public class TutorialRecipe extends ShowRecipe {
	
	@Override
	public void performAction(InventoryClickEvent event, ItemStack stack) {
		Player player = (Player)event.getWhoClicked();
		
		LoreCraftableMaterial craftMat = LoreCraftableMaterial.getCraftMaterial(stack);
		if (craftMat == null || craftMat.getConfigMaterial().ingredients == null) {
			/* Do nothing for now. */
			return;
		}
		
		String title = craftMat.getName()+" "+ChatColor.RESET+"Crafting Recipe";
		if (title.length() > 32) {
			title = title.substring(0, 32);
		}
		
		Inventory recInv = Bukkit.getServer().createInventory(player, LoreGuiItem.MAX_INV_SIZE, title);
		if (craftMat.isShaped()) {		
			int offset = START_OFFSET;
			for (String line : craftMat.getConfigMaterial().shape) {
				for (int i = 0; i < line.toCharArray().length; i++) {
					ConfigIngredient ingred = null;
					for (ConfigIngredient in : craftMat.getConfigMaterial().ingredients.values()) {
						if (in.letter.equalsIgnoreCase(String.valueOf(line.toCharArray()[i]))) {
							ingred = in;
							break;
						}
					}
					
					if (ingred != null) {
						recInv.setItem(i+offset, getIngredItem(ingred, recInv));
					}
				}
				offset += LoreGuiItem.INV_ROW_COUNT;
			}
		} else {
			int x = 0;
			int offset = START_OFFSET;
			for (ConfigIngredient ingred : craftMat.getConfigMaterial().ingredients.values()) {
				if (ingred != null) {				
					for (int i = 0; i < ingred.count; i++) {						
						recInv.setItem(x+offset, getIngredItem(ingred, recInv));
						
						x++;
						if (x >= 3) {
							x = 0;
							offset += LoreGuiItem.INV_ROW_COUNT;
						}
					}
				}
			}
		}
		
		{
			ItemStack backButton = LoreGuiItem.build(ChatColor.RESET+"Back to Recipes", ItemManager.getId(Material.MAP), 0, ChatColor.RESET+"Back to Recipes");
			backButton = LoreGuiItem.setAction(backButton, "OpenInventory");
			backButton = LoreGuiItem.setActionData(backButton, "invType", "showGuiInv");
			backButton = LoreGuiItem.setActionData(backButton, "invType", "showTutorialInventory");
			recInv.setItem(LoreGuiItem.MAX_INV_SIZE-1, backButton);
		}
		
		LoreGuiItemListener.guiInventories.put(recInv.getName(), recInv);
		buildCraftTableBorder(recInv);
		buildInfoBar(craftMat, recInv, player);
		player.openInventory(recInv);
	}

}