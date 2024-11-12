package com.vikkivuk.mcurrency.procedures;

import net.minecraft.world.entity.Entity;

import com.vikkivuk.mcurrency.network.McurrencyModVariables;

public class COCardThreeNumberProcedure {
	public static String execute(Entity entity) {
		if (entity == null)
			return "";
		if (entity.getData(McurrencyModVariables.PLAYER_VARIABLES).cards.containsKey("three")) {
			return Math.round((entity.getData(McurrencyModVariables.PLAYER_VARIABLES).cards.get("three")).x()) + " " + Math.round((entity.getData(McurrencyModVariables.PLAYER_VARIABLES).cards.get("three")).y());
		}
		return "UNKNOWN";
	}
}
