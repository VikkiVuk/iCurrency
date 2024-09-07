package com.vikkivuk.mcurrency.procedures;

import net.minecraft.world.entity.Entity;

import com.vikkivuk.mcurrency.network.McurrencyModVariables;

public class COShowCardThreeProcedure {
	public static boolean execute(Entity entity) {
		if (entity == null)
			return false;
		return entity.getData(McurrencyModVariables.PLAYER_VARIABLES).cards.containsKey("three");
	}
}
