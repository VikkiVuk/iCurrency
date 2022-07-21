
package xyz.vikkivuk.icurrency.client.gui;

import xyz.vikkivuk.icurrency.world.inventory.SetupGuiMenu;
import xyz.vikkivuk.icurrency.network.SetupGuiButtonMessage;
import xyz.vikkivuk.icurrency.IcurrencyMod;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.Checkbox;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.Minecraft;

import java.util.HashMap;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.systems.RenderSystem;

public class SetupGuiScreen extends AbstractContainerScreen<SetupGuiMenu> {
	private final static HashMap<String, Object> guistate = SetupGuiMenu.guistate;
	private final Level world;
	private final int x, y, z;
	private final Player entity;
	EditBox password;
	Checkbox allow_requests;
	Checkbox accept_risks;

	public SetupGuiScreen(SetupGuiMenu container, Inventory inventory, Component text) {
		super(container, inventory, text);
		this.world = container.world;
		this.x = container.x;
		this.y = container.y;
		this.z = container.z;
		this.entity = container.entity;
		this.imageWidth = 250;
		this.imageHeight = 150;
	}

	@Override
	public boolean isPauseScreen() {
		return true;
	}

	private static final ResourceLocation texture = new ResourceLocation("icurrency:textures/setup_gui.png");

	@Override
	public void render(PoseStack ms, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(ms);
		super.render(ms, mouseX, mouseY, partialTicks);
		this.renderTooltip(ms, mouseX, mouseY);
		password.render(ms, mouseX, mouseY, partialTicks);
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
		if (password.isFocused())
			return password.keyPressed(key, b, c);
		return super.keyPressed(key, b, c);
	}

	@Override
	public void containerTick() {
		super.containerTick();
		password.tick();
	}

	@Override
	protected void renderLabels(PoseStack poseStack, int mouseX, int mouseY) {
		this.font.draw(poseStack, "Setup iCurrency", 10, 10, -12829636);
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
		password = new EditBox(this.font, this.leftPos + 65, this.topPos + 31, 120, 20, new TextComponent("Enter a password")) {
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
		guistate.put("text:password", password);
		password.setMaxLength(32767);
		this.addWidget(this.password);
		allow_requests = new Checkbox(this.leftPos + 39, this.topPos + 56, 150, 20, new TextComponent("Allow HTTP Requests to API"), false);
		guistate.put("checkbox:allow_requests", allow_requests);
		this.addRenderableWidget(allow_requests);
		accept_risks = new Checkbox(this.leftPos + 27, this.topPos + 86, 150, 20, new TextComponent("I read the risks on the mod page"), false);
		guistate.put("checkbox:accept_risks", accept_risks);
		this.addRenderableWidget(accept_risks);
		this.addRenderableWidget(new Button(this.leftPos + 97, this.topPos + 116, 56, 20, new TextComponent("Submit"), e -> {
			if (true) {
				IcurrencyMod.PACKET_HANDLER.sendToServer(new SetupGuiButtonMessage(0, x, y, z));
				SetupGuiButtonMessage.handleButtonAction(entity, 0, x, y, z);
			}
		}));
	}
}
