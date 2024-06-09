package com.vikkivuk.icurrency.procedures;

import net.minecraft.world.entity.Entity;

import com.vikkivuk.icurrency.network.IcurrencyModVariables;

public class TrashCardFourProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		try {
			entity.getData(IcurrencyModVariables.PLAYER_VARIABLES).cards.remove("four");
		} catch (Exception e) {
		}
	}
}