package com.mike.mod.network.packets;

import com.mike.mod.main;
import com.mike.mod.utils.handlers.VectorHandler;
import com.mike.mod.utils.handlers.guns.GunFireHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class VectorPacket {
	private final Double vx;
	private final Double vy;
	private final Double vz;

	public VectorPacket(Double d1, Double d2, Double d3) {
		this.vx = d1;
		this.vy = d2;
		this.vz = d3;
	}
	public static void encode(VectorPacket msg, PacketBuffer buf) {
		buf.writeDouble(msg.vx);
		buf.writeDouble(msg.vy);
		buf.writeDouble(msg.vz);
	}
	public static VectorPacket decode(PacketBuffer buf) {
		return new VectorPacket(buf.readDouble(), buf.readDouble(), buf.readDouble());
	}
	public static void handle(VectorPacket msg, Supplier<NetworkEvent.Context> context) {
		context.get().enqueueWork(() -> {// Work that needs to be threadsafe (most work)
			
			
			
			PlayerEntity sender = context.get().getSender(); // the client who sent this packet
			sender.sendMessage(new StringTextComponent("received vector"));
			sender.sendMessage(new StringTextComponent((msg.vx.toString() + msg.vy.toString() + msg.vz.toString())));
		});
		main.LOGGER.debug("Packet handled");
		context.get().setPacketHandled(true);
	}
}
