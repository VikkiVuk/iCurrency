
package xyz.wulfco.icurrency.client.gui;

import io.netty.buffer.Unpooled;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.inventory.AbstractContainerMenu;
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
import java.util.concurrent.atomic.AtomicReference;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import xyz.wulfco.icurrency.capabilities.Wallet.WalletCapabilityProvider;
import xyz.wulfco.icurrency.world.inventory.ATMMenu;
import xyz.wulfco.icurrency.world.inventory.DepositMenu;
import xyz.wulfco.icurrency.world.inventory.TransferMenu;
import xyz.wulfco.icurrency.world.inventory.WithdrawMenu;

public class ATMScreen extends AbstractContainerScreen<ATMMenu> {
	private final static HashMap<String, Object> guistate = ATMMenu.guistate;
	private final Level world;
	private final int x, y, z;
	private final Player entity;
	Button button_deposit;
	Button button_withdraw;
	Button button_send_money;

	public ATMScreen(ATMMenu container, Inventory inventory, Component text) {
		super(container, inventory, text);
		this.world = container.world;
		this.x = container.x;
		this.y = container.y;
		this.z = container.z;
		this.entity = container.entity;
		this.imageWidth = 150;
		this.imageHeight = 145;
	}

	private static final ResourceLocation texture = new ResourceLocation("icurrency:textures/screens/atm_gui.png");

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
		entity.reviveCaps();
		entity.getCapability(WalletCapabilityProvider.WALLET_CAPABILITY).ifPresent(cap -> {
			this.font.draw(poseStack, "Balance: $".concat(String.valueOf(cap.getWalletAmount())), 11, 126, -12829636);
		});
		entity.invalidateCaps();

		this.font.draw(poseStack, "Welcome, ".concat(entity.getDisplayName().getString()), 43, 14, -12829636);
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

		button_deposit = new Button(this.leftPos + 35, this.topPos + 30, 80, 20, new TextComponent("Deposit"), e -> {
			if (entity != null) {
				entity.openMenu(new MenuProvider() {
					@Override
					public @NotNull Component getDisplayName() {
						return new TextComponent("Deposit");
					}

					@Override
					public AbstractContainerMenu createMenu(int id, @NotNull Inventory inventory, @NotNull Player player) {
						return new DepositMenu(id, inventory, new FriendlyByteBuf(Unpooled.buffer()).writeBlockPos(new BlockPos(x, y, z)));
					}
				});
			}
		});
		this.addRenderableWidget(button_deposit);

		button_withdraw = new Button(this.leftPos + 35, this.topPos + 60, 80, 20, new TextComponent("Withdraw"), e -> {
			if (entity != null) {
				entity.openMenu(new MenuProvider() {
					@Override
					public @NotNull Component getDisplayName() {
						return new TextComponent("Withdraw");
					}

					@Override
					public AbstractContainerMenu createMenu(int id, @NotNull Inventory inventory, @NotNull Player player) {
						return new WithdrawMenu(id, inventory, new FriendlyByteBuf(Unpooled.buffer()).writeBlockPos(new BlockPos(x, y, z)));
					}
				});
			}
		});
		this.addRenderableWidget(button_withdraw);

		button_send_money = new Button(this.leftPos + 35, this.topPos + 90, 80, 20, new TextComponent("Send Money"), e -> {
			if (entity != null) {
				entity.openMenu(new MenuProvider() {
					@Override
					public @NotNull Component getDisplayName() {
						return new TextComponent("Transfer");
					}

					@Override
					public AbstractContainerMenu createMenu(int id, @NotNull Inventory inventory, @NotNull Player player) {
						return new TransferMenu(id, inventory, new FriendlyByteBuf(Unpooled.buffer()).writeBlockPos(new BlockPos(x, y, z)));
					}
				});
			}
		});
		this.addRenderableWidget(button_send_money);
	}
}
