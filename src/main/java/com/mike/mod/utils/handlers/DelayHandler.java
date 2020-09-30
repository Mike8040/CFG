package com.mike.mod.utils.handlers;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import com.mike.mod.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber(modid = main.MOD_ID,bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.DEDICATED_SERVER)
public class DelayHandler{
	private static Map<PlayerEntity, Integer> delayCurrentlyReloading = new HashMap<>();
	private static ArrayList<PlayerEntity> startedReload = new ArrayList<>();

	private static Integer AKDelay = 80;
	private static Integer G18Delay = 20;
	private static Integer SpringfieldDelay = 100;
	private static Integer ChinaLakeDelay = 200;

	public static volatile Integer currentTick = 10;
	public static volatile Integer previousShotTick = 0;


	@SubscribeEvent
	public static void onServerTick(TickEvent.ServerTickEvent event){
			currentTick = currentTick + 1;
	}

	public static void addToReloadTimer(PlayerEntity player, Integer delay) {
		if(!startedReload.contains(player) && !delayCurrentlyReloading.containsKey(player)) {//if not already in map and array to prevent duplicates
			startedReload.add(player);
			delayCurrentlyReloading.put(player, delay);
		}
	}

	public static Boolean checkReloadTimer(PlayerEntity player){
		player.sendMessage(new StringTextComponent("firing checkreloadtimer"));
		if(startedReload.contains(player)) {
			player.sendMessage(new StringTextComponent("delay1"));
			for (Map.Entry<PlayerEntity, Integer> entry : delayCurrentlyReloading.entrySet()) {
				player.sendMessage(new StringTextComponent("delay2"));
				PlayerEntity k = entry.getKey();
				Integer v = entry.getValue();
				player.sendMessage(new StringTextComponent(v.toString() + " - " + currentTick.toString()));
				if (k.isEntityEqual(player) && (v - currentTick) <= 0) {
					startedReload.remove(player);
					delayCurrentlyReloading.remove(k, v);
					player.sendMessage(new StringTextComponent("delay3"));
					return false;
				}
			}return true;
		}return false;

	}public static Integer getReloadDelay(PlayerEntity player, ItemStack weapon){
		if(weapon.getItem().toString().contains("ak47")) {
			player.sendMessage(new StringTextComponent("Get reload delay firing"));
			return AKDelay + currentTick;
		}else if(weapon.getItem().toString().contains("g18")) {
			return G18Delay + currentTick;
		}else if(weapon.getItem().toString().contains("springfield")) {
			return SpringfieldDelay + currentTick;
		}else if(weapon.getItem().toString().contains("china_lake")) {
			return ChinaLakeDelay + currentTick;
		}else {
			return null;
		}
	}

	public static ArrayList<PlayerEntity> getStartedReload() {
		return startedReload;
	}
}
