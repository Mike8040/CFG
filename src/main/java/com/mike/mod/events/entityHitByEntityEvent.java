package com.mike.mod.events;

import com.mike.mod.main;
import com.mike.mod.utils.handlers.ClosestEntityHandler;
import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;


@Mod.EventBusSubscriber(modid = main.MOD_ID,bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.DEDICATED_SERVER)
public class entityHitByEntityEvent {

	@SubscribeEvent
	public static void entityHitByEntityEvent(ProjectileImpactEvent event) {
		Entity snowball = event.getEntity();
		World world = snowball.getEntityWorld();
		List<Entity> entityList = world.getEntitiesWithinAABBExcludingEntity(event.getEntity(), event.getEntity().getBoundingBox().grow(500));
		Entity entityToHit = null;

		entityToHit = (Entity) ClosestEntityHandler.getClosestEntity(entityList, snowball, false);

		if (entityToHit != null) {
			if (event.getRayTraceResult().getType() != RayTraceResult.Type.BLOCK) {
				if(snowball.getCustomName().getFormattedText().contains("AKbullet")) {
					entityToHit.attackEntityFrom(DamageSource.GENERIC, 7f);
				}else if(snowball.getCustomName().getFormattedText().contains("G18bullet")) {
					entityToHit.attackEntityFrom(DamageSource.GENERIC, 4f);
				}else if(snowball.getCustomName().getFormattedText().contains("SPbullet")) {
					entityToHit.attackEntityFrom(DamageSource.GENERIC, 25f);
				}
			}
		}
	}
}

