package xyz.vikkivuk.icurrency.procedures;

import xyz.vikkivuk.icurrency.init.IcurrencyModItems;

import net.minecraft.world.item.ItemStack;

public class OneDollarBillItemInInventoryTickProcedure {
	public static void execute(ItemStack itemstack) {
		if (itemstack.getItem() == IcurrencyModItems.ONE_DOLLAR_BILL.get()) {
			itemstack.getOrCreateTag().putDouble("value", 1);
		}
		if (itemstack.getItem() == IcurrencyModItems.FIVE_DOLLAR_BILL.get()) {
			itemstack.getOrCreateTag().putDouble("value", 5);
		}
		if (itemstack.getItem() == IcurrencyModItems.DOLLAR_TEN.get()) {
			itemstack.getOrCreateTag().putDouble("value", 10);
		}
		if (itemstack.getItem() == IcurrencyModItems.TWENTY_DOLLAR_BILL.get()) {
			itemstack.getOrCreateTag().putDouble("value", 20);
		}
		if (itemstack.getItem() == IcurrencyModItems.FIFTY_DOLLAR_BILL.get()) {
			itemstack.getOrCreateTag().putDouble("value", 50);
		}
		if (itemstack.getItem() == IcurrencyModItems.HUNDRED_DOLLAR_BILL.get()) {
			itemstack.getOrCreateTag().putDouble("value", 100);
		}
	}
}
