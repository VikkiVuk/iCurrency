package com.vikkivuk.mcurrency.client.gui;

import net.neoforged.neoforge.network.PacketDistributor;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.Checkbox;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.Minecraft;

import java.util.HashMap;

import com.vikkivuk.mcurrency.world.inventory.ATMSetupMenu;
import com.vikkivuk.mcurrency.network.ATMSetupButtonMessage;

import com.mojang.blaze3d.systems.RenderSystem;

public class ATMSetupScreen extends AbstractContainerScreen<ATMSetupMenu> {
	private final static HashMap<String, Object> guistate = ATMSetupMenu.guistate;
	private final Level world;
	private final int x, y, z;
	private final Player entity;
	EditBox bank_name;
	EditBox bank_owner;
	Checkbox tos;
	Button button_empty;

	public ATMSetupScreen(ATMSetupMenu container, Inventory inventory, Component text) {
		super(container, inventory, text);
		this.world = container.world;
		this.x = container.x;
		this.y = container.y;
		this.z = container.z;
		this.entity = container.entity;
		this.imageWidth = 330;
		this.imageHeight = 166;
	}

	public static HashMap<String, String> getEditBoxAndCheckBoxValues() {
		HashMap<String, String> textstate = new HashMap<>();
		if (Minecraft.getInstance().screen instanceof ATMSetupScreen sc) {
			textstate.put("textin:bank_name", sc.bank_name.getValue());
			textstate.put("textin:bank_owner", sc.bank_owner.getValue());

			textstate.put("checkboxin:tos", sc.tos.selected() ? "true" : "false");
		}
		return textstate;
	}

	private static final ResourceLocation texture = new ResourceLocation("mcurrency:textures/screens/atm_setup.png");

	@Override
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(guiGraphics, mouseX, mouseY, partialTicks);
		super.render(guiGraphics, mouseX, mouseY, partialTicks);
		bank_name.render(guiGraphics, mouseX, mouseY, partialTicks);
		bank_owner.render(guiGraphics, mouseX, mouseY, partialTicks);
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
		if (bank_name.isFocused())
			return bank_name.keyPressed(key, b, c);
		if (bank_owner.isFocused())
			return bank_owner.keyPressed(key, b, c);
		return super.keyPressed(key, b, c);
	}

	@Override
	public void resize(Minecraft minecraft, int width, int height) {
		String bank_nameValue = bank_name.getValue();
		String bank_ownerValue = bank_owner.getValue();
		super.resize(minecraft, width, height);
		bank_name.setValue(bank_nameValue);
		bank_owner.setValue(bank_ownerValue);
	}

	@Override
	protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
		guiGraphics.drawString(this.font, Component.translatable("gui.mcurrency.atm_setup.label_hi_thank_you_for_partnering_wit"), 21, 16, -12829636, false);
		guiGraphics.drawString(this.font, Component.translatable("gui.mcurrency.atm_setup.label_financial_features_to_your_custo"), 20, 25, -12829636, false);
		guiGraphics.drawString(this.font, Component.translatable("gui.mcurrency.atm_setup.label_to_start_using_this_atm_you_nee"), 20, 34, -12829636, false);
		guiGraphics.drawString(this.font, Component.translatable("gui.mcurrency.atm_setup.label_fill_out_the_fields_below"), 20, 43, -12829636, false);
		guiGraphics.drawString(this.font, Component.translatable("gui.mcurrency.atm_setup.label_bank_name"), 56, 61, -12829636, false);
		guiGraphics.drawString(this.font, Component.translatable("gui.mcurrency.atm_setup.label_bank_owner"), 223, 61, -12829636, false);
	}

	@Override
	public void init() {
		super.init();
		bank_name = new EditBox(this.font, this.leftPos + 21, this.topPos + 71, 118, 18, Component.translatable("gui.mcurrency.atm_setup.bank_name"));
		bank_name.setMaxLength(32767);
		guistate.put("text:bank_name", bank_name);
		this.addWidget(this.bank_name);
		bank_owner = new EditBox(this.font, this.leftPos + 189, this.topPos + 71, 118, 18, Component.translatable("gui.mcurrency.atm_setup.bank_owner"));
		bank_owner.setMaxLength(32767);
		guistate.put("text:bank_owner", bank_owner);
		this.addWidget(this.bank_owner);
		button_empty = Button.builder(Component.translatable("gui.mcurrency.atm_setup.button_empty"), e -> {
			if (true) {
				PacketDistributor.sendToServer(new ATMSetupButtonMessage(0, x, y, z, getEditBoxAndCheckBoxValues()));
				ATMSetupButtonMessage.handleButtonAction(entity, 0, x, y, z, getEditBoxAndCheckBoxValues());
			}
		}).bounds(this.leftPos + 41, this.topPos + 127, 245, 20).build();
		guistate.put("button:button_empty", button_empty);
		this.addRenderableWidget(button_empty);
		tos = Checkbox.builder(Component.translatable("gui.mcurrency.atm_setup.tos"), this.font).pos(this.leftPos + 55, this.topPos + 97)

				.build();
		guistate.put("checkbox:tos", tos);
		this.addRenderableWidget(tos);
	}
}
