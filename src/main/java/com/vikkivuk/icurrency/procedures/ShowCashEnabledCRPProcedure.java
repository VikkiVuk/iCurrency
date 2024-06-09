package com.vikkivuk.icurrency.procedures;

import net.minecraft.world.entity.Entity;

import com.vikkivuk.icurrency.network.IcurrencyModVariables;

public class ShowCashEnabledCRPProcedure {
	public static boolean execute(Entity entity) {
		if (entity == null)
			return false;
		return !entity.getData(IcurrencyModVariables.PLAYER_VARIABLES).cash_disabled;
	}
}