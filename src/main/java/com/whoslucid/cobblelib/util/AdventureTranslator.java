/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.ChatFormatting
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.network.chat.Style
 *  net.minecraft.network.chat.TextColor
 *  net.minecraft.server.level.ServerPlayer
 *  org.jetbrains.annotations.Nullable
 */
package com.whoslucid.cobblelib.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.Nullable;

public class AdventureTranslator {
    private static final Pattern HEX_PATTERN = Pattern.compile("&#([0-9a-fA-F]{6})");
    private static final Pattern GRADIENT_PATTERN = Pattern.compile("<gradient:#([0-9a-fA-F]{6}):#([0-9a-fA-F]{6})>(.*?)</gradient>");
    private static final Map<Character, ChatFormatting> FORMAT_MAP = Map.ofEntries(Map.entry(Character.valueOf('0'), ChatFormatting.BLACK), Map.entry(Character.valueOf('1'), ChatFormatting.DARK_BLUE), Map.entry(Character.valueOf('2'), ChatFormatting.DARK_GREEN), Map.entry(Character.valueOf('3'), ChatFormatting.DARK_AQUA), Map.entry(Character.valueOf('4'), ChatFormatting.DARK_RED), Map.entry(Character.valueOf('5'), ChatFormatting.DARK_PURPLE), Map.entry(Character.valueOf('6'), ChatFormatting.GOLD), Map.entry(Character.valueOf('7'), ChatFormatting.GRAY), Map.entry(Character.valueOf('8'), ChatFormatting.DARK_GRAY), Map.entry(Character.valueOf('9'), ChatFormatting.BLUE), Map.entry(Character.valueOf('a'), ChatFormatting.GREEN), Map.entry(Character.valueOf('b'), ChatFormatting.AQUA), Map.entry(Character.valueOf('c'), ChatFormatting.RED), Map.entry(Character.valueOf('d'), ChatFormatting.LIGHT_PURPLE), Map.entry(Character.valueOf('e'), ChatFormatting.YELLOW), Map.entry(Character.valueOf('f'), ChatFormatting.WHITE), Map.entry(Character.valueOf('k'), ChatFormatting.OBFUSCATED), Map.entry(Character.valueOf('l'), ChatFormatting.BOLD), Map.entry(Character.valueOf('m'), ChatFormatting.STRIKETHROUGH), Map.entry(Character.valueOf('n'), ChatFormatting.UNDERLINE), Map.entry(Character.valueOf('o'), ChatFormatting.ITALIC), Map.entry(Character.valueOf('r'), ChatFormatting.RESET));

    private static int interpolateColor(int startColor, int endColor, float ratio) {
        int startR = startColor >> 16 & 0xFF;
        int startG = startColor >> 8 & 0xFF;
        int startB = startColor & 0xFF;
        int endR = endColor >> 16 & 0xFF;
        int endG = endColor >> 8 & 0xFF;
        int endB = endColor & 0xFF;
        int r = Math.round((float)startR + (float)(endR - startR) * ratio);
        int g = Math.round((float)startG + (float)(endG - startG) * ratio);
        int b = Math.round((float)startB + (float)(endB - startB) * ratio);
        return r << 16 | g << 8 | b;
    }

    private static String processGradients(String text) {
        if (text == null || text.isEmpty()) {
            return text;
        }
        Matcher matcher = GRADIENT_PATTERN.matcher(text);
        StringBuilder result = new StringBuilder();
        while (matcher.find()) {
            String startHex = matcher.group(1);
            String endHex = matcher.group(2);
            String content = matcher.group(3);
            int startColor = Integer.parseInt(startHex, 16);
            int endColor = Integer.parseInt(endHex, 16);
            StringBuilder gradientText = new StringBuilder();
            int length = content.length();
            for (int i = 0; i < length; ++i) {
                float ratio = length > 1 ? (float)i / (float)(length - 1) : 0.0f;
                int color = AdventureTranslator.interpolateColor(startColor, endColor, ratio);
                gradientText.append(String.format("&#%06x%c", color, Character.valueOf(content.charAt(i))));
            }
            matcher.appendReplacement(result, Matcher.quoteReplacement(gradientText.toString()));
        }
        matcher.appendTail(result);
        return result.toString();
    }

