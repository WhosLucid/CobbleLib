/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  ca.landonjw.gooeylibs2.api.button.Button
 *  ca.landonjw.gooeylibs2.api.button.ButtonAction
 *  ca.landonjw.gooeylibs2.api.button.GooeyButton
 *  ca.landonjw.gooeylibs2.api.button.GooeyButton$Builder
 *  ca.landonjw.gooeylibs2.api.button.linked.LinkType
 *  ca.landonjw.gooeylibs2.api.button.linked.LinkedPageButton
 *  ca.landonjw.gooeylibs2.api.template.types.ChestTemplate
 *  com.cobblemon.mod.common.api.pokemon.PokemonProperties
 *  com.cobblemon.mod.common.item.PokemonItem
 *  lombok.Generated
 *  net.minecraft.core.component.DataComponents
 *  net.minecraft.core.registries.BuiltInRegistries
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.component.ItemLore
 *  net.minecraft.world.level.ItemLike
 */
package com.whoslucid.cobblelib.Model;

import ca.landonjw.gooeylibs2.api.button.Button;
import ca.landonjw.gooeylibs2.api.button.ButtonAction;
import ca.landonjw.gooeylibs2.api.button.GooeyButton;
import ca.landonjw.gooeylibs2.api.button.linked.LinkType;
import ca.landonjw.gooeylibs2.api.button.linked.LinkedPageButton;
import ca.landonjw.gooeylibs2.api.template.types.ChestTemplate;
import com.cobblemon.mod.common.api.pokemon.PokemonProperties;
import com.cobblemon.mod.common.item.PokemonItem;
import com.whoslucid.cobblelib.util.AdventureTranslator;
import com.whoslucid.cobblelib.util.UIUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import lombok.Generated;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemLore;
import net.minecraft.world.level.ItemLike;

public class ItemModel {
    private Integer slot;
    private Integer[] slots;
    private String item;
    private String displayname;
    private List<String> lore = new ArrayList<String>();
    private long CustomModelData = 0L;
    private String nbt;
    private Object tooltip;

    public ItemModel() {
        this.slot = null;
        this.slots = new Integer[0];
        this.item = "minecraft:emerald";
        this.displayname = "";
        this.lore = new ArrayList<String>();
        this.nbt = "";
    }

    public ItemModel(String item) {
        this.slot = 0;
        this.slots = new Integer[0];
        this.item = item;
        this.displayname = "";
        this.lore = new ArrayList<String>();
        this.nbt = "";
    }

    public ItemModel(String item, String displayname) {
        this.slot = 0;
        this.slots = new Integer[0];
        this.item = item;
        this.displayname = displayname;
        this.lore = new ArrayList<String>();
        this.nbt = "";
    }

    public ItemModel(String item, String displayname, List<String> lore) {
        this.slot = 0;
        this.slots = new Integer[0];
        this.item = item;
        this.displayname = displayname;
        this.lore = lore;
    }

    public ItemModel(Integer slot, String item, String displayname, List<String> lore, int customModelData) {
        this.slot = slot;
        this.item = item;
        this.displayname = displayname;
        this.lore = lore;
        this.CustomModelData = customModelData;
    }

    public ItemModel(Integer slot, String item, String displayname, List<String> lore, long customModelData) {
        this.slot = slot;
        this.item = item;
        this.displayname = displayname;
        this.lore = lore;
        this.CustomModelData = customModelData;
    }

    public ItemModel(ItemModel other) {
        this.slot = other.getSlot();
        this.slots = other.getSlots();
        this.item = other.getItem();
        this.displayname = other.getDisplayname();
        this.lore = other.getLore() != null ? new ArrayList<String>(other.getLore()) : new ArrayList();
        this.CustomModelData = other.getCustomModelData();
        this.nbt = other.getNbt();
    }

    public ItemStack getItemStack() {
        return ItemModel.getItemStack(this, 1);
    }

    public ItemStack getItemStack(int amount) {
        return ItemModel.getItemStack(this, amount);
    }

    public static ItemStack getItemStack(ItemModel itemModel) {
        return ItemModel.getItemStack(itemModel, 1);
    }

