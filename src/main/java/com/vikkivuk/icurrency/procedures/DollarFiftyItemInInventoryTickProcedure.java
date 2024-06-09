package com.vikkivuk.icurrency.procedures;

import net.minecraft.world.item.ItemStack;

public class DollarFiftyItemInInventoryTickProcedure {
	public static void execute(ItemStack itemstack) {
		itemstack.getOrCreateTag().putDouble("value", 50);
	}
}
