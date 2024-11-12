package com.vikkivuk.mcurrency.procedures;

import net.minecraft.world.entity.Entity;

import com.vikkivuk.mcurrency.network.McurrencyModVariables;

public class COCardTwoNumberProcedure {
	public static String execute(Entity entity) {
		if (entity == null)
			return "";
		if (entity.getData(McurrencyModVariables.PLAYER_VARIABLES).cards.containsKey("two")) {
			return Math.round((entity.getData(McurrencyModVariables.PLAYER_VARIABLES).cards.get("two")).x()) + " " + Math.round((entity.getData(McurrencyModVariables.PLAYER_VARIABLES).cards.get("two")).y());
		}
		return "UNKNOWN";
	}
}
