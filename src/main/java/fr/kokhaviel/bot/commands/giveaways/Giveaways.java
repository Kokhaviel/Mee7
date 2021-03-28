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

package fr.kokhaviel.bot.commands.giveaways;

import com.google.gson.JsonObject;
import fr.kokhaviel.bot.Config;
import fr.kokhaviel.bot.JsonUtilities;
import fr.kokhaviel.bot.Settings;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.io.File;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static fr.kokhaviel.bot.Mee7.getCurrentGiveaways;
import static java.lang.String.format;


public class Giveaways extends ListenerAdapter {

	private Message giveawayMessage;
	private TextChannel channel;
	private int timeInSec;
	private int winners;
	private String prize;
	private final ArrayList<User> participants = new ArrayList<>();
	private final ArrayList<User> alreadyDrawn = new ArrayList<>();

	private int timeLeft;

	private boolean isSetupStarted;
	private boolean isChannelSetUp;
	private boolean isDurationSetUp;
	private boolean isWinnersSetUp;
	private boolean isPrizeSetup;
	private boolean isGiveawayStarted;
	private boolean isGiveawayEnd;
	private boolean isGiveawayForceEnd;
	private boolean isGiveawayCancelled;

	private final JsonObject GENERAL_OBJECT;
	private final JsonObject GIVEAWAYS_OBJECT;

	public Giveaways(MessageReceivedEvent event) {

		final File LANG_FILE = Settings.getLanguageFile(event.getGuild().getId(), this.getClass().getClassLoader());
		assert LANG_FILE != null;
		final JsonObject LANG_OBJECT = JsonUtilities.readJson(LANG_FILE).getAsJsonObject();
		this.GENERAL_OBJECT = LANG_OBJECT.get("general").getAsJsonObject();
		this.GIVEAWAYS_OBJECT = LANG_OBJECT.get("giveaways").getAsJsonObject();
	}

	public Message getGiveawayMessage() {
		return giveawayMessage;
	}

	public TextChannel getChannel() {
		return channel;
	}

	public int getTimeInSec() {
		return timeInSec;
	}

	public int getWinners() {
		return winners;
	}

	public String getPrize() {
		return prize;
	}

	public ArrayList<User> getParticipants() {
		return participants;
	}

	public ArrayList<User> getAlreadyDrawn() {
		return alreadyDrawn;
	}

	public int getTimeLeft() {
		return timeLeft;
	}

	public boolean isSetupStarted() {
		return isSetupStarted;
	}

	public boolean isChannelSetUp() {
		return isChannelSetUp;
	}

	public boolean isDurationSetUp() {
		return isDurationSetUp;
	}

	public boolean isWinnersSetUp() {
		return isWinnersSetUp;
	}

	public boolean isPrizeSetup() {
		return isPrizeSetup;
	}

	public boolean isGiveawayStarted() {
		return isGiveawayStarted;
	}

	public boolean isGiveawayEnd() {
		return isGiveawayEnd;
	}

	public boolean isGiveawayForceEnd() {
		return isGiveawayForceEnd;
	}

	public boolean isGiveawayCancelled() {
		return isGiveawayCancelled;
	}


	public void setGiveawayMessage(Message giveawayMessage) {
		this.giveawayMessage = giveawayMessage;
	}

	public void setChannel(TextChannel channel) {
		this.channel = channel;
	}

	public void setTimeInSec(int timeInSec) {
		this.timeInSec = timeInSec;
	}

	public void setWinners(int winners) {
		this.winners = winners;
	}

	public void setPrize(String prize) {
		this.prize = prize;
	}

	public void setTimeLeft(int timeLeft) {
		this.timeLeft = timeLeft;
	}

	public void setSetupStarted(boolean setupStarted) {
		isSetupStarted = setupStarted;
	}

	public void setChannelSetUp(boolean channelSetUp) {
		isChannelSetUp = channelSetUp;
	}

	public void setDurationSetUp(boolean durationSetUp) {
		isDurationSetUp = durationSetUp;
	}

