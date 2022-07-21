
package xyz.vikkivuk.icurrency.client.gui;

import xyz.vikkivuk.icurrency.world.inventory.AtmGuiMenu;
import xyz.vikkivuk.icurrency.network.AtmGuiButtonMessage;
import xyz.vikkivuk.icurrency.IcurrencyMod;

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

public class AtmGuiScreen extends AbstractContainerScreen<AtmGuiMenu> {
	private final static HashMap<String, Object> guistate = AtmGuiMenu.guistate;
	private final Level world;
	private final int x, y, z;
	private final Player entity;

	public AtmGuiScreen(AtmGuiMenu container, Inventory inventory, Component text) {
		super(container, inventory, text);
		this.world = container.world;
		this.x = container.x;
		this.y = container.y;
		this.z = container.z;
		this.entity = container.entity;
		this.imageWidth = 120;
		this.imageHeight = 120;
	}

	private static final ResourceLocation texture = new ResourceLocation("icurrency:textures/atm_gui.png");

	@Override
	public void render(PoseStack ms, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(ms);
		super.render(ms, mouseX, mouseY, partialTicks);
		this.renderTooltip(ms, mouseX, mouseY);
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
	protected void renderLabels(PoseStack poseStack, int mouseX, int mouseY) {
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
		this.minecraft.keyboardHandler.setSendRepeatsToGui(true);
		this.addRenderableWidget(new Button(this.leftPos + 29, this.topPos + 31, 61, 20, new TextComponent("Deposit"), e -> {
			if (true) {
				IcurrencyMod.PACKET_HANDLER.sendToServer(new AtmGuiButtonMessage(0, x, y, z));
				AtmGuiButtonMessage.handleButtonAction(entity, 0, x, y, z);
			}
		}));
		this.addRenderableWidget(new Button(this.leftPos + 26, this.topPos + 57, 67, 20, new TextComponent("Withdraw"), e -> {
			if (true) {
				IcurrencyMod.PACKET_HANDLER.sendToServer(new AtmGuiButtonMessage(1, x, y, z));
				AtmGuiButtonMessage.handleButtonAction(entity, 1, x, y, z);
			}
		}));
		this.addRenderableWidget(new Button(this.leftPos + 21, this.topPos + 84, 77, 20, new TextComponent("Send Money"), e -> {
			if (true) {
				IcurrencyMod.PACKET_HANDLER.sendToServer(new AtmGuiButtonMessage(2, x, y, z));
				AtmGuiButtonMessage.handleButtonAction(entity, 2, x, y, z);
			}
		}));
	}
}
