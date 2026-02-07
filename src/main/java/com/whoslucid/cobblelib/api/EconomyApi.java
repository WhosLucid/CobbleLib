package com.whoslucid.cobblelib.api;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.whoslucid.cobblelib.CobbleLib;
import com.whoslucid.cobblelib.Model.EconomyUse;
import com.whoslucid.cobblelib.util.Utils;
import lombok.Data;

import java.io.File;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Data
public class EconomyApi {
    private static final Map<UUID, Integer> balances = new ConcurrentHashMap<>();
    private static boolean balancesLoaded = false;

    /**
     * Add money to a player's virtual balance.
     */
    public static boolean addMoney(UUID playerUuid, BigDecimal money, EconomyUse economy) {
        int amount = money.intValue();
        if (amount <= 0) {
            return true;
        }
        loadBalances();
        int current = balances.getOrDefault(playerUuid, 0);
        balances.put(playerUuid, current + amount);
        saveBalances();
        return true;
    }

    /**
     * Remove money from a player's virtual balance.
     */
    public static boolean removeMoney(UUID playerUuid, BigDecimal money, EconomyUse economy) {
        int amount = money.intValue();
        if (amount <= 0) {
            return true;
        }
        loadBalances();
        int current = balances.getOrDefault(playerUuid, 0);
        if (current < amount) {
            return false;
        }
        balances.put(playerUuid, current - amount);
        saveBalances();
        return true;
    }

    /**
     * Get a player's virtual balance.
     */
    public static BigDecimal getBalance(UUID playerUuid, EconomyUse economy) {
        loadBalances();
        return BigDecimal.valueOf(balances.getOrDefault(playerUuid, 0));
    }

    /**
     * Format money with the currency symbol.
     */
    public static String formatMoney(BigDecimal money, EconomyUse economy) {
        String symbol = CobbleLib.config.getEconomySymbol();
        return money.intValue() + " " + symbol;
    }

    /**
     * Check if player has enough money, optionally deducting if true.
     */
    public static boolean hasEnoughMoney(UUID playerUuid, BigDecimal money, EconomyUse economy, boolean removeMoney) {
        int amount = money.intValue();
        if (amount <= 0) {
            return true;
        }
        loadBalances();
        int current = balances.getOrDefault(playerUuid, 0);
        boolean hasEnough = current >= amount;
        if (hasEnough && removeMoney) {
            return removeMoney(playerUuid, money, economy);
        }
        return hasEnough;
    }

    /**
     * Get the currency symbol.
     */
    public static String getSymbol(EconomyUse economy) {
        return CobbleLib.config.getEconomySymbol();
    }

    /**
     * Set a player's virtual balance directly.
     */
    public static void setBalance(UUID playerUuid, int amount) {
        loadBalances();
        balances.put(playerUuid, Math.max(0, amount));
        saveBalances();
    }

    /**
     * Get the virtual balance as int.
     */
    public static int getVirtualBalance(UUID playerUuid) {
        loadBalances();
        return balances.getOrDefault(playerUuid, 0);
    }

    /**
     * Transfer money between players.
     */
    public static boolean transfer(UUID fromUuid, UUID toUuid, int amount) {
        if (amount <= 0) {
            return false;
        }
        loadBalances();
        int fromBalance = balances.getOrDefault(fromUuid, 0);
        if (fromBalance < amount) {
            return false;
        }
        int toBalance = balances.getOrDefault(toUuid, 0);
        balances.put(fromUuid, fromBalance - amount);
        balances.put(toUuid, toBalance + amount);
        saveBalances();
        return true;
    }

    private static File getBalanceFile() {
        File dir = Utils.getAbsolutePath("/config/cobblelib");
        dir.mkdirs();
        return new File(dir, "economy.json");
    }

    public static void loadBalances() {
        if (balancesLoaded) {
            return;
        }
        try {
            File file = getBalanceFile();
            if (file.exists()) {
                String content = Files.readString(file.toPath(), StandardCharsets.UTF_8);
                Gson gson = Utils.newGson();
                Type type = new TypeToken<Map<String, Integer>>(){}.getType();
                Map<String, Integer> raw = gson.fromJson(content, type);
                if (raw != null) {
                    balances.clear();
                    raw.forEach((key, val) -> {
                        try {
                            balances.put(UUID.fromString(key), val);
                        } catch (Exception ignored) {}
                    });
                }
            }
            balancesLoaded = true;
        } catch (Exception e) {
            CobbleLib.LOGGER.error("Failed to load economy balances: " + e.getMessage());
        }
    }

    public static void saveBalances() {
        try {
            File file = getBalanceFile();
            Gson gson = Utils.newGson();
            ConcurrentHashMap<String, Integer> raw = new ConcurrentHashMap<>();
            balances.forEach((uuid, val) -> raw.put(uuid.toString(), val));
            String json = gson.toJson(raw);
            Files.writeString(file.toPath(), json, StandardCharsets.UTF_8);
        } catch (Exception e) {
            CobbleLib.LOGGER.error("Failed to save economy balances: " + e.getMessage());
        }
    }

    public static void reloadBalances() {
        balancesLoaded = false;
        loadBalances();
    }
}
