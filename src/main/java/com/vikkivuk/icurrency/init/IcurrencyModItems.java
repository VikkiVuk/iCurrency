
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package com.vikkivuk.icurrency.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.bus.api.IEventBus;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.BlockItem;
import net.minecraft.core.registries.BuiltInRegistries;

import com.vikkivuk.icurrency.item.ReceiptItem;
import com.vikkivuk.icurrency.item.DollarTwoHundredItem;
import com.vikkivuk.icurrency.item.DollarTwentyItem;
import com.vikkivuk.icurrency.item.DollarThousandItem;
import com.vikkivuk.icurrency.item.DollarTenItem;
import com.vikkivuk.icurrency.item.DollarHundredItem;
import com.vikkivuk.icurrency.item.DollarFiveHundredItem;
import com.vikkivuk.icurrency.item.DollarFiftyItem;
import com.vikkivuk.icurrency.item.DebitCardItem;
import com.vikkivuk.icurrency.IcurrencyMod;

public class IcurrencyModItems {
	public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(BuiltInRegistries.ITEM, IcurrencyMod.MODID);
	public static final DeferredHolder<Item, Item> DEBIT_CARD = REGISTRY.register("debit_card", () -> new DebitCardItem());
	public static final DeferredHolder<Item, Item> DOLLAR_TEN = REGISTRY.register("dollar_ten", () -> new DollarTenItem());
	public static final DeferredHolder<Item, Item> DOLLAR_TWENTY = REGISTRY.register("dollar_twenty", () -> new DollarTwentyItem());
	public static final DeferredHolder<Item, Item> DOLLAR_FIFTY = REGISTRY.register("dollar_fifty", () -> new DollarFiftyItem());
	public static final DeferredHolder<Item, Item> DOLLAR_HUNDRED = REGISTRY.register("dollar_hundred", () -> new DollarHundredItem());
	public static final DeferredHolder<Item, Item> DOLLAR_TWO_HUNDRED = REGISTRY.register("dollar_two_hundred", () -> new DollarTwoHundredItem());
	public static final DeferredHolder<Item, Item> DOLLAR_FIVE_HUNDRED = REGISTRY.register("dollar_five_hundred", () -> new DollarFiveHundredItem());
	public static final DeferredHolder<Item, Item> DOLLAR_THOUSAND = REGISTRY.register("dollar_thousand", () -> new DollarThousandItem());
	public static final DeferredHolder<Item, Item> ATM = block(IcurrencyModBlocks.ATM);
	public static final DeferredHolder<Item, Item> CASH_REGISTER = block(IcurrencyModBlocks.CASH_REGISTER);
	public static final DeferredHolder<Item, Item> RECEIPT = REGISTRY.register("receipt", () -> new ReceiptItem());

	// Start of user code block custom items
	// End of user code block custom items
	public static void register(IEventBus bus) {
		REGISTRY.register(bus);
	}

	private static DeferredHolder<Item, Item> block(DeferredHolder<Block, Block> block) {
		return REGISTRY.register(block.getId().getPath(), () -> new BlockItem(block.get(), new Item.Properties()));
	}
}
