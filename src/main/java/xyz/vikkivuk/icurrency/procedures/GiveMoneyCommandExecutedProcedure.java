package xyz.vikkivuk.icurrency.procedures;

import xyz.vikkivuk.icurrency.network.IcurrencyModVariables;

import org.checkerframework.checker.units.qual.s;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.network.chat.TextComponent;

import java.util.HashMap;

public class GiveMoneyCommandExecutedProcedure {
	public static void execute(LevelAccessor world, Entity entity, HashMap cmdparams) {
		if (entity == null || cmdparams == null)
			return;
		IcurrencyModVariables.MapVariables.get(world).account_balance = entity.getPersistentData().getDouble("account_balance_tag") + new Object() {
			double convert(String s) {
				try {
					return Double.parseDouble(s.trim());
				} catch (Exception e) {
				}
				return 0;
			}
		}.convert(cmdparams.containsKey("0") ? cmdparams.get("0").toString() : "");
		IcurrencyModVariables.MapVariables.get(world).syncData(world);
		if (entity instanceof Player _player && !_player.level.isClientSide())
			_player.displayClientMessage(new TextComponent("Account Balance Updated"), (true));
	}
}
