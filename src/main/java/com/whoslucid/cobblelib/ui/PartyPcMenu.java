/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  ca.landonjw.gooeylibs2.api.UIManager
 *  ca.landonjw.gooeylibs2.api.button.Button
 *  ca.landonjw.gooeylibs2.api.button.ButtonAction
 *  ca.landonjw.gooeylibs2.api.button.GooeyButton
 *  ca.landonjw.gooeylibs2.api.page.GooeyPage
 *  ca.landonjw.gooeylibs2.api.page.Page
 *  ca.landonjw.gooeylibs2.api.template.Template
 *  ca.landonjw.gooeylibs2.api.template.types.ChestTemplate
 *  com.cobblemon.mod.common.Cobblemon
 *  com.cobblemon.mod.common.api.storage.party.PlayerPartyStore
 *  com.cobblemon.mod.common.api.storage.pc.PCBox
 *  com.cobblemon.mod.common.api.storage.pc.PCStore
 *  com.cobblemon.mod.common.item.PokemonItem
 *  com.cobblemon.mod.common.pokemon.Pokemon
 *  lombok.Generated
 *  net.minecraft.core.component.DataComponents
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.component.ItemLore
 */
package com.whoslucid.cobblelib.ui;

import ca.landonjw.gooeylibs2.api.UIManager;
import ca.landonjw.gooeylibs2.api.button.Button;
import ca.landonjw.gooeylibs2.api.button.ButtonAction;
import ca.landonjw.gooeylibs2.api.button.GooeyButton;
import ca.landonjw.gooeylibs2.api.page.GooeyPage;
import ca.landonjw.gooeylibs2.api.page.Page;
import ca.landonjw.gooeylibs2.api.template.Template;
import ca.landonjw.gooeylibs2.api.template.types.ChestTemplate;
import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.api.storage.party.PlayerPartyStore;
import com.cobblemon.mod.common.api.storage.pc.PCBox;
import com.cobblemon.mod.common.api.storage.pc.PCStore;
import com.cobblemon.mod.common.item.PokemonItem;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.whoslucid.cobblelib.Model.ItemModel;
import com.whoslucid.cobblelib.Model.PanelsConfig;
import com.whoslucid.cobblelib.Model.PokemonBlackList;
import com.whoslucid.cobblelib.action.PokemonButtonAction;
import com.whoslucid.cobblelib.ui.ConfirmMenu;
import com.whoslucid.cobblelib.ui.builds.PartyPcMenuBuilder;
import com.whoslucid.cobblelib.util.AdventureTranslator;
import com.whoslucid.cobblelib.util.PokemonUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import lombok.Generated;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemLore;

public class PartyPcMenu {
    private String titleParty = "&0Party";
    private int rowsParty = 3;
    private Integer[] slotsParty = new Integer[]{10, 11, 12, 14, 15, 16};
    private ItemModel pc = new ItemModel("cobblemon:pc", "&bPC");
    private ItemModel closeParty;
    private List<PanelsConfig> panelsParty;
    private String titlePc;
    private int rowsPc;
    private Integer[] slotsPc;
    private ItemModel previousBox;
    private ItemModel nextBox;
    private ItemModel backToParty;
    private ItemModel closePc;
    private List<PanelsConfig> panelsPc;

    public PartyPcMenu() {
        this.pc.setSlot(13);
        this.closeParty = new ItemModel("minecraft:barrier", "&cClose");
        this.closeParty.setSlot(22);
        this.panelsParty = new ArrayList<PanelsConfig>();
        this.panelsParty.add(new PanelsConfig(new ItemModel("minecraft:yellow_stained_glass_pane"), this.rowsParty));
        this.titlePc = "&bPC Box %box%";
        this.rowsPc = 6;
        this.slotsPc = new Integer[]{10, 11, 12, 13, 14, 15, 19, 20, 21, 22, 23, 24, 28, 29, 30, 31, 32, 33, 37, 38, 39, 40, 41, 42, 46, 47, 48, 49, 50, 51};
        this.previousBox = new ItemModel("minecraft:arrow", "&ePrevious Box");
        this.previousBox.setSlot(0);
        this.nextBox = new ItemModel("minecraft:arrow", "&eNext Box");
        this.nextBox.setSlot(8);
        this.backToParty = new ItemModel("cobblemon:poke_ball", "&aBack to Party");
        this.backToParty.setSlot(45);
        this.closePc = new ItemModel("minecraft:barrier", "&cClose");
        this.closePc.setSlot(53);
        this.panelsPc = new ArrayList<PanelsConfig>();
        this.panelsPc.add(new PanelsConfig(new ItemModel("minecraft:yellow_stained_glass_pane"), this.rowsPc));
    }

