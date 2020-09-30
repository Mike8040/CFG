package com.mike.mod.utils.handlers.guns;

import com.mike.mod.utils.sound.SoundRegistrator;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.StringTextComponent;

import java.util.HashMap;
import java.util.Map;

import static com.mike.mod.utils.handlers.DelayHandler.*;

public class GunReloadHandler {
	private static Map<PlayerEntity, ItemStack> currentlyReloading = new HashMap<>();

	public static void reload(PlayerEntity player, ItemStack weapon) {
		if (player.getHeldItemOffhand().getItem().toString().contains("ammo")) {
			player.sendMessage(new StringTextComponent("rl"));
			if (getStartedReload() == null) {
				player.sendMessage(new StringTextComponent("rl2"));
				String emptyName = weapon.getDisplayName().getString();
				Integer weaponLength = emptyName.length();
				String reloadedName = emptyName.substring(0, weaponLength - 5) + weapon.getDisplayName().getString().substring(weaponLength -2, weaponLength) + weapon.getDisplayName().getString().substring(weaponLength -3, weaponLength);
				weapon.setDisplayName(new StringTextComponent(reloadedName));

				currentlyReloading.put(player, weapon);
				player.sendMessage(new StringTextComponent("adding to map"));

				addToReloadTimer(player, getReloadDelay(player, weapon));
				playReloadSound(player, weapon);

				player.inventory.getCurrentItem().setCount(0);
				//Integer slot = player.inventory.getSlotFor(weapon);temporarily removed as server doesnt like
				//player.inventory.removeStackFromSlot(slot);
			}
			else if (getStartedReload().contains(player)) {
				player.sendMessage(new StringTextComponent("rl3"));
				return;
			} else {
				player.sendMessage(new StringTextComponent("rl4"));
				String emptyName = weapon.getDisplayName().getString();
				Integer weaponLength = emptyName.length();
				String reloadedName = emptyName.substring(0, weaponLength - 5) + weapon.getDisplayName().getString().substring(weaponLength -2, weaponLength) + weapon.getDisplayName().getString().substring(weaponLength -3, weaponLength);
				//String reloadedName = ("AK-47  30/30");
				weapon.setDisplayName(new StringTextComponent(reloadedName));

				currentlyReloading.put(player, weapon);
				player.sendMessage(new StringTextComponent("adding to map"));

				addToReloadTimer(player, getReloadDelay(player, weapon));
				playReloadSound(player, weapon);
				player.inventory.getCurrentItem().setCount(0);
				//Integer slot = player.inventory.getSlotFor(weapon);temporarily removed as server doesnt like
				//player.inventory.removeStackFromSlot(slot);
			}
		}
	}

	public static ItemStack checkWeaponReloading(PlayerEntity player){
		player.sendMessage(new StringTextComponent("1"));
		if(currentlyReloading.isEmpty()){
			player.sendMessage(new StringTextComponent("empty"));
		}
		for (Map.Entry<PlayerEntity, ItemStack> entry : currentlyReloading.entrySet()) {
			player.sendMessage(new StringTextComponent("2"));
			PlayerEntity k = entry.getKey();
			ItemStack weapon = entry.getValue();
			player.sendMessage(new StringTextComponent(Boolean.toString(k.isEntityEqual(player)) + "  " + Boolean.toString(checkReloadTimer(player))));
			if(k.isEntityEqual(player) && checkReloadTimer(player) == false){
				player.sendMessage(new StringTextComponent("3"));
				//checkReloadTimer(player);
				currentlyReloading.remove(k, weapon);
				setWeaponReloaded(player, weapon);
				return weapon;
			}
		}return null;
	}

	public static void playReloadSound(PlayerEntity player, ItemStack weapon){
		if(weapon.getItem().toString().contains("ak47")) {
			player.getEntityWorld().playSound(player, player.getPosition(), SoundRegistrator.akReloadSound, SoundCategory.PLAYERS, 1000, 1);
		}else if(weapon.getItem().toString().contains("g18")) {

		}else if(weapon.getItem().toString().contains("springfield")) {

		}else if(weapon.getItem().toString().contains("china_lake")) {

		}
	}

	public static void setWeaponReloaded(PlayerEntity player, ItemStack weapon){
		player.inventory.addItemStackToInventory(weapon);//should work but needs to send packet to client to do the same so client and server are on same page
		// player.inventory.addItemStackToInventory(weapon);
		player.sendMessage(new StringTextComponent("attempting to add weapon to inv"));
	}
}
