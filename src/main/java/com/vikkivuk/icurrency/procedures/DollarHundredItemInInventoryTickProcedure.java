package com.vikkivuk.icurrency.procedures;

import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.component.DataComponents;

public class DollarHundredItemInInventoryTickProcedure {
	public static void execute(ItemStack itemstack) {
		{
			final String _tagName = "value";
			final double _tagValue = 100;
			CustomData.update(DataComponents.CUSTOM_DATA, itemstack, tag -> tag.putDouble(_tagName, _tagValue));
		}
	}
}
