package com.vikkivuk.icurrency.procedures;

import net.minecraft.world.entity.Entity;

import com.vikkivuk.icurrency.network.IcurrencyModVariables;

public class TrashCardOneProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		entity.getData(IcurrencyModVariables.PLAYER_VARIABLES).cards.remove("one");
	}
}
