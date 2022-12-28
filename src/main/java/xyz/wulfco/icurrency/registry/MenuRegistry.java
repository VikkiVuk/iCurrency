package xyz.wulfco.icurrency.registry;

import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.RegistryEvent;

import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.AbstractContainerMenu;
import xyz.wulfco.icurrency.world.inventory.*;

import java.util.List;
import java.util.ArrayList;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class MenuRegistry {
	private static final List<MenuType<?>> REGISTRY = new ArrayList<>();
	public static final MenuType<TransferMenu> PAY_GUI = register("pay_gui", TransferMenu::new);
	public static final MenuType<DepositMenu> ADD_MONEY_TO_BANK_GUI = register("add_money_to_bank_gui", DepositMenu::new);
	public static final MenuType<ATMMenu> ATM_GUI = register("atm_gui", ATMMenu::new);
	public static final MenuType<WithdrawMenu> WITHDRAW_GUI = register("withdraw_gui", WithdrawMenu::new);
	public static final MenuType<EnterCVCMenu> ENTER_CVC = register("enter_cvc", EnterCVCMenu::new);

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
