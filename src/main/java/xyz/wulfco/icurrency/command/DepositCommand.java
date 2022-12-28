
package xyz.wulfco.icurrency.command;

import io.netty.buffer.Unpooled;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraftforge.network.NetworkHooks;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.RegisterCommandsEvent;

import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.commands.Commands;
import net.minecraft.commands.CommandSourceStack;

import com.mojang.brigadier.context.CommandContext;
import org.jetbrains.annotations.NotNull;
import xyz.wulfco.icurrency.world.inventory.DepositMenu;

@Mod.EventBusSubscriber
public class DepositCommand {
	@SubscribeEvent
	public static void registerCommand(RegisterCommandsEvent event) {
		event.getDispatcher().register(Commands.literal("deposit").executes(DepositCommand::execute));
	}

	private static int execute(CommandContext<CommandSourceStack> ctx) {
		ServerLevel world = ctx.getSource().getLevel();
		double x = ctx.getSource().getPosition().x();
		double y = ctx.getSource().getPosition().y();
		double z = ctx.getSource().getPosition().z();
		Entity entity = ctx.getSource().getEntity();

		if (entity == null)
			return 0;

		if (entity instanceof ServerPlayer _ent) {
			BlockPos bpos = new BlockPos(x, y, z);
			NetworkHooks.openGui(_ent, new MenuProvider() {
				@Override
				public @NotNull Component getDisplayName() {
					return new TextComponent("Deposit");
				}

				@Override
				public AbstractContainerMenu createMenu(int id, @NotNull Inventory inventory, @NotNull Player player) {
					return new DepositMenu(id, inventory, new FriendlyByteBuf(Unpooled.buffer()).writeBlockPos(bpos));
				}
			}, bpos);
		}

		return 0;
	}
}