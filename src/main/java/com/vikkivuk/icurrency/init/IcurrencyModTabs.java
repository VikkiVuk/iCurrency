
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package com.vikkivuk.icurrency.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.network.chat.Component;
import net.minecraft.core.registries.Registries;

import com.vikkivuk.icurrency.IcurrencyMod;

public class IcurrencyModTabs {
	public static final DeferredRegister<CreativeModeTab> REGISTRY = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, IcurrencyMod.MODID);
	public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MOD_TAB = REGISTRY.register("mod_tab",
			() -> CreativeModeTab.builder().title(Component.translatable("item_group.icurrency.mod_tab")).icon(() -> new ItemStack(IcurrencyModItems.DEBIT_CARD.get())).displayItems((parameters, tabData) -> {
				tabData.accept(IcurrencyModItems.DEBIT_CARD.get());
				tabData.accept(IcurrencyModItems.DOLLAR_TEN.get());
				tabData.accept(IcurrencyModItems.DOLLAR_TWENTY.get());
				tabData.accept(IcurrencyModItems.DOLLAR_FIFTY.get());
				tabData.accept(IcurrencyModItems.DOLLAR_HUNDRED.get());
				tabData.accept(IcurrencyModItems.DOLLAR_TWO_HUNDRED.get());
				tabData.accept(IcurrencyModItems.DOLLAR_FIVE_HUNDRED.get());
				tabData.accept(IcurrencyModItems.DOLLAR_THOUSAND.get());
				tabData.accept(IcurrencyModBlocks.ATM.get().asItem());
				tabData.accept(IcurrencyModBlocks.CASH_REGISTER.get().asItem());
				tabData.accept(IcurrencyModItems.RECEIPT.get());
			}).withSearchBar().build());
}
