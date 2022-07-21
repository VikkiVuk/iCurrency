package xyz.vikkivuk.icurrency.procedures;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.Entity;

public class CardItemInInventoryTickProcedure {
	public static void execute(Entity entity, ItemStack itemstack) {
		if (entity == null)
			return;
		itemstack.getOrCreateTag().putString("owner_username", (entity.getDisplayName().getString()));
		itemstack.getOrCreateTag().putBoolean("credit_card", (false));
	}
}