    public static PartyPcMenuBuilder builder() {
        return new PartyPcMenuBuilder();
    }

    public void openParty(PartyPcMenuBuilder builder) {
        ServerPlayer player = builder.getPlayer();
        if (player == null) {
            return;
        }
        ChestTemplate template = ChestTemplate.builder((int)this.rowsParty).build();
        PanelsConfig.applyConfig(template, this.panelsParty);
        PlayerPartyStore party = Cobblemon.INSTANCE.getStorage().getParty(player);
        for (int i = 0; i < this.slotsParty.length && i < 6; ++i) {
            List<String> lore;
            Pokemon pokemon = party.get(i);
            if (pokemon == null || builder.getBlackList() != null && builder.getBlackList().isBlackListed(pokemon) || builder.getCustomFilter() != null && !builder.getCustomFilter().test(pokemon)) continue;
            ItemStack pokemonItem = builder.getItemStackProvider() != null ? builder.getItemStackProvider().apply(pokemon) : PokemonItem.from((Pokemon)pokemon);
            pokemonItem.set(DataComponents.CUSTOM_NAME, AdventureTranslator.toNative("&b" + PokemonUtils.replace(pokemon)));
            List<String> list = lore = builder.getLorePokemon() != null ? PokemonUtils.replace(new ArrayList<String>(builder.getLorePokemon()), pokemon) : PokemonUtils.replaceLore(pokemon);
            if (builder.getLoreModifier() != null) {
                builder.getLoreModifier().accept(pokemon, lore);
            }
            if (!lore.isEmpty()) {
                pokemonItem.set(DataComponents.LORE, new ItemLore(AdventureTranslator.toNativeL(lore)));
            }
            GooeyButton button = GooeyButton.builder().display(pokemonItem).onClick(action -> {
                if (builder.getPokemonAction() != null) {
                    builder.getPokemonAction().accept(new PokemonButtonAction((ButtonAction)action, pokemon));
                }
            }).build();
            template.set(this.slotsParty[i].intValue(), (Button)button);
        }
        if (this.pc != null) {
            GooeyButton pcButton = this.pc.getButton(action -> this.openPCBox(builder, 0));
            this.pc.applyTemplate(template, pcButton);
        }
        if (builder.getCloseAction() != null && this.closeParty != null) {
            GooeyButton closeButton = this.closeParty.getButton(builder.getCloseAction());
            this.closeParty.applyTemplate(template, closeButton);
        }
        GooeyPage page = GooeyPage.builder().title(AdventureTranslator.toNative(this.titleParty)).template((Template)template).build();
        UIManager.openUIForcefully((ServerPlayer)player, (Page)page);
    }

    public void openParty(ServerPlayer player, Consumer<Template> templateConsumer, Consumer<PokemonButtonAction> pokemonAction, Consumer<ButtonAction> closeAction, PokemonBlackList blackList, List<String> lorePokemon, BiConsumer<Pokemon, List<String>> loreModifier, ConfirmMenu confirmMenu) {
        PartyPcMenuBuilder builder = PartyPcMenu.builder().setPlayer(player).setPokemonAction(pokemonAction).setCloseAction(closeAction).setBlackList(blackList).setLorePokemon(lorePokemon).setLoreModifier(loreModifier).setConfirmMenu(confirmMenu);
        this.openParty(builder);
    }

