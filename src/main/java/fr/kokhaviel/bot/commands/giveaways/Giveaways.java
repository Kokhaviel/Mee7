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

import fr.kokhaviel.bot.Config;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.math.BigDecimal;
import java.text.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.*;

import static fr.kokhaviel.bot.Mee7.getCurrentGiveaways;


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

		event.getChannel().sendMessage("Ready to setup a giveaway :tada: !").queue(
				delete -> delete.delete().queueAfter(10, TimeUnit.SECONDS));

		event.getChannel().sendMessage("Now Set a Channel for The Giveaway :tada: !").queue(
				delete -> delete.delete().queueAfter(1, TimeUnit.MINUTES));

		giveaway.setSetupStarted(true);
	}

	public void channelSetup(Giveaways giveaway, MessageReceivedEvent event) {

		event.getMessage().delete().queue();

		final Pattern CHANNEL_PATTERN = Pattern.compile("^<#\\d{18}>$");

		if(!event.getMessage().getContentRaw().matches(CHANNEL_PATTERN.pattern())) {

			event.getChannel().sendMessage("I Said a Channel ... Nothing Else !").queue(
					delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));
			giveaway.cancelGiveaway(giveaway, event);
			return;
		}
		event.getChannel().sendMessage("Checking ...").queue(
				delete -> delete.delete().queueAfter(2, TimeUnit.SECONDS));

		final String channelId = event.getMessage().getContentRaw()
				.replace("<#", "")
				.replace(">", "");

		if(!getChannelIDs(event).contains(channelId)) {

			event.getChannel().sendMessage("This channel doesn't exist ...").queue(
					delete -> delete.delete().queueAfter(10, TimeUnit.SECONDS));
			giveaway.cancelGiveaway(giveaway, event);
			return;
		}
		giveaway.setChannel(event.getGuild().getTextChannelById(channelId));
		event.getChannel().sendMessage("Channel Successfully Setup :tada:").queue(
				delete -> delete.delete().queueAfter(1, TimeUnit.MINUTES));
		event.getChannel().sendMessage("Now Let's Set a Duration To The Giveaway :tada: \n\n`Add to your duration a S for seconds, a M for minutes, a H for hours or a D for day !`").queue(
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
			event.getChannel().sendMessage("Successfully Set a Seconds Duration :tada:").queue(
					delete -> delete.delete().queueAfter(1, TimeUnit.MINUTES));
			giveaway.setDurationSetUp(true);
			event.getChannel().sendMessage("Now Let's Set The Number of Winners :tada: !").queue(
					delete -> delete.delete().queueAfter(1, TimeUnit.MINUTES));

		} else if(raw.matches(DURATION_MINUTES_PATTERN.pattern())) {

			giveaway.setTimeInSec(convertMinutesToSeconds(Integer.parseInt(splitStringInt(raw).get(0))));
			event.getChannel().sendMessage("Successfully Set a Minutes Duration :tada:").queue(
					delete -> delete.delete().queueAfter(1, TimeUnit.MINUTES));
			giveaway.setDurationSetUp(true);
			event.getChannel().sendMessage("Now Let's Set The Number of Winners :tada: !").queue(
					delete -> delete.delete().queueAfter(1, TimeUnit.MINUTES));


		} else if(raw.matches(DURATION_HOURS_PATTERN.pattern())) {

			giveaway.setTimeInSec(convertHoursToSeconds(Integer.parseInt(splitStringInt(raw).get(0))));
			event.getChannel().sendMessage("Successfully Set a Hours Duration :tada:").queue(
					delete -> delete.delete().queueAfter(1, TimeUnit.MINUTES));
			giveaway.setDurationSetUp(true);
			event.getChannel().sendMessage("Now Let's Set The Number of Winners :tada: !").queue(
					delete -> delete.delete().queueAfter(1, TimeUnit.MINUTES));


		} else if(raw.matches(DURATION_DAYS_PATTERN.pattern())) {

			giveaway.setTimeInSec(convertDaysToSeconds(Integer.parseInt(splitStringInt(raw).get(0))));
			event.getChannel().sendMessage("Successfully Set a Days Duration :tada:").queue(
					delete -> delete.delete().queueAfter(1, TimeUnit.MINUTES));
			giveaway.setDurationSetUp(true);
			event.getChannel().sendMessage("Now Let's Set The Number of Winners :tada: !").queue(
					delete -> delete.delete().queueAfter(1, TimeUnit.MINUTES));

		} else {
			event.getChannel().sendMessage("The duration specified is not valid \n\n`Add to your duration a S for seconds, a M for minutes, a H for hours or a D for day !`").queue();
			giveaway.cancelGiveaway(giveaway, event);
		}
	}

	public void winnersSetup(Giveaways giveaway, MessageReceivedEvent event) {

		event.getMessage().delete().queue();

		try {
			final int winnersNumber = Integer.parseInt(event.getMessage().getContentRaw());
			giveaway.setWinners(winnersNumber);
			event.getChannel().sendMessage("Successfully Set Numbers of Winners !").queue(
					delete -> delete.delete().queueAfter(1, TimeUnit.MINUTES));
			event.getChannel().sendMessage("Now Let's Set The Prize of Your Giveaway :tada: !").queue(
					delete -> delete.delete().queueAfter(1, TimeUnit.MINUTES));
			giveaway.setWinnersSetUp(true);

		} catch(NumberFormatException e) {
			event.getChannel().sendMessage("Please Specify a Real Number (Integer)\n" + e.getClass().getSimpleName() + " : " + e.getMessage()).queue(
					delete -> delete.delete().queueAfter(10, TimeUnit.SECONDS));
		}
	}

	public void prizeSetup(Giveaways giveaway, MessageReceivedEvent event) throws InterruptedException, ParseException {

		event.getMessage().delete().queue();

		giveaway.setPrize(event.getMessage().getContentRaw());
		event.getChannel().sendMessage("Prize Successfully Setup !").queue(
				delete -> delete.delete().queueAfter(1, TimeUnit.MINUTES));
		giveaway.setPrizeSetup(true);
		giveaway.startGiveaway(giveaway, event);

	}

	public void startGiveaway(Giveaways giveaway, MessageReceivedEvent event) throws ParseException {

		giveaway.setTimeLeft(giveaway.getTimeInSec());

		event.getChannel().sendMessage("Giveaway Started !").queue();
		EmbedBuilder giveawayEmbed = new EmbedBuilder();
		giveawayEmbed.setAuthor("Giveaway", null, "https://www.pinclipart.com/picdir/big/107-1079672_bing-christmas-clip-art-ndash-discord-tada-emoji.png");
		giveawayEmbed.setColor(new Color(0, 159, 231));
		giveawayEmbed.setThumbnail(event.getGuild().getIconUrl());
		giveawayEmbed.setTitle(giveaway.getPrize() + " Giveaway");
		giveawayEmbed.setFooter("Developed by " + Config.DEVELOPER_TAG, "https://cdn.discordapp.com/avatars/560156789178368010/790bd41a9474a82b20ca813f2be49641.webp?size=128");

		giveawayEmbed.addField(giveaway.getPrize(), "React with :tada: to participate !\nTime Remaining : " + giveaway.convertTimeLeft(giveaway.getTimeLeft()), false);
		giveawayEmbed.addField("Winners : ", String.valueOf(giveaway.getWinners()), false);

		LocalDateTime end = LocalDateTime.now().plusSeconds(timeInSec);
		MessageEmbed.Field endField = new MessageEmbed.Field("Ends At : ", String.format("%s %s %s - %s ",
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
					event.getChannel().sendMessage("Internal Error ...").queue();
				}

				if(i % 10 == 0 && i != 0) {
					giveawayEmbed.clearFields()
							.addField(giveaway.getPrize(), "React with :tada: to participate !\nTime Remaining : " + giveaway.convertTimeLeft(giveaway.getTimeLeft()), false)
							.addField("Winners : ", String.valueOf(giveaway.getWinners()), false)
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

		event.getChannel().sendMessage("Cancelled").queue();
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

		event.getChannel().sendMessage("Giveaway Ended :tada: !").queue();
		getCurrentGiveaways().remove(event.getGuild().getIdLong());

		if(giveaway.getParticipants().size() < giveaway.getWinners()) {
			event.getChannel().sendMessage("There are more winners than participants ...").queue();
			return;
		}

		giveaway.getParticipants().remove(event.getJDA().getSelfUser());
		EmbedBuilder endEmbed = new EmbedBuilder();
		endEmbed.setAuthor("Giveaway", null, "https://www.pinclipart.com/picdir/big/107-1079672_bing-christmas-clip-art-ndash-discord-tada-emoji.png");
		endEmbed.setColor(new Color(0, 159, 231));
		endEmbed.setThumbnail(event.getGuild().getIconUrl());
		endEmbed.setTitle(giveaway.getPrize() + " Giveaway Ended");
		endEmbed.setFooter("Developed by " + Config.DEVELOPER_TAG, "https://cdn.discordapp.com/avatars/560156789178368010/790bd41a9474a82b20ca813f2be49641.webp?size=128");

		for(int i = 0; i < giveaway.getWinners(); i++) {

			int randomUser = new Random().nextInt(giveaway.getParticipants().size());
			User winner;

			do {
				winner = giveaway.getParticipants().get(randomUser);
			} while(giveaway.getAlreadyDrawn().contains(winner));
			giveaway.getAlreadyDrawn().add(winner);
			endEmbed.addField("Winner " + (i + 1), winner.getAsTag(), false);
		}

		giveaway.getAlreadyDrawn().clear();
		event.getChannel().sendMessage(endEmbed.build()).queue();
		giveaway.setGiveawayEnd(true);
	}

	public void forceEnd(Giveaways giveaway, MessageReceivedEvent event) {

		if(!giveaway.isGiveawayStarted()) {
			event.getChannel().sendMessage("No giveaway currently running :tada: !").queue(
					delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));
			return;
		}

		giveaway.setGiveawayForceEnd(true);
		event.getChannel().sendMessage("Giveaway Force Ended !").queue(
				delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));
		giveaway.endGiveaway(giveaway, event);

		giveaway.setGiveawayEnd(true);

	}

	public void rerollGiveaway(Giveaways giveaway, MessageReceivedEvent event) {

		if(!giveaway.isGiveawayStarted()) {
			event.getChannel().sendMessage("No giveaway currently running :tada: !").queue(
					delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));
			return;
		}

		if(!giveaway.isGiveawayEnd()) {
			event.getChannel().sendMessage("Current giveaway is not finished :tada: !").queue(
					delete -> delete.delete().queueAfter(5, TimeUnit.SECONDS));
			return;
		}

		event.getChannel().sendMessage("Giveaway Rerolled").queue();
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

		return String.format("%dh : %dm : %ds", hours.intValue(), minutes.intValue(), seconds.intValue());
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