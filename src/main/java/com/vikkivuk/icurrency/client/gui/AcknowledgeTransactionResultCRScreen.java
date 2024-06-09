package com.vikkivuk.icurrency.client.gui;

import net.neoforged.neoforge.network.PacketDistributor;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.GuiGraphics;

import java.util.HashMap;

import com.vikkivuk.icurrency.world.inventory.AcknowledgeTransactionResultCRMenu;
import com.vikkivuk.icurrency.procedures.ShowTransactionResultProcedure;
import com.vikkivuk.icurrency.network.AcknowledgeTransactionResultCRButtonMessage;

import com.mojang.blaze3d.systems.RenderSystem;

public class AcknowledgeTransactionResultCRScreen extends AbstractContainerScreen<AcknowledgeTransactionResultCRMenu> {
	private final static HashMap<String, Object> guistate = AcknowledgeTransactionResultCRMenu.guistate;
	private final Level world;
	private final int x, y, z;
	private final Player entity;
	private final static HashMap<String, String> textstate = new HashMap<>();
	Button button_empty;

	public AcknowledgeTransactionResultCRScreen(AcknowledgeTransactionResultCRMenu container, Inventory inventory, Component text) {
		super(container, inventory, text);
		this.world = container.world;
		this.x = container.x;
		this.y = container.y;
		this.z = container.z;
		this.entity = container.entity;
		this.imageWidth = 236;
		this.imageHeight = 60;
	}

	private static final ResourceLocation texture = new ResourceLocation("icurrency:textures/screens/acknowledge_transaction_result_cr.png");

	@Override
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(guiGraphics, mouseX, mouseY, partialTicks);
		super.render(guiGraphics, mouseX, mouseY, partialTicks);
		this.renderTooltip(guiGraphics, mouseX, mouseY);
	}

	@Override
	protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int gx, int gy) {
		RenderSystem.setShaderColor(1, 1, 1, 1);
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		guiGraphics.blit(texture, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
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
	protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
		guiGraphics.drawString(this.font,

				ShowTransactionResultProcedure.execute(world, x, y, z), 14, 12, -12829636, false);
	}

	@Override
	public void init() {
		super.init();
		button_empty = Button.builder(Component.translatable("gui.icurrency.acknowledge_transaction_result_cr.button_empty"), e -> {
			if (true) {
				PacketDistributor.SERVER.noArg().send(new AcknowledgeTransactionResultCRButtonMessage(0, x, y, z, textstate));
				AcknowledgeTransactionResultCRButtonMessage.handleButtonAction(entity, 0, x, y, z, textstate);
			}
		}).bounds(this.leftPos + 15, this.topPos + 30, 208, 20).build();
		guistate.put("button:button_empty", button_empty);
		this.addRenderableWidget(button_empty);
	}
}