    public void openPCBox(PartyPcMenuBuilder builder, int boxIndex) {
        ServerPlayer player = builder.getPlayer();
        if (player == null) {
            return;
        }
        PCStore pcStore = Cobblemon.INSTANCE.getStorage().getPC(player);
        List boxes = pcStore.getBoxes();
        if (boxes.isEmpty()) {
            return;
        }
        int currentBox = Math.max(0, Math.min(boxIndex, boxes.size() - 1));
        PCBox box = (PCBox)boxes.get(currentBox);
        ChestTemplate template = ChestTemplate.builder((int)this.rowsPc).build();
        PanelsConfig.applyConfig(template, this.panelsPc);
        for (int i = 0; i < this.slotsPc.length && i < 30; ++i) {
            List<String> lore;
            Pokemon pokemon = box.get(i);
            if (pokemon == null || builder.getBlackList() != null && builder.getBlackList().isBlackListed(pokemon) || builder.getCustomFilter() != null && !builder.getCustomFilter().test(pokemon)) continue;
            ItemStack pokemonItem = builder.getItemStackProvider() != null ? builder.getItemStackProvider().apply(pokemon) : PokemonItem.from((Pokemon)pokemon);
            pokemonItem.set(DataComponents.CUSTOM_NAME, AdventureTranslator.toNative("&b" + PokemonUtils.replace(pokemon)));
            List<String> list = lore = builder.getLorePokemon() != null ? PokemonUtils.replace(new ArrayList<String>(builder.getLorePokemon()), pokemon) : PokemonUtils.replaceLore(pokemon);
            if (builder.getLoreModifier() != null) {
                builder.getLoreModifier().accept(pokemon, lore);
            }
            if (!lore.isEmpty()) {
                pokemonItem.set(DataComponents.LORE, new ItemLore(AdventureTranslator.toNativeL(lore)));
            }
            GooeyButton button = GooeyButton.builder().display(pokemonItem).onClick(action -> {
                if (builder.getPokemonAction() != null) {
                    builder.getPokemonAction().accept(new PokemonButtonAction((ButtonAction)action, pokemon));
                }
            }).build();
            template.set(this.slotsPc[i].intValue(), (Button)button);
        }
        if (currentBox > 0 && this.previousBox != null) {
            int prevBox = currentBox - 1;
            GooeyButton prevButton = this.previousBox.getButton(action -> this.openPCBox(builder, prevBox));
            this.previousBox.applyTemplate(template, prevButton);
        }
        if (currentBox < boxes.size() - 1 && this.nextBox != null) {
            int nextBoxIndex = currentBox + 1;
            GooeyButton nextButton = this.nextBox.getButton(action -> this.openPCBox(builder, nextBoxIndex));
            this.nextBox.applyTemplate(template, nextButton);
        }
        if (this.backToParty != null) {
            GooeyButton backButton = this.backToParty.getButton(action -> this.openParty(builder));
            this.backToParty.applyTemplate(template, backButton);
        }
        if (builder.getCloseAction() != null && this.closePc != null) {
            GooeyButton closeButton = this.closePc.getButton(builder.getCloseAction());
            this.closePc.applyTemplate(template, closeButton);
        }
        String title = this.titlePc.replace("%box%", String.valueOf(currentBox + 1));
        GooeyPage page = GooeyPage.builder().title(AdventureTranslator.toNative(title)).template((Template)template).build();
        UIManager.openUIForcefully((ServerPlayer)player, (Page)page);
    }

    @Generated
    public String getTitleParty() {
        return this.titleParty;
    }

    @Generated
    public int getRowsParty() {
        return this.rowsParty;
    }

    @Generated
    public Integer[] getSlotsParty() {
        return this.slotsParty;
    }

    @Generated
    public ItemModel getPc() {
        return this.pc;
    }

    @Generated
    public ItemModel getCloseParty() {
        return this.closeParty;
    }

    @Generated
    public List<PanelsConfig> getPanelsParty() {
        return this.panelsParty;
    }

    @Generated
    public String getTitlePc() {
        return this.titlePc;
    }

