/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  ca.landonjw.gooeylibs2.api.button.ButtonAction
 *  com.cobblemon.mod.common.pokemon.Pokemon
 *  lombok.Generated
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.item.ItemStack
 */
package com.whoslucid.cobblelib.ui.builds;

import ca.landonjw.gooeylibs2.api.button.ButtonAction;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.whoslucid.cobblelib.Model.PokemonBlackList;
import com.whoslucid.cobblelib.action.PokemonButtonAction;
import com.whoslucid.cobblelib.ui.ConfirmMenu;
import com.whoslucid.cobblelib.ui.PartyPcMenu;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import lombok.Generated;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

public class PartyPcMenuBuilder {
    private ServerPlayer player;
    private PartyPcMenu partyPcMenu;
    private ConfirmMenu confirmMenu;
    private List<String> lorePokemon;
    private PokemonBlackList blackList;
    private Consumer<ButtonAction> closeAction;
    private Consumer<PokemonButtonAction> pokemonAction;
    private Function<Pokemon, ItemStack> itemStackProvider;
    private BiConsumer<Pokemon, List<String>> loreModifier;
    private Predicate<Pokemon> customFilter;

    public PartyPcMenuBuilder setCustomFilter(Predicate<Pokemon> customFilter) {
        this.customFilter = customFilter;
        return this;
    }

    public PartyPcMenuBuilder setPlayer(ServerPlayer player) {
        this.player = player;
        return this;
    }

    public PartyPcMenuBuilder setItemStackProvider(Function<Pokemon, ItemStack> itemStackProvider) {
        this.itemStackProvider = itemStackProvider;
        return this;
    }

    public PartyPcMenuBuilder setPokemonAction(Consumer<PokemonButtonAction> pokemonAction) {
        this.pokemonAction = pokemonAction;
        return this;
    }

    public PartyPcMenuBuilder setCloseAction(Consumer<ButtonAction> closeAction) {
        this.closeAction = closeAction;
        return this;
    }

    public PartyPcMenuBuilder setBlackList(PokemonBlackList blackList) {
        this.blackList = blackList;
        return this;
    }

    public PartyPcMenuBuilder setLorePokemon(List<String> lorePokemon) {
        this.lorePokemon = lorePokemon;
        return this;
    }

    public PartyPcMenuBuilder setLoreModifier(BiConsumer<Pokemon, List<String>> loreModifier) {
        this.loreModifier = loreModifier;
        return this;
    }

    public PartyPcMenuBuilder setConfirmMenu(ConfirmMenu confirmMenu) {
        this.confirmMenu = confirmMenu;
        return this;
    }

    public PartyPcMenuBuilder build() {
        if (this.partyPcMenu == null) {
            this.partyPcMenu = new PartyPcMenu();
        }
        if (this.player == null) {
            throw new IllegalArgumentException("Player cannot be null");
        }
        return this;
    }

    @Generated
    public PartyPcMenuBuilder() {
    }

    @Generated
    public ServerPlayer getPlayer() {
        return this.player;
    }

    @Generated
    public PartyPcMenu getPartyPcMenu() {
        return this.partyPcMenu;
    }

    @Generated
    public ConfirmMenu getConfirmMenu() {
        return this.confirmMenu;
    }

    @Generated
    public List<String> getLorePokemon() {
        return this.lorePokemon;
    }

    @Generated
    public PokemonBlackList getBlackList() {
        return this.blackList;
    }

    @Generated
    public Consumer<ButtonAction> getCloseAction() {
        return this.closeAction;
    }

    @Generated
    public Consumer<PokemonButtonAction> getPokemonAction() {
        return this.pokemonAction;
    }

    @Generated
    public Function<Pokemon, ItemStack> getItemStackProvider() {
        return this.itemStackProvider;
    }

    @Generated
    public BiConsumer<Pokemon, List<String>> getLoreModifier() {
        return this.loreModifier;
    }

    @Generated
    public Predicate<Pokemon> getCustomFilter() {
        return this.customFilter;
    }

