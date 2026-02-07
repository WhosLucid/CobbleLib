/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  ca.landonjw.gooeylibs2.api.button.ButtonAction
 *  com.cobblemon.mod.common.pokemon.Pokemon
 *  lombok.Generated
 */
package com.whoslucid.cobblelib.action;

import ca.landonjw.gooeylibs2.api.button.ButtonAction;
import com.cobblemon.mod.common.pokemon.Pokemon;
import lombok.Generated;

public class PokemonButtonAction {
    private ButtonAction action;
    private Pokemon pokemon;

    public PokemonButtonAction(ButtonAction action, Pokemon pokemon) {
        this.action = action;
        this.pokemon = pokemon;
    }

    @Generated
    public ButtonAction getAction() {
        return this.action;
    }

    @Generated
    public Pokemon getPokemon() {
        return this.pokemon;
    }

    @Generated
    public void setAction(ButtonAction action) {
        this.action = action;
    }

    @Generated
    public void setPokemon(Pokemon pokemon) {
        this.pokemon = pokemon;
    }

    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof PokemonButtonAction)) {
            return false;
        }
        PokemonButtonAction other = (PokemonButtonAction)o;
        if (!other.canEqual(this)) {
            return false;
        }
        ButtonAction this$action = this.getAction();
        ButtonAction other$action = other.getAction();
        if (this$action == null ? other$action != null : !this$action.equals(other$action)) {
            return false;
        }
        Pokemon this$pokemon = this.getPokemon();
        Pokemon other$pokemon = other.getPokemon();
        return !(this$pokemon == null ? other$pokemon != null : !this$pokemon.equals(other$pokemon));
    }

    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof PokemonButtonAction;
    }

    @Generated
    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        ButtonAction $action = this.getAction();
        result = result * 59 + ($action == null ? 43 : $action.hashCode());
        Pokemon $pokemon = this.getPokemon();
        result = result * 59 + ($pokemon == null ? 43 : $pokemon.hashCode());
        return result;
    }

    @Generated
    public String toString() {
        return "PokemonButtonAction(action=" + String.valueOf(this.getAction()) + ", pokemon=" + String.valueOf(this.getPokemon()) + ")";
    }
}

