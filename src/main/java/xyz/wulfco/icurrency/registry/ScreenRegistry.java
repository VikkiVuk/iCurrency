package xyz.wulfco.icurrency.registry;

import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.client.gui.screens.MenuScreens;
import xyz.wulfco.icurrency.client.gui.*;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ScreenRegistry {
	@SubscribeEvent
	public static void clientLoad(FMLClientSetupEvent event) {
		event.enqueueWork(() -> {
			MenuScreens.register(MenuRegistry.PAY_GUI, TransferScreen::new);
			MenuScreens.register(MenuRegistry.ADD_MONEY_TO_BANK_GUI, DepositScreen::new);
			MenuScreens.register(MenuRegistry.ATM_GUI, ATMScreen::new);
			MenuScreens.register(MenuRegistry.WITHDRAW_GUI, WithdrawScreen::new);
			MenuScreens.register(MenuRegistry.ENTER_CVC, EnterCVCScreen::new);
		});
	}
}
