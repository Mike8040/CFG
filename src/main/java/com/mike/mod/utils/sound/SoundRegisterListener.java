package com.mike.mod.utils.sound;

import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class SoundRegisterListener {
	@SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
	public void registerSoundEvents(RegistryEvent.Register<SoundEvent> event) {
		event.getRegistry().registerAll(SoundRegistrator.akShotSound,SoundRegistrator.akReloadSound);
	}
}