    @Generated
    public int getRowsPc() {
        return this.rowsPc;
    }

    @Generated
    public Integer[] getSlotsPc() {
        return this.slotsPc;
    }

    @Generated
    public ItemModel getPreviousBox() {
        return this.previousBox;
    }

    @Generated
    public ItemModel getNextBox() {
        return this.nextBox;
    }

    @Generated
    public ItemModel getBackToParty() {
        return this.backToParty;
    }

    @Generated
    public ItemModel getClosePc() {
        return this.closePc;
    }

    @Generated
    public List<PanelsConfig> getPanelsPc() {
        return this.panelsPc;
    }

    @Generated
    public void setTitleParty(String titleParty) {
        this.titleParty = titleParty;
    }

    @Generated
    public void setRowsParty(int rowsParty) {
        this.rowsParty = rowsParty;
    }

    @Generated
    public void setSlotsParty(Integer[] slotsParty) {
        this.slotsParty = slotsParty;
    }

    @Generated
    public void setPc(ItemModel pc) {
        this.pc = pc;
    }

    @Generated
    public void setCloseParty(ItemModel closeParty) {
        this.closeParty = closeParty;
    }

    @Generated
    public void setPanelsParty(List<PanelsConfig> panelsParty) {
        this.panelsParty = panelsParty;
    }

    @Generated
    public void setTitlePc(String titlePc) {
        this.titlePc = titlePc;
    }

    @Generated
    public void setRowsPc(int rowsPc) {
        this.rowsPc = rowsPc;
    }

    @Generated
    public void setSlotsPc(Integer[] slotsPc) {
        this.slotsPc = slotsPc;
    }

    @Generated
    public void setPreviousBox(ItemModel previousBox) {
        this.previousBox = previousBox;
    }

    @Generated
    public void setNextBox(ItemModel nextBox) {
        this.nextBox = nextBox;
    }

    @Generated
    public void setBackToParty(ItemModel backToParty) {
        this.backToParty = backToParty;
    }

    @Generated
    public void setClosePc(ItemModel closePc) {
        this.closePc = closePc;
    }

