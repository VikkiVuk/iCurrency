package com.vikkivuk.icurrency.procedures;

import net.minecraft.world.item.ItemStack;

public class DollarThousandItemInInventoryTickProcedure {
	public static void execute(ItemStack itemstack) {
		itemstack.getOrCreateTag().putDouble("value", 1000);
	}
}
