package com.vikkivuk.mcurrency.procedures;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.network.chat.Component;

import com.vikkivuk.mcurrency.network.McurrencyModVariables;

public class CheckBalanceProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if (entity instanceof Player _player && !_player.level().isClientSide())
			_player.displayClientMessage(Component.literal(("Your current balance is \u01B5" + entity.getData(McurrencyModVariables.PLAYER_VARIABLES).money)), false);
		if (entity instanceof Player _player)
			_player.closeContainer();
	}
}
