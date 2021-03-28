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

package fr.kokhaviel.bot.commands.minecraft;

import com.google.gson.JsonObject;
import fr.kokhaviel.bot.JsonUtilities;
import fr.kokhaviel.bot.Settings;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import static fr.kokhaviel.bot.Mee7.getCurrentAchievements;
import static java.lang.String.format;

public class Achievements {

	 final List<String> supportedItems = new ArrayList<>(Arrays.asList(
			"stone", "grass", "dirt",
			"cobblestone", "bedrock", "sand",
			"gravel", "leaves", "sponge",
			"glass", "dispenser", "sandstone",
			"piston", "wool", "tnt",
			"obsidian", "chest", "workbench",
			"furnace", "ice", "cactus",
			"jukebox", "pumpkin", "netherrack",
			"glowstone", "trapdoor", "mycelium",
			"hopper", "repeater", "bow",
			"arrow", "coal", "diamond",
			"diamond_sword", "diamond_pickaxe", "diamond_axe",
			"diamond_hoe", "stick", "bowl",
			"string", "feather", "gunpowder",
			"wheat", "bread", "diamond_helmet",
			"diamond_chestplate", "diamond_leggings", "diamond_boots",
			"flint", "porkchop", "painting",
			"golden_apple", "bucket", "water_bucket",
			"lava_bucket", "minecart", "saddle",
			"redstone", "snowball", "oak_boat",
			"leather", "milk_bucket", "brick",
			"paper", "book", "egg",
			"fishing_rod", "salmon", "tropical_fish",
			"pufferfish", "cod", "ink_sac",
			"cocoa_beans", "lapis_lazuli", "bone",
			"bone_meal", "sugar", "cake",
			"bed", "cookie", "shears", "melon_slice",
			"cooked_beef", "cooked_chicken", "rotten_flesh",
			"ender_pearl", "blaze_rod", "nether_wart",
			"potion", "blaze_powder", "magma_cream",
			"brewing_stand", "ender_eye", "experience_bottle",
			"fire_charge", "writable_book", "emerald",
			"carrot_on_a_stick", "nether_star", "firework_rocket",
			"quartz", "tnt_minecart", "armor_stand",
			"lead", "name_tag", "mutton",
			"end_crystal", "chorus_fruit", "dragon_breath",
			"elytra", "totem_of_undying", "trident",
			"campfire", "honey_bottle", "honeycomb"));

	private TextChannel channel;
	private String block;
	private String title;
	private String String;

	private boolean isSetupStarted;
	private boolean isBlockSetup;
	private boolean isTitleSetup;
	private boolean isStringSetup;

	private final JsonObject ACHIEVEMENTS_OBJECT;

	public Achievements(MessageReceivedEvent event) {

		final File LANG_FILE = Settings.getLanguageFile(event.getGuild().getId(), this.getClass().getClassLoader());
		assert LANG_FILE != null;
		final JsonObject LANG_OBJECT = JsonUtilities.readJson(LANG_FILE).getAsJsonObject();
		this.ACHIEVEMENTS_OBJECT = LANG_OBJECT.get("minecraft").getAsJsonObject().get("achievements").getAsJsonObject();
	}

	public TextChannel getChannel() {
		return channel;
	}

	public String getBlock() {
		return block;
	}

	public String getTitle() {
		return title;
	}

	public String getString() {
		return String;
	}

	public boolean isSetupStarted() {
		return isSetupStarted;
	}

	public boolean isBlockSetup() {
		return isBlockSetup;
	}

	public boolean isTitleSetup() {
		return isTitleSetup;
	}

	public boolean isStringSetup() {
		return isStringSetup;
	}


	public void setChannel(TextChannel channel) {
		this.channel = channel;
	}

