package com.mike.mod.network.packets;

import com.mike.mod.main;
import com.mike.mod.utils.handlers.VectorHandler;
import com.mike.mod.utils.handlers.guns.GunFireHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class StringPacket {
	private final String itemName;

	public StringPacket(String str) {
		this.itemName = str;
	}
	public static void encode(StringPacket msg, PacketBuffer buf) {
		buf.writeString(msg.itemName);
	}
	public static StringPacket decode(PacketBuffer buf) {
		return new StringPacket(buf.readString(100));
	}
	public static void handle(StringPacket msg, Supplier<NetworkEvent.Context> context) {
		context.get().enqueueWork(() -> {// Work that needs to be threadsafe (most work)
			PlayerEntity sender = context.get().getSender(); // the client who sent this packet
			sender.sendMessage(new StringTextComponent("received"));
			VectorHandler.addToMap(sender);
			GunFireHandler.handleGunFirePacket(msg.itemName, context);
		});
		main.LOGGER.debug("Packet handled");
		context.get().setPacketHandled(true);
	}
}
