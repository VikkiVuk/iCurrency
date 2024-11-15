package com.vikkivuk.mcurrency.procedures;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.network.chat.Component;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.CommandSourceStack;

import com.vikkivuk.mcurrency.network.McurrencyModVariables;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.arguments.DoubleArgumentType;

public class RemoveMoneyCommandProcedure {
	public static void execute(CommandContext<CommandSourceStack> arguments, Entity entity) {
		if (entity == null)
			return;
		try {
			for (Entity entityiterator : EntityArgument.getEntities(arguments, "name")) {
				{
					McurrencyModVariables.PlayerVariables _vars = entityiterator.getData(McurrencyModVariables.PLAYER_VARIABLES);
					_vars.money = entityiterator.getData(McurrencyModVariables.PLAYER_VARIABLES).money - DoubleArgumentType.getDouble(arguments, "amount");
					_vars.syncPlayerVariables(entityiterator);
				}
				if (entityiterator instanceof Player _player && !_player.level().isClientSide())
					_player.displayClientMessage(Component.literal(("Your updated balance is \u01B5" + entityiterator.getData(McurrencyModVariables.PLAYER_VARIABLES).money)), false);
				if (entity instanceof Player _player && !_player.level().isClientSide())
					_player.displayClientMessage(Component.literal(("Updated money of " + entityiterator.getDisplayName().getString())), false);
			}
		} catch (CommandSyntaxException e) {
			e.printStackTrace();
		}
	}
}