    @Generated
    public void setPanelsPc(List<PanelsConfig> panelsPc) {
        this.panelsPc = panelsPc;
    }

    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof PartyPcMenu)) {
            return false;
        }
        PartyPcMenu other = (PartyPcMenu)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (this.getRowsParty() != other.getRowsParty()) {
            return false;
        }
        if (this.getRowsPc() != other.getRowsPc()) {
            return false;
        }
        String this$titleParty = this.getTitleParty();
        String other$titleParty = other.getTitleParty();
        if (this$titleParty == null ? other$titleParty != null : !this$titleParty.equals(other$titleParty)) {
            return false;
        }
        if (!Arrays.deepEquals(this.getSlotsParty(), other.getSlotsParty())) {
            return false;
        }
        ItemModel this$pc = this.getPc();
        ItemModel other$pc = other.getPc();
        if (this$pc == null ? other$pc != null : !((Object)this$pc).equals(other$pc)) {
            return false;
        }
        ItemModel this$closeParty = this.getCloseParty();
        ItemModel other$closeParty = other.getCloseParty();
        if (this$closeParty == null ? other$closeParty != null : !((Object)this$closeParty).equals(other$closeParty)) {
            return false;
        }
        List<PanelsConfig> this$panelsParty = this.getPanelsParty();
        List<PanelsConfig> other$panelsParty = other.getPanelsParty();
        if (this$panelsParty == null ? other$panelsParty != null : !((Object)this$panelsParty).equals(other$panelsParty)) {
            return false;
        }
        String this$titlePc = this.getTitlePc();
        String other$titlePc = other.getTitlePc();
        if (this$titlePc == null ? other$titlePc != null : !this$titlePc.equals(other$titlePc)) {
            return false;
        }
        if (!Arrays.deepEquals(this.getSlotsPc(), other.getSlotsPc())) {
            return false;
        }
        ItemModel this$previousBox = this.getPreviousBox();
        ItemModel other$previousBox = other.getPreviousBox();
        if (this$previousBox == null ? other$previousBox != null : !((Object)this$previousBox).equals(other$previousBox)) {
            return false;
        }
        ItemModel this$nextBox = this.getNextBox();
        ItemModel other$nextBox = other.getNextBox();
        if (this$nextBox == null ? other$nextBox != null : !((Object)this$nextBox).equals(other$nextBox)) {
            return false;
        }
        ItemModel this$backToParty = this.getBackToParty();
        ItemModel other$backToParty = other.getBackToParty();
        if (this$backToParty == null ? other$backToParty != null : !((Object)this$backToParty).equals(other$backToParty)) {
            return false;
        }
        ItemModel this$closePc = this.getClosePc();
        ItemModel other$closePc = other.getClosePc();
        if (this$closePc == null ? other$closePc != null : !((Object)this$closePc).equals(other$closePc)) {
            return false;
        }
        List<PanelsConfig> this$panelsPc = this.getPanelsPc();
        List<PanelsConfig> other$panelsPc = other.getPanelsPc();
        return !(this$panelsPc == null ? other$panelsPc != null : !((Object)this$panelsPc).equals(other$panelsPc));
    }

    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof PartyPcMenu;
    }

    @Generated
    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + this.getRowsParty();
        result = result * 59 + this.getRowsPc();
        String $titleParty = this.getTitleParty();
        result = result * 59 + ($titleParty == null ? 43 : $titleParty.hashCode());
        result = result * 59 + Arrays.deepHashCode(this.getSlotsParty());
        ItemModel $pc = this.getPc();
        result = result * 59 + ($pc == null ? 43 : ((Object)$pc).hashCode());
        ItemModel $closeParty = this.getCloseParty();
        result = result * 59 + ($closeParty == null ? 43 : ((Object)$closeParty).hashCode());
        List<PanelsConfig> $panelsParty = this.getPanelsParty();
        result = result * 59 + ($panelsParty == null ? 43 : ((Object)$panelsParty).hashCode());
        String $titlePc = this.getTitlePc();
        result = result * 59 + ($titlePc == null ? 43 : $titlePc.hashCode());
        result = result * 59 + Arrays.deepHashCode(this.getSlotsPc());
        ItemModel $previousBox = this.getPreviousBox();
        result = result * 59 + ($previousBox == null ? 43 : ((Object)$previousBox).hashCode());
        ItemModel $nextBox = this.getNextBox();
        result = result * 59 + ($nextBox == null ? 43 : ((Object)$nextBox).hashCode());
        ItemModel $backToParty = this.getBackToParty();
        result = result * 59 + ($backToParty == null ? 43 : ((Object)$backToParty).hashCode());
        ItemModel $closePc = this.getClosePc();
        result = result * 59 + ($closePc == null ? 43 : ((Object)$closePc).hashCode());
        List<PanelsConfig> $panelsPc = this.getPanelsPc();
        result = result * 59 + ($panelsPc == null ? 43 : ((Object)$panelsPc).hashCode());
        return result;
    }

    @Generated
    public String toString() {
        return "PartyPcMenu(titleParty=" + this.getTitleParty() + ", rowsParty=" + this.getRowsParty() + ", slotsParty=" + Arrays.deepToString(this.getSlotsParty()) + ", pc=" + String.valueOf(this.getPc()) + ", closeParty=" + String.valueOf(this.getCloseParty()) + ", panelsParty=" + String.valueOf(this.getPanelsParty()) + ", titlePc=" + this.getTitlePc() + ", rowsPc=" + this.getRowsPc() + ", slotsPc=" + Arrays.deepToString(this.getSlotsPc()) + ", previousBox=" + String.valueOf(this.getPreviousBox()) + ", nextBox=" + String.valueOf(this.getNextBox()) + ", backToParty=" + String.valueOf(this.getBackToParty()) + ", closePc=" + String.valueOf(this.getClosePc()) + ", panelsPc=" + String.valueOf(this.getPanelsPc()) + ")";
    }
}

