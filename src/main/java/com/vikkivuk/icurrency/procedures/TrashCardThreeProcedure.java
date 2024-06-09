package com.vikkivuk.icurrency.procedures;

import net.minecraft.world.entity.Entity;

import com.vikkivuk.icurrency.network.IcurrencyModVariables;

public class TrashCardThreeProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		try {
			entity.getData(IcurrencyModVariables.PLAYER_VARIABLES).cards.remove("three");
		} catch (Exception e) {
		}
	}
}
