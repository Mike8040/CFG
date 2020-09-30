package com.mike.mod.network.packets;

import com.mike.mod.main;
import com.mike.mod.utils.handlers.SoundHandler;
import com.mike.mod.utils.handlers.guns.GunFireHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

public class SoundPacket {
	private final UUID player;
	private final String sound;
	private final String category;
	private final Integer volume;
	private final Integer pitch;

	//player.getEntityWorld().playSound(player, player.getPosition(), SoundRegistrator.akShotSound, SoundCategory.PLAYERS, 1000, 1);

	public SoundPacket(UUID pla, String sou, String cat, Integer vol, Integer pit) {
		this.player = pla;
		this.sound = sou;
		this.category = cat;
		this.volume = vol;
		this.pitch = pit;
	}
	public static void encode(SoundPacket msg, PacketBuffer buf) {
		buf.writeUniqueId(msg.player);
		buf.writeString(msg.sound);
		buf.writeInt(msg.volume);
		buf.writeInt(msg.pitch);
	}
	public static SoundPacket decode(PacketBuffer buf) {
		return new SoundPacket(buf.readUniqueId(), buf.readString(), buf.readString(), buf.readInt(), buf.readInt());
	}
	public static void handle(SoundPacket msg, Supplier<NetworkEvent.Context> context) {
		context.get().enqueueWork(() -> {// Work that needs to be threadsafe (most work)

			//SoundHandler.playSound(msg.player, msg.sound, msg.category, msg.volume, msg.pitch);

			PlayerEntity sender = context.get().getSender(); // the client who sent this packet
			sender.sendMessage(new StringTextComponent("received sound packet"));
		});
		main.LOGGER.debug("Packet handled");
		context.get().setPacketHandled(true);
	}
}
