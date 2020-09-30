package com.mike.mod.utils.handlers.guns;

import com.mike.mod.network.packets.SoundPacket;
import com.mike.mod.network.packets.StringPacket;
import com.mike.mod.utils.handlers.ClosestEntityHandler;
import com.mike.mod.utils.handlers.DelayHandler;
import com.mike.mod.utils.handlers.VectorHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ShulkerBulletEntity;
import net.minecraft.entity.projectile.SnowballEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.List;
import java.util.function.Supplier;

import static com.mike.mod.utils.handlers.DelayHandler.currentTick;
import static com.mike.mod.utils.handlers.DelayHandler.previousShotTick;
import static com.mike.mod.utils.handlers.guns.GunReloadHandler.checkWeaponReloading;

import com.mike.mod.main;

public class GunFireHandler {
	volatile static String weaponString;
	volatile static Integer ammo;
	volatile static Integer c;
	volatile static Integer p;

	public static void handleGunFirePacket(String msg, Supplier<NetworkEvent.Context> context) {
		PlayerEntity player = context.get().getSender();
		player.sendMessage(new StringTextComponent("handlegunfirepacket fired"));
		c = currentTick;//delay.getTicks()[0];
		p = previousShotTick;//delay.getTicks()[1];
		World worldIn = player.world;
		Vec3d look = VectorHandler.getVec3d(player);
		ItemStack weapon = player.getHeldItemMainhand();
		String weaponID = weapon.getItem().toString();
		weaponString = msg;
		Integer weaponLength = weaponString.length();
		ammo = 0;
		Float distance;

		player.sendMessage(new StringTextComponent("handlegunfirepacket firing"));
		player.sendMessage(new StringTextComponent(weaponID));
		player.sendMessage(new StringTextComponent(weaponString));

		//check if holding air
		if(weaponID.contains("air")){
			player.sendMessage(new StringTextComponent(weaponString));
			checkWeaponReloading(player);
/*			ItemStack newWeapon = checkWeaponReloading(player);*/
/*			if(newWeapon != null){
				player.sendMessage(new StringTextComponent(newWeapon.getDisplayName().getString()+ "cocking nora "));
				weaponID = weapon.getItem().toString();
				weaponString = weapon.getDisplayName().getString();
				weaponLength = weaponString.length();
				ammo = 0;
				player.sendMessage(new StringTextComponent(weaponString));
			}*/
			return;
		}


		List<Entity> entityList = player.world.getEntitiesWithinAABBExcludingEntity(player, player.getBoundingBox().grow(500));

		player.sendMessage(new StringTextComponent(String.valueOf(weaponString.charAt(weaponLength - 5)) + weaponString.charAt(weaponLength - 4)));

		try{
			ammo = Integer.parseInt(String.valueOf(weaponString.charAt(weaponLength - 5)) + String.valueOf(weaponString.charAt(weaponLength - 4)));
		}catch(Exception ae) {
			return;
		}
		if (weaponID.contains("ak47")) {
			//player.sendMessage(new StringTextComponent("z1- " + currentTick + " - " + previousShotTick));
			if (currentTick - previousShotTick >= 2) {
				player.sendMessage(new StringTextComponent("z2"));
				if (ammo == 0) {//call reload
					player.sendMessage(new StringTextComponent("z2:calling reload"));
					player.sendMessage(new StringTextComponent(weaponString + "p     " + ammo));
					GunReloadHandler.reload(player, weapon);
					return;
				}

				//player.sendMessage(new StringTextComponent(ammo.toString()));
				else if (ammo > 00) {//fire if enough ammo
					player.sendMessage(new StringTextComponent("z3"));
					distance = (Float) ClosestEntityHandler.getClosestEntity(entityList, player, true);
					fireAK(worldIn, look, player, distance);
					main.LOGGER.debug("Gun Firing");
					ammo = ammo - 1;
					if (ammo < 10) {
						weapon.setDisplayName(new StringTextComponent("AK-47  0" + Integer.toString(ammo) + "/30"));
					} else {
						weapon.setDisplayName(new StringTextComponent("AK-47  " + Integer.toString(ammo) + "/30"));
					}
					previousShotTick = currentTick;
				}
			}

		} else if (weaponID.contains("g18")) {
			if (c - p >= 1) {
				if (ammo == 00) {//call reload
					GunReloadHandler.reload(player, weapon);
				}
				if (ammo > 00) {
					distance = (Float) ClosestEntityHandler.getClosestEntity(entityList, player, true);
					fireG18(worldIn, look, player, distance);
					main.LOGGER.debug("Gun Firing");
					ammo = ammo - 1;
					if (ammo < 10) {
						weapon.setDisplayName(new StringTextComponent("Glock 18  0" + Integer.toString(ammo) + "/17"));
					} else {
						weapon.setDisplayName(new StringTextComponent("Glock 18  " + Integer.toString(ammo) + "/17"));
					}
					previousShotTick = currentTick;
				}
			}

		} else if (weaponID.contains("springfield")) {
			if (ammo == 00) {//call reload
				GunReloadHandler.reload(player, weapon);
			} else if (ammo > 00) {
				distance = (Float) ClosestEntityHandler.getClosestEntity(entityList, player, true);
				fireSpringfield(worldIn, look, player, distance);
				main.LOGGER.debug("Gun Firing");
				ammo = ammo - 1;
				weapon.setDisplayName(new StringTextComponent("Springfield  0" + Integer.toString(ammo) + "/01"));
				previousShotTick = currentTick;
			}

		} else if (weaponID.contains("china_lake")) {
			if (ammo == 00) {//call reload
				GunReloadHandler.reload(player, weapon);
			} else if (ammo > 00) {
				fireExplosive(worldIn, look, player);
				main.LOGGER.debug("Gun Firing");
				ammo = ammo - 1;
				weapon.setDisplayName(new StringTextComponent("China Lake  0" + Integer.toString(ammo) + "/02"));
				previousShotTick = currentTick;
			}
		}
	}
	public static void fireAK(World worldIn, Vec3d look, PlayerEntity player, Float distance){
		player.sendMessage(new StringTextComponent("z4"));
		Float multiplier = 3f;
		if(distance > 6){
			multiplier = 5f;
		}else if(distance > 15){
			multiplier = 8f;
		}
		SnowballEntity bullet = new SnowballEntity(worldIn, player.getPosX(), player.getPosY()+1.5d, player.getPosZ());
		//bullet.setVelocity(look.x*multiplier+(Math.random()-0.5)/2, look.y*multiplier+(Math.random()-0.5)/2, look.z*multiplier+(Math.random()-0.5)/2);
		bullet.setCustomName(new StringTextComponent("AKbullet"));
		bullet.addVelocity(look.x*multiplier+(Math.random()-0.5)/2, look.y*multiplier+(Math.random()-0.5)/2, look.z*multiplier+(Math.random()-0.5)/2);
		worldIn.addEntity(bullet);
		//INSTANCE.send(PacketDistributor.ALL.noArg(), new SoundPacket(player.getUniqueID(), "akShotSound", "PLAYERS", 1000, 1));
		//player.getEntityWorld().playSound(player, player.getPosition(), SoundRegistrator.akShotSound, SoundCategory.PLAYERS, 1000, 1);//need to register packet first


	}public static void fireG18(World worldIn, Vec3d look, PlayerEntity player, Float distance){
		Float multiplier = 3f;
		if(distance > 6){
			multiplier = 5f;
		}
		SnowballEntity bullet = new SnowballEntity(worldIn, player.getPosX(), player.getPosY()+1.5d, player.getPosZ());
		bullet.setVelocity(look.x*multiplier+(Math.random()-0.5)*1.5, look.y*multiplier+(Math.random()-0.5)*1.5, look.z*multiplier+(Math.random()-0.5)*1.5);
		bullet.setCustomName(new StringTextComponent("G18bullet"));
		worldIn.addEntity(bullet);


	}public static void fireSpringfield(World worldIn, Vec3d look, PlayerEntity player, Float distance){
		SnowballEntity bullet = new SnowballEntity(worldIn, player.getPosX(), player.getPosY()+1.5d, player.getPosZ());
		bullet.setVelocity(look.x*500, look.y*500, look.z*500);
		bullet.setCustomName(new StringTextComponent("SPbullet"));
		worldIn.addEntity(bullet);
		//player.rotationYaw = player.rotationYaw + (float) (Math.random()-0.5);
		for (int i = 0; i < 1000; i++) {
			player.rotationPitch = player.rotationPitch - (float) (Math.random()/50);
		}


	}public static void fireExplosive(World worldIn, Vec3d look, PlayerEntity player) {
		//SnowballEntity bullet = new SnowballEntity(worldIn, player.getPosX(), player.getPosY()+1.5d, player.getPosZ());
		ShulkerBulletEntity bullet = new ShulkerBulletEntity(worldIn, player.getPosX(), player.getPosY() + 1.5d, player.getPosZ(), player.getPosX(), player.getPosY(), player.getPosZ());
		bullet.setVelocity(look.x, look.y, look.z);
		worldIn.addEntity(bullet);
	}
}
