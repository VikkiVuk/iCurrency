
package xyz.wulfco.icurrency.client.gui;

import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.Minecraft;

import java.util.HashMap;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.systems.RenderSystem;
import xyz.wulfco.icurrency.iCurrency;
import xyz.wulfco.icurrency.network.ATMPackets.WithdrawButtonMessage;
import xyz.wulfco.icurrency.world.inventory.WithdrawMenu;

public class WithdrawScreen extends AbstractContainerScreen<WithdrawMenu> {
	private final static HashMap<String, Object> guistate = WithdrawMenu.guistate;
	private final Level world;
	private final int x, y, z;
	private final Player entity;
	EditBox amount;

	public WithdrawScreen(WithdrawMenu container, Inventory inventory, Component text) {
		super(container, inventory, text);
		this.world = container.world;
		this.x = container.x;
		this.y = container.y;
		this.z = container.z;
		this.entity = container.entity;
		this.imageWidth = 176;
		this.imageHeight = 166;
	}

	private static final ResourceLocation texture = new ResourceLocation("icurrency:textures/screens/withdraw_gui.png");

	@Override
	public void render(PoseStack ms, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(ms);
		super.render(ms, mouseX, mouseY, partialTicks);
		this.renderTooltip(ms, mouseX, mouseY);
		amount.render(ms, mouseX, mouseY, partialTicks);
	}

	@Override
	protected void renderBg(PoseStack ms, float partialTicks, int gx, int gy) {
		RenderSystem.setShaderColor(1, 1, 1, 1);
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		RenderSystem.setShaderTexture(0, texture);
		this.blit(ms, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
		RenderSystem.disableBlend();
	}

	@Override
	public boolean keyPressed(int key, int b, int c) {
		if (key == 256) {
			assert this.minecraft != null;
			this.minecraft.player.closeContainer();
			return true;
		}
		if (amount.isFocused())
			return amount.keyPressed(key, b, c);
		return super.keyPressed(key, b, c);
	}

	@Override
	public void containerTick() {
		super.containerTick();
		amount.tick();
	}

	@Override
	protected void renderLabels(PoseStack poseStack, int mouseX, int mouseY) {
		this.font.draw(poseStack, "Withdraw", 6, 8, -12829636);
	}

	@Override
	public void onClose() {
		super.onClose();
		Minecraft.getInstance().keyboardHandler.setSendRepeatsToGui(false);
	}

	@Override
	public void init() {
		super.init();
		this.minecraft.keyboardHandler.setSendRepeatsToGui(true);
		amount = new EditBox(this.font, this.leftPos + 26, this.topPos + 26, 120, 20, new TextComponent("Amount to Withdraw")) {
			{
				setSuggestion("Amount to Withdraw");
			}

			@Override
			public void insertText(String text) {
				super.insertText(text);
				if (getValue().isEmpty())
					setSuggestion("Amount to Withdraw");
				else
					setSuggestion(null);
			}

			@Override
			public void moveCursorTo(int pos) {
				super.moveCursorTo(pos);
				if (getValue().isEmpty())
					setSuggestion("Amount to Withdraw");
				else
					setSuggestion(null);
			}
		};
		guistate.put("text:amount", amount);
		amount.setMaxLength(32767);
		this.addWidget(this.amount);
		this.addRenderableWidget(new Button(this.leftPos + 52, this.topPos + 52, 67, 20, new TextComponent("Withdraw"), e -> {
			iCurrency.PACKET_HANDLER.sendToServer(new WithdrawButtonMessage(0, x, y, z));
			WithdrawButtonMessage.handleButtonAction(entity, 0, x, y, z);
		}));
	}
}
