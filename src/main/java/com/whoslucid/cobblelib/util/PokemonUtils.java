/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cobblemon.mod.common.api.abilities.AbilityPool
 *  com.cobblemon.mod.common.api.abilities.PotentialAbility
 *  com.cobblemon.mod.common.api.moves.Move
 *  com.cobblemon.mod.common.api.pokemon.evolution.PreEvolution
 *  com.cobblemon.mod.common.api.pokemon.stats.Stat
 *  com.cobblemon.mod.common.api.pokemon.stats.Stats
 *  com.cobblemon.mod.common.pokemon.EVs
 *  com.cobblemon.mod.common.pokemon.FormData
 *  com.cobblemon.mod.common.pokemon.IVs
 *  com.cobblemon.mod.common.pokemon.Nature
 *  com.cobblemon.mod.common.pokemon.Pokemon
 *  com.cobblemon.mod.common.pokemon.Species
 */
package com.whoslucid.cobblelib.util;

import com.cobblemon.mod.common.api.abilities.AbilityPool;
import com.cobblemon.mod.common.api.abilities.PotentialAbility;
import com.cobblemon.mod.common.api.moves.Move;
import com.cobblemon.mod.common.api.pokemon.evolution.PreEvolution;
import com.cobblemon.mod.common.api.pokemon.stats.Stat;
import com.cobblemon.mod.common.api.pokemon.stats.Stats;
import com.cobblemon.mod.common.pokemon.EVs;
import com.cobblemon.mod.common.pokemon.FormData;
import com.cobblemon.mod.common.pokemon.IVs;
import com.cobblemon.mod.common.pokemon.Nature;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.mod.common.pokemon.Species;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class PokemonUtils {
    public static final List<Stats> STATS_LIST = List.of(Stats.HP, Stats.ATTACK, Stats.DEFENCE, Stats.SPECIAL_ATTACK, Stats.SPECIAL_DEFENCE, Stats.SPEED);

    public static String replace(Pokemon pokemon) {
        return pokemon.getDisplayName(false).getString();
    }

    public static String replace(String text, Pokemon pokemon) {
        if (text == null) {
            return "";
        }
        String pokemonName = pokemon.getDisplayName(false).getString();
        String result = text.replace("%pokemon%", pokemonName).replace("%level%", String.valueOf(pokemon.getLevel())).replace("%shiny%", pokemon.getShiny() ? "Yes" : "No").replace("%nature%", PokemonUtils.getNatureTranslate(pokemon.getNature())).replace("%ability%", PokemonUtils.capitalize(pokemon.getAbility().getName())).replace("%gender%", pokemon.getGender().name()).replace("%form%", pokemon.getForm().getName()).replace("%ball%", pokemon.getCaughtBall().getName().toString()).replace("%ivs_total%", String.valueOf(PokemonUtils.getIvsTotal(pokemon.getIvs()))).replace("%evs_total%", String.valueOf(PokemonUtils.getEvsTotal(pokemon.getEvs()))).replace("%ivs_avg%", String.format("%.1f", Float.valueOf(PokemonUtils.getIvsAverage(pokemon.getIvs())))).replace("%evs_avg%", String.format("%.1f", Float.valueOf(PokemonUtils.getEvsAverage(pokemon.getEvs())))).replace("%ivs_perfect%", String.valueOf(PokemonUtils.getTotalPerfectIvs(pokemon.getIvs())));
        IVs ivs = pokemon.getIvs();
        result = result.replace("%iv_hp%", String.valueOf(ivs.getOrDefault((Stat)Stats.HP))).replace("%iv_atk%", String.valueOf(ivs.getOrDefault((Stat)Stats.ATTACK))).replace("%iv_def%", String.valueOf(ivs.getOrDefault((Stat)Stats.DEFENCE))).replace("%iv_spa%", String.valueOf(ivs.getOrDefault((Stat)Stats.SPECIAL_ATTACK))).replace("%iv_spd%", String.valueOf(ivs.getOrDefault((Stat)Stats.SPECIAL_DEFENCE))).replace("%iv_spe%", String.valueOf(ivs.getOrDefault((Stat)Stats.SPEED)));
        EVs evs = pokemon.getEvs();
        result = result.replace("%ev_hp%", String.valueOf(evs.getOrDefault((Stat)Stats.HP))).replace("%ev_atk%", String.valueOf(evs.getOrDefault((Stat)Stats.ATTACK))).replace("%ev_def%", String.valueOf(evs.getOrDefault((Stat)Stats.DEFENCE))).replace("%ev_spa%", String.valueOf(evs.getOrDefault((Stat)Stats.SPECIAL_ATTACK))).replace("%ev_spd%", String.valueOf(evs.getOrDefault((Stat)Stats.SPECIAL_DEFENCE))).replace("%ev_spe%", String.valueOf(evs.getOrDefault((Stat)Stats.SPEED)));
        return result;
    }

    public static List<String> replace(List<String> lore, Pokemon pokemon) {
        ArrayList<String> result = new ArrayList<String>();
        for (String line : lore) {
            result.add(PokemonUtils.replace(line, pokemon));
        }
        return result;
    }

    public static List<String> replaceLore(Pokemon pokemon) {
        ArrayList<String> lore = new ArrayList<String>();
        lore.add("&7Level: &e" + pokemon.getLevel());
        lore.add("&7Nature: &e" + PokemonUtils.getNatureTranslate(pokemon.getNature()));
        lore.add("&7Ability: &e" + PokemonUtils.capitalize(pokemon.getAbility().getName()));
        lore.add("&7Shiny: &e" + (pokemon.getShiny() ? "Yes" : "No"));
        lore.add("&7IVs: &e" + PokemonUtils.getIvsTotal(pokemon.getIvs()) + "/186");
        lore.add("&7EVs: &e" + PokemonUtils.getEvsTotal(pokemon.getEvs()) + "/510");
        return lore;
    }

    public static Pokemon getFirstEvolution(Pokemon pokemon) {
        try {
            Species preEvoSpecies;
            Species species = pokemon.getSpecies();
            PreEvolution preEvolution = species.getPreEvolution();
            if (preEvolution != null && (preEvoSpecies = preEvolution.getSpecies().create(1).getSpecies()) != null) {
                Pokemon preEvo = new Pokemon();
                preEvo.setSpecies(preEvoSpecies);
                return PokemonUtils.getFirstEvolution(preEvo);
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        return pokemon;
    }

    public static String getMoveTranslate(Move move) {
        return PokemonUtils.capitalize(move.getName());
    }

    public static int getEvsTotal(EVs evs) {
        int total = 0;
        for (Stats stat : STATS_LIST) {
            total += evs.getOrDefault((Stat)stat);
        }
        return total;
    }

    public static int getIvsTotal(IVs ivs) {
        int total = 0;
        for (Stats stat : STATS_LIST) {
            total += ivs.getOrDefault((Stat)stat);
        }
        return total;
    }

    public static float getIvsAverage(IVs ivs) {
        return (float)PokemonUtils.getIvsTotal(ivs) / (float)STATS_LIST.size();
    }

    public static float getEvsAverage(EVs evs) {
        return (float)PokemonUtils.getEvsTotal(evs) / (float)STATS_LIST.size();
    }

    public static int getTotalPerfectIvs(IVs ivs) {
        int count = 0;
        for (Stats stat : STATS_LIST) {
            if (ivs.getOrDefault((Stat)stat) != 31) continue;
            ++count;
        }
        return count;
    }

    public static String getNatureTranslate(Nature nature) {
        return PokemonUtils.capitalize(nature.getName().getPath());
    }

    public static String capitalize(String s) {
        if (s == null || s.isEmpty()) {
            return s;
        }
        String[] parts = s.split("_");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < parts.length; ++i) {
            if (i > 0) {
                sb.append(" ");
            }
            if (parts[i].isEmpty()) continue;
            sb.append(Character.toUpperCase(parts[i].charAt(0)));
            if (parts[i].length() <= 1) continue;
            sb.append(parts[i].substring(1));
        }
        return sb.toString();
    }

    public static boolean isAH(Pokemon pokemon) {
        try {
            FormData form = pokemon.getForm();
            AbilityPool abilities = form.getAbilities();
            String currentAbilityName = pokemon.getAbility().getName().toLowerCase();
            int index = 0;
            for (PotentialAbility potentialAbility : abilities) {
                if (potentialAbility.getTemplate().getName().toLowerCase().equals(currentAbilityName)) {
                    return index >= 2;
                }
                ++index;
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        return false;
    }

    public static boolean isBreedable(Pokemon pokemon) {
        return !pokemon.getPersistentData().getBoolean("breedable");
    }

    public static String getRarityS(Pokemon pokemon) {
        try {
            HashSet labels = pokemon.getSpecies().getLabels();
            if (labels.contains("legendary")) {
                return "legendary";
            }
            if (labels.contains("mythical")) {
                return "mythical";
            }
            if (labels.contains("ultra_beast")) {
                return "ultra_beast";
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        return "common";
    }
}

