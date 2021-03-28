/*
 * Copyright (C) 2021 Kokhaviel
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package fr.kokhaviel.bot;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

public class Settings extends ListenerAdapter {

	@Override
	public void onGuildJoin(@NotNull GuildJoinEvent event) {
		try {
			write(event);
		} catch(IOException e) {
			e.printStackTrace();
		}

	}

	private void write(GuildJoinEvent event) throws IOException {

		if(!Paths.get("guild_settings.json").toFile().exists()) {
			Files.createFile(Paths.get("guild_settings.json"));

			try(FileWriter writer = new FileWriter("guild_settings.json")) {
				writer.write("{}");
			}
		}

		File file = new File("guild_settings.json");

		JsonObject fileContent = JsonUtilities.readJson(file).getAsJsonObject();
		FileWriter fw = new FileWriter(file, false);
		if(fileContent.has(event.getGuild().getId())) return;
		JsonElement tree = Mee7.gson.toJsonTree(fileContent);
		JsonObject guildObject = new JsonObject();
		guildObject.addProperty("language", "us");
		guildObject.addProperty("prefix", "!");
		guildObject.addProperty("music_prefix", "m!");
		guildObject.addProperty("hypixel_prefix", "h!");
		guildObject.addProperty("funcraft_prefix", "f!");
		guildObject.addProperty("wikipedia_prefix", "w!");
		guildObject.addProperty("giveaways_prefix", "g!");
		guildObject.addProperty("covid_prefix", "c!");
		guildObject.addProperty("minecraft_prefix", "mc!");

		tree.getAsJsonObject().add(event.getGuild().getId(), guildObject);

		fw.write(String.valueOf(tree));
		fw.close();

	}

	public static File getLanguageFile(String guildID, ClassLoader classLoader) {

		File file = new File("guild_settings.json");
		JsonObject object = JsonUtilities.readJson(file).getAsJsonObject();
		JsonObject guildObject = object.get(guildID).getAsJsonObject();
		String lang = guildObject.get("language").getAsString();

		switch(lang) {
			case "de":
				return new File(Objects.requireNonNull(classLoader.getResource("languages/de_DE.json")).getFile());
			case "us":
				return new File(Objects.requireNonNull(classLoader.getResource("languages/en_US.json")).getFile());
			case "es":
				return new File(Objects.requireNonNull(classLoader.getResource("languages/es_ES.json")).getFile());
			case "fr":
				return new File(Objects.requireNonNull(classLoader.getResource("languages/fr_FR.json")).getFile());
			case "it":
				return new File(Objects.requireNonNull(classLoader.getResource("languages/it_IT.json")).getFile());
			case "ja":
				return new File(Objects.requireNonNull(classLoader.getResource("languages/ja_JP.json")).getFile());
			case "lt":
				return new File(Objects.requireNonNull(classLoader.getResource("languages/lt_LT.json")).getFile());
			case "pt":
				return new File(Objects.requireNonNull(classLoader.getResource("languages/pt_BR.json")).getFile());
			case "ru":
				return new File(Objects.requireNonNull(classLoader.getResource("languages/ru_RU.json")).getFile());
			default:
				return null;
		}
	}
}
