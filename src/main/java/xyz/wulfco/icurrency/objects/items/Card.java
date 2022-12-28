package xyz.wulfco.icurrency.objects.items;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import xyz.wulfco.icurrency.iCurrency;
import xyz.wulfco.icurrency.util.FileHandler;
import xyz.wulfco.icurrency.util.NetworkHandler;

import javax.json.Json;
import javax.json.JsonObject;
import java.util.List;
import java.util.Objects;

public class Card extends Item {
    public Card() {
        super(new Properties().tab(iCurrency.TAB).stacksTo(1));
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemstack, Level world, @NotNull List<Component> list, @NotNull TooltipFlag flag) {
        super.appendHoverText(itemstack, world, list, flag);

        if (itemstack.getOrCreateTag().get("owner_name") == null) {
            list.add(new TextComponent("Use " + ChatFormatting.GOLD + "Shift + RMB" + ChatFormatting.WHITE + " to set owner"));
        } else {
            list.add(new TextComponent("This card belongs to " + ChatFormatting.GREEN + itemstack.getOrCreateTag().getString("owner_name")));
        }
    }

    boolean used = false;

    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {
        if (used) return InteractionResult.PASS;

        if (Objects.requireNonNull(context.getPlayer()).isShiftKeyDown()) {
            if (context.getItemInHand().getOrCreateTag().get("owner_icid") != null) {
                Objects.requireNonNull(context.getPlayer()).displayClientMessage(new TextComponent(ChatFormatting.RED + "You cannot change the owner of a card!"), true);

                return InteractionResult.SUCCESS;
            }

            used = true;

            final JsonObject session = NetworkHandler.decodeJson(FileHandler.read("_ic-session.json"));
            if (session == null) { return InteractionResult.FAIL; }
            final JsonObject cardResponse;
            if (iCurrency.cracked) {
                cardResponse = NetworkHandler.post("https://icurrency.wulfco.xyz/card/cracked", Json.createObjectBuilder().add("icid", session.getString("icid")).add("session", session.getString("session")).build());
            } else {
                cardResponse = NetworkHandler.post("https://icurrency.wulfco.xyz/card/premium", Json.createObjectBuilder().add("icid", session.getString("icid")).add("session", session.getString("session")).build());
            }

            if (cardResponse == null) { return InteractionResult.FAIL; }
            if (cardResponse.getString("status").equals("fail")) {
                Objects.requireNonNull(context.getPlayer()).displayClientMessage(new TextComponent(ChatFormatting.RED + cardResponse.getString("message")), true);
            } else {
                context.getItemInHand().getOrCreateTag().putString("owner_name", context.getPlayer().getName().getString());
                context.getItemInHand().getOrCreateTag().putString("owner_icid", session.getString("icid"));
                context.getItemInHand().getOrCreateTag().putString("card_id", cardResponse.getString("card_id"));
                context.getItemInHand().getOrCreateTag().putString("card_cvc", cardResponse.getString("card_cvc"));

                context.getPlayer().displayClientMessage(new TextComponent("Card owner set to " + ChatFormatting.GREEN + context.getPlayer().getName().getString() + ChatFormatting.WHITE + ". Your CVC is " + ChatFormatting.GREEN + cardResponse.getString("card_cvc")), false);
            }
            used = false;
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }

    public static String getOwner(ItemStack itemStack) {
        if (itemStack.getOrCreateTag().get("owner_icid") == null) {
            return null;
        } else {
            return itemStack.getOrCreateTag().getString("owner_icid");
        }
    }

    public static String getCardId(ItemStack itemStack) {
        if (itemStack.getOrCreateTag().get("card_id") == null) {
            return null;
        } else {
            return itemStack.getOrCreateTag().getString("card_id");
        }
    }

    public static String getCardCvc(ItemStack itemStack) {
        if (itemStack.getOrCreateTag().get("card_cvc") == null) {
            return null;
        } else {
            return itemStack.getOrCreateTag().getString("card_cvc");
        }
    }

    public static boolean checkCVC(ItemStack itemStack, String cvc) {
        if (itemStack.getOrCreateTag().get("card_cvc") == null) {
            return false;
        } else {
            return itemStack.getOrCreateTag().getString("card_cvc").equals(cvc);
        }
    }
}
