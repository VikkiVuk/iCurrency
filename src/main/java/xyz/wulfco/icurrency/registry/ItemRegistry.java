package xyz.wulfco.icurrency.registry;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import xyz.wulfco.icurrency.iCurrency;
import xyz.wulfco.icurrency.objects.items.Card;
import xyz.wulfco.icurrency.objects.items.DollarBill;

public class ItemRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, iCurrency.MOD_ID);

    public static final RegistryObject<Item> CARD = ITEMS.register("card", Card::new);

    // Dollars
    public static final RegistryObject<Item> DOLLAR_TEN = ITEMS.register("dollar_ten", DollarBill::new);
    public static final RegistryObject<Item> DOLLAR_TWENTY = ITEMS.register("dollar_twenty", DollarBill::new);
    public static final RegistryObject<Item> DOLLAR_FIFTY = ITEMS.register("dollar_fifty", DollarBill::new);
    public static final RegistryObject<Item> DOLLAR_HUNDRED = ITEMS.register("dollar_hundred", DollarBill::new);
    public static final RegistryObject<Item> DOLLAR_TWO_HUNDRED = ITEMS.register("dollar_two_hundred", DollarBill::new);
    public static final RegistryObject<Item> DOLLAR_FIVE_HUNDRED = ITEMS.register("dollar_five_hundred", DollarBill::new);
    public static final RegistryObject<Item> DOLLAR_THOUSAND = ITEMS.register("dollar_thousand", DollarBill::new);
}
