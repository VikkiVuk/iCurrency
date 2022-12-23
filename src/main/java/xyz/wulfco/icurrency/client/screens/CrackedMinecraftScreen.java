package xyz.wulfco.icurrency.client.screens;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.network.chat.TextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.gui.widget.ExtendedButton;
import xyz.wulfco.icurrency.util.NetworkUtils;

import javax.json.Json;
import javax.json.JsonObject;

@OnlyIn(Dist.CLIENT)
public class CrackedMinecraftScreen extends ErrorScreen {
    EditBox password;

    public CrackedMinecraftScreen() {
        super(new TextComponent(ChatFormatting.YELLOW + "Cracked Minecraft!"), new TextComponent("Please login or register to use iCurrency."), null);
    }

    @Override
    public void init()
    {
        super.init();
        this.clearWidgets();
        int yOffset = 46;

        int editBoxWidth = 200;
        int editBoxHeight = 20;
        int editBoxX = (this.width - editBoxWidth) / 2;
        int editBoxY = (this.height - editBoxHeight) / 2 + yOffset - 20;
        password = new EditBox(this.font, editBoxX, editBoxY, editBoxWidth, editBoxHeight, new TextComponent("Password")) {
            {
                setSuggestion("Enter a password");
            }

            @Override
            public void insertText(String text) {
                super.insertText(text);
                if (getValue().isEmpty())
                    setSuggestion("Enter a password");
                else
                    setSuggestion(null);
            }

            @Override
            public void moveCursorTo(int pos) {
                super.moveCursorTo(pos);
                if (getValue().isEmpty())
                    setSuggestion("Enter a password");
                else
                    setSuggestion(null);
            }
        };

        password.setMaxLength(20);

        this.addWidget(this.password);

        this.addRenderableWidget(new ExtendedButton(50, this.height - yOffset, this.width / 2 - 55, 20, new TextComponent("Submit"), b -> {
            final JsonObject json = Json.createObjectBuilder()
                    .add("username", Minecraft.getInstance().getUser().getName())
                    .add("password", password.getValue())
                    .build();

            final JsonObject response = NetworkUtils.post("https://icurrency.wulfco.xyz/login/cracked", json);
            assert response != null;
            if (response.getString("status").equals("ok")) {
                Minecraft.getInstance().setScreen(new TitleScreen());
            } else {
                Minecraft.getInstance().setScreen(new ErrorScreen(new TextComponent(ChatFormatting.RED + "Error"), new TextComponent(response.getString("message")), b1 -> Minecraft.getInstance().setScreen(new CrackedMinecraftScreen())));
            }
        }));
        this.addRenderableWidget(new ExtendedButton(this.width / 2 + 5, this.height - yOffset, this.width / 2 - 55, 20, new TextComponent("Exit"), b -> {
            assert this.minecraft != null;
            this.minecraft.setScreen(new TitleScreen());
        }));
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTick)
    {
        this.renderBackground(poseStack);
        super.render(poseStack, mouseX, mouseY, partialTick);
        this.renderables.forEach(button -> button.render(poseStack, mouseX, mouseY, partialTick));
        password.render(poseStack, mouseX, mouseY, partialTick);
    }

    @Override
    public void onClose() {
        super.onClose();
        Minecraft.getInstance().keyboardHandler.setSendRepeatsToGui(false);
    }
}
