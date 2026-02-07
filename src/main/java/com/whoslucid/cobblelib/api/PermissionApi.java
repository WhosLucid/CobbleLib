package com.whoslucid.cobblelib.api;

import com.whoslucid.cobblelib.CobbleLib;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.fml.ModList;

import java.lang.reflect.Method;
import java.util.List;
import java.util.UUID;

/**
 * Permission API with optional FTB Ranks integration.
 * Falls back to vanilla op levels if FTB Ranks is not installed.
 */
public class PermissionApi {
    private static Boolean ftbRanksLoaded = null;
    private static Method getPermissionValueMethod = null;
    private static Method isEmptyMethod = null;
    private static Method asBooleanOrFalseMethod = null;

    private static boolean isFtbRanksAvailable() {
        if (ftbRanksLoaded == null) {
            ftbRanksLoaded = ModList.get().isLoaded("ftbranks");
            if (ftbRanksLoaded) {
                try {
                    Class<?> ftbRanksApiClass = Class.forName("dev.ftb.mods.ftbranks.api.FTBRanksAPI");
                    Class<?> permissionValueClass = Class.forName("dev.ftb.mods.ftbranks.api.PermissionValue");

                    getPermissionValueMethod = ftbRanksApiClass.getMethod("getPermissionValue", ServerPlayer.class, String.class);
                    isEmptyMethod = permissionValueClass.getMethod("isEmpty");
                    asBooleanOrFalseMethod = permissionValueClass.getMethod("asBooleanOrFalse");

                    CobbleLib.LOGGER.info("FTB Ranks detected - permission integration enabled!");
                } catch (Exception e) {
                    CobbleLib.LOGGER.warn("FTB Ranks found but failed to initialize: " + e.getMessage());
                    ftbRanksLoaded = false;
                }
            }
        }
        return ftbRanksLoaded;
    }

    private static Boolean checkFtbPermission(ServerPlayer player, String permission) {
        if (!isFtbRanksAvailable()) {
            return null; // FTB Ranks not available
        }
        try {
            Object permValue = getPermissionValueMethod.invoke(null, player, permission);
            boolean isEmpty = (Boolean) isEmptyMethod.invoke(permValue);
            if (isEmpty) {
                return null; // Permission not configured in FTB Ranks
            }
            return (Boolean) asBooleanOrFalseMethod.invoke(permValue);
        } catch (Exception e) {
            return null;
        }
    }

    public static boolean hasPermission(ServerPlayer player, String permission, int level) {
        if (player == null) {
            return false;
        }
        Boolean ftbResult = checkFtbPermission(player, permission);
        if (ftbResult != null) {
            return ftbResult;
        }
        return player.hasPermissions(level);
    }

    public static boolean hasPermission(ServerPlayer player, List<String> permissions, int level) {
        if (player == null) {
            return false;
        }
        boolean anyConfigured = false;
        for (String permission : permissions) {
            Boolean ftbResult = checkFtbPermission(player, permission);
            if (ftbResult != null) {
                anyConfigured = true;
                if (ftbResult) {
                    return true;
                }
            }
        }
        if (anyConfigured) {
            return false;
        }
        return player.hasPermissions(level);
    }

    public static boolean hasPermission(UUID uuid, String permission, int level) {
        if (CobbleLib.server == null) {
            return false;
        }
        ServerPlayer player = CobbleLib.server.getPlayerList().getPlayer(uuid);
        if (player != null) {
            return hasPermission(player, permission, level);
        }
        return false;
    }

    public static boolean hasPermission(CommandSourceStack source, String permission, int level) {
        if (source == null) {
            return false;
        }
        ServerPlayer player = source.getPlayer();
        if (player != null) {
            return hasPermission(player, permission, level);
        }
        return source.hasPermission(level);
    }

    public static boolean hasPermission(CommandSourceStack source, List<String> permissions, int level) {
        if (source == null) {
            return false;
        }
        ServerPlayer player = source.getPlayer();
        if (player != null) {
            return hasPermission(player, permissions, level);
        }
        return source.hasPermission(level);
    }
}
