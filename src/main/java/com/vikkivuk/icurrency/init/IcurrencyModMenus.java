
/*
 *	MCreator note: This file will be REGENERATED on each build.
 */
package com.vikkivuk.icurrency.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;

import net.minecraft.world.inventory.MenuType;
import net.minecraft.core.registries.Registries;

import com.vikkivuk.icurrency.world.inventory.WithdrawMoneyMenu;
import com.vikkivuk.icurrency.world.inventory.TransferMoneyMenu;
import com.vikkivuk.icurrency.world.inventory.SetCardPinMenu;
import com.vikkivuk.icurrency.world.inventory.OpenCashRegisterCheckMenu;
import com.vikkivuk.icurrency.world.inventory.ExchangeMoneyMenu;
import com.vikkivuk.icurrency.world.inventory.EnterPinMenu;
import com.vikkivuk.icurrency.world.inventory.DepositMoneyMenu;
import com.vikkivuk.icurrency.world.inventory.CashRegisterSetupMenu;
import com.vikkivuk.icurrency.world.inventory.CashRegisterSafeMenu;
import com.vikkivuk.icurrency.world.inventory.CashRegisterPayCashMenu;
import com.vikkivuk.icurrency.world.inventory.CashRegisterPayCardMenu;
import com.vikkivuk.icurrency.world.inventory.CashRegisterOverviewMenu;
import com.vikkivuk.icurrency.world.inventory.CashRegisterCheckoutGUIMenu;
import com.vikkivuk.icurrency.world.inventory.CardOverviewMenu;
import com.vikkivuk.icurrency.world.inventory.AcknowledgeTransactionResultCRMenu;
import com.vikkivuk.icurrency.world.inventory.ATMSetupMenu;
import com.vikkivuk.icurrency.world.inventory.ATMEntryPointMenu;
import com.vikkivuk.icurrency.IcurrencyMod;

public class IcurrencyModMenus {
	public static final DeferredRegister<MenuType<?>> REGISTRY = DeferredRegister.create(Registries.MENU, IcurrencyMod.MODID);
	public static final DeferredHolder<MenuType<?>, MenuType<ATMEntryPointMenu>> ATM_ENTRY_POINT = REGISTRY.register("atm_entry_point", () -> IMenuTypeExtension.create(ATMEntryPointMenu::new));
	public static final DeferredHolder<MenuType<?>, MenuType<ATMSetupMenu>> ATM_SETUP = REGISTRY.register("atm_setup", () -> IMenuTypeExtension.create(ATMSetupMenu::new));
	public static final DeferredHolder<MenuType<?>, MenuType<DepositMoneyMenu>> DEPOSIT_MONEY = REGISTRY.register("deposit_money", () -> IMenuTypeExtension.create(DepositMoneyMenu::new));
	public static final DeferredHolder<MenuType<?>, MenuType<WithdrawMoneyMenu>> WITHDRAW_MONEY = REGISTRY.register("withdraw_money", () -> IMenuTypeExtension.create(WithdrawMoneyMenu::new));
	public static final DeferredHolder<MenuType<?>, MenuType<TransferMoneyMenu>> TRANSFER_MONEY = REGISTRY.register("transfer_money", () -> IMenuTypeExtension.create(TransferMoneyMenu::new));
	public static final DeferredHolder<MenuType<?>, MenuType<ExchangeMoneyMenu>> EXCHANGE_MONEY = REGISTRY.register("exchange_money", () -> IMenuTypeExtension.create(ExchangeMoneyMenu::new));
	public static final DeferredHolder<MenuType<?>, MenuType<CardOverviewMenu>> CARD_OVERVIEW = REGISTRY.register("card_overview", () -> IMenuTypeExtension.create(CardOverviewMenu::new));
	public static final DeferredHolder<MenuType<?>, MenuType<CashRegisterPayCardMenu>> CASH_REGISTER_PAY_CARD = REGISTRY.register("cash_register_pay_card", () -> IMenuTypeExtension.create(CashRegisterPayCardMenu::new));
	public static final DeferredHolder<MenuType<?>, MenuType<CashRegisterPayCashMenu>> CASH_REGISTER_PAY_CASH = REGISTRY.register("cash_register_pay_cash", () -> IMenuTypeExtension.create(CashRegisterPayCashMenu::new));
	public static final DeferredHolder<MenuType<?>, MenuType<CashRegisterSetupMenu>> CASH_REGISTER_SETUP = REGISTRY.register("cash_register_setup", () -> IMenuTypeExtension.create(CashRegisterSetupMenu::new));
	public static final DeferredHolder<MenuType<?>, MenuType<CashRegisterOverviewMenu>> CASH_REGISTER_OVERVIEW = REGISTRY.register("cash_register_overview", () -> IMenuTypeExtension.create(CashRegisterOverviewMenu::new));
	public static final DeferredHolder<MenuType<?>, MenuType<OpenCashRegisterCheckMenu>> OPEN_CASH_REGISTER_CHECK = REGISTRY.register("open_cash_register_check", () -> IMenuTypeExtension.create(OpenCashRegisterCheckMenu::new));
	public static final DeferredHolder<MenuType<?>, MenuType<CashRegisterSafeMenu>> CASH_REGISTER_SAFE = REGISTRY.register("cash_register_safe", () -> IMenuTypeExtension.create(CashRegisterSafeMenu::new));
	public static final DeferredHolder<MenuType<?>, MenuType<AcknowledgeTransactionResultCRMenu>> ACKNOWLEDGE_TRANSACTION_RESULT_CR = REGISTRY.register("acknowledge_transaction_result_cr",
			() -> IMenuTypeExtension.create(AcknowledgeTransactionResultCRMenu::new));
	public static final DeferredHolder<MenuType<?>, MenuType<SetCardPinMenu>> SET_CARD_PIN = REGISTRY.register("set_card_pin", () -> IMenuTypeExtension.create(SetCardPinMenu::new));
	public static final DeferredHolder<MenuType<?>, MenuType<EnterPinMenu>> ENTER_PIN = REGISTRY.register("enter_pin", () -> IMenuTypeExtension.create(EnterPinMenu::new));
	public static final DeferredHolder<MenuType<?>, MenuType<CashRegisterCheckoutGUIMenu>> CASH_REGISTER_CHECKOUT_GUI = REGISTRY.register("cash_register_checkout_gui", () -> IMenuTypeExtension.create(CashRegisterCheckoutGUIMenu::new));
}
