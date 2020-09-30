package com.mike.mod.network;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;

import static com.mike.mod.main.MOD_ID;

@Mod.EventBusSubscriber(modid = MOD_ID,bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.DEDICATED_SERVER)
public class PlayerListHandler {
	private static ArrayList<PlayerEntity> connectedPlayers = new ArrayList<>();

	@SubscribeEvent
	public void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
		connectedPlayers.add(event.getPlayer());
	}

	@SubscribeEvent
	public void onPlayerLeave(PlayerEvent.PlayerLoggedOutEvent event) {
		connectedPlayers.remove(event.getPlayer());
	}

	public static ArrayList<PlayerEntity> getConnectedPlayers() {
		return connectedPlayers;
	}
}