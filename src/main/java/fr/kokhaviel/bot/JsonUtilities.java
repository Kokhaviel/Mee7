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
import com.google.gson.JsonNull;
import com.google.gson.JsonParser;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonUtilities {

    public static String getErrorCode(String errorCode) {
        switch (errorCode) {
            case "1":
                return "Data doesn't exist. This player haven't play this game mode !";
            case "2":
                return "Incorrect Period, Please Contact " + Config.DEVELOPER_TAG;
            case "3":
                return "Invalid Game Mode, Please contact " + Config.DEVELOPER_TAG;
            case "4":
                return "Player doesn't exist !";
            case "5":
                return "No API Key Specified, Please Contact " + Config.DEVELOPER_TAG;
            case "6":
                return "API Key doesn't exist, Please Contact " + Config.DEVELOPER_TAG;
            case "7":
                return "Please Wait 10 Seconds Between Each Request !";
            case "8":
                return "Internal Error ....";
            case "9":
                return "Unable to connect to the Funcraft site.";
        }
        return "Unknown Error ...";
    }

    public static JsonElement readJson(URL jsonURL) {

        try {

            return readJson(catchForbidden(jsonURL));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return JsonNull.INSTANCE;
    }

    public static JsonElement readJson(File file) {

        JsonElement element;
        String content = "";

        try {
            content = new String(Files.readAllBytes(Paths.get(file.getPath())));
        } catch(IOException e) {
            e.printStackTrace();
        }

        element = JsonParser.parseString(content);
        return element;
    }

    private static JsonElement readJson(InputStream inputStream) {

        JsonElement element = JsonNull.INSTANCE;
        try(InputStream stream = new BufferedInputStream(inputStream)) {

            final Reader reader = new BufferedReader(new InputStreamReader(stream));
            final StringBuilder sb = new StringBuilder();

            int character;
            while ((character = reader.read()) != -1) sb.append((char)character);

            element =  JsonParser.parseString(sb.toString());
        } catch (IOException e) {

            e.printStackTrace();
        }

        return element.getAsJsonObject();
    }

    private static InputStream catchForbidden(URL url) throws IOException {

        final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.addRequestProperty("User-Agent", "Mozilla/5.0 AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.124 Safari/537.36");
        connection.setInstanceFollowRedirects(true);
        return connection.getInputStream();
    }
}
