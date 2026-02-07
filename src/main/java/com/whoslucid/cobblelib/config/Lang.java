/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  lombok.Generated
 */
package com.whoslucid.cobblelib.config;

import com.google.gson.Gson;
import com.whoslucid.cobblelib.CobbleLib;
import com.whoslucid.cobblelib.Model.ItemModel;
import com.whoslucid.cobblelib.ui.ConfirmMenu;
import com.whoslucid.cobblelib.util.Utils;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.util.ArrayList;
import java.util.List;
import lombok.Generated;

public class Lang {
    private String prefix = "&8[&bCobbleLib&8] ";
    private String messageReload = "&aConfiguration reloaded!";
    private String messageNoPermission = "&cYou don't have permission to do that.";
    private String messageNotEnoughMoney = "&cYou don't have enough %symbol%! You need %cost% %symbol%.";
    private String messageMoneyDeducted = "&aDeducted %cost% %symbol% from your account.";
    private List<String> lorepokemon = new ArrayList<String>();
    private ItemModel itemConfirm = new ItemModel("minecraft:emerald", "&aConfirm");
    private ItemModel itemCancel = new ItemModel("minecraft:redstone", "&cCancel");
    private ItemModel itemClose = new ItemModel("minecraft:barrier", "&cClose");
    private ItemModel itemInvisible = new ItemModel("minecraft:air");
    private ConfirmMenu confirmMenu = new ConfirmMenu();

    public Lang() {
        this.lorepokemon.add("&8\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501");
        this.lorepokemon.add("&6\u2756 &eLv.%level% &8\u2503 &6%nature% &8\u2503 &e%ability%");
        this.lorepokemon.add("&8\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501");
        this.lorepokemon.add("");
        this.lorepokemon.add("&6IVs &8(&e%ivs_total%&8/&e186&8) &6\u2726&e%ivs_perfect%");
        this.lorepokemon.add("&6EVs &8(&e%evs_total%&8/&e510&8)");
        this.lorepokemon.add("");
        this.lorepokemon.add("&8\u250f\u2501\u2501\u2501\u2501\u2501\u2501\u2533\u2501\u2501\u2501\u2501\u2501\u2501\u2533\u2501\u2501\u2501\u2501\u2501\u2501\u2513");
        this.lorepokemon.add("&8\u2503 &6STAT &8\u2503 &6 IV &8\u2503 &6 EV &8\u2503");
        this.lorepokemon.add("&8\u2523\u2501\u2501\u2501\u2501\u2501\u2501\u254b\u2501\u2501\u2501\u2501\u2501\u2501\u254b\u2501\u2501\u2501\u2501\u2501\u2501\u252b");
        this.lorepokemon.add("&8\u2503 &6HP   &8\u2503 &e%iv_hp%   &8\u2503 &e%ev_hp%   &8\u2503");
        this.lorepokemon.add("&8\u2503 &6ATK  &8\u2503 &e%iv_atk%   &8\u2503 &e%ev_atk%   &8\u2503");
        this.lorepokemon.add("&8\u2503 &6DEF  &8\u2503 &e%iv_def%   &8\u2503 &e%ev_def%   &8\u2503");
        this.lorepokemon.add("&8\u2503 &6SPA  &8\u2503 &e%iv_spa%   &8\u2503 &e%ev_spa%   &8\u2503");
        this.lorepokemon.add("&8\u2503 &6SPD  &8\u2503 &e%iv_spd%   &8\u2503 &e%ev_spd%   &8\u2503");
        this.lorepokemon.add("&8\u2503 &6SPE  &8\u2503 &e%iv_spe%   &8\u2503 &e%ev_spe%   &8\u2503");
        this.lorepokemon.add("&8\u2517\u2501\u2501\u2501\u2501\u2501\u2501\u253b\u2501\u2501\u2501\u2501\u2501\u2501\u253b\u2501\u2501\u2501\u2501\u2501\u2501\u251b");
        this.lorepokemon.add("");
        this.lorepokemon.add("&6Shiny &8\u00bb &e%shiny% &8\u2503 &6Gender &8\u00bb &e%gender% &8\u2503 &6Ball &8\u00bb &e%ball%");
    }

    public static Lang load() {
        Gson gson = Utils.newGson();
        Lang lang = new Lang();
        try {
            String content;
            File dir = new File(Utils.getAbsolutePath("/config/cobblelib"), "lang");
            dir.mkdirs();
            File file = new File(dir, "en.json");
            if (file.exists() && (lang = (Lang)gson.fromJson(content = Files.readString(file.toPath(), StandardCharsets.UTF_8), Lang.class)) == null) {
                lang = new Lang();
            }
            String json = gson.toJson((Object)lang);
            Files.writeString(file.toPath(), (CharSequence)json, StandardCharsets.UTF_8, new OpenOption[0]);
        }
        catch (Exception e) {
            CobbleLib.LOGGER.error("Failed to load language: " + e.getMessage());
        }
        return lang;
    }