    private static Component parseFormattedText(String text) {
        if (text == null || text.isEmpty()) {
            return Component.literal((String)"");
        }
        String gradientProcessed = AdventureTranslator.processGradients(text);
        Matcher hexMatcher = HEX_PATTERN.matcher(gradientProcessed);
        StringBuilder hexProcessed = new StringBuilder();
        while (hexMatcher.find()) {
            hexMatcher.appendReplacement(hexProcessed, "\u00a7x" + hexMatcher.group(1));
        }
        hexMatcher.appendTail(hexProcessed);
        String processed = hexProcessed.toString();
        MutableComponent result = Component.empty();
        Style currentStyle = Style.EMPTY;
        StringBuilder currentText = new StringBuilder();
        for (int i = 0; i < processed.length(); ++i) {
            char next;
            ChatFormatting formatting;
            char c = processed.charAt(i);
            if (c == '&' && i + 1 < processed.length() && (formatting = FORMAT_MAP.get(Character.valueOf(next = Character.toLowerCase(processed.charAt(i + 1))))) != null) {
                if (currentText.length() > 0) {
                    result.append((Component)Component.literal((String)currentText.toString()).withStyle(currentStyle));
                    currentText.setLength(0);
                }
                currentStyle = formatting == ChatFormatting.RESET ? Style.EMPTY : (formatting.isColor() ? Style.EMPTY.withColor(formatting) : currentStyle.applyFormat(formatting));
                ++i;
                continue;
            }
            if (c == '\u00a7' && i + 1 < processed.length() && processed.charAt(i + 1) == 'x' && i + 7 < processed.length()) {
                String hex = processed.substring(i + 2, i + 8);
                try {
                    int color = Integer.parseInt(hex, 16);
                    if (currentText.length() > 0) {
                        result.append((Component)Component.literal((String)currentText.toString()).withStyle(currentStyle));
                        currentText.setLength(0);
                    }
                    currentStyle = Style.EMPTY.withColor(TextColor.fromRgb((int)color));
                    i += 7;
                    continue;
                }
                catch (NumberFormatException color) {
                    // empty catch block
                }
            }
            if (c == '\u00a7' && i + 1 < processed.length() && (formatting = FORMAT_MAP.get(Character.valueOf(next = Character.toLowerCase(processed.charAt(i + 1))))) != null) {
                if (currentText.length() > 0) {
                    result.append((Component)Component.literal((String)currentText.toString()).withStyle(currentStyle));
                    currentText.setLength(0);
                }
                currentStyle = formatting == ChatFormatting.RESET ? Style.EMPTY : (formatting.isColor() ? Style.EMPTY.withColor(formatting) : currentStyle.applyFormat(formatting));
                ++i;
                continue;
            }
            currentText.append(c);
        }
        if (currentText.length() > 0) {
            result.append((Component)Component.literal((String)currentText.toString()).withStyle(currentStyle));
        }
        return result;
    }

    public static Component toNative(String text) {
        if (text == null || text.isBlank()) {
            return Component.literal((String)" ");
        }
        return AdventureTranslator.parseFormattedText(text);
    }

    public static Component toNative(String text, @Nullable String prefix) {
        if (text == null || text.isBlank()) {
            return Component.literal((String)" ");
        }
        String replaced = prefix != null ? text.replace("%prefix%", prefix) : text;
        return AdventureTranslator.parseFormattedText(replaced);
    }

    public static Component toNative(String text, @Nullable String prefix, @Nullable ServerPlayer player) {
        return AdventureTranslator.toNative(text, prefix);
    }

    public static Component toNativeWithOutPrefix(String text) {
        return AdventureTranslator.toNative(text);
    }

    public static Component toNativeWithOutPrefix(String text, @Nullable ServerPlayer player) {
        return AdventureTranslator.toNative(text);
    }

    public static List<Component> toNativeL(List<String> lore) {
        ArrayList<Component> result = new ArrayList<Component>(lore.size());
        for (String line : lore) {
            result.add(AdventureTranslator.toNative(line));
        }
        return result;
    }

    public static List<Component> toNativeL(List<String> lore, @Nullable ServerPlayer player) {
        return AdventureTranslator.toNativeL(lore);
    }

    public static Component toNativeComponent(String text) {
        return Component.empty().append(AdventureTranslator.toNative(text));
    }
}

