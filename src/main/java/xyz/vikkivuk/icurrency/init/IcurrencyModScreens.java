
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package xyz.vikkivuk.icurrency.init;

import xyz.vikkivuk.icurrency.client.gui.WithdrawGuiScreen;
import xyz.vikkivuk.icurrency.client.gui.SetupGuiScreen;
import xyz.vikkivuk.icurrency.client.gui.PayGuiScreen;
import xyz.vikkivuk.icurrency.client.gui.AtmGuiScreen;
import xyz.vikkivuk.icurrency.client.gui.AddMoneyToBankGUIScreen;

import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.client.gui.screens.MenuScreens;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class IcurrencyModScreens {
	@SubscribeEvent
	public static void clientLoad(FMLClientSetupEvent event) {
		event.enqueueWork(() -> {
			MenuScreens.register(IcurrencyModMenus.PAY_GUI, PayGuiScreen::new);
			MenuScreens.register(IcurrencyModMenus.ADD_MONEY_TO_BANK_GUI, AddMoneyToBankGUIScreen::new);
			MenuScreens.register(IcurrencyModMenus.ATM_GUI, AtmGuiScreen::new);
			MenuScreens.register(IcurrencyModMenus.WITHDRAW_GUI, WithdrawGuiScreen::new);
			MenuScreens.register(IcurrencyModMenus.SETUP_GUI, SetupGuiScreen::new);
		});
	}
}