    @Generated
    public void setPartyPcMenu(PartyPcMenu partyPcMenu) {
        this.partyPcMenu = partyPcMenu;
    }

    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof PartyPcMenuBuilder)) {
            return false;
        }
        PartyPcMenuBuilder other = (PartyPcMenuBuilder)o;
        if (!other.canEqual(this)) {
            return false;
        }
        ServerPlayer this$player = this.getPlayer();
        ServerPlayer other$player = other.getPlayer();
        if (this$player == null ? other$player != null : !this$player.equals(other$player)) {
            return false;
        }
        PartyPcMenu this$partyPcMenu = this.getPartyPcMenu();
        PartyPcMenu other$partyPcMenu = other.getPartyPcMenu();
        if (this$partyPcMenu == null ? other$partyPcMenu != null : !((Object)this$partyPcMenu).equals(other$partyPcMenu)) {
            return false;
        }
        ConfirmMenu this$confirmMenu = this.getConfirmMenu();
        ConfirmMenu other$confirmMenu = other.getConfirmMenu();
        if (this$confirmMenu == null ? other$confirmMenu != null : !((Object)this$confirmMenu).equals(other$confirmMenu)) {
            return false;
        }
        List<String> this$lorePokemon = this.getLorePokemon();
        List<String> other$lorePokemon = other.getLorePokemon();
        if (this$lorePokemon == null ? other$lorePokemon != null : !((Object)this$lorePokemon).equals(other$lorePokemon)) {
            return false;
        }
        PokemonBlackList this$blackList = this.getBlackList();
        PokemonBlackList other$blackList = other.getBlackList();
        if (this$blackList == null ? other$blackList != null : !this$blackList.equals(other$blackList)) {
            return false;
        }
        Consumer<ButtonAction> this$closeAction = this.getCloseAction();
        Consumer<ButtonAction> other$closeAction = other.getCloseAction();
        if (this$closeAction == null ? other$closeAction != null : !this$closeAction.equals(other$closeAction)) {
            return false;
        }
        Consumer<PokemonButtonAction> this$pokemonAction = this.getPokemonAction();
        Consumer<PokemonButtonAction> other$pokemonAction = other.getPokemonAction();
        if (this$pokemonAction == null ? other$pokemonAction != null : !this$pokemonAction.equals(other$pokemonAction)) {
            return false;
        }
        Function<Pokemon, ItemStack> this$itemStackProvider = this.getItemStackProvider();
        Function<Pokemon, ItemStack> other$itemStackProvider = other.getItemStackProvider();
        if (this$itemStackProvider == null ? other$itemStackProvider != null : !this$itemStackProvider.equals(other$itemStackProvider)) {
            return false;
        }
        BiConsumer<Pokemon, List<String>> this$loreModifier = this.getLoreModifier();
        BiConsumer<Pokemon, List<String>> other$loreModifier = other.getLoreModifier();
        if (this$loreModifier == null ? other$loreModifier != null : !this$loreModifier.equals(other$loreModifier)) {
            return false;
        }
        Predicate<Pokemon> this$customFilter = this.getCustomFilter();
        Predicate<Pokemon> other$customFilter = other.getCustomFilter();
        return !(this$customFilter == null ? other$customFilter != null : !this$customFilter.equals(other$customFilter));
    }

    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof PartyPcMenuBuilder;
    }

    @Generated
    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        ServerPlayer $player = this.getPlayer();
        result = result * 59 + ($player == null ? 43 : $player.hashCode());
        PartyPcMenu $partyPcMenu = this.getPartyPcMenu();
        result = result * 59 + ($partyPcMenu == null ? 43 : ((Object)$partyPcMenu).hashCode());
        ConfirmMenu $confirmMenu = this.getConfirmMenu();
        result = result * 59 + ($confirmMenu == null ? 43 : ((Object)$confirmMenu).hashCode());
        List<String> $lorePokemon = this.getLorePokemon();
        result = result * 59 + ($lorePokemon == null ? 43 : ((Object)$lorePokemon).hashCode());
        PokemonBlackList $blackList = this.getBlackList();
        result = result * 59 + ($blackList == null ? 43 : $blackList.hashCode());
        Consumer<ButtonAction> $closeAction = this.getCloseAction();
        result = result * 59 + ($closeAction == null ? 43 : $closeAction.hashCode());
        Consumer<PokemonButtonAction> $pokemonAction = this.getPokemonAction();
        result = result * 59 + ($pokemonAction == null ? 43 : $pokemonAction.hashCode());
        Function<Pokemon, ItemStack> $itemStackProvider = this.getItemStackProvider();
        result = result * 59 + ($itemStackProvider == null ? 43 : $itemStackProvider.hashCode());
        BiConsumer<Pokemon, List<String>> $loreModifier = this.getLoreModifier();
        result = result * 59 + ($loreModifier == null ? 43 : $loreModifier.hashCode());
        Predicate<Pokemon> $customFilter = this.getCustomFilter();
        result = result * 59 + ($customFilter == null ? 43 : $customFilter.hashCode());
        return result;
    }

    @Generated
    public String toString() {
        return "PartyPcMenuBuilder(player=" + String.valueOf(this.getPlayer()) + ", partyPcMenu=" + String.valueOf(this.getPartyPcMenu()) + ", confirmMenu=" + String.valueOf(this.getConfirmMenu()) + ", lorePokemon=" + String.valueOf(this.getLorePokemon()) + ", blackList=" + String.valueOf(this.getBlackList()) + ", closeAction=" + String.valueOf(this.getCloseAction()) + ", pokemonAction=" + String.valueOf(this.getPokemonAction()) + ", itemStackProvider=" + String.valueOf(this.getItemStackProvider()) + ", loreModifier=" + String.valueOf(this.getLoreModifier()) + ", customFilter=" + String.valueOf(this.getCustomFilter()) + ")";
    }
}

