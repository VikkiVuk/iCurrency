
package com.vikkivuk.icurrency.item;

import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item;

public class ReceiptItem extends Item {
	public ReceiptItem() {
		super(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON));
	}
}
