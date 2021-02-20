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

package fr.kokhaviel.bot.commands.covid;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import fr.kokhaviel.bot.Config;
import fr.kokhaviel.bot.JsonUtilities;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Covid {


	public Covid(MessageReceivedEvent event, String[] args) throws MalformedURLException {

		if(args.length == 1) {

			final String url = "https://api.covid19api.com/summary";
			JsonObject object = JsonUtilities.readJson(new URL(url)).getAsJsonObject();
			JsonObject globalObject = object.getAsJsonObject("Global");

			final String update = globalObject.get("Date").getAsString().replace("T", " ").substring(0, 19);

			EmbedBuilder globalEmbed = new EmbedBuilder();

			globalEmbed.setAuthor("Covid Stats", null, "https://static.data.gouv.fr/avatars/30/3624ff029d41908d4b3d2c11dfa349.png");
			globalEmbed.setColor(Color.RED);
			globalEmbed.setThumbnail("https://media.npr.org/assets/img/2013/03/06/bluemarble3k-smaller-nasa_custom-644f0b7082d6d0f6814a9e82908569c07ea55ccb-s800-c85.jpg");
			globalEmbed.setTitle("Covid Global Stats");
			globalEmbed.setFooter("Developed by " + Config.DEVELOPER_TAG + "\nSource : https://api.covid19api.com/summary\nUpdated On : " + update, "https://cdn.discordapp.com/avatars/560156789178368010/790bd41a9474a82b20ca813f2be49641.webp?size=128");

			globalEmbed.addField("New Confirmed : ", globalObject.get("NewConfirmed").getAsString(), false);
			globalEmbed.addField("New Deaths : ", globalObject.get("NewDeaths").getAsString(), false);
			globalEmbed.addField("New Recovered : ", globalObject.get("NewRecovered").getAsString(), false);

			globalEmbed.addBlankField(false);
			globalEmbed.addField("Total Confirmed : ", globalObject.get("TotalConfirmed").getAsString(), false);
			globalEmbed.addField("Total Deaths : ", globalObject.get("TotalDeaths").getAsString(), false);
			globalEmbed.addField("Total Recovered : ", globalObject.get("TotalRecovered").getAsString(), false);

			event.getChannel().sendMessage(globalEmbed.build()).queue();

		}

		if(args.length == 2) {
				Gson gson = new Gson();
				final String url = "https://api.covid19api.com/summary";
				JsonObject object = JsonUtilities.readJson(new URL(url)).getAsJsonObject();
				JsonArray countriesArray = object.getAsJsonArray("Countries");
				JsonObject country = countriesArray.get(getCountry(getArg(args))).getAsJsonObject();

				CountryStats stats = gson.fromJson(country, CountryStats.class);
				try {
					event.getChannel().sendMessage(getStats(stats).build()).queue();
				} catch(ArrayIndexOutOfBoundsException e) {
					event.getChannel().sendMessage("Country Specified Doesn't Exist ...").queue();
				}
		}
	}

	private EmbedBuilder getStats(CountryStats stats) {
		EmbedBuilder statsEmbed = new EmbedBuilder();

		statsEmbed.setAuthor("Covid Stats", null, "https://static.data.gouv.fr/avatars/30/3624ff029d41908d4b3d2c11dfa349.png");
		statsEmbed.setColor(Color.RED);
		statsEmbed.setTitle(String.format("Covid %s Stats", stats.Country));
		statsEmbed.setFooter("Developed by " + Config.DEVELOPER_TAG + "\nSource : https://api.covid19api.com/summary\nUpdated On : " + stats.Date.replace("T", " ").substring(0, 19), "https://cdn.discordapp.com/avatars/560156789178368010/790bd41a9474a82b20ca813f2be49641.webp?size=128");

		statsEmbed.addField("Total Confirmed : ", stats.TotalConfirmed, true);
		statsEmbed.addField("Total Deaths : ", stats.TotalDeaths, true);
		statsEmbed.addField("Total Recovered : ", stats.TotalRecovered, true);

		statsEmbed.addField("Update At : ", stats.Date.replace("T", " ").substring(0, 19), false);

		return statsEmbed;
	}

	private String getArg(String[] args) {

		List<String> list = new ArrayList<>(Arrays.asList(args));
		list.remove(args[0]);
		args = list.toArray(new String[0]);
		StringBuilder sb = new StringBuilder();
		for(String arg : args) {
			sb.append(arg).append(" ");
		}

		return sb.toString();

	}

	static class CountryStats {
		String Country;
		String TotalConfirmed;
		String TotalDeaths;
		String TotalRecovered;
		String Date;
	}

	private int getCountry(String arg) {

		switch(arg.trim()) {
			case "Afghanistan":
			case "AF":
			case "afghanistan":
				return 0;
			case "Albania":
			case "AL":
			case "albania":
				return 1;
			case "Algeria":
			case "DZ":
			case "algeria":
				return 2;
			case "Andorra":
			case "AD":
			case "andorra":
				return 3;
			case "Angola":
			case "AO":
			case "angola":
				return 4;
			case "Antigua and Barbuda":
			case "AG":
			case "antigua-and-barbuda":
				return 5;
			case "Argentina":
			case "AR":
			case "argentina":
				return 6;
			case "Armenia":
			case "AM":
			case "armenia":
				return 7;
			case "Australia":
			case "AU":
			case "australia":
				return 8;
			case "Austria":
			case "AT":
			case "austria":
				return 9;
			case "Azerbaijan":
			case "AZ":
			case "azerbaijan":
				return 10;
			case "Bahamas":
			case "BS":
			case "bahamas":
				return 11;
			case "Bahrain":
			case "BH":
			case "bahrain":
				return 12;
			case "Bangladesh":
			case "BD":
			case "bangladesh":
				return 13;
			case "Barbados":
			case "BB":
			case "barbados":
				return 14;
			case "Belarus":
			case "BY":
			case "belarus":
				return 15;
			case "Belgium":
			case "BE":
			case "belgium":
				return 16;
			case "Belize":
			case "BZ":
			case "belize":
				return 17;
			case "Benin":
			case "BJ":
			case "benin":
				return 18;
			case "Bhutan":
			case "BT":
			case "bhutan":
				return 19;
			case "Bolivia":
			case "BO":
			case "bolivia":
				return 20;
			case "Bosnia and Herzegovina":
			case "BA":
			case "bosnia-and-herzegovina":
				return 21;
			case "Botswana":
			case "BW":
			case "botswana":
				return 22;
			case "Brazil":
			case "BR":
			case "brazil":
				return 23;
			case "Brunei":
			case "BN":
			case "brunei":
				return 24;
			case "Bulgaria":
			case "BG":
			case "bulgaria":
				return 25;
			case "Burkina Faso":
			case "BF":
			case "burkina-faso":
				return 26;
			case "Burundi":
			case "BI":
			case "burundi":
				return 27;
			case "Cambodia":
			case "KH":
			case "cambodia":
				return 28;
			case "Cameroon":
			case "CM":
			case "cameroon":
				return 29;
			case "Canada":
			case "CA":
			case "canada":
				return 30;
			case "Cape Verde":
			case "CV":
			case "cape-verde":
				return 31;
			case "Central African Republic":
			case "CF":
			case "central-african-republic":
				return 32;
			case "Chad":
			case "TD":
			case "chad":
				return 33;
			case "Chile":
			case "CL":
			case "chile":
				return 34;
			case "China":
			case "CN":
			case "china":
				return 35;
			case "Colombia":
			case "CO":
			case "colombia":
				return 36;
			case "Comoros":
			case "KM":
			case "comoros":
				return 37;
			case "Congo (Brazzaville)":
			case "CG":
			case "congo-brazzaville":
				return 38;
			case "Congo (Kinshasa)":
			case "CD":
			case "congo-kinshasa":
				return 39;
			case "Costa Rica":
			case "CR":
			case "costa-rica":
				return 40;
			case "Croatia":
			case "HR":
			case "croatia":
				return 41;
			case "Cuba":
			case "CU":
			case "cuba":
				return 42;
			case "Cyprus":
			case "CY":
			case "cyprus":
				return 43;
			case "Czech Republic":
			case "CZ":
			case "czech-republic":
				return 44;
			case "Côte d'Ivoire":
			case "CI":
			case "cote-divoire":
				return 45;
			case "Denmark":
			case "DK":
			case "denmark":
				return 46;
			case "Djibouti":
			case "DJ":
			case "djibouti":
				return 47;
			case "Dominica":
			case "DM":
			case "dominica":
				return 48;
			case "Dominican Republic":
			case "DO":
			case "dominican-republic":
				return 49;
			case "Ecuador":
			case "EC":
			case "ecuador":
				return 50;
			case "Egypt":
			case "EG":
			case "egypt":
				return 51;
			case "El Salvador":
			case "SV":
			case "el-salvador":
				return 52;
			case "Equatorial Guinea":
			case "GQ":
			case "equatorial-guinea":
				return 53;
			case "Eritrea":
			case "ER":
			case "eritrea":
				return 54;
			case "Estonia":
			case "EE":
			case "estonia":
				return 55;
			case "Ethiopia":
			case "ET":
			case "ethiopia":
				return 56;
			case "Fiji":
			case "FJ":
			case "fiji":
				return 57;
			case "Finland":
			case "FI":
			case "finland":
				return 58;
			case "France":
			case "FR":
			case "france":
				return 59;
			case "Gabon":
			case "GA":
			case "gabon":
				return 60;
			case "Gambia":
			case "GM":
			case "gambia":
				return 61;
			case "Georgia":
			case "GE":
			case "georgia":
				return 62;
			case "Germany":
			case "DE":
			case "germany":
				return 63;
			case "Ghana":
			case "GH":
			case "ghana":
				return 64;
			case "Greece":
			case "GR":
			case "greece":
				return 65;
			case "Grenada":
			case "GD":
			case "grenada":
				return 66;
			case "Guatemala":
			case "GT":
			case "guatemala":
				return 67;
			case "Guinea":
			case "GN":
			case "guinea":
				return 68;
			case "Guinea-Bissau":
			case "GW":
			case "guinea-bissau":
				return 69;
			case "Guyana":
			case "GY":
			case "guyana":
				return 70;
			case "Haiti":
			case "HT":
			case "haiti":
				return 71;
			case "Vatican":
			case "VA":
			case "vatican":
				return 72;
			case "Honduras":
			case "HN":
			case "honduras":
				return 73;
			case "Hungary":
			case "HU":
			case "hungary":
				return 74;
			case "Iceland":
			case "IS":
			case "iceland":
				return 75;
			case "India":
			case "IN":
			case "india":
				return 76;
			case "Indonesia":
			case "ID":
			case "indonesia":
				return 77;
			case "Iran":
			case "IR":
			case "iran":
				return 78;
			case "Iraq":
			case "IQ":
			case "iraq":
				return 79;
			case "Ireland":
			case "IE":
			case "ireland":
				return 80;
			case "Israel":
			case "IL":
			case "israel":
				return 81;
			case "Italy":
			case "IT":
			case "italy":
				return 82;
			case "Jamaica":
			case "JM":
			case "jamaica":
				return 83;
			case "Japan":
			case "JP":
			case "japan":
				return 84;
			case "Jordan":
			case "JO":
			case "jordan":
				return 85;
			case "Kazakhstan":
			case "KZ":
			case "kazakhstan":
				return 86;
			case "Kenya":
			case "KE":
			case "kenya":
				return 87;
			case "Korea":
			case "KR":
			case "korea-south":
				return 88;
			case "Kuwait":
			case "KW":
			case "kuwait":
				return 89;
			case "Kyrgyzstan":
			case "KG":
			case "kyrgyzstan":
				return 90;
			case "Lao PDR":
			case "LA":
			case "lao-pdr":
				return 91;
			case "Latvia":
			case "LV":
			case "latvia":
				return 92;
			case "Lebanon":
			case "LB":
			case "lebanon":
				return 93;
			case "Lesotho":
			case "LS":
			case "lesotho":
				return 94;
			case "Liberia":
			case "LR":
			case "liberia":
				return 95;
			case "Libya":
			case "LY":
			case "libya":
				return 96;
			case "Liechtenstein":
			case "LI":
			case "liechtenstein":
				return 97;
			case "Lithuania":
			case "LT":
			case "lithuania":
				return 98;
			case "Luxembourg":
			case "LU":
			case "luxembourg":
				return 99;
			case "Macedonia":
			case "MK":
			case "macedonia":
				return 100;
			case "Madagascar":
			case "MG":
			case "madagascar":
				return 101;
			case "Malawi":
			case "MW":
			case "malawi":
				return 102;
			case "Malaysia":
			case "MY":
			case "malaysia":
				return 103;
			case "Maldives":
			case "MV":
			case "maldives":
				return 104;
			case "Mali":
			case "ML":
			case "mali":
				return 105;
			case "Malta":
			case "MT":
			case "malta":
				return 106;
			case "Marshall Islands":
			case "MH":
			case "marshall-islands":
				return 107;
			case "Mauritania":
			case "MR":
			case "mauritania":
				return 108;
			case "Mauritius":
			case "MU":
			case "mauritius":
				return 109;
			case "Mexico":
			case "MX":
			case "mexico":
				return 110;
			case "Micronesia":
			case "FM":
			case "micronesia":
				return 111;
			case "Moldova":
			case "MD":
			case "moldova":
				return 112;
			case "Monaco":
			case "MC":
			case "monaco":
				return 113;
			case "Mongolia":
			case "MN":
			case "mongolia":
				return 114;
			case "Montenegro":
			case "ME":
			case "montenegro":
				return 115;
			case "Morocco":
			case "MA":
			case "morocco":
				return 116;
			case "Mozambique":
			case "MZ":
			case "mozambique":
				return 117;
			case "Myanmar":
			case "MM":
			case "myanmar":
				return 118;
			case "Namibia":
			case "NA":
			case "namibia":
				return 119;
			case "Nepal":
			case "NP":
			case "nepal":
				return 120;
			case "Netherlands":
			case "NL":
			case "netherlands":
				return 121;
			case "New Zealand":
			case "NZ":
			case "new-zealand":
				return 122;
			case "Nicaragua":
			case "NI":
			case "nicaragua":
				return 123;
			case "Niger":
			case "NE":
			case "niger":
				return 124;
			case "Nigeria":
			case "NG":
			case "nigeria":
				return 125;
			case "Norway":
			case "NO":
			case "norway":
				return 126;
			case "Oman":
			case "OM":
			case "oman":
				return 127;
			case "Pakistan":
			case "PK":
			case "pakistan":
				return 128;
			case "Palestine":
			case "PS":
			case "palestine":
				return 129;
			case "Panama":
			case "PA":
			case "panama":
				return 130;
			case "Papua New Guinea":
			case "PG":
			case "papua-new-guinea":
				return 131;
			case "Paraguay":
			case "PY":
			case "paraguay":
				return 132;
			case "Peru":
			case "PE":
			case "peru":
				return 133;
			case "Philippines":
			case "PH":
			case "philippines":
				return 134;
			case "Poland":
			case "PL":
			case "poland":
				return 135;
			case "Portugal":
			case "PT":
			case "portugal":
				return 136;
			case "Qatar":
			case "QA":
			case "qatar":
				return 137;
			case "Kosovo":
			case "XK":
			case "kosovo":
				return 138;
			case "Romania":
			case "RO":
			case "romania":
				return 139;
			case "Russia":
			case "RU":
			case "russia":
				return 140;
			case "Rwanda":
			case "RW":
			case "rwanda":
				return 141;
			case "Saint Kitts and Nevis":
			case "KN":
			case "saint-kitts-and-nevis":
				return 142;
			case "Saint Lucia":
			case "LC":
			case "saint-lucia":
				return 143;
			case "Saint Vincent and Grenadines":
			case "VC":
			case "saint-vincent-and-the-grenadines":
				return 144;
			case "Samoa":
			case "WS":
			case "samoa":
				return 145;
			case "San Marino":
			case "SM":
			case "san-marino":
				return 146;
			case "Sao Tome and Principe":
			case "ST":
			case "sao-tome-and-principe":
				return 147;
			case "Saudi Arabia":
			case "SA":
			case "saudi-arabia":
				return 148;
			case "Senegal":
			case "SN":
			case "senegal":
				return 149;
			case "Serbia":
			case "RS":
			case "serbia":
				return 150;
			case "Seychelles":
			case "SC":
			case "seychelles":
				return 151;
			case "Sierra Leone":
			case "SL":
			case "sierra-leone":
				return 152;
			case "Singapore":
			case "SG":
			case "singapore":
				return 153;
			case "Slovakia":
			case "SK":
			case "slovakia":
				return 154;
			case "Slovenia":
			case "SI":
			case "slovenia":
				return 155;
			case "Solomon Islands":
			case "SB":
			case "solomon-islands":
				return 156;
			case "Somalia":
			case "SO":
			case "somalia":
				return 157;
			case "South Africa":
			case "ZA":
			case "south-africa":
				return 158;
			case "South Sudan":
			case "SS":
			case "south-sudan":
				return 159;
			case "Spain":
			case "ES":
			case "spain":
				return 160;
			case "Sri Lanka":
			case "LK":
			case "sri-lanka":
				return 161;
			case "Sudan":
			case "SD":
			case "sudan":
				return 162;
			case "Suriname":
			case "SR":
			case "suriname":
				return 163;
			case "Swaziland":
			case "SZ":
			case "swaziland":
				return 164;
			case "Sweden":
			case "SE":
			case "sweden":
				return 165;
			case "Switzerland":
			case "CH":
			case "switzerland":
				return 166;
			case "Syria":
			case "SY":
			case "syria":
				return 167;
			case "Taiwan":
			case "TW":
			case "taiwan":
				return 168;
			case "Tajikistan":
			case "TJ":
			case "tajikistan":
				return 169;
			case "Tanzania":
			case "TZ":
			case "tanzania":
				return 170;
			case "Thailand":
			case "TH":
			case "thailand":
				return 171;
			case "Timor-Leste":
			case "TL":
			case "timor-leste":
				return 172;
			case "Togo":
			case "TG":
			case "togo":
				return 173;
			case "Trinidad and Tobago":
			case "TT":
			case "trinidad-and-tobago":
				return 174;
			case "Tunisia":
			case "TN":
			case "tunisia":
				return 175;
			case "Turkey":
			case "TR":
			case "turkey":
				return 176;
			case "Uganda":
			case "UG":
			case "uganda":
				return 177;
			case "Ukraine":
			case "UA":
			case "ukraine":
				return 178;
			case "United Arab Emirates":
			case "AE":
			case "united-arab-emirates":
				return 179;
			case "United Kingdom":
			case "GB":
			case "united-kingdom":
				return 180;
			case "United States of America":
			case "US":
			case "USA":
			case "united-states":
				return 181;
			case "Uruguay":
			case "UY":
			case "uruguay":
				return 182;
			case "Uzbekistan":
			case "UZ":
			case "uzbekistan":
				return  183;
			case "Vanuatu":
			case "VU":
			case "vanuatu":
				return 184;
			case "Venezuela":
			case "VE":
			case "venezuela":
				return 185;
			case "Viet Nam":
			case "VN":
			case "vietnam":
				return 186;
			case "Yemen":
			case "YE":
			case "yemen":
				return 187;
			case "Zambia":
			case "ZM":
			case "zambia":
				return 188;
			case "Zimbabwe":
			case "ZW":
			case "zimbabwe":
				return 189;
			default:
				return -1;
		}
	}
}
