package xyz.wulfco.icurrency.objects.items;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import xyz.wulfco.icurrency.iCurrency;
import xyz.wulfco.icurrency.registry.ItemRegistry;

public class DollarBill extends Item {
    public DollarBill() {
        super(new Properties().tab(iCurrency.TAB));
    }

    @Override
    public void inventoryTick(@NotNull ItemStack itemStack, @NotNull Level level, @NotNull Entity entity, int slot, boolean selected) {
        super.inventoryTick(itemStack, level, entity, slot, selected);
        if (itemStack.getOrCreateTag().get("money_value") != null) return;

        if (itemStack.getItem() == ItemRegistry.DOLLAR_TEN.get()) {
            itemStack.getOrCreateTag().putDouble("money_value", 10);
        }
        if (itemStack.getItem() == ItemRegistry.DOLLAR_TWENTY.get()) {
            itemStack.getOrCreateTag().putDouble("money_value", 20);
        }
        if (itemStack.getItem() == ItemRegistry.DOLLAR_FIFTY.get()) {
            itemStack.getOrCreateTag().putDouble("money_value", 50);
        }
        if (itemStack.getItem() == ItemRegistry.DOLLAR_HUNDRED.get()) {
            itemStack.getOrCreateTag().putDouble("money_value", 100);
        }
        if (itemStack.getItem() == ItemRegistry.DOLLAR_TWO_HUNDRED.get()) {
            itemStack.getOrCreateTag().putDouble("money_value", 200);
        }
        if (itemStack.getItem() == ItemRegistry.DOLLAR_FIVE_HUNDRED.get()) {
            itemStack.getOrCreateTag().putDouble("money_value", 500);
        }
        if (itemStack.getItem() == ItemRegistry.DOLLAR_THOUSAND.get()) {
            itemStack.getOrCreateTag().putDouble("money_value", 1000);
        }
    }
}
