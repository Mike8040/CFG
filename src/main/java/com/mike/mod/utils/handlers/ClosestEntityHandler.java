package com.mike.mod.utils.handlers;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import com.mike.mod.main;

import java.util.List;

public class ClosestEntityHandler {

	public static Object getClosestEntity(List<Entity> entityList, Entity entityFrom, Boolean returnDist){//return dist true if you want distance returned rather than entity
		float closest = Float.MAX_VALUE;
		Entity thisOne = null;

		for (int i = 0; i < entityList.size(); i++) {
			if (((Entity) entityList.get(i)) instanceof PlayerEntity){
				main.LOGGER.debug("Closest entity was player");
			}else {
				if (((Entity) entityList.get(i)).getDistance(entityFrom) < closest) {
					if (entityList.get(i) instanceof Entity) {
						closest = ((Entity) entityList.get(i)).getDistance(entityFrom);
						thisOne = ((Entity) entityList.get(i));
					}
				}
			}
		}if(returnDist == false) {
			return thisOne;
		}else{
			return closest;
		}
	}
}
