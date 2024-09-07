
package com.vikkivuk.mcurrency.item;

import net.minecraft.world.level.Level;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.entity.Entity;

import com.vikkivuk.mcurrency.procedures.DollarHundredItemInInventoryTickProcedure;

public class DollarHundredItem extends Item {
	public DollarHundredItem() {
		super(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON));
	}

	@Override
	public void inventoryTick(ItemStack itemstack, Level world, Entity entity, int slot, boolean selected) {
		super.inventoryTick(itemstack, world, entity, slot, selected);
		DollarHundredItemInInventoryTickProcedure.execute(itemstack);
	}
}
