
package xyz.vikkivuk.icurrency.item;

import xyz.vikkivuk.icurrency.procedures.OneDollarBillItemInInventoryTickProcedure;
import xyz.vikkivuk.icurrency.init.IcurrencyModTabs;

import net.minecraft.world.level.Level;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.entity.Entity;

public class FiveDollarBillItem extends Item {
	public FiveDollarBillItem() {
		super(new Item.Properties().tab(IcurrencyModTabs.TAB_I_CURRENCY_TAB).stacksTo(64).rarity(Rarity.COMMON));
	}

	@Override
	public UseAnim getUseAnimation(ItemStack itemstack) {
		return UseAnim.EAT;
	}

	@Override
	public int getUseDuration(ItemStack itemstack) {
		return 0;
	}

	@Override
	public void inventoryTick(ItemStack itemstack, Level world, Entity entity, int slot, boolean selected) {
		super.inventoryTick(itemstack, world, entity, slot, selected);
		OneDollarBillItemInInventoryTickProcedure.execute(itemstack);
	}
}