	public void setWinnersSetUp(boolean winnersSetUp) {
		isWinnersSetUp = winnersSetUp;
	}

	public void setPrizeSetup(boolean prizeSetup) {
		isPrizeSetup = prizeSetup;
	}

	public void setGiveawayStarted(boolean giveawayStarted) {
		isGiveawayStarted = giveawayStarted;
	}

	public void setGiveawayEnd(boolean giveawayEnd) {
		isGiveawayEnd = giveawayEnd;
	}

	public void setGiveawayForceEnd(boolean giveawayForceEnd) {
		isGiveawayForceEnd = giveawayForceEnd;
	}

	public void setGiveawayCancelled(boolean giveawayCancelled) {
		isGiveawayCancelled = giveawayCancelled;
	}

	public void startSetup(Giveaways giveaway, MessageReceivedEvent event) {

		event.getMessage().delete().queue();

		event.getChannel().sendMessage(format("%s :tada: !", GIVEAWAYS_OBJECT.get("ready_setup").getAsString())).queue(
				delete -> delete.delete().queueAfter(10, TimeUnit.SECONDS));

		event.getChannel().sendMessage(format("%s :tada: !", GIVEAWAYS_OBJECT.get("set_channel").getAsString())).queue(
				delete -> delete.delete().queueAfter(1, TimeUnit.MINUTES));

		giveaway.setSetupStarted(true);
	}

	public void channelSetup(Giveaways giveaway, MessageReceivedEvent event) {

		event.getMessage().delete().queue();

		final Pattern CHANNEL_PATTERN = Pattern.compile("^<#\\d{18}>$");

		if(!event.getMessage().getContentRaw().matches(CHANNEL_PATTERN.pattern())) {

			event.getChannel().sendMessage(GIVEAWAYS_OBJECT.get("channel_nothing_else").getAsString()).queue(
					delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));
			giveaway.cancelGiveaway(giveaway, event);
			return;
		}
		event.getChannel().sendMessage(format("%s ...", GIVEAWAYS_OBJECT.get("checking").getAsString())).queue(
				delete -> delete.delete().queueAfter(2, TimeUnit.SECONDS));

		final String channelId = event.getMessage().getContentRaw()
				.replace("<#", "")
				.replace(">", "");

