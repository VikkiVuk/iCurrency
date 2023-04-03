
package xyz.wulfco.icurrency.command;

import com.mojang.brigadier.context.CommandContext;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import xyz.wulfco.icurrency.capabilities.Wallet.WalletCapabilityProvider;

@Mod.EventBusSubscriber
public class BalanceCommand {
	@SubscribeEvent
	public static void registerCommand(RegisterCommandsEvent event) {
		event.getDispatcher().register(Commands.literal("balance").executes(BalanceCommand::execute));
	}

	private static int execute(CommandContext<CommandSourceStack> ctx) {
		Entity entity = ctx.getSource().getEntity();
		if (entity == null)
			return 0;

		if (entity instanceof Player player) {
			player.getCapability(WalletCapabilityProvider.WALLET_CAPABILITY).ifPresent((cap) -> {
				player.displayClientMessage(new TextComponent(ChatFormatting.GREEN + "Balance: " + ChatFormatting.GOLD + "$" + cap.getWalletAmount()), false);
			});
		}

		return 0;
	}
}
