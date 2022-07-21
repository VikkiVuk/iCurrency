package xyz.vikkivuk.icurrency.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.client.server.IntegratedServer;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.Minecraft;

public class ServerinfoCommandExecutedProcedure {
	public static void execute(LevelAccessor world, Entity entity) {
		if (entity == null)
			return;
		if (world.isClientSide()) {
			if (entity instanceof Player _player && !_player.level.isClientSide())
				_player.displayClientMessage(new TextComponent(("Is Singleplayer: " + (new Object() {
					public Boolean getAddress() {
						try {
							final IntegratedServer server = Minecraft.getInstance().getSingleplayerServer();
							if (!server.equals(null)) {
								return true;
							} else {
								return false;
							}
						} catch (NullPointerException e) {
							return false;
						}
					}
				}.getAddress()))), (false));
			if (entity instanceof Player _player && !_player.level.isClientSide())
				_player.displayClientMessage(new TextComponent(("Server IP: " + (new Object() {
					public String getAddress() {
						try {
							final ServerData server = Minecraft.getInstance().getCurrentServer();
							if (!server.equals(null)) {
								return server.ip.toString();
							} else {
								return "";
							}
						} catch (NullPointerException e) {
							return "";
						}
					}
				}.getAddress()))), (false));
		} else {
			if (entity instanceof Player _player && !_player.level.isClientSide())
				_player.displayClientMessage(new TextComponent("Command can only be ran on client side!"), (false));
		}
	}
}
