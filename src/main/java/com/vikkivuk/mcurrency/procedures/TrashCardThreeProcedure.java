package com.vikkivuk.mcurrency.procedures;

import net.minecraft.world.entity.Entity;

import com.vikkivuk.mcurrency.network.McurrencyModVariables;

public class TrashCardThreeProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		entity.getData(McurrencyModVariables.PLAYER_VARIABLES).cards.remove("three");
	}
}
