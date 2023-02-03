package xyz.wulfco.icurrency.client.screen;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TextComponent;

public class UpdateRequiredScreen extends ErrorScreen {
    public UpdateRequiredScreen() {
        super(new TextComponent(ChatFormatting.RED + "iCurrency is outdated!"), new TextComponent("You need to update to continue using iCurrency!"), b1 -> new UpdateRequiredScreen());
    }
}
