package com.vikkivuk.mcurrency.procedures;

import net.minecraft.world.entity.Entity;

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
				return Math.round((entity.getData(McurrencyModVariables.PLAYER_VARIABLES).cards.get("one")).x()) + " " + Math.round((entity.getData(McurrencyModVariables.PLAYER_VARIABLES).cards.get("one")).y());
			}
		} else {
			if (entity.getData(McurrencyModVariables.PLAYER_VARIABLES).cards.containsKey("one")) {
				return Math.round((entity.getData(McurrencyModVariables.PLAYER_VARIABLES).cards.get("one")).x()) + " " + Math.round((entity.getData(McurrencyModVariables.PLAYER_VARIABLES).cards.get("one")).y());
			}
		}
		return "UNKNOWN";
	}
}
