package xyz.vikkivuk.icurrency.procedures;

import org.apache.http.util.EntityUtils;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.HttpEntity;

import net.minecraftforge.fml.loading.FMLPaths;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.Checkbox;

import java.util.HashMap;

import java.io.IOException;
import java.io.FileWriter;
import java.io.File;

import com.google.gson.GsonBuilder;
import com.google.gson.Gson;

public class SetupProcedureProcedure {
	public static void execute(Entity entity, HashMap guistate) {
		if (entity == null || guistate == null)
			return;
		com.google.gson.JsonObject jsond = new com.google.gson.JsonObject();
		File icurrency_data = new File("");
		String response = "";
		icurrency_data = new File((FMLPaths.GAMEDIR.get().toString() + "/icurrency"), File.separator + "icurrency_data.json");
		if (!icurrency_data.exists()) {
			try {
				icurrency_data.getParentFile().mkdirs();
				icurrency_data.createNewFile();
			} catch (IOException exception) {
				exception.printStackTrace();
			}
		}
		response = new Object() {
			public String getResponse() {
				try {
					CloseableHttpClient httpclient = HttpClients.createDefault();
					HttpGet httpget = new HttpGet(("https://icurrency.vikkivuk.xyz/api/register_user?password="
							+ (guistate.containsKey("text:password") ? ((EditBox) guistate.get("text:password")).getValue() : "") + "&accepted_reqs="
							+ (guistate.containsKey("checkbox:allow_requests")
									? ((Checkbox) guistate.get("checkbox:allow_requests")).selected()
									: false)
							+ "&accepted_risks="
							+ (guistate.containsKey("checkbox:accept_risks") ? ((Checkbox) guistate.get("checkbox:accept_risks")).selected() : false)
							+ "&username=" + entity.getDisplayName().getString()));
					CloseableHttpResponse httpresponse = httpclient.execute(httpget);
					HttpEntity entity = httpresponse.getEntity();
					String responseString = EntityUtils.toString(entity, "UTF-8");
					return responseString;
				} catch (IOException e) {
					System.out.println("Error fetching URL");
					return null;
				}
			}
		}.getResponse();
		if (entity instanceof Player _player && !_player.level.isClientSide())
			_player.displayClientMessage(new TextComponent(("Your uuid is now: " + response)), (false));
		if ((response).equals("ERROR")) {
			if (entity instanceof Player _player && !_player.level.isClientSide())
				_player.displayClientMessage(new TextComponent("An error has ocurred. Run the command /setup to restart."), (false));
		} else {
			jsond.addProperty("uuid", response);
			{
				Gson mainGSONBuilderVariable = new GsonBuilder().setPrettyPrinting().create();
				try {
					FileWriter fileWriter = new FileWriter(icurrency_data);
					fileWriter.write(mainGSONBuilderVariable.toJson(jsond));
					fileWriter.close();
				} catch (IOException exception) {
					exception.printStackTrace();
				}
			}
			if (entity instanceof Player _player)
				_player.closeContainer();
		}
	}
}
