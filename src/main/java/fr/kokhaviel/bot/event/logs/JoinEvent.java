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

package fr.kokhaviel.bot.event.logs;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public class JoinEvent extends ListenerAdapter {

	//FIXME : DONT FORGET TO RE-SET TO onGuildMemberJoin(GuildMemberJoinEvent)
	@Override
	public void onMessageReceived(@NotNull MessageReceivedEvent event) {
		final Member member = event.getMember();

		if(event.getAuthor().isBot()) return;

		if(member == null) return;
		final Path finalImgPath = Path.of(System.getProperty("java.io.tmpdir") + "/" + member.getId() + "-final.jpg");
		final Path avatarImgPath = Path.of(System.getProperty("java.io.tmpdir") + "/" + member.getId() + "-avatar.jpg");


		try {
			Files.deleteIfExists(finalImgPath);
			Files.deleteIfExists(avatarImgPath);

			Files.createFile(finalImgPath);
			Files.createFile(avatarImgPath);


			BufferedImage finalImg = new BufferedImage(800, 384, BufferedImage.TYPE_INT_ARGB);
			Graphics2D graphics = finalImg.createGraphics();
			graphics.drawImage(ImageIO.read(ClassLoader.getSystemResource("welcome/welcome-template.png")), 0, 0, null);
			graphics.setColor(Color.CYAN);
			graphics.setFont(new Font(graphics.getFont().getName(), Font.BOLD, 15));
			this.drawCenteredString(graphics, "Welcome " + member.getUser().getAsTag() + " to " + event.getGuild().getName(),
								finalImg, graphics.getFont());
			graphics.setColor(Color.WHITE);
			graphics.drawRect(336, 170, 129, 129);
			graphics.drawImage(ImageIO.read(new URL(Objects.requireNonNull(member.getUser().getAvatarUrl()))), 337, 171, null);
			ImageIO.write(finalImg, "png", finalImgPath.toFile());

			Objects.requireNonNull(event.getGuild().getSystemChannel()).sendFile(finalImgPath.toFile(), "welcome_" + member.getUser().getIdLong() + ".png").queue();

		} catch(IOException e) {
			e.printStackTrace();
		} finally {
			try {
				Files.deleteIfExists(finalImgPath);
				Files.deleteIfExists(avatarImgPath);
			} catch(IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	/**
	 * Draw a String centered in the middle of an Image (adapted to my needs so not really the middle).
	 * Taken from StackOverflow, by Daniel Kvist (https://stackoverflow.com/a/27740330)
	 *
	 * @param g The Graphics instance.
	 * @param text The String to draw.
	 * @param image The Image to center the text in.
	 */
	public void drawCenteredString(Graphics g, String text, BufferedImage image, Font font) {
		FontMetrics metrics = g.getFontMetrics(font);
		int x = (image.getWidth() - metrics.stringWidth(text)) / 2;
		int y = (int) (((image.getHeight() - metrics.getHeight()) / 3.5) + metrics.getAscent());
		g.setFont(font);
		g.drawString(text, x, y);
	}
}
