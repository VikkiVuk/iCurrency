
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package xyz.vikkivuk.icurrency.init;

import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.CreativeModeTab;

public class IcurrencyModTabs {
	public static CreativeModeTab TAB_I_CURRENCY_TAB;

	public static void load() {
		TAB_I_CURRENCY_TAB = new CreativeModeTab("tabi_currency_tab") {
			@Override
			public ItemStack makeIcon() {
				return new ItemStack(IcurrencyModItems.ONE_DOLLAR_BILL.get());
			}

			@OnlyIn(Dist.CLIENT)
			public boolean hasSearchBar() {
				return true;
			}
		}.setBackgroundSuffix("item_search.png");
	}
}
