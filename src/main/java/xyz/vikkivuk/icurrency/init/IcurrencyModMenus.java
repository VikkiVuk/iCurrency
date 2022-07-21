
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package xyz.vikkivuk.icurrency.init;

import xyz.vikkivuk.icurrency.world.inventory.WithdrawGuiMenu;
import xyz.vikkivuk.icurrency.world.inventory.SetupGuiMenu;
import xyz.vikkivuk.icurrency.world.inventory.PayGuiMenu;
import xyz.vikkivuk.icurrency.world.inventory.AtmGuiMenu;
import xyz.vikkivuk.icurrency.world.inventory.AddMoneyToBankGUIMenu;

import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.RegistryEvent;

import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.AbstractContainerMenu;

import java.util.List;
import java.util.ArrayList;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class IcurrencyModMenus {
	private static final List<MenuType<?>> REGISTRY = new ArrayList<>();
	public static final MenuType<PayGuiMenu> PAY_GUI = register("pay_gui", (id, inv, extraData) -> new PayGuiMenu(id, inv, extraData));
	public static final MenuType<AddMoneyToBankGUIMenu> ADD_MONEY_TO_BANK_GUI = register("add_money_to_bank_gui",
			(id, inv, extraData) -> new AddMoneyToBankGUIMenu(id, inv, extraData));
	public static final MenuType<AtmGuiMenu> ATM_GUI = register("atm_gui", (id, inv, extraData) -> new AtmGuiMenu(id, inv, extraData));
	public static final MenuType<WithdrawGuiMenu> WITHDRAW_GUI = register("withdraw_gui",
			(id, inv, extraData) -> new WithdrawGuiMenu(id, inv, extraData));
	public static final MenuType<SetupGuiMenu> SETUP_GUI = register("setup_gui", (id, inv, extraData) -> new SetupGuiMenu(id, inv, extraData));

	private static <T extends AbstractContainerMenu> MenuType<T> register(String registryname, IContainerFactory<T> containerFactory) {
		MenuType<T> menuType = new MenuType<T>(containerFactory);
		menuType.setRegistryName(registryname);
		REGISTRY.add(menuType);
		return menuType;
	}

	@SubscribeEvent
	public static void registerContainers(RegistryEvent.Register<MenuType<?>> event) {
		event.getRegistry().registerAll(REGISTRY.toArray(new MenuType[0]));
	}
}
