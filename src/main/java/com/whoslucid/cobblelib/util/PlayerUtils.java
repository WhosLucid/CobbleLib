/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.protocol.Packet
 *  net.minecraft.network.protocol.game.ClientboundSetActionBarTextPacket
 *  net.minecraft.server.level.ServerPlayer
 */
package com.whoslucid.cobblelib.util;

import com.whoslucid.cobblelib.CobbleLib;
import com.whoslucid.cobblelib.util.AdventureTranslator;
import com.whoslucid.cobblelib.util.TypeMessage;
import java.util.UUID;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundSetActionBarTextPacket;
import net.minecraft.server.level.ServerPlayer;

public class PlayerUtils {
    public static void sendMessage(UUID playerUUID, String message, String prefix, TypeMessage typeMessage) {
        if (CobbleLib.server == null) {
            return;
        }
        ServerPlayer player = CobbleLib.server.getPlayerList().getPlayer(playerUUID);
        if (player == null) {
            return;
        }
        PlayerUtils.sendMessage(player, message, prefix, typeMessage);
    }

    public static void sendMessage(ServerPlayer player, String message) {
        if (message.isEmpty() || player == null) {
            return;
        }
        player.sendSystemMessage(AdventureTranslator.toNativeWithOutPrefix(message, player));
    }

    public static void sendMessage(ServerPlayer player, String message, String prefix) {
        if (message.isEmpty() || player == null) {
            return;
        }
        player.sendSystemMessage(AdventureTranslator.toNative(message, prefix, player));
    }

    public static void sendMessage(ServerPlayer player, String message, String prefix, TypeMessage typeMessage) {
        if (message.isEmpty() || player == null) {
            return;
        }
        Component component = AdventureTranslator.toNative(message, prefix, player);
        switch (typeMessage) {
            case CHAT: {
                player.sendSystemMessage(component);
                break;
            }
            case ACTIONBAR: {
                player.connection.send((Packet)new ClientboundSetActionBarTextPacket(component));
                break;
            }
            case ACTIONBAR_BROADCAST: {
                if (CobbleLib.server == null) break;
                for (ServerPlayer p : CobbleLib.server.getPlayerList().getPlayers()) {
                    p.connection.send((Packet)new ClientboundSetActionBarTextPacket(component));
                }
                break;
            }
            case BROADCAST: {
                if (CobbleLib.server == null) break;
                for (ServerPlayer p : CobbleLib.server.getPlayerList().getPlayers()) {
                    p.sendSystemMessage(component);
                }
                break;
            }
        }
    }
}