		if(!getChannelIDs(event).contains(channelId)) {

			event.getChannel().sendMessage(GIVEAWAYS_OBJECT.get("channel_doesnt_exist").getAsString()).queue(
					delete -> delete.delete().queueAfter(10, TimeUnit.SECONDS));
			giveaway.cancelGiveaway(giveaway, event);
			return;
		}
		giveaway.setChannel(event.getGuild().getTextChannelById(channelId));
		event.getChannel().sendMessage(format("%s :tada: !", GIVEAWAYS_OBJECT.get("success_set_channel").getAsString())).queue(
				delete -> delete.delete().queueAfter(1, TimeUnit.MINUTES));
		event.getChannel().sendMessage(format("%s :tada: \n\n`%s`",
				GIVEAWAYS_OBJECT.get("set_duration").getAsString(),
				GIVEAWAYS_OBJECT.get("hint_set_duration").getAsString()))
				.queue(
					delete -> delete.delete().queueAfter(1, TimeUnit.MINUTES));
		giveaway.setChannelSetUp(true);
	}

	public void durationSetup(Giveaways giveaway, MessageReceivedEvent event) {

		event.getMessage().delete().queue();

		final String raw = event.getMessage().getContentRaw();

		final Pattern DURATION_SECONDS_PATTERN = Pattern.compile("^(\\d)+S$");
		final Pattern DURATION_MINUTES_PATTERN = Pattern.compile("^(\\d)+M$");
		final Pattern DURATION_HOURS_PATTERN = Pattern.compile("^(\\d)+H$");
		final Pattern DURATION_DAYS_PATTERN = Pattern.compile("^(\\d)+D$");

		if(raw.matches(DURATION_SECONDS_PATTERN.pattern())) {

			giveaway.setTimeInSec(Integer.parseInt(splitStringInt(raw).get(0)));
			event.getChannel().sendMessage(format("%s :tada: !", GIVEAWAYS_OBJECT.get("success_set_seconds_duration").getAsString())).queue(
					delete -> delete.delete().queueAfter(1, TimeUnit.MINUTES));
			giveaway.setDurationSetUp(true);
			event.getChannel().sendMessage(format("%s :tada: !", GIVEAWAYS_OBJECT.get("set_winners").getAsString())).queue(
					delete -> delete.delete().queueAfter(1, TimeUnit.MINUTES));

		} else if(raw.matches(DURATION_MINUTES_PATTERN.pattern())) {

			giveaway.setTimeInSec(convertMinutesToSeconds(Integer.parseInt(splitStringInt(raw).get(0))));
			event.getChannel().sendMessage(format("%s :tada: !", GIVEAWAYS_OBJECT.get("success_set_minutes_duration").getAsString())).queue(
					delete -> delete.delete().queueAfter(1, TimeUnit.MINUTES));
			giveaway.setDurationSetUp(true);
			event.getChannel().sendMessage(format("%s :tada: !", GIVEAWAYS_OBJECT.get("set_winners").getAsString())).queue(
					delete -> delete.delete().queueAfter(1, TimeUnit.MINUTES));

		} else if(raw.matches(DURATION_HOURS_PATTERN.pattern())) {

			giveaway.setTimeInSec(convertHoursToSeconds(Integer.parseInt(splitStringInt(raw).get(0))));
			event.getChannel().sendMessage(format("%s :tada: !", GIVEAWAYS_OBJECT.get("success_set_hours_duration").getAsString())).queue(
					delete -> delete.delete().queueAfter(1, TimeUnit.MINUTES));
			giveaway.setDurationSetUp(true);
			event.getChannel().sendMessage(format("%s :tada: !", GIVEAWAYS_OBJECT.get("set_winners").getAsString())).queue(
					delete -> delete.delete().queueAfter(1, TimeUnit.MINUTES));


		} else if(raw.matches(DURATION_DAYS_PATTERN.pattern())) {

			giveaway.setTimeInSec(convertDaysToSeconds(Integer.parseInt(splitStringInt(raw).get(0))));
			event.getChannel().sendMessage(format("%s :tada: !", GIVEAWAYS_OBJECT.get("success_set_days_duration").getAsString())).queue(
					delete -> delete.delete().queueAfter(1, TimeUnit.MINUTES));
			giveaway.setDurationSetUp(true);
			event.getChannel().sendMessage(format("%s :tada: !", GIVEAWAYS_OBJECT.get("set_winners").getAsString())).queue(
					delete -> delete.delete().queueAfter(1, TimeUnit.MINUTES));

		} else {
			event.getChannel().sendMessage(format("%s \n\n`%s`",
					GIVEAWAYS_OBJECT.get("not_valid_duration").getAsString(),
					GIVEAWAYS_OBJECT.get("hint_set_duration").getAsString()))
					.queue();
			giveaway.cancelGiveaway(giveaway, event);
		}
	}

	public void winnersSetup(Giveaways giveaway, MessageReceivedEvent event) {

		event.getMessage().delete().queue();

		try {
			final int winnersNumber = Integer.parseInt(event.getMessage().getContentRaw());
			giveaway.setWinners(winnersNumber);
			event.getChannel().sendMessage(format("%s :tada: !", GIVEAWAYS_OBJECT.get("success_set_winners").getAsString())).queue(
					delete -> delete.delete().queueAfter(1, TimeUnit.MINUTES));
			event.getChannel().sendMessage(format("%s :tada: !", GIVEAWAYS_OBJECT.get("set_prize").getAsString())).queue(
					delete -> delete.delete().queueAfter(1, TimeUnit.MINUTES));
			giveaway.setWinnersSetUp(true);

		} catch(NumberFormatException e) {
			event.getChannel().sendMessage( format("%s \n%s : %s", GIVEAWAYS_OBJECT.get("not_number").getAsString(), e.getClass().getSimpleName(), e.getMessage())).queue(
					delete -> delete.delete().queueAfter(10, TimeUnit.SECONDS));
		}
	}

	public void prizeSetup(Giveaways giveaway, MessageReceivedEvent event) throws InterruptedException, ParseException {

		event.getMessage().delete().queue();

		giveaway.setPrize(event.getMessage().getContentRaw());
		event.getChannel().sendMessage(format("%s :tada: !", GIVEAWAYS_OBJECT.get("success_set_prize").getAsString())).queue(
				delete -> delete.delete().queueAfter(1, TimeUnit.MINUTES));
		giveaway.setPrizeSetup(true);
		giveaway.startGiveaway(giveaway, event);

	}

	public void startGiveaway(Giveaways giveaway, MessageReceivedEvent event) throws ParseException {

		giveaway.setTimeLeft(giveaway.getTimeInSec());

		event.getChannel().sendMessage(format("Giveaway %s !", GIVEAWAYS_OBJECT.get("started").getAsString())).queue(
				delete -> delete.delete().queueAfter(1, TimeUnit.MINUTES)
		);
		EmbedBuilder giveawayEmbed = new EmbedBuilder();
		giveawayEmbed.setAuthor("Giveaway", null, Config.GIVEAWAYS_ICON);
		giveawayEmbed.setColor(new Color(0, 159, 231));
		giveawayEmbed.setThumbnail(event.getGuild().getIconUrl());
		giveawayEmbed.setTitle(giveaway.getPrize() + " Giveaway");
		giveawayEmbed.setFooter(GENERAL_OBJECT.get("developed_by").getAsString() + Config.DEVELOPER_TAG, Config.DEVELOPER_AVATAR);

		giveawayEmbed.addField(giveaway.getPrize(), format("%s !\n%s : ",
				GIVEAWAYS_OBJECT.get("react_participate").getAsString(),
				GIVEAWAYS_OBJECT.get("time_remaining").getAsString()
		) + giveaway.convertTimeLeft(giveaway.getTimeLeft()), false);
		giveawayEmbed.addField(format("%s : ", GIVEAWAYS_OBJECT.get("winners").getAsString()), String.valueOf(giveaway.getWinners()), false);

		LocalDateTime end = LocalDateTime.now().plusSeconds(timeInSec);
		MessageEmbed.Field endField = new MessageEmbed.Field(format("%s : ", GIVEAWAYS_OBJECT.get("ends_at").getAsString()),
				format("%s %s %s - %s ",
				giveaway.getDay(end.getDayOfWeek().name()),
				giveaway.getMonth(end.getMonth().name()),
				giveaway.getDayOfMonth(end.getDayOfMonth()),
				giveaway.getHour(end.getHour(), end.getMinute())), false);

		giveawayEmbed.addField(endField);
		MessageEmbed giveawayMessageEmbed = giveawayEmbed.build();
		giveaway.setGiveawayStarted(true);

		event.getChannel().sendMessage(giveawayMessageEmbed).queue(message -> {
			giveaway.setGiveawayMessage(message);
			message.addReaction("\uD83C\uDF89").queue();
			for(int i = timeLeft; i > -1; i--) {
				timeLeft = i;
				try {
					Thread.sleep(1000);
				} catch(InterruptedException e) {
					e.printStackTrace();
					event.getChannel().sendMessage("Internal Error ...").queue(
							delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS)
					);
				}

				if(i % 10 == 0 && i != 0) {
					giveawayEmbed.clearFields()
							.addField(giveaway.getPrize(),
									format("%s\n%s : ",
											GIVEAWAYS_OBJECT.get("react_participate").getAsString(),
											GIVEAWAYS_OBJECT.get("time_remaining").getAsString()
									) + giveaway.convertTimeLeft(giveaway.getTimeLeft()), false)
							.addField(format("%s : ", GIVEAWAYS_OBJECT.get("winners").getAsString()),
									String.valueOf(giveaway.getWinners()), false)
							.addField(endField);
					message.editMessage(giveawayEmbed.build()).queue();
				}
				if(timeLeft == 0) {
					message.delete().queue();
					giveaway.endGiveaway(giveaway, event);
				}
				if(giveaway.isGiveawayForceEnd()) {
					giveaway.endGiveaway(giveaway, event);
					break;
				}
				if(giveaway.isGiveawayCancelled()) {
					break;
				}
			}
		});
	}

	public void cancelGiveaway(Giveaways giveaway, MessageReceivedEvent event) {

		event.getChannel().sendMessage(GIVEAWAYS_OBJECT.get("cancelled").getAsString()).queue();
		getCurrentGiveaways().remove(event.getGuild().getIdLong());
		giveaway.setSetupStarted(false);
		giveaway.setChannelSetUp(false);
		giveaway.setDurationSetUp(false);
		giveaway.setWinnersSetUp(false);
		giveaway.setPrizeSetup(false);
		giveaway.setGiveawayStarted(false);
		giveaway.setGiveawayCancelled(true);
	}

	public void endGiveaway(Giveaways giveaway, MessageReceivedEvent event) {

		event.getChannel().sendMessage(format("Giveaway %s :tada: !", GIVEAWAYS_OBJECT.get("ended").getAsString())).queue(
				delete -> delete.delete().queueAfter(1, TimeUnit.MINUTES)
		);
		getCurrentGiveaways().remove(event.getGuild().getIdLong());

		if(giveaway.getParticipants().size() < giveaway.getWinners()) {
			event.getChannel().sendMessage(format("%s ...", GIVEAWAYS_OBJECT.get("more_winners_participants").getAsString())).queue(
					delete -> delete.delete().queueAfter(1, TimeUnit.MINUTES)
			);
			return;
		}

		giveaway.getParticipants().remove(event.getJDA().getSelfUser());
		EmbedBuilder endEmbed = new EmbedBuilder();
		endEmbed.setAuthor("Giveaway", null, Config.GIVEAWAYS_ICON);
		endEmbed.setColor(new Color(0, 159, 231));
		endEmbed.setThumbnail(event.getGuild().getIconUrl());
		endEmbed.setTitle(giveaway.getPrize() + format("Giveaway %s", GIVEAWAYS_OBJECT.get("ended").getAsString()));
		endEmbed.setFooter(GENERAL_OBJECT.get("developed_by").getAsString() + Config.DEVELOPER_TAG, Config.DEVELOPER_AVATAR);

		for(int i = 0; i < giveaway.getWinners(); i++) {

			int randomUser = new Random().nextInt(giveaway.getParticipants().size());
			User winner;

			do {
				winner = giveaway.getParticipants().get(randomUser);
			} while(giveaway.getAlreadyDrawn().contains(winner));
			giveaway.getAlreadyDrawn().add(winner);
			endEmbed.addField(format("%s ", GIVEAWAYS_OBJECT.get("winner").getAsString()) + (i + 1), winner.getAsTag(), false);
		}

		giveaway.getAlreadyDrawn().clear();
		event.getChannel().sendMessage(endEmbed.build()).queue();
		giveaway.setGiveawayEnd(true);
	}

	public void forceEnd(Giveaways giveaway, MessageReceivedEvent event) {

		if(!giveaway.isGiveawayStarted()) {
			event.getChannel().sendMessage(format("%s :tada: !", GIVEAWAYS_OBJECT.get("no_giveaway_running").getAsString())).queue(
					delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));
			return;
		}

		giveaway.setGiveawayForceEnd(true);
		event.getChannel().sendMessage(format("Giveaway %s !", GIVEAWAYS_OBJECT.get("force_ended").getAsString())).queue(
				delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));
		giveaway.endGiveaway(giveaway, event);

		giveaway.setGiveawayEnd(true);

	}

	public void rerollGiveaway(Giveaways giveaway, MessageReceivedEvent event) {

		if(!giveaway.isGiveawayStarted()) {
			event.getChannel().sendMessage(format("%s :tada : !", GIVEAWAYS_OBJECT.get("no_giveaway_running").getAsString())).queue(
					delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));
			return;
		}

		if(!giveaway.isGiveawayEnd()) {
			event.getChannel().sendMessage(format("%s :tada: !", GIVEAWAYS_OBJECT.get("not_finished").getAsString())).queue(
					delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));
			return;
		}

		event.getChannel().sendMessage(format("Giveaway %s", GIVEAWAYS_OBJECT.get("rerolled").getAsString())).queue();
		giveaway.endGiveaway(giveaway, event);

	}

	private ArrayList<String> getChannelIDs(MessageReceivedEvent event) {

		ArrayList<String> guildChannelIDs = new ArrayList<>();

		for(TextChannel channel : event.getGuild().getTextChannels()) {
			guildChannelIDs.add(channel.getId());
		}

		return guildChannelIDs;
	}

	private int convertMinutesToSeconds(int minutes) {
		return minutes * 60;
	}

	private int convertHoursToSeconds(int hours) {
		return hours * 60 * 60;
	}

	private int convertDaysToSeconds(int days) {
		return days * 60 * 60 * 24;
	}

	private String convertTimeLeft(int giveawayDuration) {

		BigDecimal roundThreeCalc = new BigDecimal(giveawayDuration);
		BigDecimal hours, remainder, minutes, seconds;
		BigDecimal var3600 = new BigDecimal("3600");
		BigDecimal var60 = new BigDecimal("60");

		hours = roundThreeCalc.divide(var3600, BigDecimal.ROUND_FLOOR);
		remainder = roundThreeCalc.remainder(var3600);
		minutes = remainder.divide(var60, BigDecimal.ROUND_FLOOR);
		seconds = remainder.remainder(var60);

		return format("%dh : %dm : %ds", hours.intValue(), minutes.intValue(), seconds.intValue());
	}


	private List<String> splitStringInt(String toParse) {
		final Pattern VALID_PATTERN = Pattern.compile("[0-9]+|[A-Z]+");
		List<String> chunks = new LinkedList<>();
		Matcher matcher = VALID_PATTERN.matcher(toParse);
		while(matcher.find()) {
			chunks.add(matcher.group());
		}
		return chunks;
	}

	private String getMonth(String month) {
		switch(month) {
			case "JANUARY":
				return "Jan";
			case "FEBRUARY":
				return "Feb";
			case "MARCH":
				return "Mar";
			case "APRIL":
				return "Apr";
			case "MAY":
				return "May";
			case "JUNE":
				return "Jun";
			case "JULY":
				return "Jul";
			case "AUGUST":
				return "Aug";
			case "SEPTEMBER":
				return "Sep";
			case "OCTOBER":
				return "Oct";
			case "NOVEMBER":
				return "Nov";
			case "December":
				return "Dec";
			default:
				return "None";
		}
	}

	private String getDay(String day) {

		switch(day) {
			case "MONDAY":
				return "Mon";
			case "TUESDAY":
				return "Tue";
			case "WEDNESDAY":
				return "Wed";
			case "THURSDAY":
				return "Thu";
			case "FRIDAY":
				return "Fri";
			case "SATURDAY":
				return "Sat";
			case "SUNDAY":
				return "Sun";
			default:
				return "None";
		}
	}

	private String getDayOfMonth(int day) {

		switch(day) {

			case 1:
			case 21:
			case 31:
				return day + "st";
			case 2:
			case 22:
				return day + "nd";
			case 3:
			case 23:
				return day + "rd";
			default:
				return day + "th";
		}

	}

	private String getHour(int hours, int minutes) throws ParseException {

		String input = hours + ":" + minutes;

		DateFormat outputFormat = new SimpleDateFormat("hh:mm aa");
		DateFormat inputFormat = new SimpleDateFormat("hh:mm");
		Date date = inputFormat.parse(input);

		return outputFormat.format(date);
	}

}