    public void save() {
        try {
            Gson gson = Utils.newGson();
            File dir = new File(Utils.getAbsolutePath("/config/cobblelib"), "lang");
            dir.mkdirs();
            File file = new File(dir, "en.json");
            String json = gson.toJson((Object)this);
            Files.writeString(file.toPath(), (CharSequence)json, StandardCharsets.UTF_8, new OpenOption[0]);
        }
        catch (Exception e) {
            CobbleLib.LOGGER.error("Failed to save language: " + e.getMessage());
        }
    }

    @Generated
    public String getPrefix() {
        return this.prefix;
    }

    @Generated
    public String getMessageReload() {
        return this.messageReload;
    }

    @Generated
    public String getMessageNoPermission() {
        return this.messageNoPermission;
    }

    @Generated
    public String getMessageNotEnoughMoney() {
        return this.messageNotEnoughMoney;
    }

    @Generated
    public String getMessageMoneyDeducted() {
        return this.messageMoneyDeducted;
    }

    @Generated
    public List<String> getLorepokemon() {
        return this.lorepokemon;
    }

    @Generated
    public ItemModel getItemConfirm() {
        return this.itemConfirm;
    }

    @Generated
    public ItemModel getItemCancel() {
        return this.itemCancel;
    }

    @Generated
    public ItemModel getItemClose() {
        return this.itemClose;
    }

    @Generated
    public ItemModel getItemInvisible() {
        return this.itemInvisible;
    }

    @Generated
    public ConfirmMenu getConfirmMenu() {
        return this.confirmMenu;
    }

    @Generated
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    @Generated
    public void setMessageReload(String messageReload) {
        this.messageReload = messageReload;
    }

    @Generated
    public void setMessageNoPermission(String messageNoPermission) {
        this.messageNoPermission = messageNoPermission;
    }

    @Generated
    public void setMessageNotEnoughMoney(String messageNotEnoughMoney) {
        this.messageNotEnoughMoney = messageNotEnoughMoney;
    }

    @Generated
    public void setMessageMoneyDeducted(String messageMoneyDeducted) {
        this.messageMoneyDeducted = messageMoneyDeducted;
    }

    @Generated
    public void setLorepokemon(List<String> lorepokemon) {
        this.lorepokemon = lorepokemon;
    }

    @Generated
    public void setItemConfirm(ItemModel itemConfirm) {
        this.itemConfirm = itemConfirm;
    }

    @Generated
    public void setItemCancel(ItemModel itemCancel) {
        this.itemCancel = itemCancel;
    }

    @Generated
    public void setItemClose(ItemModel itemClose) {
        this.itemClose = itemClose;
    }

    @Generated
    public void setItemInvisible(ItemModel itemInvisible) {
        this.itemInvisible = itemInvisible;
    }

