
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package com.vikkivuk.mcurrency.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.network.chat.Component;
import net.minecraft.core.registries.Registries;

import com.vikkivuk.mcurrency.McurrencyMod;

public class McurrencyModTabs {
	public static final DeferredRegister<CreativeModeTab> REGISTRY = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, McurrencyMod.MODID);
	public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MOD_TAB = REGISTRY.register("mod_tab",
			() -> CreativeModeTab.builder().title(Component.translatable("item_group.mcurrency.mod_tab")).icon(() -> new ItemStack(McurrencyModItems.DEBIT_CARD.get())).displayItems((parameters, tabData) -> {
				tabData.accept(McurrencyModItems.DEBIT_CARD.get());
				tabData.accept(McurrencyModItems.DOLLAR_TEN.get());
				tabData.accept(McurrencyModItems.DOLLAR_TWENTY.get());
				tabData.accept(McurrencyModItems.DOLLAR_FIFTY.get());
				tabData.accept(McurrencyModItems.DOLLAR_HUNDRED.get());
				tabData.accept(McurrencyModItems.DOLLAR_TWO_HUNDRED.get());
				tabData.accept(McurrencyModItems.DOLLAR_FIVE_HUNDRED.get());
				tabData.accept(McurrencyModItems.DOLLAR_THOUSAND.get());
				tabData.accept(McurrencyModBlocks.ATM.get().asItem());
				tabData.accept(McurrencyModBlocks.CASH_REGISTER.get().asItem());
				tabData.accept(McurrencyModItems.RECEIPT.get());
				tabData.accept(McurrencyModBlocks.ACM.get().asItem());
			}).withSearchBar().build());
}
