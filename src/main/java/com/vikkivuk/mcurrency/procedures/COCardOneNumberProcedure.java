package com.vikkivuk.mcurrency.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.entity.Entity;

import java.util.HashMap;

import com.vikkivuk.mcurrency.network.McurrencyModVariables;

public class COCardOneNumberProcedure {
	public static String execute(Entity entity) {
		if (entity == null)
			return "";
		if (entity.getData(McurrencyModVariables.PLAYER_VARIABLES).refresh_cards) {
			{
				McurrencyModVariables.PlayerVariables _vars = entity.getData(McurrencyModVariables.PLAYER_VARIABLES);
				_vars.refresh_cards = false;
				_vars.syncPlayerVariables(entity);
			}
			if (entity.getData(McurrencyModVariables.PLAYER_VARIABLES).cards.containsKey("one")) {
				return Math.round(((new Object() {
					public Vec3 get(HashMap<String, Vec3> hashMap, String key) {
						Vec3 vec3 = hashMap.get(key);
						return vec3 == null ? Vec3.ZERO : vec3;
					}
				}).get(entity.getData(McurrencyModVariables.PLAYER_VARIABLES).cards, "one")).x()) + " " + Math.round(((new Object() {
					public Vec3 get(HashMap<String, Vec3> hashMap, String key) {
						Vec3 vec3 = hashMap.get(key);
						return vec3 == null ? Vec3.ZERO : vec3;
					}
				}).get(entity.getData(McurrencyModVariables.PLAYER_VARIABLES).cards, "one")).y());
			}
		} else {
			if (entity.getData(McurrencyModVariables.PLAYER_VARIABLES).cards.containsKey("one")) {
				return Math.round(((new Object() {
					public Vec3 get(HashMap<String, Vec3> hashMap, String key) {
						Vec3 vec3 = hashMap.get(key);
						return vec3 == null ? Vec3.ZERO : vec3;
					}
				}).get(entity.getData(McurrencyModVariables.PLAYER_VARIABLES).cards, "one")).x()) + " " + Math.round(((new Object() {
					public Vec3 get(HashMap<String, Vec3> hashMap, String key) {
						Vec3 vec3 = hashMap.get(key);
						return vec3 == null ? Vec3.ZERO : vec3;
					}
				}).get(entity.getData(McurrencyModVariables.PLAYER_VARIABLES).cards, "one")).y());
			}
		}
		return "UNKNOWN";
	}
}