	public void setBlock(String block) {
		this.block = block;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setString(String String) {
		this.String = String;
	}

	public void setSetupStarted(boolean setupStarted) {
		isSetupStarted = setupStarted;
	}

	public void setBlockSetup(boolean blockSetup) {
		isBlockSetup = blockSetup;
	}

	public void setTitleSetup(boolean titleSetup) {
		isTitleSetup = titleSetup;
	}

	public void setStringSetup(boolean StringSetup) {
		isStringSetup = StringSetup;
	}

	public void startSetup(Achievements achievements, MessageReceivedEvent event) {

		event.getMessage().delete().queue();

		event.getChannel().sendMessage(ACHIEVEMENTS_OBJECT.get("ready_setup").getAsString()).queue(
				delete -> delete.delete().queueAfter(10, TimeUnit.SECONDS));

		event.getChannel().sendMessage(ACHIEVEMENTS_OBJECT.get("set_block").getAsString()).queue(
				delete -> delete.delete().queueAfter(1, TimeUnit.MINUTES));

		achievements.setSetupStarted(true);
	}

	public void blockSetup(Achievements achievements, MessageReceivedEvent event) {

		final String GITHUB_URL = "https://github.com/Kokhaviel/Mee7/blob/master/src/main/java/fr/kokhaviel/bot/commands/minecraft/Achievements.java#L38";

		if(!supportedItems.contains(event.getMessage().getContentRaw())) {
			event.getChannel().sendMessage(format("%s \n%s %s",
					ACHIEVEMENTS_OBJECT.get("item_not_supported").getAsString(),
					ACHIEVEMENTS_OBJECT.get("complete_list").getAsString(),
					GITHUB_URL)).queue();
			achievements.cancelSetup(achievements, event);
			return;
		}

		event.getChannel().sendMessage( format("%s %s !", ACHIEVEMENTS_OBJECT.get("success_set_block").getAsString(), event.getMessage().getContentRaw())).queue(
				delete -> delete.delete().queueAfter(1, TimeUnit.MINUTES));

		event.getChannel().sendMessage(ACHIEVEMENTS_OBJECT.get("set_title").getAsString()).queue(
				delete -> delete.delete().queueAfter(1, TimeUnit.MINUTES));

		achievements.setBlock(event.getMessage().getContentRaw());
		achievements.setBlockSetup(true);
	}

	public void titleSetup(Achievements achievements, MessageReceivedEvent event) {

		final Pattern titlePattern = Pattern.compile("^[A-Za-z0-9_\\s]+$");

		if(!event.getMessage().getContentRaw().matches(titlePattern.pattern())) {
			event.getChannel().sendMessage(ACHIEVEMENTS_OBJECT.get("invalid_char").getAsString()).queue();
			achievements.cancelSetup(achievements, event);
			return;
		}

		event.getChannel().sendMessage( format("%s %s !", ACHIEVEMENTS_OBJECT.get("success_set_title").getAsString(), event.getMessage().getContentRaw())).queue(
				delete -> delete.delete().queueAfter(1, TimeUnit.MINUTES));

		event.getChannel().sendMessage(ACHIEVEMENTS_OBJECT.get("set_text").getAsString()).queue(
				delete -> delete.delete().queueAfter(1, TimeUnit.MINUTES));

		achievements.setTitle(event.getMessage().getContentRaw().replace(" ", ".."));
		achievements.setTitleSetup(true);
	}

	public void stringSetup(Achievements achievements, MessageReceivedEvent event) {

		final Pattern titlePattern = Pattern.compile("^[A-Za-z0-9_\\s]+$");

		if(!event.getMessage().getContentRaw().matches(titlePattern.pattern())) {
			event.getChannel().sendMessage(ACHIEVEMENTS_OBJECT.get("invalid_char").getAsString()).queue();
			achievements.cancelSetup(achievements, event);
			return;
		}

		event.getChannel().sendMessage(ACHIEVEMENTS_OBJECT.get("success_set_text").getAsString()).queue(
				delete -> delete.delete().queueAfter(1, TimeUnit.MINUTES));

		achievements.setString(event.getMessage().getContentRaw().replace(" ", ".."));
		achievements.setStringSetup(true);
		achievements.requestImage(achievements, event);
	}

	public void requestImage(Achievements achievements, MessageReceivedEvent event) {

		getCurrentAchievements().remove(event.getGuild().getIdLong());

		achievements.setSetupStarted(false);
		achievements.setBlockSetup(false);
		achievements.setTitleSetup(false);
		achievements.setStringSetup(false);

		final String url = "https://minecraft-api.com/api/achivements/"
				+ achievements.getBlock() + "/"
				+ achievements.getTitle() + "/"
				+ achievements.getString();

		event.getChannel().sendMessage(url).queue();
	}


	public void cancelSetup(Achievements achievements, MessageReceivedEvent event) {

		getCurrentAchievements().remove(event.getGuild().getIdLong());

		achievements.setSetupStarted(false);
		achievements.setBlockSetup(false);
		achievements.setTitleSetup(false);
		achievements.setStringSetup(false);

		event.getChannel().sendMessage("Achievement Setup Cancelled").queue(
				delete -> delete.delete().queueAfter(10, TimeUnit.SECONDS)
		);
	}
}
