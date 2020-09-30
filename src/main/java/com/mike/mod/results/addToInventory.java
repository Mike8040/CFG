package com.mike.mod.results;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class addToInventory {
	public static void addToInv(PlayerEntity player){
		ItemStack items = new ItemStack(Item.getItemById(354), 1);
		player.inventory.addItemStackToInventory(items);
	}
}
