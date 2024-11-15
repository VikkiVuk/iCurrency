package com.vikkivuk.mcurrency.procedures;

import org.checkerframework.checker.units.qual.s;

import net.minecraft.world.entity.Entity;

import java.util.HashMap;

import com.vikkivuk.mcurrency.network.McurrencyModVariables;

public class CashRegisterGUIWhileThisGUIIsOpenTickProcedure {
	public static void execute(Entity entity, HashMap guistate) {
		if (entity == null || guistate == null)
			return;
		if ((guistate.containsKey("textin:product") ? (String) guistate.get("textin:product") : "").isEmpty() || (guistate.containsKey("textin:price") ? (String) guistate.get("textin:price") : "").isEmpty()
				|| (guistate.containsKey("textin:tip") ? (String) guistate.get("textin:tip") : "").isEmpty()) {
			{
				McurrencyModVariables.PlayerVariables _vars = entity.getData(McurrencyModVariables.PLAYER_VARIABLES);
				_vars.card_disabled = true;
				_vars.syncPlayerVariables(entity);
			}
			{
				McurrencyModVariables.PlayerVariables _vars = entity.getData(McurrencyModVariables.PLAYER_VARIABLES);
				_vars.cash_disabled = true;
				_vars.syncPlayerVariables(entity);
			}
		} else {
			{
				McurrencyModVariables.PlayerVariables _vars = entity.getData(McurrencyModVariables.PLAYER_VARIABLES);
				_vars.cash_disabled = false;
				_vars.syncPlayerVariables(entity);
			}
			{
				McurrencyModVariables.PlayerVariables _vars = entity.getData(McurrencyModVariables.PLAYER_VARIABLES);
				_vars.card_disabled = false;
				_vars.syncPlayerVariables(entity);
			}
			if (new Object() {
				double convert(String s) {
					try {
						return Double.parseDouble(s.trim());
					} catch (Exception e) {
					}
					return 0;
				}
			}.convert(guistate.containsKey("textin:tip") ? (String) guistate.get("textin:tip") : "") > 50) {
				{
					McurrencyModVariables.PlayerVariables _vars = entity.getData(McurrencyModVariables.PLAYER_VARIABLES);
					_vars.cash_disabled = true;
					_vars.syncPlayerVariables(entity);
				}
				{
					McurrencyModVariables.PlayerVariables _vars = entity.getData(McurrencyModVariables.PLAYER_VARIABLES);
					_vars.card_disabled = true;
					_vars.syncPlayerVariables(entity);
				}
			}
			if (new Object() {
				double convert(String s) {
					try {
						return Double.parseDouble(s.trim());
					} catch (Exception e) {
					}
					return 0;
				}
			}.convert(guistate.containsKey("textin:price") ? (String) guistate.get("textin:price") : "") > 500000) {
				{
					McurrencyModVariables.PlayerVariables _vars = entity.getData(McurrencyModVariables.PLAYER_VARIABLES);
					_vars.cash_disabled = true;
					_vars.syncPlayerVariables(entity);
				}
			}
		}
	}
}
