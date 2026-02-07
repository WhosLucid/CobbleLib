/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cobblemon.mod.common.api.types.ElementalType
 *  com.cobblemon.mod.common.pokemon.Pokemon
 *  lombok.Generated
 */
package com.whoslucid.cobblelib.Model;

import com.cobblemon.mod.common.api.types.ElementalType;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.whoslucid.cobblelib.util.PokemonUtils;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.Generated;

public class PokemonBlackList {
    private boolean onlyImplemented;
    private boolean allowEvolutions = true;
    private Set<String> properties = new HashSet<String>();
    private Set<String> pokemons = new HashSet<String>();
    private Set<String> forms = new HashSet<String>();
    private Set<String> aspects = new HashSet<String>();
    private Set<String> labels = new HashSet<String>();
    private Set<String> types = new HashSet<String>();
    private Set<String> rarities = new HashSet<String>();
    private Map<String, List<Object>> persistentDataMap = new HashMap<String, List<Object>>();

    public PokemonBlackList() {
        this.pokemons.add("egg");
        this.pokemons.add("pokestop");
        this.labels.add("legendary");
        this.labels.add("mythical");
        this.labels.add("ultra_beast");
    }

    public boolean isBlackListed(Pokemon pokemon) {
        if (pokemon == null) {
            return true;
        }
        String showdownId = pokemon.showdownId();
        if (this.pokemons.contains(showdownId)) {
            return true;
        }
        String formName = pokemon.getForm().getName();
        if (!formName.isEmpty() && this.forms.contains(formName.toLowerCase())) {
            return true;
        }
        for (String aspect : pokemon.getAspects()) {
            if (!this.aspects.contains(aspect.toLowerCase())) continue;
            return true;
        }
        try {
            Set<String> speciesLabels = pokemon.getSpecies().getLabels();
            for (String label : this.labels) {
                if (!speciesLabels.contains(label)) continue;
                return true;
            }
        }
        catch (Exception speciesLabels) {
            // empty catch block
        }
        try {
            Iterable<ElementalType> pokemonTypes = pokemon.getForm().getTypes();
            for (ElementalType type : pokemonTypes) {
                if (!this.types.contains(type.getName().toLowerCase())) continue;
                return true;
            }
        }
        catch (Exception pokemonTypes) {
            // empty catch block
        }
        try {
            String rarity = PokemonUtils.getRarityS(pokemon);
            if (this.rarities.contains(rarity.toLowerCase())) {
                return true;
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        return this.onlyImplemented && !pokemon.getSpecies().getImplemented();
    }

    @Generated
    public boolean isOnlyImplemented() {
        return this.onlyImplemented;
    }

    @Generated
    public boolean isAllowEvolutions() {
        return this.allowEvolutions;
    }

    @Generated
    public Set<String> getProperties() {
        return this.properties;
    }

    @Generated
    public Set<String> getPokemons() {
        return this.pokemons;
    }

    @Generated
    public Set<String> getForms() {
        return this.forms;
    }

    @Generated
    public Set<String> getAspects() {
        return this.aspects;
    }

    @Generated
    public Set<String> getLabels() {
        return this.labels;
    }

    @Generated
    public Set<String> getTypes() {
        return this.types;
    }

    @Generated
    public Set<String> getRarities() {
        return this.rarities;
    }

    @Generated
    public Map<String, List<Object>> getPersistentDataMap() {
        return this.persistentDataMap;
    }

    @Generated
    public void setOnlyImplemented(boolean onlyImplemented) {
        this.onlyImplemented = onlyImplemented;
    }

    @Generated
    public void setAllowEvolutions(boolean allowEvolutions) {
        this.allowEvolutions = allowEvolutions;
    }

    @Generated
    public void setProperties(Set<String> properties) {
        this.properties = properties;
    }

    @Generated
    public void setPokemons(Set<String> pokemons) {
        this.pokemons = pokemons;
    }

    @Generated
    public void setForms(Set<String> forms) {
        this.forms = forms;
    }

    @Generated
    public void setAspects(Set<String> aspects) {
        this.aspects = aspects;
    }

    @Generated
    public void setLabels(Set<String> labels) {
        this.labels = labels;
    }

    @Generated
    public void setTypes(Set<String> types) {
        this.types = types;
    }

    @Generated
    public void setRarities(Set<String> rarities) {
        this.rarities = rarities;
    }

    @Generated
    public void setPersistentDataMap(Map<String, List<Object>> persistentDataMap) {
        this.persistentDataMap = persistentDataMap;
    }
}

