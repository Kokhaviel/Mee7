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

package fr.kokhaviel.api.hypixel.player.stats;

import com.google.gson.annotations.SerializedName;

public class Duels {

	@SerializedName("coins")
	int coins = 0;

	@SerializedName("wins")
	int wins = 0;

	@SerializedName("kills")
	int kills = 0;

	@SerializedName("deaths")
	int deaths = 0;

	@SerializedName("losses")
	int losses = 0;

	@SerializedName("blocks_placed")
	int blocksPlaced = 0;

	@SerializedName("games_played_duels")
	int gamesPlayed = 0;

	@SerializedName("melee_hits")
	int meleeHits = 0;

	@SerializedName("bow_shots")
	int bowShots = 0;

	@SerializedName("bow_hits")
	int bowHits = 0;

	@SerializedName("damage_dealt")
	int damageDealt = 0;

	@SerializedName("health_regenerated")
	int healthRegenerated = 0;

	@SerializedName("golden_apples_eaten")
	int gappleEaten = 0;

	@SerializedName("current_winstreak")
	int currentWinstreak = 0;

	@SerializedName("best_overall_winstreak")
	int bestWinstreak = 0;

	@SerializedName("active_cosmetictitle")
	String cosmeticTitle = "";

	@SerializedName("status_field")
	String status = "";

	@SerializedName("chat_enabled")
	String chatEnabled = "";

	@SerializedName("current_bridge_winstreak")
	int currentBridgeWinstreak = 0;

	@SerializedName("bridge_kills")
	int bridgeKills = 0;

	@SerializedName("bridge_deaths")
	int bridgeDeaths = 0;

	@SerializedName("bridge_duel_goals")
	int bridgeGoals = 0;

	@SerializedName("bridge_duel_losses")
	int bridgeLosses = 0;

	@SerializedName("bridge_duel_bow_shots")
	int bridgeBowShots = 0;

	@SerializedName("bridge_duel_damage_dealt")
	int bridgeDamageDealt = 0;

	@SerializedName("bridge_duel_health_regenerated")
	int bridgeHealthRegenerated = 0;

	@SerializedName("bridge_duel_melee_hits")
	int bridgeMeleeHits = 0;

	@SerializedName("current_skywars_winstreak")
	int currentSkywarsWinstreak = 0;

	@SerializedName("best_skywars_winstreak")
	int bestSkywarsWinstreak = 0;

	@SerializedName("sw_duel_kills")
	int skywarsKills = 0;

	@SerializedName("sw_duel_wins")
	int skywarsWins = 0;

	@SerializedName("sw_duel_deaths")
	int skywarsDeaths = 0;

	@SerializedName("sw_duel_losses")
	int skywarsLosses = 0;

	@SerializedName("sw_duel_health_regenerated")
	int skywarsHealthRegenerated = 0;

	@SerializedName("sw_duel_damage_dealt")
	int skywarsDamageDealt = 0;

	@SerializedName("sw_duel_melee_hits")
	int skywarsMeleeHits = 0;

	@SerializedName("sw_duel_bow_shots")
	int skywarsBowShots = 0;

	@SerializedName("sw_duel_bow_hits")
	int skywarsBowHits = 0;

	@SerializedName("bowspleef_duel_wins")
	int bowspleefWins = 0;

	@SerializedName("bowspleef_duel_losses")
	int bowspleefLosses = 0;

	@SerializedName("bowspleef_duel_rounds_played")
	int bowspleefRoundsPlayed = 0;

	@SerializedName("bowspleef_duel_bow_shoots")
	int bowspleefBowShots = 0;

	@SerializedName("current_uhc_winstreak")
	int currentUHCWinstreak = 0;

	@SerializedName("best_uhc_winstreak")
	int bestUHCWinstreak = 0;

	@SerializedName("uhc_duel_kills")
	int uhcKills = 0;

	@SerializedName("uhc_duel_wins")
	int uhcWins = 0;

	@SerializedName("uhc_duel_losses")
	int uhcLosses = 0;

	@SerializedName("uhc_duel_rounds_played")
	int uhcRoundsPlayed = 0;

