
/*
 *	MCreator note: This file will be REGENERATED on each build.
 */
package com.vikkivuk.icurrency.init;

import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.api.distmarker.Dist;

import com.vikkivuk.icurrency.client.gui.WithdrawMoneyScreen;
import com.vikkivuk.icurrency.client.gui.TransferMoneyScreen;
import com.vikkivuk.icurrency.client.gui.SetCardPinScreen;
import com.vikkivuk.icurrency.client.gui.OpenCashRegisterCheckScreen;
import com.vikkivuk.icurrency.client.gui.ExchangeMoneyScreen;
import com.vikkivuk.icurrency.client.gui.EnterPinScreen;
import com.vikkivuk.icurrency.client.gui.DepositMoneyScreen;
import com.vikkivuk.icurrency.client.gui.CashRegisterSetupScreen;
import com.vikkivuk.icurrency.client.gui.CashRegisterSafeScreen;
import com.vikkivuk.icurrency.client.gui.CashRegisterPayCashScreen;
import com.vikkivuk.icurrency.client.gui.CashRegisterPayCardScreen;
import com.vikkivuk.icurrency.client.gui.CashRegisterOverviewScreen;
import com.vikkivuk.icurrency.client.gui.CashRegisterGUIScreen;
import com.vikkivuk.icurrency.client.gui.CardOverviewScreen;
import com.vikkivuk.icurrency.client.gui.AcknowledgeTransactionResultCRScreen;
import com.vikkivuk.icurrency.client.gui.ATMSetupScreen;
import com.vikkivuk.icurrency.client.gui.ATMEntryPointScreen;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class IcurrencyModScreens {
	@SubscribeEvent
	public static void clientLoad(RegisterMenuScreensEvent event) {
		event.register(IcurrencyModMenus.ATM_ENTRY_POINT.get(), ATMEntryPointScreen::new);
		event.register(IcurrencyModMenus.ATM_SETUP.get(), ATMSetupScreen::new);
		event.register(IcurrencyModMenus.DEPOSIT_MONEY.get(), DepositMoneyScreen::new);
		event.register(IcurrencyModMenus.WITHDRAW_MONEY.get(), WithdrawMoneyScreen::new);
		event.register(IcurrencyModMenus.TRANSFER_MONEY.get(), TransferMoneyScreen::new);
		event.register(IcurrencyModMenus.EXCHANGE_MONEY.get(), ExchangeMoneyScreen::new);
		event.register(IcurrencyModMenus.CARD_OVERVIEW.get(), CardOverviewScreen::new);
		event.register(IcurrencyModMenus.CASH_REGISTER_GUI.get(), CashRegisterGUIScreen::new);
		event.register(IcurrencyModMenus.CASH_REGISTER_PAY_CARD.get(), CashRegisterPayCardScreen::new);
		event.register(IcurrencyModMenus.CASH_REGISTER_PAY_CASH.get(), CashRegisterPayCashScreen::new);
		event.register(IcurrencyModMenus.CASH_REGISTER_SETUP.get(), CashRegisterSetupScreen::new);
		event.register(IcurrencyModMenus.CASH_REGISTER_OVERVIEW.get(), CashRegisterOverviewScreen::new);
		event.register(IcurrencyModMenus.OPEN_CASH_REGISTER_CHECK.get(), OpenCashRegisterCheckScreen::new);
		event.register(IcurrencyModMenus.CASH_REGISTER_SAFE.get(), CashRegisterSafeScreen::new);
		event.register(IcurrencyModMenus.ACKNOWLEDGE_TRANSACTION_RESULT_CR.get(), AcknowledgeTransactionResultCRScreen::new);
		event.register(IcurrencyModMenus.SET_CARD_PIN.get(), SetCardPinScreen::new);
		event.register(IcurrencyModMenus.ENTER_PIN.get(), EnterPinScreen::new);
	}
}
