
package xyz.wulfco.icurrency.client.gui;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.Minecraft;

import java.util.HashMap;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.systems.RenderSystem;
import org.jetbrains.annotations.NotNull;
import xyz.wulfco.icurrency.iCurrency;
import xyz.wulfco.icurrency.network.ATMButtonMessage;
import xyz.wulfco.icurrency.world.inventory.ATMMenu;

public class ATMScreen extends AbstractContainerScreen<ATMMenu> {
	private final static HashMap<String, Object> guistate = ATMMenu.guistate;
	private final Level world;
	private final int x, y, z;
	private final Player entity;

	public ATMScreen(ATMMenu container, Inventory inventory, Component text) {
		super(container, inventory, text);
		this.world = container.world;
		this.x = container.x;
		this.y = container.y;
		this.z = container.z;
		this.entity = container.entity;
		this.imageWidth = 120;
		this.imageHeight = 120;
	}

	private static final ResourceLocation texture = new ResourceLocation("icurrency:textures/screens/atm_gui.png");

	@Override
	public void render(@NotNull PoseStack ms, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(ms);
		super.render(ms, mouseX, mouseY, partialTicks);
		this.renderTooltip(ms, mouseX, mouseY);
	}

	@Override
	protected void renderBg(@NotNull PoseStack ms, float partialTicks, int gx, int gy) {
		RenderSystem.setShaderColor(1, 1, 1, 1);
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		RenderSystem.setShaderTexture(0, texture);
		blit(ms, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
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
		return super.keyPressed(key, b, c);
	}

	@Override
	public void containerTick() {
		super.containerTick();
	}

	@Override
	protected void renderLabels(@NotNull PoseStack poseStack, int mouseX, int mouseY) {
		this.font.draw(poseStack, "Welcome Customer!", 14, 14, -12829636);
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
		this.addRenderableWidget(new Button(this.leftPos + 29, this.topPos + 31, 61, 20, new TextComponent("Deposit"), e -> {
			iCurrency.PACKET_HANDLER.sendToServer(new ATMButtonMessage(0, x, y, z));
			ATMButtonMessage.handleButtonAction(entity, 0, x, y, z);
		}));
		this.addRenderableWidget(new Button(this.leftPos + 26, this.topPos + 57, 67, 20, new TextComponent("Withdraw"), e -> {
			iCurrency.PACKET_HANDLER.sendToServer(new ATMButtonMessage(1, x, y, z));
			ATMButtonMessage.handleButtonAction(entity, 1, x, y, z);
		}));
		this.addRenderableWidget(new Button(this.leftPos + 21, this.topPos + 84, 77, 20, new TextComponent("Send Money"), e -> {
			iCurrency.PACKET_HANDLER.sendToServer(new ATMButtonMessage(2, x, y, z));
			ATMButtonMessage.handleButtonAction(entity, 2, x, y, z);
		}));
	}
}
