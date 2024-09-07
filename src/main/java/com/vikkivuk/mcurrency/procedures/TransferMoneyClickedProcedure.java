package com.vikkivuk.mcurrency.procedures;

import org.checkerframework.checker.units.qual.s;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.network.chat.Component;

import java.util.HashMap;
import java.util.ArrayList;

import com.vikkivuk.mcurrency.network.McurrencyModVariables;

public class TransferMoneyClickedProcedure {
	public static void execute(LevelAccessor world, Entity entity, HashMap guistate) {
		if (entity == null || guistate == null)
			return;
		boolean found_player = false;
		for (Entity entityiterator : new ArrayList<>(world.players())) {
			if (((guistate.containsKey("textin:receiver") ? (String) guistate.get("textin:receiver") : "").toLowerCase()).equals((entityiterator.getDisplayName().getString()).toLowerCase())) {
				if (new Object() {
					double convert(String s) {
						try {
							return Double.parseDouble(s.trim());
						} catch (Exception e) {
						}
						return 0;
					}
				}.convert(guistate.containsKey("textin:amount") ? (String) guistate.get("textin:amount") : "") <= entity.getData(McurrencyModVariables.PLAYER_VARIABLES).money) {
					if (entity instanceof Player _player && !_player.level().isClientSide())
						_player.displayClientMessage(Component.literal(("Sent \u01B5" + (guistate.containsKey("textin:amount") ? (String) guistate.get("textin:amount") : ""))), false);
					if (entityiterator instanceof Player _player && !_player.level().isClientSide())
						_player.displayClientMessage(Component.literal(("You received \u01B5" + (guistate.containsKey("textin:amount") ? (String) guistate.get("textin:amount") : "") + " from " + entity.getDisplayName().getString())), false);
					{
						McurrencyModVariables.PlayerVariables _vars = entity.getData(McurrencyModVariables.PLAYER_VARIABLES);
						_vars.money = entity.getData(McurrencyModVariables.PLAYER_VARIABLES).money - new Object() {
							double convert(String s) {
								try {
									return Double.parseDouble(s.trim());
								} catch (Exception e) {
								}
								return 0;
							}
						}.convert(guistate.containsKey("textin:amount") ? (String) guistate.get("textin:amount") : "");
						_vars.syncPlayerVariables(entity);
					}
					{
						McurrencyModVariables.PlayerVariables _vars = entityiterator.getData(McurrencyModVariables.PLAYER_VARIABLES);
						_vars.money = entityiterator.getData(McurrencyModVariables.PLAYER_VARIABLES).money + new Object() {
							double convert(String s) {
								try {
									return Double.parseDouble(s.trim());
								} catch (Exception e) {
								}
								return 0;
							}
						}.convert(guistate.containsKey("textin:amount") ? (String) guistate.get("textin:amount") : "");
						_vars.syncPlayerVariables(entityiterator);
					}
				} else {
					if (entity instanceof Player _player && !_player.level().isClientSide())
						_player.displayClientMessage(Component.literal("Not enough money!"), false);
				}
				found_player = true;
			}
		}
		if (!found_player) {
			if (entity instanceof Player _player && !_player.level().isClientSide())
				_player.displayClientMessage(Component.literal("Player not online!"), false);
		}
		if (entity instanceof Player _player)
			_player.closeContainer();
	}
}
