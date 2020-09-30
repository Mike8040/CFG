package com.mike.mod.events;

import com.mike.mod.network.packets.StringPacket;
import com.mike.mod.network.packets.VectorPacket;
import com.mike.mod.utils.handlers.DelayHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;

import static com.mike.mod.main.MOD_ID;
import static com.mike.mod.network.NetworkHandler.INSTANCE;

@Mod.EventBusSubscriber(modid = MOD_ID,bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class onInteractEvent {
	private static ArrayList<String> allowedItemNames = new ArrayList<>();

	public static void init(){
		allowedItemNames.add("air");
		allowedItemNames.add("ak47");
		allowedItemNames.add("g18");
		allowedItemNames.add("springfield");
		allowedItemNames.add("china_lake");
	}

	@SubscribeEvent
	public static void playerInteractEvent(PlayerInteractEvent e){
		if (!e.getHand().equals(Hand.OFF_HAND)){
			PlayerEntity player = e.getPlayer();
			ItemStack heldItem = player.getHeldItemMainhand();
			String itemID = heldItem.getItem().toString();
			if(allowedItemNames.contains(itemID)) {
				Double x = player.getLookVec().getX();
				Double y = player.getLookVec().getY();
				Double z = player.getLookVec().getZ();
				player.sendMessage(new StringTextComponent("sending"));
				player.sendMessage(new StringTextComponent(DelayHandler.currentTick.toString()));
				INSTANCE.sendToServer(new StringPacket(heldItem.getDisplayName().getString()));
				//INSTANCE.sendToServer(new VectorPacket(x,y,z)); //no longer needed
			}
		}
	}
}
