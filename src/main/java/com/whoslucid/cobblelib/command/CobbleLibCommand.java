package com.whoslucid.cobblelib.command;

import com.mojang.brigadier.CommandDispatcher;
import com.whoslucid.cobblelib.CobbleLib;
import com.whoslucid.cobblelib.api.PermissionApi;
import com.whoslucid.cobblelib.util.AdventureTranslator;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

public class CobbleLibCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
            Commands.literal("cobblelib")
                .requires(source -> PermissionApi.hasPermission(source, "cobblelib.base", 2))
                // /cobblelib reload - Reload configs
                .then(Commands.literal("reload")
                    .requires(source -> PermissionApi.hasPermission(source, "cobblelib.reload", 4))
                    .executes(context -> {
                        CobbleLib.load();
                        context.getSource().sendSystemMessage(
                            AdventureTranslator.toNative(CobbleLib.language.getMessageReload())
                        );
                        return 1;
                    })
                )
                // /cobblelib info - Display mod info
                .then(Commands.literal("info")
                    .executes(context -> {
                        CommandSourceStack source = context.getSource();
                        source.sendSystemMessage(AdventureTranslator.toNative("&bCobbleLib NeoForge &7v1.0.0"));
                        source.sendSystemMessage(AdventureTranslator.toNative("&7Currency: &e" + CobbleLib.config.getEconomySymbol()));
                        source.sendSystemMessage(AdventureTranslator.toNative("&7Use &e/cobbletokens &7to manage your balance."));
                        return 1;
                    })
                )
        );
    }
}