	@SerializedName("uhc_duel_health_regenerated")
	int uhcHealthRegenerated = 0;

	@SerializedName("uhc_duel_damage_dealt")
	int uhcDamageDealt = 0;

	@SerializedName("uhc_duel_melee_hits")
	int uhcMeleeHits = 0;

	@SerializedName("uhc_duel_bow_shots")
	int uhcBowShots = 0;

	@SerializedName("uhc_duel_bow_hits")
	int uhcBowHits = 0;

	@SerializedName("uhc_duel_blocks_placed")
	int uhcBlocksPlaced = 0;

	@SerializedName("uhc_meetup_kills")
	int uhcMeetupKills = 0;

	@SerializedName("uhc_meetup_deaths")
	int uhcMeetupDeaths = 0;

	@SerializedName("uhc_meetup_losses")
	int uhcMeetupLosses = 0;

	@SerializedName("uhc_meetup_health_regenerated")
	int uhcMeetupHealthRegenerated = 0;

	@SerializedName("uhc_meetup_damage_dealt")
	int uhcMeetupDamageDealt = 0;

	@SerializedName("uhc_meetup_melee_hits")
	int uhcMeetupMeleeHits = 0;

	@SerializedName("uhc_meetup_bow_shots")
	int uhcMeetupBowShots = 0;

	@SerializedName("uhc_meetup_bow_hits")
	int uhcMeetupBowHits = 0;

	@SerializedName("uhc_meetup_rounds_played")
	int uhcMeetupRoundsPlayed = 0;

	@SerializedName("uhc_meetup_blocks_placed")
	int uhcMeetupBlocksPlaced = 0;

	@SerializedName("current_classic_winstreak")
	int currentClassicWinstreak = 0;

	@SerializedName("best_classic_winstreak")
	int bestClassicWinstreak = 0;

	@SerializedName("classic_duel_kills")
	int classicKills = 0;

	@SerializedName("classic_duel_wins")
	int classicWins = 0;

	@SerializedName("classic_duel_losses")
	int classicLosses = 0;

	@SerializedName("classic_duel_health_regenerated")
	int classicHealthRegenerated = 0;

	@SerializedName("classic_duel_damage_dealt")
	int classicDamageDealt = 0;

	@SerializedName("classic_duel_melee_hits")
	int classicMeleeHits = 0;

	@SerializedName("classic_duel_bow_hits")
	int classicBowHits = 0;

	@SerializedName("classic_duel_rounds_played")
	int classicRoundsPlayed = 0;

	@SerializedName("current_op_winstreak")
	int currentOpWinstreak = 0;

	@SerializedName("best_op_winstreak")
	int bestOpWinstreak = 0;

	@SerializedName("op_duel_kills")
	int opKills = 0;

	@SerializedName("op_duel_wins")
	int opWins = 0;

	@SerializedName("op_duel_losses")
	int opLosses = 0;

	@SerializedName("op_duel_health_regenerated")
	int opHealthRegenerated = 0;

	@SerializedName("op_duel_damage_dealt")
	int opDamageDealt = 0;

	@SerializedName("op_duel_melee_hits")
	int opMeleeHits = 0;

	@SerializedName("op_duel_rounds_played")
	int opRoundsPlayed = 0;

	@SerializedName("current_bow_winstreak")
	int currentBowWinstreak = 0;

	@SerializedName("bow_duel_wins")
	int bowWins = 0;

	@SerializedName("bow_duel_losses")
	int bowLosses = 0;

	@SerializedName("bow_duel_bow_shots")
	int bowBowShots = 0;

	@SerializedName("bow_duel_bow_hits")
	int bowBowHits = 0;

	@SerializedName("bow_duel_health_regenerated")
	int bowHealthRegenerated = 0;

	@SerializedName("bow_duel_damage_dealt")
	int bowDamageDealt = 0;

	@SerializedName("bow_duel_rounds_played")
	int bowRoundsPlayed = 0;

	@SerializedName("current_sumo_winstreak")
	int currentSumoWinstreak = 0;

	@SerializedName("best_sumo_winstreak")
	int bestSumoWinstreak = 0;

	@SerializedName("sumo_duel_kills")
	int sumoKills = 0;

