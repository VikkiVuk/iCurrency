
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
import org.jetbrains.annotations.NotNull;
import xyz.wulfco.icurrency.iCurrency;
import xyz.wulfco.icurrency.network.TransferButtonMessage;
import xyz.wulfco.icurrency.world.inventory.TransferMenu;

public class TransferScreen extends AbstractContainerScreen<TransferMenu> {
	private final static HashMap<String, Object> guistate = TransferMenu.guistate;
	private final Level world;
	private final int x, y, z;
	private final Player entity;
	EditBox amount;
	EditBox player_username;

	public TransferScreen(TransferMenu container, Inventory inventory, Component text) {
		super(container, inventory, text);
		this.world = container.world;
		this.x = container.x;
		this.y = container.y;
		this.z = container.z;
		this.entity = container.entity;
		this.imageWidth = 176;
		this.imageHeight = 110;
	}

	@Override
	public boolean isPauseScreen() {
		return true;
	}

	private static final ResourceLocation texture = new ResourceLocation("icurrency:textures/screens/pay_gui.png");

	@Override
	public void render(PoseStack ms, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(ms);
		super.render(ms, mouseX, mouseY, partialTicks);
		this.renderTooltip(ms, mouseX, mouseY);
		amount.render(ms, mouseX, mouseY, partialTicks);
		player_username.render(ms, mouseX, mouseY, partialTicks);
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
			assert this.minecraft.player != null;
			this.minecraft.player.closeContainer();
			return true;
		}
		if (amount.isFocused())
			return amount.keyPressed(key, b, c);
		if (player_username.isFocused())
			return player_username.keyPressed(key, b, c);
		return super.keyPressed(key, b, c);
	}

	@Override
	public void containerTick() {
		super.containerTick();
		amount.tick();
		player_username.tick();
	}

	@Override
	protected void renderLabels(@NotNull PoseStack poseStack, int mouseX, int mouseY) {
		this.font.draw(poseStack, "Transfer Money", 7, 7, -12829636);
	}

	@Override
	public void onClose() {
		super.onClose();
		Minecraft.getInstance().keyboardHandler.setSendRepeatsToGui(false);
	}

	@Override
	public void init() {
		super.init();
		assert this.minecraft != null;
		this.minecraft.keyboardHandler.setSendRepeatsToGui(true);
		this.addRenderableWidget(new Button(this.leftPos + 56, this.topPos + 80, 60, 20, new TextComponent("Transfer"), e -> {
			iCurrency.PACKET_HANDLER.sendToServer(new TransferButtonMessage(0, x, y, z));
			TransferButtonMessage.handleButtonAction(entity, 0, x, y, z);
		}));
		amount = new EditBox(this.font, this.leftPos + 27, this.topPos + 50, 120, 20, new TextComponent("Amount to transfer")) {
			{
				setSuggestion("Amount to transfer");
			}

			@Override
			public void insertText(String text) {
				super.insertText(text);
				if (getValue().isEmpty())
					setSuggestion("Amount to transfer");
				else
					setSuggestion(null);
			}

			@Override
			public void moveCursorTo(int pos) {
				super.moveCursorTo(pos);
				if (getValue().isEmpty())
					setSuggestion("Amount to transfer");
				else
					setSuggestion(null);
			}
		};
		guistate.put("text:amount", amount);
		amount.setMaxLength(32767);
		this.addWidget(this.amount);
		player_username = new EditBox(this.font, this.leftPos + 27, this.topPos + 24, 120, 20, new TextComponent("Player Username")) {
			{
				setSuggestion("Player Username");
			}

			@Override
			public void insertText(String text) {
				super.insertText(text);
				if (getValue().isEmpty())
					setSuggestion("Player Username");
				else
					setSuggestion(null);
			}

			@Override
			public void moveCursorTo(int pos) {
				super.moveCursorTo(pos);
				if (getValue().isEmpty())
					setSuggestion("Player Username");
				else
					setSuggestion(null);
			}
		};
		guistate.put("text:player_username", player_username);
		player_username.setMaxLength(32767);
		this.addWidget(this.player_username);
	}
}
