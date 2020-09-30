package com.mike.mod.network;

import com.mike.mod.main;
import com.mike.mod.network.packets.StringPacket;
import com.mike.mod.network.packets.VectorPacket;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class NetworkHandler {
	private static final String PROTOCOL_VERSION = "1";
	public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
			new ResourceLocation(main.MOD_ID, "main"),
			() -> PROTOCOL_VERSION,
			PROTOCOL_VERSION::equals,
			PROTOCOL_VERSION::equals);
	public static void init(){
		int id = 0;
		INSTANCE.registerMessage(id++, StringPacket.class, StringPacket::encode, StringPacket::decode, StringPacket::handle);
		INSTANCE.registerMessage(id++, VectorPacket.class, VectorPacket::encode, VectorPacket::decode, VectorPacket::handle);
	}
}