	@SerializedName("sumo_duel_wins")
	int sumoWins = 0;

	@SerializedName("sumo_duel_losses")
	int sumoLosses = 0;

	@SerializedName("sumo_duel_melee_hits")
	int sumoMeleeHits = 0;

	@SerializedName("sumo_duel_rounds_played")
	int sumoRoundsPlayed = 0;

	@SerializedName("combo_duel_golden_apples_eaten")
	int comboGapplesEaten = 0;

	@SerializedName("combo_duel_melee_hits")
	int comboMeleeHits = 0;

	@SerializedName("combo_duel_rounds_played")
	int comboRoundsPlayed = 0;

	@SerializedName("potion_duel_damage_dealt")
	int potionDamageDealt = 0;

	@SerializedName("potion_duel_heal_pots_used")
	int potionPotsUsed = 0;

	@SerializedName("potion_duel_health_regenerated")
	int potionHealthRegenerated = 0;

	@SerializedName("potion_duel_melee_hits")
	int potionMeleeHits = 0;

	@SerializedName("potion_duel_rounds_played")
	int potionRoundsPlayed = 0;

	@SerializedName("mw_duel_class")
	String megaWallsClass = "";

	@SerializedName("mw_duel_health_regenerated")
	int megaWallsHealthRegenerated = 0;

	@SerializedName("mw_duel_damage_dealt")
	int megaWallsDamageDealt = 0;

	@SerializedName("mw_duel_melee_hits")
	int megaWallsMeleeHits = 0;

	@SerializedName("mw_duel_bow_shots")
	int megaWallsBowShots = 0;

	@SerializedName("mw_duel_bow_hits")
	int megaWallsBowHits = 0;

	@SerializedName("mw_duel_rounds_played")
	int megaWallsRoundsPlayed = 0;

	@SerializedName("blitz_duels_kit")
	String blitzKit = "";

	@SerializedName("current_blitz_winstreak")
	int currentBlitzWinstreak = 0;

	@SerializedName("best_blitz_winstreak")
	int bestBlitzWinstreak = 0;

	@SerializedName("blitz_duel_health_regenerated")
	int blitzHealthRegenerated = 0;

	@SerializedName("blitz_duel_kills")
	int blitzKills = 0;

	@SerializedName("blitz_duel_wins")
	int blitzWins = 0;

	@SerializedName("blitz_duel_losses")
	int blitzLosses = 0;

	@SerializedName("blitz_duel_melee_hits")
	int blitzMeleeHits = 0;

	@SerializedName("blitz_duel_damage_dealt")
	int blitzDamageDealt = 0;

	@SerializedName("blitz_duel_rounds_played")
	int blitzRoundsPlayed = 0;

	@SerializedName("blitz_duel_blocks_placed")
	int blitzBlocksPlaced = 0;


	public int getCoins() {
		return coins;
	}

	public int getWins() {
		return wins;
	}

	public int getKills() {
		return kills;
	}

	public int getDeaths() {
		return deaths;
	}

	public int getLosses() {
		return losses;
	}

	public int getBlocksPlaced() {
		return blocksPlaced;
	}

	public int getGamesPlayed() {
		return gamesPlayed;
	}

	public int getMeleeHits() {
		return meleeHits;
	}

	public int getBowShots() {
		return bowShots;
	}

	public int getBowHits() {
		return bowHits;
	}

	public int getDamageDealt() {
		return damageDealt;
	}

	public int getHealthRegenerated() {
		return healthRegenerated;
	}

	public int getGappleEaten() {
		return gappleEaten;
	}

	public int getCurrentWinstreak() {
		return currentWinstreak;
	}

	public int getBestWinstreak() {
		return bestWinstreak;
	}

	public String getCosmeticTitle() {
		return cosmeticTitle;
	}

	public String getStatus() {
		return status;
	}

	public String getChatEnabled() {
		return chatEnabled;
	}

	public int getCurrentBridgeWinstreak() {
		return currentBridgeWinstreak;
	}

	public int getBridgeKills() {
		return bridgeKills;
	}

	public int getBridgeDeaths() {
		return bridgeDeaths;
	}