    @Generated
    public void setConfirmMenu(ConfirmMenu confirmMenu) {
        this.confirmMenu = confirmMenu;
    }

    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Lang)) {
            return false;
        }
        Lang other = (Lang)o;
        if (!other.canEqual(this)) {
            return false;
        }
        String this$prefix = this.getPrefix();
        String other$prefix = other.getPrefix();
        if (this$prefix == null ? other$prefix != null : !this$prefix.equals(other$prefix)) {
            return false;
        }
        String this$messageReload = this.getMessageReload();
        String other$messageReload = other.getMessageReload();
        if (this$messageReload == null ? other$messageReload != null : !this$messageReload.equals(other$messageReload)) {
            return false;
        }
        String this$messageNoPermission = this.getMessageNoPermission();
        String other$messageNoPermission = other.getMessageNoPermission();
        if (this$messageNoPermission == null ? other$messageNoPermission != null : !this$messageNoPermission.equals(other$messageNoPermission)) {
            return false;
        }
        String this$messageNotEnoughMoney = this.getMessageNotEnoughMoney();
        String other$messageNotEnoughMoney = other.getMessageNotEnoughMoney();
        if (this$messageNotEnoughMoney == null ? other$messageNotEnoughMoney != null : !this$messageNotEnoughMoney.equals(other$messageNotEnoughMoney)) {
            return false;
        }
        String this$messageMoneyDeducted = this.getMessageMoneyDeducted();
        String other$messageMoneyDeducted = other.getMessageMoneyDeducted();
        if (this$messageMoneyDeducted == null ? other$messageMoneyDeducted != null : !this$messageMoneyDeducted.equals(other$messageMoneyDeducted)) {
            return false;
        }
        List<String> this$lorepokemon = this.getLorepokemon();
        List<String> other$lorepokemon = other.getLorepokemon();
        if (this$lorepokemon == null ? other$lorepokemon != null : !((Object)this$lorepokemon).equals(other$lorepokemon)) {
            return false;
        }
        ItemModel this$itemConfirm = this.getItemConfirm();
        ItemModel other$itemConfirm = other.getItemConfirm();
        if (this$itemConfirm == null ? other$itemConfirm != null : !((Object)this$itemConfirm).equals(other$itemConfirm)) {
            return false;
        }
        ItemModel this$itemCancel = this.getItemCancel();
        ItemModel other$itemCancel = other.getItemCancel();
        if (this$itemCancel == null ? other$itemCancel != null : !((Object)this$itemCancel).equals(other$itemCancel)) {
            return false;
        }
        ItemModel this$itemClose = this.getItemClose();
        ItemModel other$itemClose = other.getItemClose();
        if (this$itemClose == null ? other$itemClose != null : !((Object)this$itemClose).equals(other$itemClose)) {
            return false;
        }
        ItemModel this$itemInvisible = this.getItemInvisible();
        ItemModel other$itemInvisible = other.getItemInvisible();
        if (this$itemInvisible == null ? other$itemInvisible != null : !((Object)this$itemInvisible).equals(other$itemInvisible)) {
            return false;
        }
        ConfirmMenu this$confirmMenu = this.getConfirmMenu();
        ConfirmMenu other$confirmMenu = other.getConfirmMenu();
        return !(this$confirmMenu == null ? other$confirmMenu != null : !((Object)this$confirmMenu).equals(other$confirmMenu));
    }

    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof Lang;
    }

    @Generated
    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $prefix = this.getPrefix();
        result = result * 59 + ($prefix == null ? 43 : $prefix.hashCode());
        String $messageReload = this.getMessageReload();
        result = result * 59 + ($messageReload == null ? 43 : $messageReload.hashCode());
        String $messageNoPermission = this.getMessageNoPermission();
        result = result * 59 + ($messageNoPermission == null ? 43 : $messageNoPermission.hashCode());
        String $messageNotEnoughMoney = this.getMessageNotEnoughMoney();
        result = result * 59 + ($messageNotEnoughMoney == null ? 43 : $messageNotEnoughMoney.hashCode());
        String $messageMoneyDeducted = this.getMessageMoneyDeducted();
        result = result * 59 + ($messageMoneyDeducted == null ? 43 : $messageMoneyDeducted.hashCode());
        List<String> $lorepokemon = this.getLorepokemon();
        result = result * 59 + ($lorepokemon == null ? 43 : ((Object)$lorepokemon).hashCode());
        ItemModel $itemConfirm = this.getItemConfirm();
        result = result * 59 + ($itemConfirm == null ? 43 : ((Object)$itemConfirm).hashCode());
        ItemModel $itemCancel = this.getItemCancel();
        result = result * 59 + ($itemCancel == null ? 43 : ((Object)$itemCancel).hashCode());
        ItemModel $itemClose = this.getItemClose();
        result = result * 59 + ($itemClose == null ? 43 : ((Object)$itemClose).hashCode());
        ItemModel $itemInvisible = this.getItemInvisible();
        result = result * 59 + ($itemInvisible == null ? 43 : ((Object)$itemInvisible).hashCode());
        ConfirmMenu $confirmMenu = this.getConfirmMenu();
        result = result * 59 + ($confirmMenu == null ? 43 : ((Object)$confirmMenu).hashCode());
        return result;
    }

    @Generated
    public String toString() {
        return "Lang(prefix=" + this.getPrefix() + ", messageReload=" + this.getMessageReload() + ", messageNoPermission=" + this.getMessageNoPermission() + ", messageNotEnoughMoney=" + this.getMessageNotEnoughMoney() + ", messageMoneyDeducted=" + this.getMessageMoneyDeducted() + ", lorepokemon=" + String.valueOf(this.getLorepokemon()) + ", itemConfirm=" + String.valueOf(this.getItemConfirm()) + ", itemCancel=" + String.valueOf(this.getItemCancel()) + ", itemClose=" + String.valueOf(this.getItemClose()) + ", itemInvisible=" + String.valueOf(this.getItemInvisible()) + ", confirmMenu=" + String.valueOf(this.getConfirmMenu()) + ")";
    }
}

