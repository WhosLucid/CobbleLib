/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  ca.landonjw.gooeylibs2.api.button.Button
 *  ca.landonjw.gooeylibs2.api.button.GooeyButton
 *  ca.landonjw.gooeylibs2.api.template.types.ChestTemplate
 *  lombok.Generated
 *  net.minecraft.world.item.ItemStack
 */
package com.whoslucid.cobblelib.Model;

import ca.landonjw.gooeylibs2.api.button.Button;
import ca.landonjw.gooeylibs2.api.button.GooeyButton;
import ca.landonjw.gooeylibs2.api.template.types.ChestTemplate;
import com.whoslucid.cobblelib.Model.ItemModel;
import com.whoslucid.cobblelib.util.UIUtils;
import java.util.ArrayList;
import java.util.List;
import lombok.Generated;
import net.minecraft.world.item.ItemStack;

public class PanelsConfig {
    private ItemModel fill;
    private List<Integer> slots;

    public PanelsConfig() {
        this.fill = new ItemModel();
        this.slots = List.of();
    }

    public PanelsConfig(int rows) {
        this.fill = new ItemModel("minecraft:gray_stained_glass_pane");
        this.slots = new ArrayList<Integer>();
        int size = rows * 9;
        for (int i = 0; i < size; ++i) {
            this.slots.add(i);
        }
    }

    public PanelsConfig(ItemModel fill, List<Integer> slots) {
        this.fill = fill;
        this.slots = slots;
    }

    public PanelsConfig(ItemModel fill, int rows) {
        this.fill = fill;
        this.slots = new ArrayList<Integer>();
        int size = rows * 9;
        for (int i = 0; i < size; ++i) {
            this.slots.add(i);
        }
    }

    public static void applyConfig(ChestTemplate template, List<PanelsConfig> panelsConfigs) {
        PanelsConfig.applyConfig(template, panelsConfigs, template.getRows());
    }

    public static void applyConfig(ChestTemplate template, List<PanelsConfig> panelsConfigs, int rows) {
        for (PanelsConfig panelsConfig : panelsConfigs) {
            ItemStack itemStack = panelsConfig.getFill().getItemStack();
            GooeyButton button = GooeyButton.builder().display(itemStack).build();
            for (Integer slot : panelsConfig.getSlots()) {
                if (!UIUtils.isInside(slot, rows)) continue;
                template.set(slot.intValue(), (Button)button);
            }
        }
    }

    @Generated
    public ItemModel getFill() {
        return this.fill;
    }

    @Generated
    public List<Integer> getSlots() {
        return this.slots;
    }

    @Generated
    public void setFill(ItemModel fill) {
        this.fill = fill;
    }

    @Generated
    public void setSlots(List<Integer> slots) {
        this.slots = slots;
    }
}

