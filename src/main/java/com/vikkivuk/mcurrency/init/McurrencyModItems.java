
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package com.vikkivuk.mcurrency.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.BlockItem;
import net.minecraft.core.registries.BuiltInRegistries;

import com.vikkivuk.mcurrency.item.ReceiptItem;
import com.vikkivuk.mcurrency.item.DollarTwoHundredItem;
import com.vikkivuk.mcurrency.item.DollarTwentyItem;
import com.vikkivuk.mcurrency.item.DollarThousandItem;
import com.vikkivuk.mcurrency.item.DollarTenItem;
import com.vikkivuk.mcurrency.item.DollarHundredItem;
import com.vikkivuk.mcurrency.item.DollarFiveHundredItem;
import com.vikkivuk.mcurrency.item.DollarFiftyItem;
import com.vikkivuk.mcurrency.item.DebitCardItem;
import com.vikkivuk.mcurrency.McurrencyMod;

public class McurrencyModItems {
	public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(BuiltInRegistries.ITEM, McurrencyMod.MODID);
	public static final DeferredHolder<Item, Item> DEBIT_CARD = REGISTRY.register("debit_card", DebitCardItem::new);
	public static final DeferredHolder<Item, Item> DOLLAR_TEN = REGISTRY.register("dollar_ten", DollarTenItem::new);
	public static final DeferredHolder<Item, Item> DOLLAR_TWENTY = REGISTRY.register("dollar_twenty", DollarTwentyItem::new);
	public static final DeferredHolder<Item, Item> DOLLAR_FIFTY = REGISTRY.register("dollar_fifty", DollarFiftyItem::new);
	public static final DeferredHolder<Item, Item> DOLLAR_HUNDRED = REGISTRY.register("dollar_hundred", DollarHundredItem::new);
	public static final DeferredHolder<Item, Item> DOLLAR_TWO_HUNDRED = REGISTRY.register("dollar_two_hundred", DollarTwoHundredItem::new);
	public static final DeferredHolder<Item, Item> DOLLAR_FIVE_HUNDRED = REGISTRY.register("dollar_five_hundred", DollarFiveHundredItem::new);
	public static final DeferredHolder<Item, Item> DOLLAR_THOUSAND = REGISTRY.register("dollar_thousand", DollarThousandItem::new);
	public static final DeferredHolder<Item, Item> ATM = block(McurrencyModBlocks.ATM);
	public static final DeferredHolder<Item, Item> CASH_REGISTER = block(McurrencyModBlocks.CASH_REGISTER);
	public static final DeferredHolder<Item, Item> RECEIPT = REGISTRY.register("receipt", ReceiptItem::new);
	public static final DeferredHolder<Item, Item> ACM = block(McurrencyModBlocks.ACM);

	// Start of user code block custom items
	// End of user code block custom items
	private static DeferredHolder<Item, Item> block(DeferredHolder<Block, Block> block) {
		return REGISTRY.register(block.getId().getPath(), () -> new BlockItem(block.get(), new Item.Properties()));
	}
}