	public int getBridgeGoals() {
		return bridgeGoals;
	}

	public int getBridgeLosses() {
		return bridgeLosses;
	}

	public int getBridgeBowShots() {
		return bridgeBowShots;
	}

	public int getBridgeDamageDealt() {
		return bridgeDamageDealt;
	}

	public int getBridgeHealthRegenerated() {
		return bridgeHealthRegenerated;
	}

	public int getBridgeMeleeHits() {
		return bridgeMeleeHits;
	}

	public int getCurrentSkywarsWinstreak() {
		return currentSkywarsWinstreak;
	}

	public int getBestSkywarsWinstreak() {
		return bestSkywarsWinstreak;
	}

	public int getSkywarsKills() {
		return skywarsKills;
	}

	public int getSkywarsWins() {
		return skywarsWins;
	}

	public int getSkywarsDeaths() {
		return skywarsDeaths;
	}

	public int getSkywarsLosses() {
		return skywarsLosses;
	}

	public int getSkywarsHealthRegenerated() {
		return skywarsHealthRegenerated;
	}

	public int getSkywarsDamageDealt() {
		return skywarsDamageDealt;
	}

	public int getSkywarsMeleeHits() {
		return skywarsMeleeHits;
	}

	public int getSkywarsBowShots() {
		return skywarsBowShots;
	}

	public int getSkywarsBowHits() {
		return skywarsBowHits;
	}

	public int getBowspleefWins() {
		return bowspleefWins;
	}

	public int getBowspleefLosses() {
		return bowspleefLosses;
	}

	public int getBowspleefRoundsPlayed() {
		return bowspleefRoundsPlayed;
	}

	public int getBowspleefBowShots() {
		return bowspleefBowShots;
	}

	public int getCurrentUHCWinstreak() {
		return currentUHCWinstreak;
	}

	public int getBestUHCWinstreak() {
		return bestUHCWinstreak;
	}

	public int getUhcKills() {
		return uhcKills;
	}

	public int getUhcWins() {
		return uhcWins;
	}

	public int getUhcLosses() {
		return uhcLosses;
	}

	public int getUhcRoundsPlayed() {
		return uhcRoundsPlayed;
	}

	public int getUhcHealthRegenerated() {
		return uhcHealthRegenerated;
	}

	public int getUhcDamageDealt() {
		return uhcDamageDealt;
	}

	public int getUhcMeleeHits() {
		return uhcMeleeHits;
	}

	public int getUhcBowShots() {
		return uhcBowShots;
	}

	public int getUhcBowHits() {
		return uhcBowHits;
	}

	public int getUhcBlocksPlaced() {
		return uhcBlocksPlaced;
	}

	public int getUhcMeetupKills() {
		return uhcMeetupKills;
	}

	public int getUhcMeetupDeaths() {
		return uhcMeetupDeaths;
	}

	public int getUhcMeetupLosses() {
		return uhcMeetupLosses;
	}

	public int getUhcMeetupHealthRegenerated() {
		return uhcMeetupHealthRegenerated;
	}

	public int getUhcMeetupDamageDealt() {
		return uhcMeetupDamageDealt;
	}

	public int getUhcMeetupMeleeHits() {
		return uhcMeetupMeleeHits;
	}

	public int getUhcMeetupBowShots() {
		return uhcMeetupBowShots;
	}

	public int getUhcMeetupBowHits() {
		return uhcMeetupBowHits;
	}

	public int getUhcMeetupRoundsPlayed() {
		return uhcMeetupRoundsPlayed;
	}

	public int getUhcMeetupBlocksPlaced() {
		return uhcMeetupBlocksPlaced;
	}

	public int getCurrentClassicWinstreak() {
		return currentClassicWinstreak;
	}

	public int getBestClassicWinstreak() {
		return bestClassicWinstreak;
	}

	public int getClassicKills() {
		return classicKills;
	}

	public int getClassicWins() {
		return classicWins;
	}

	public int getClassicLosses() {
		return classicLosses;
	}

	public int getClassicHealthRegenerated() {
		return classicHealthRegenerated;
	}

	public int getClassicDamageDealt() {
		return classicDamageDealt;
	}

