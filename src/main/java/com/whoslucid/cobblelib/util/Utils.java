/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cobblemon.mod.common.api.pokemon.PokemonProperties
 *  com.cobblemon.mod.common.item.PokemonItem
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  net.minecraft.core.registries.BuiltInRegistries
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.ListTag
 *  net.minecraft.nbt.NumericTag
 *  net.minecraft.nbt.StringTag
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.ItemLike
 */
package com.whoslucid.cobblelib.util;

import com.cobblemon.mod.common.api.pokemon.PokemonProperties;
import com.cobblemon.mod.common.item.PokemonItem;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.whoslucid.cobblelib.Model.ItemModel;
import java.io.File;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NumericTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;

public abstract class Utils {
    public static Gson newGson() {
        return new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
    }

    public static File getAbsolutePath(String path) {
        return new File(System.getProperty("user.dir") + path);
    }

    public static CompletableFuture<Boolean> readFileAsync(String path, String filename, Consumer<String> callback) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                File file = new File(Utils.getAbsolutePath(path), filename);
                if (!file.exists()) {
                    return false;
                }
                String content = Files.readString(file.toPath(), StandardCharsets.UTF_8);
                callback.accept(content);
                return true;
            }
            catch (Exception e) {
                return false;
            }
        });
    }

    public static CompletableFuture<Boolean> writeFileAsync(String path, String filename, String data) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                File dir = Utils.getAbsolutePath(path);
                dir.mkdirs();
                File file = new File(dir, filename);
                try (FileWriter writer = new FileWriter(file, StandardCharsets.UTF_8);){
                    writer.write(data);
                }
                return true;
            }
            catch (Exception e) {
                return false;
            }
        });
    }

    public static ItemStack parseItemId(String itemId, int amount) {
        return Utils.parseItemId(itemId, amount, 0L);
    }

    public static ItemStack parseItemId(String itemId, int amount, long customModelData) {
        try {
            Optional itemOptional;
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

    public static ItemStack parseItemModel(ItemModel itemModel, int amount) {
        return ItemModel.getItemStack(itemModel, amount);
    }

    public static ItemStack getHead(String value, int amount) {
        try {
            ItemStack head = new ItemStack((ItemLike)Items.PLAYER_HEAD, Math.max(amount, 1));
            return head;
        }
        catch (Exception exception) {
            return ItemStack.EMPTY;
        }
    }

    public static boolean isPlaceholder() {
        try {
            Class.forName("eu.pb4.placeholders.api.PlaceholderContext");
            return true;
        }
        catch (ClassNotFoundException e) {
            return false;
        }
    }

    public static Object convertNbtValue(Object nbtElement) {
        if (nbtElement instanceof StringTag) {
            StringTag stringTag = (StringTag)nbtElement;
            return stringTag.getAsString();
        }
        if (nbtElement instanceof NumericTag) {
            NumericTag numericTag = (NumericTag)nbtElement;
            return numericTag.getAsNumber();
        }
        if (nbtElement instanceof CompoundTag) {
            CompoundTag compoundTag = (CompoundTag)nbtElement;
            HashMap<String, Object> map = new HashMap<String, Object>();
            for (String key : compoundTag.getAllKeys()) {
                map.put(key, Utils.convertNbtValue(compoundTag.get(key)));
            }
            return map;
        }
        if (nbtElement instanceof ListTag) {
            ListTag listTag = (ListTag)nbtElement;
            ArrayList<Object> list = new ArrayList<Object>();
            for (int i = 0; i < listTag.size(); ++i) {
                list.add(Utils.convertNbtValue(listTag.get(i)));
            }
            return list;
        }
        return null;
    }
}

