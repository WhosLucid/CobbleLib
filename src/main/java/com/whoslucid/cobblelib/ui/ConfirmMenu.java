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
 *  lombok.Generated
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.item.ItemStack
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
import com.whoslucid.cobblelib.Model.ItemModel;
import com.whoslucid.cobblelib.Model.PanelsConfig;
import com.whoslucid.cobblelib.util.AdventureTranslator;
import java.util.List;
import java.util.function.Consumer;
import lombok.Generated;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

public class ConfirmMenu {
    private int rows = 3;
    private String title = "Confirm";
    private int slotDisplay = 13;
    private ItemModel confirm = new ItemModel("minecraft:emerald", "&aConfirm");
    private ItemModel cancel = new ItemModel("minecraft:redstone", "&cCancel");
    private ItemModel close = new ItemModel("minecraft:barrier", "&cClose");
    private List<PanelsConfig> panels;

    public ConfirmMenu() {
        this.confirm.setSlot(10);
        this.cancel.setSlot(16);
        this.close.setSlot(22);
        this.panels = List.of(new PanelsConfig(this.rows));
    }

    public void open(ServerPlayer player, ItemStack itemStack, Consumer<ButtonAction> onConfirm, Consumer<ButtonAction> onCancel) {
        ChestTemplate template = ChestTemplate.builder((int)this.rows).build();
        PanelsConfig.applyConfig(template, this.panels);
        template.set(this.slotDisplay, (Button)GooeyButton.builder().display(itemStack.copy()).build());
        if (this.confirm != null) {
            GooeyButton confirmButton = this.confirm.getButton(onConfirm);
            this.confirm.applyTemplate(template, confirmButton);
        }
        if (this.cancel != null) {
            GooeyButton cancelButton = this.cancel.getButton(onCancel);
            this.cancel.applyTemplate(template, cancelButton);
        }
        if (this.close != null) {
            GooeyButton closeButton = this.close.getButton(onCancel);
            this.close.applyTemplate(template, closeButton);
        }
        GooeyPage page = GooeyPage.builder().title(AdventureTranslator.toNative(this.title)).template((Template)template).build();
        UIManager.openUIForcefully((ServerPlayer)player, (Page)page);
    }

    @Generated
    public int getRows() {
        return this.rows;
    }

    @Generated
    public String getTitle() {
        return this.title;
    }

    @Generated
    public int getSlotDisplay() {
        return this.slotDisplay;
    }

    @Generated
    public ItemModel getConfirm() {
        return this.confirm;
    }

    @Generated
    public ItemModel getCancel() {
        return this.cancel;
    }

    @Generated
    public ItemModel getClose() {
        return this.close;
    }

    @Generated
    public List<PanelsConfig> getPanels() {
        return this.panels;
    }

    @Generated
    public void setRows(int rows) {
        this.rows = rows;
    }

    @Generated
    public void setTitle(String title) {
        this.title = title;
    }

    @Generated
    public void setSlotDisplay(int slotDisplay) {
        this.slotDisplay = slotDisplay;
    }

    @Generated
    public void setConfirm(ItemModel confirm) {
        this.confirm = confirm;
    }

    @Generated
    public void setCancel(ItemModel cancel) {
        this.cancel = cancel;
    }

    @Generated
    public void setClose(ItemModel close) {
        this.close = close;
    }

    @Generated
    public void setPanels(List<PanelsConfig> panels) {
        this.panels = panels;
    }

    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ConfirmMenu)) {
            return false;
        }
        ConfirmMenu other = (ConfirmMenu)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (this.getRows() != other.getRows()) {
            return false;
        }
        if (this.getSlotDisplay() != other.getSlotDisplay()) {
            return false;
        }
        String this$title = this.getTitle();
        String other$title = other.getTitle();
        if (this$title == null ? other$title != null : !this$title.equals(other$title)) {
            return false;
        }
        ItemModel this$confirm = this.getConfirm();
        ItemModel other$confirm = other.getConfirm();
        if (this$confirm == null ? other$confirm != null : !((Object)this$confirm).equals(other$confirm)) {
            return false;
        }
        ItemModel this$cancel = this.getCancel();
        ItemModel other$cancel = other.getCancel();
        if (this$cancel == null ? other$cancel != null : !((Object)this$cancel).equals(other$cancel)) {
            return false;
        }
        ItemModel this$close = this.getClose();
        ItemModel other$close = other.getClose();
        if (this$close == null ? other$close != null : !((Object)this$close).equals(other$close)) {
            return false;
        }
        List<PanelsConfig> this$panels = this.getPanels();
        List<PanelsConfig> other$panels = other.getPanels();
        return !(this$panels == null ? other$panels != null : !((Object)this$panels).equals(other$panels));
    }

    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof ConfirmMenu;
    }

    @Generated
    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + this.getRows();
        result = result * 59 + this.getSlotDisplay();
        String $title = this.getTitle();
        result = result * 59 + ($title == null ? 43 : $title.hashCode());
        ItemModel $confirm = this.getConfirm();
        result = result * 59 + ($confirm == null ? 43 : ((Object)$confirm).hashCode());
        ItemModel $cancel = this.getCancel();
        result = result * 59 + ($cancel == null ? 43 : ((Object)$cancel).hashCode());
        ItemModel $close = this.getClose();
        result = result * 59 + ($close == null ? 43 : ((Object)$close).hashCode());
        List<PanelsConfig> $panels = this.getPanels();
        result = result * 59 + ($panels == null ? 43 : ((Object)$panels).hashCode());
        return result;
    }

    @Generated
    public String toString() {
        return "ConfirmMenu(rows=" + this.getRows() + ", title=" + this.getTitle() + ", slotDisplay=" + this.getSlotDisplay() + ", confirm=" + String.valueOf(this.getConfirm()) + ", cancel=" + String.valueOf(this.getCancel()) + ", close=" + String.valueOf(this.getClose()) + ", panels=" + String.valueOf(this.getPanels()) + ")";
    }
}

