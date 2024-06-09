package com.vikkivuk.icurrency.procedures;

import net.minecraft.world.item.ItemStack;

public class DollarTwentyItemInInventoryTickProcedure {
	public static void execute(ItemStack itemstack) {
		itemstack.getOrCreateTag().putDouble("value", 20);
	}
}
