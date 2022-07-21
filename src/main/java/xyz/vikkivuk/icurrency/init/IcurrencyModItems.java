
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package xyz.vikkivuk.icurrency.init;

import xyz.vikkivuk.icurrency.item.TwentyDollarBillItem;
import xyz.vikkivuk.icurrency.item.OneDollarBillItem;
import xyz.vikkivuk.icurrency.item.HundredDollarBillItem;
import xyz.vikkivuk.icurrency.item.FiveDollarBillItem;
import xyz.vikkivuk.icurrency.item.FiftyDollarBillItem;
import xyz.vikkivuk.icurrency.item.DollarTenItem;
import xyz.vikkivuk.icurrency.item.CardItem;
import xyz.vikkivuk.icurrency.IcurrencyMod;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.BlockItem;

public class IcurrencyModItems {
	public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, IcurrencyMod.MODID);
	public static final RegistryObject<Item> ONE_DOLLAR_BILL = REGISTRY.register("one_dollar_bill", () -> new OneDollarBillItem());
	public static final RegistryObject<Item> FIVE_DOLLAR_BILL = REGISTRY.register("five_dollar_bill", () -> new FiveDollarBillItem());
	public static final RegistryObject<Item> DOLLAR_TEN = REGISTRY.register("dollar_ten", () -> new DollarTenItem());
	public static final RegistryObject<Item> TWENTY_DOLLAR_BILL = REGISTRY.register("twenty_dollar_bill", () -> new TwentyDollarBillItem());
	public static final RegistryObject<Item> FIFTY_DOLLAR_BILL = REGISTRY.register("fifty_dollar_bill", () -> new FiftyDollarBillItem());
	public static final RegistryObject<Item> HUNDRED_DOLLAR_BILL = REGISTRY.register("hundred_dollar_bill", () -> new HundredDollarBillItem());
	public static final RegistryObject<Item> CARD = REGISTRY.register("card", () -> new CardItem());
	public static final RegistryObject<Item> ATM_BLOCK = block(IcurrencyModBlocks.ATM_BLOCK, IcurrencyModTabs.TAB_I_CURRENCY_TAB);

	private static RegistryObject<Item> block(RegistryObject<Block> block, CreativeModeTab tab) {
		return REGISTRY.register(block.getId().getPath(), () -> new BlockItem(block.get(), new Item.Properties().tab(tab)));
	}
}
