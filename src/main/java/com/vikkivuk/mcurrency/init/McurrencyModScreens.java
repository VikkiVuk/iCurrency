
/*
 *	MCreator note: This file will be REGENERATED on each build.
 */
package com.vikkivuk.mcurrency.init;

import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.api.distmarker.Dist;

import com.vikkivuk.mcurrency.client.gui.WithdrawMoneyScreen;
import com.vikkivuk.mcurrency.client.gui.TransferMoneyScreen;
import com.vikkivuk.mcurrency.client.gui.SetCardPinScreen;
import com.vikkivuk.mcurrency.client.gui.OpenCashRegisterCheckScreen;
import com.vikkivuk.mcurrency.client.gui.ExchangeMoneyScreen;
import com.vikkivuk.mcurrency.client.gui.EnterPinScreen;
import com.vikkivuk.mcurrency.client.gui.DepositMoneyScreen;
import com.vikkivuk.mcurrency.client.gui.CashRegisterSetupScreen;
import com.vikkivuk.mcurrency.client.gui.CashRegisterSafeScreen;
import com.vikkivuk.mcurrency.client.gui.CashRegisterPayCashScreen;
import com.vikkivuk.mcurrency.client.gui.CashRegisterPayCardScreen;
import com.vikkivuk.mcurrency.client.gui.CashRegisterOverviewScreen;
import com.vikkivuk.mcurrency.client.gui.CashRegisterCheckoutGUIScreen;
import com.vikkivuk.mcurrency.client.gui.CardOverviewScreen;
import com.vikkivuk.mcurrency.client.gui.AcknowledgeTransactionResultCRScreen;
import com.vikkivuk.mcurrency.client.gui.ATMSetupScreen;
import com.vikkivuk.mcurrency.client.gui.ATMEntryPointScreen;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class McurrencyModScreens {
	@SubscribeEvent
	public static void clientLoad(RegisterMenuScreensEvent event) {
		event.register(McurrencyModMenus.ATM_ENTRY_POINT.get(), ATMEntryPointScreen::new);
		event.register(McurrencyModMenus.ATM_SETUP.get(), ATMSetupScreen::new);
		event.register(McurrencyModMenus.DEPOSIT_MONEY.get(), DepositMoneyScreen::new);
		event.register(McurrencyModMenus.WITHDRAW_MONEY.get(), WithdrawMoneyScreen::new);
		event.register(McurrencyModMenus.TRANSFER_MONEY.get(), TransferMoneyScreen::new);
		event.register(McurrencyModMenus.EXCHANGE_MONEY.get(), ExchangeMoneyScreen::new);
		event.register(McurrencyModMenus.CARD_OVERVIEW.get(), CardOverviewScreen::new);
		event.register(McurrencyModMenus.CASH_REGISTER_PAY_CARD.get(), CashRegisterPayCardScreen::new);
		event.register(McurrencyModMenus.CASH_REGISTER_PAY_CASH.get(), CashRegisterPayCashScreen::new);
		event.register(McurrencyModMenus.CASH_REGISTER_SETUP.get(), CashRegisterSetupScreen::new);
		event.register(McurrencyModMenus.CASH_REGISTER_OVERVIEW.get(), CashRegisterOverviewScreen::new);
		event.register(McurrencyModMenus.OPEN_CASH_REGISTER_CHECK.get(), OpenCashRegisterCheckScreen::new);
		event.register(McurrencyModMenus.CASH_REGISTER_SAFE.get(), CashRegisterSafeScreen::new);
		event.register(McurrencyModMenus.ACKNOWLEDGE_TRANSACTION_RESULT_CR.get(), AcknowledgeTransactionResultCRScreen::new);
		event.register(McurrencyModMenus.SET_CARD_PIN.get(), SetCardPinScreen::new);
		event.register(McurrencyModMenus.ENTER_PIN.get(), EnterPinScreen::new);
		event.register(McurrencyModMenus.CASH_REGISTER_CHECKOUT_GUI.get(), CashRegisterCheckoutGUIScreen::new);
	}
}
