package com.mike.mod.utils.sound;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

public class SoundRegistrator {
	public static final SoundEvent akShotSound;
	public static final SoundEvent akReloadSound;

	static {
		akShotSound = addSoundsToRegistry("akshot");
		akReloadSound = addSoundsToRegistry("akreload");
	}

	private static SoundEvent addSoundsToRegistry(String soundId) {
		ResourceLocation shotSoundLocation = new ResourceLocation("cfg", soundId);
		SoundEvent soundEvent = new SoundEvent(shotSoundLocation);
		soundEvent.setRegistryName(shotSoundLocation);
		return soundEvent;
	}
}