    public static ItemStack getItemStack(ItemModel itemModel, int amount) {
        try {
            Optional itemOptional;
            String itemId = itemModel.getItem();
            if (itemId.startsWith("pokemon:")) {
                return PokemonItem.from((PokemonProperties)PokemonProperties.Companion.parse(itemId.replace("pokemon:", "")));
            }
            ResourceLocation rl = ResourceLocation.tryParse((String)itemId);
            if (rl != null && (itemOptional = BuiltInRegistries.ITEM.getOptional(rl)).isPresent()) {
                return new ItemStack((ItemLike)itemOptional.get(), Math.max(amount, 1));
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        return ItemStack.EMPTY;
    }

    public GooeyButton getButton(Consumer<ButtonAction> action) {
        return this.getButton(1, null, null, action);
    }

    public GooeyButton getButton(int amount, Consumer<ButtonAction> action) {
        return this.getButton(amount, null, null, action);
    }

    public GooeyButton getButton(int amount, String name, Consumer<ButtonAction> action) {
        return this.getButton(amount, name, null, action);
    }

    public GooeyButton getButton(int amount, String name, List<String> lore, Consumer<ButtonAction> action) {
        List<String> resultLore;
        ItemStack itemStack = this.getItemStack(amount);
        GooeyButton.Builder builder = GooeyButton.builder().display(itemStack);
        String resultName = name != null ? name : this.getDisplayname();
        builder.with(DataComponents.CUSTOM_NAME, AdventureTranslator.toNative(resultName));
        List<String> list = resultLore = lore != null ? lore : this.getLore();
        if (resultLore != null && !resultLore.isEmpty()) {
            ItemLore loreComponent = new ItemLore(AdventureTranslator.toNativeL(resultLore));
            builder.with(DataComponents.LORE, loreComponent);
        }
        if (action != null) {
            builder.onClick(action);
        }
        return builder.build();
    }

    public LinkedPageButton getLinkedPageButton(LinkType linkType) {
        ItemStack itemStack = this.getItemStack();
        return LinkedPageButton.builder().display(itemStack).linkType(linkType).build();
    }

    public void applyTemplate(ChestTemplate template, GooeyButton button) {
        int rows = template.getRows();
        if (UIUtils.isInside(this.slot, rows)) {
            template.set(this.slot.intValue(), (Button)button);
        }
        if (this.slots != null) {
            for (Integer s : this.slots) {
                if (!UIUtils.isInside(s, rows)) continue;
                template.set(s.intValue(), (Button)button);
            }
        }
    }

    @Generated
    public Integer getSlot() {
        return this.slot;
    }

    @Generated
    public Integer[] getSlots() {
        return this.slots;
    }

    @Generated
    public String getItem() {
        return this.item;
    }

    @Generated
    public String getDisplayname() {
        return this.displayname;
    }

    @Generated
    public List<String> getLore() {
        return this.lore;
    }

    @Generated
    public long getCustomModelData() {
        return this.CustomModelData;
    }

    @Generated
    public String getNbt() {
        return this.nbt;
    }

    @Generated
    public Object getTooltip() {
        return this.tooltip;
    }

    @Generated
    public void setSlot(Integer slot) {
        this.slot = slot;
    }

    @Generated
    public void setSlots(Integer[] slots) {
        this.slots = slots;
    }

    @Generated
    public void setItem(String item) {
        this.item = item;
    }

    @Generated
    public void setDisplayname(String displayname) {
        this.displayname = displayname;
    }

    @Generated
    public void setLore(List<String> lore) {
        this.lore = lore;
    }

    @Generated
    public void setCustomModelData(long CustomModelData) {
        this.CustomModelData = CustomModelData;
    }

    @Generated
    public void setNbt(String nbt) {
        this.nbt = nbt;
    }

    @Generated
    public void setTooltip(Object tooltip) {
        this.tooltip = tooltip;
    }

    @Generated
    public String toString() {
        return "ItemModel(slot=" + this.getSlot() + ", slots=" + Arrays.deepToString(this.getSlots()) + ", item=" + this.getItem() + ", displayname=" + this.getDisplayname() + ", lore=" + String.valueOf(this.getLore()) + ", CustomModelData=" + this.getCustomModelData() + ", nbt=" + this.getNbt() + ", tooltip=" + String.valueOf(this.getTooltip()) + ")";
    }

    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ItemModel)) {
            return false;
        }
        ItemModel other = (ItemModel)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (this.getCustomModelData() != other.getCustomModelData()) {
            return false;
        }
        Integer this$slot = this.getSlot();
        Integer other$slot = other.getSlot();
        if (this$slot == null ? other$slot != null : !((Object)this$slot).equals(other$slot)) {
            return false;
        }
        if (!Arrays.deepEquals(this.getSlots(), other.getSlots())) {
            return false;
        }
        String this$item = this.getItem();
        String other$item = other.getItem();
        if (this$item == null ? other$item != null : !this$item.equals(other$item)) {
            return false;
        }
        String this$displayname = this.getDisplayname();
        String other$displayname = other.getDisplayname();
        if (this$displayname == null ? other$displayname != null : !this$displayname.equals(other$displayname)) {
            return false;
        }
        List<String> this$lore = this.getLore();
        List<String> other$lore = other.getLore();
        if (this$lore == null ? other$lore != null : !((Object)this$lore).equals(other$lore)) {
            return false;
        }
        String this$nbt = this.getNbt();
        String other$nbt = other.getNbt();
        if (this$nbt == null ? other$nbt != null : !this$nbt.equals(other$nbt)) {
            return false;
        }
        Object this$tooltip = this.getTooltip();
        Object other$tooltip = other.getTooltip();
        return !(this$tooltip == null ? other$tooltip != null : !this$tooltip.equals(other$tooltip));
    }

    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof ItemModel;
    }

    @Generated
    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        long $CustomModelData = this.getCustomModelData();
        result = result * 59 + (int)($CustomModelData >>> 32 ^ $CustomModelData);
        Integer $slot = this.getSlot();
        result = result * 59 + ($slot == null ? 43 : ((Object)$slot).hashCode());
        result = result * 59 + Arrays.deepHashCode(this.getSlots());
        String $item = this.getItem();
        result = result * 59 + ($item == null ? 43 : $item.hashCode());
        String $displayname = this.getDisplayname();
        result = result * 59 + ($displayname == null ? 43 : $displayname.hashCode());
        List<String> $lore = this.getLore();
        result = result * 59 + ($lore == null ? 43 : ((Object)$lore).hashCode());
        String $nbt = this.getNbt();
        result = result * 59 + ($nbt == null ? 43 : $nbt.hashCode());
        Object $tooltip = this.getTooltip();
        result = result * 59 + ($tooltip == null ? 43 : $tooltip.hashCode());
        return result;
    }
}

