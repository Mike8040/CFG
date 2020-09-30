package com.mike.mod.utils.handlers;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import java.util.HashMap;
import java.util.Map;

public class VectorHandler {
	private static Map<PlayerEntity, Vec3d> playerLookVector = new HashMap<>();

	public static void addToMap(PlayerEntity p) {
		for (Map.Entry<PlayerEntity, Vec3d> entry : playerLookVector.entrySet()) {//check if player already in map
			if(entry.getKey() == p){
				playerLookVector.remove(entry);
			}
		}
		playerLookVector.put(p, p.getLookVec());
	}public static Vec3d getVec3d(PlayerEntity p){
		for (Map.Entry<PlayerEntity, Vec3d> entry : playerLookVector.entrySet()) {//check if player in map
			if(entry.getKey() == p){
				Vec3d e = entry.getValue();
				playerLookVector.remove(entry);
				return e;
			}
		}return null;
	}
}
