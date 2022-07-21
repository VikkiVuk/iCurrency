
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package xyz.vikkivuk.icurrency.init;

import net.minecraftforge.fml.common.Mod;

import net.minecraft.world.level.GameRules;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class IcurrencyModGameRules {
	public static final GameRules.Key<GameRules.BooleanValue> USEONLYATM = GameRules.register("useOnlyAtm", GameRules.Category.PLAYER,
			GameRules.BooleanValue.create(false));
}
