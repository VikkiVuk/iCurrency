
package com.vikkivuk.mcurrency.item;

import net.minecraft.world.level.Level;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.entity.Entity;

import com.vikkivuk.mcurrency.procedures.DollarFiftyItemInInventoryTickProcedure;

public class DollarFiftyItem extends Item {
	public DollarFiftyItem() {
		super(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON));
	}

	@Override
	public void inventoryTick(ItemStack itemstack, Level world, Entity entity, int slot, boolean selected) {
		super.inventoryTick(itemstack, world, entity, slot, selected);
		DollarFiftyItemInInventoryTickProcedure.execute(itemstack);
	}
}