	public int getClassicMeleeHits() {
		return classicMeleeHits;
	}

	public int getClassicBowHits() {
		return classicBowHits;
	}

	public int getClassicRoundsPlayed() {
		return classicRoundsPlayed;
	}

	public int getCurrentOpWinstreak() {
		return currentOpWinstreak;
	}

	public int getBestOpWinstreak() {
		return bestOpWinstreak;
	}

	public int getOpKills() {
		return opKills;
	}

	public int getOpWins() {
		return opWins;
	}

	public int getOpLosses() {
		return opLosses;
	}

	public int getOpHealthRegenerated() {
		return opHealthRegenerated;
	}

	public int getOpDamageDealt() {
		return opDamageDealt;
	}

	public int getOpMeleeHits() {
		return opMeleeHits;
	}

	public int getOpRoundsPlayed() {
		return opRoundsPlayed;
	}

	public int getCurrentBowWinstreak() {
		return currentBowWinstreak;
	}

	public int getBowWins() {
		return bowWins;
	}

	public int getBowLosses() {
		return bowLosses;
	}

	public int getBowBowShots() {
		return bowBowShots;
	}

	public int getBowBowHits() {
		return bowBowHits;
	}

	public int getBowHealthRegenerated() {
		return bowHealthRegenerated;
	}

	public int getBowDamageDealt() {
		return bowDamageDealt;
	}

	public int getBowRoundsPlayed() {
		return bowRoundsPlayed;
	}

	public int getCurrentSumoWinstreak() {
		return currentSumoWinstreak;
	}

	public int getBestSumoWinstreak() {
		return bestSumoWinstreak;
	}

	public int getSumoKills() {
		return sumoKills;
	}

	public int getSumoWins() {
		return sumoWins;
	}

	public int getSumoLosses() {
		return sumoLosses;
	}

	public int getSumoMeleeHits() {
		return sumoMeleeHits;
	}

	public int getSumoRoundsPlayed() {
		return sumoRoundsPlayed;
	}

	public int getComboGapplesEaten() {
		return comboGapplesEaten;
	}

	public int getComboMeleeHits() {
		return comboMeleeHits;
	}

	public int getComboRoundsPlayed() {
		return comboRoundsPlayed;
	}

	public int getPotionDamageDealt() {
		return potionDamageDealt;
	}

	public int getPotionPotsUsed() {
		return potionPotsUsed;
	}

	public int getPotionHealthRegenerated() {
		return potionHealthRegenerated;
	}

	public int getPotionMeleeHits() {
		return potionMeleeHits;
	}

	public int getPotionRoundsPlayed() {
		return potionRoundsPlayed;
	}

	public String getMegaWallsClass() {
		return megaWallsClass;
	}

	public int getMegaWallsHealthRegenerated() {
		return megaWallsHealthRegenerated;
	}

	public int getMegaWallsDamageDealt() {
		return megaWallsDamageDealt;
	}

	public int getMegaWallsMeleeHits() {
		return megaWallsMeleeHits;
	}

	public int getMegaWallsBowShots() {
		return megaWallsBowShots;
	}

	public int getMegaWallsBowHits() {
		return megaWallsBowHits;
	}

	public int getMegaWallsRoundsPlayed() {
		return megaWallsRoundsPlayed;
	}

	public String getBlitzKit() {
		return blitzKit;
	}

	public int getCurrentBlitzWinstreak() {
		return currentBlitzWinstreak;
	}

	public int getBestBlitzWinstreak() {
		return bestBlitzWinstreak;
	}

	public int getBlitzHealthRegenerated() {
		return blitzHealthRegenerated;
	}

	public int getBlitzKills() {
		return blitzKills;
	}

	public int getBlitzWins() {
		return blitzWins;
	}

	public int getBlitzLosses() {
		return blitzLosses;
	}

	public int getBlitzMeleeHits() {
		return blitzMeleeHits;
	}

	public int getBlitzDamageDealt() {
		return blitzDamageDealt;
	}

	public int getBlitzRoundsPlayed() {
		return blitzRoundsPlayed;
	}

	public int getBlitzBlocksPlaced() {
		return blitzBlocksPlaced;
	}
}
