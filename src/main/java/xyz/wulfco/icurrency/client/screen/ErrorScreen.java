package xyz.wulfco.icurrency.client.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;

public class ErrorScreen extends Screen {
    private final Component message;
    private Button.OnPress backAction = button -> {
        assert this.minecraft != null;
        this.minecraft.setScreen(new TitleScreen());
    };

    public ErrorScreen(Component title, Component message, Button.OnPress backAction) {
        super(title);
        this.message = message;
        if (backAction != null) {
            this.backAction = backAction;
        }
    }

    protected void init() {
        super.init();

        this.addRenderableWidget(new Button(this.width / 2 - 100, 140, 200, 20, new TextComponent("Continue"), this.backAction));
    }

    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(poseStack);
        drawCenteredString(poseStack, this.font, this.title, this.width / 2, 90, 16777215);
        drawCenteredString(poseStack, this.font, this.message, this.width / 2, 110, 16777215);
        super.render(poseStack, mouseX, mouseY, partialTick);
    }

    public boolean shouldCloseOnEsc() {
        return false;
    }
}
