package com.vikkivuk.mcurrency.procedures;

import net.minecraft.world.entity.Entity;

import com.vikkivuk.mcurrency.network.McurrencyModVariables;

public class COCardFourCVCProcedure {
	public static String execute(Entity entity) {
		if (entity == null)
			return "";
		if (entity.getData(McurrencyModVariables.PLAYER_VARIABLES).cards.containsKey("four")) {
			return "" + Math.round((entity.getData(McurrencyModVariables.PLAYER_VARIABLES).cards.get("four")).z());
		}
		return "UNKNOWN";
	}
}
