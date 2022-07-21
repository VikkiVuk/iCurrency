package xyz.vikkivuk.icurrency.procedures;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.client.server.IntegratedServer;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.Minecraft;

import javax.annotation.Nullable;

import java.util.Map;
import java.util.HashMap;
@SubscribeEvent public void joinedServer(ClientPlayerNetworkEvent.LoggedInEvent event) {
PlayerEntity entity = event.getPlayer();
double i=entity.getPosX();
double j=entity.getPosY();
double k=entity.getPosZ();
World world=entity.world;
Map<String, Object> dependencies = new HashMap<>();
dependencies.put("x",i);
dependencies.put("y",j);
dependencies.put("z",k);
dependencies.put("world",world);
dependencies.put("entity",entity);
this.executeProcedure(dependencies);
}
public static void execute(
Entity entity
) {
execute(null,entity);
}
private static void execute(
@Nullable Event event,
Entity entity
) {
if(
entity == null
) return ;
if (entity instanceof Player _player && !_player.level.isClientSide())
_player.displayClientMessage(new TextComponent(("Server IP: "+(new Object(){
public String getAddress(){
try {
final ServerData server = Minecraft.getInstance().getCurrentServer();
if (!server.equals(null)) {
return server.ip.toString();
} else {
return "";
}
} catch(Exception e) {
return "";
}
}
}.getAddress()))), (false));if (entity instanceof Player _player && !_player.level.isClientSide())
_player.displayClientMessage(new TextComponent(("Singleplayer: "+(new Object(){
public Boolean getAddress(){
try {
final IntegratedServer server = Minecraft.getInstance().getSingleplayerServer();
if (!server.equals(null)) {
return true;
} else {
return false;
}
} catch (Exception e) {
return false;
}
}
}.getAddress()))), (false));
}
}
