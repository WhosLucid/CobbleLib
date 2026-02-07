package com.whoslucid.cobblelib.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.whoslucid.cobblelib.CobbleLib;
import com.whoslucid.cobblelib.api.EconomyApi;
import com.whoslucid.cobblelib.api.PermissionApi;
import com.whoslucid.cobblelib.util.AdventureTranslator;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.server.level.ServerPlayer;

import java.math.BigDecimal;

public class CobbleTokensCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
            Commands.literal("cobbletokens")
                .requires(source -> PermissionApi.hasPermission(source, "cobblelib.tokens", 0))
                // /cobbletokens - Check own balance
                .executes(context -> showOwnBalance(context))
                // /cobbletokens balance - Check own balance
                .then(Commands.literal("balance")
                    .executes(context -> showOwnBalance(context))
                    // /cobbletokens balance <player> - Check another player's balance (admin)
                    .then(Commands.argument("player", EntityArgument.player())
                        .requires(source -> PermissionApi.hasPermission(source, "cobblelib.tokens.admin", 4))
                        .executes(context -> {
                            ServerPlayer target = EntityArgument.getPlayer(context, "player");
                            return showBalance(context.getSource(), target);
                        })
                    )
                )
                // /cobbletokens give <player> <amount> - Give tokens (admin)
                .then(Commands.literal("give")
                    .requires(source -> PermissionApi.hasPermission(source, "cobblelib.tokens.admin", 4))
                    .then(Commands.argument("player", EntityArgument.player())
                        .then(Commands.argument("amount", IntegerArgumentType.integer(1))
                            .executes(context -> {
                                ServerPlayer target = EntityArgument.getPlayer(context, "player");
                                int amount = IntegerArgumentType.getInteger(context, "amount");
                                return giveTokens(context.getSource(), target, amount);
                            })
                        )
                    )
                )
                // /cobbletokens take <player> <amount> - Take tokens (admin)
                .then(Commands.literal("take")
                    .requires(source -> PermissionApi.hasPermission(source, "cobblelib.tokens.admin", 4))
                    .then(Commands.argument("player", EntityArgument.player())
                        .then(Commands.argument("amount", IntegerArgumentType.integer(1))
                            .executes(context -> {
                                ServerPlayer target = EntityArgument.getPlayer(context, "player");
                                int amount = IntegerArgumentType.getInteger(context, "amount");
                                return takeTokens(context.getSource(), target, amount);
                            })
                        )
                    )
                )
                // /cobbletokens set <player> <amount> - Set balance (admin)
                .then(Commands.literal("set")
                    .requires(source -> PermissionApi.hasPermission(source, "cobblelib.tokens.admin", 4))
                    .then(Commands.argument("player", EntityArgument.player())
                        .then(Commands.argument("amount", IntegerArgumentType.integer(0))
                            .executes(context -> {
                                ServerPlayer target = EntityArgument.getPlayer(context, "player");
                                int amount = IntegerArgumentType.getInteger(context, "amount");
                                return setTokens(context.getSource(), target, amount);
                            })
                        )
                    )
                )
                // /cobbletokens send <player> <amount> - Send tokens to another player
                .then(Commands.literal("send")
                    .requires(source -> source.isPlayer())
                    .then(Commands.argument("player", EntityArgument.player())
                        .then(Commands.argument("amount", IntegerArgumentType.integer(1))
                            .executes(context -> {
                                ServerPlayer sender = context.getSource().getPlayerOrException();
                                ServerPlayer target = EntityArgument.getPlayer(context, "player");
                                int amount = IntegerArgumentType.getInteger(context, "amount");
                                return sendTokens(context.getSource(), sender, target, amount);
                            })
                        )
                    )
                )
                // /cobbletokens pay <player> <amount> - Alias for send
                .then(Commands.literal("pay")
                    .requires(source -> source.isPlayer())
                    .then(Commands.argument("player", EntityArgument.player())
                        .then(Commands.argument("amount", IntegerArgumentType.integer(1))
                            .executes(context -> {
                                ServerPlayer sender = context.getSource().getPlayerOrException();
                                ServerPlayer target = EntityArgument.getPlayer(context, "player");
                                int amount = IntegerArgumentType.getInteger(context, "amount");
                                return sendTokens(context.getSource(), sender, target, amount);
                            })
                        )
                    )
                )
        );
    }

    private static int showOwnBalance(CommandContext<CommandSourceStack> context) {
        CommandSourceStack source = context.getSource();
        if (!source.isPlayer()) {
            source.sendSystemMessage(AdventureTranslator.toNative("&cThis command can only be used by players."));
            return 0;
        }
        try {
            ServerPlayer player = source.getPlayerOrException();
            return showBalance(source, player);
        } catch (Exception e) {
            return 0;
        }
    }

    private static int showBalance(CommandSourceStack source, ServerPlayer target) {
        String symbol = CobbleLib.config.getEconomySymbol();
        int balance = EconomyApi.getVirtualBalance(target.getUUID());
        source.sendSystemMessage(AdventureTranslator.toNative(
            "&e" + target.getName().getString() + "&7's balance: &a" + balance + " " + symbol
        ));
        return 1;
    }

    private static int giveTokens(CommandSourceStack source, ServerPlayer target, int amount) {
        String symbol = CobbleLib.config.getEconomySymbol();
        boolean success = EconomyApi.addMoney(target.getUUID(), BigDecimal.valueOf(amount), null);
        if (success) {
            source.sendSystemMessage(AdventureTranslator.toNative(
                "&aGave &e" + amount + " " + symbol + " &ato &e" + target.getName().getString()
            ));
            // Notify the target player
            target.sendSystemMessage(AdventureTranslator.toNative(
                "&aYou received &e" + amount + " " + symbol + "&a!"
            ));
        } else {
            source.sendSystemMessage(AdventureTranslator.toNative(
                "&cFailed to give " + symbol + " to " + target.getName().getString()
            ));
        }
        return success ? 1 : 0;
    }

    private static int takeTokens(CommandSourceStack source, ServerPlayer target, int amount) {
        String symbol = CobbleLib.config.getEconomySymbol();
        boolean success = EconomyApi.removeMoney(target.getUUID(), BigDecimal.valueOf(amount), null);
        if (success) {
            source.sendSystemMessage(AdventureTranslator.toNative(
                "&aTook &e" + amount + " " + symbol + " &afrom &e" + target.getName().getString()
            ));
        } else {
            source.sendSystemMessage(AdventureTranslator.toNative(
                "&c" + target.getName().getString() + " doesn't have enough " + symbol
            ));
        }
        return success ? 1 : 0;
    }

    private static int setTokens(CommandSourceStack source, ServerPlayer target, int amount) {
        String symbol = CobbleLib.config.getEconomySymbol();
        EconomyApi.setBalance(target.getUUID(), amount);
        source.sendSystemMessage(AdventureTranslator.toNative(
            "&aSet &e" + target.getName().getString() + "&a's balance to &e" + amount + " " + symbol
        ));
        return 1;
    }

    private static int sendTokens(CommandSourceStack source, ServerPlayer sender, ServerPlayer target, int amount) {
        String symbol = CobbleLib.config.getEconomySymbol();

        // Can't send to yourself
        if (sender.getUUID().equals(target.getUUID())) {
            source.sendSystemMessage(AdventureTranslator.toNative("&cYou cannot send " + symbol + " to yourself!"));
            return 0;
        }

        boolean success = EconomyApi.transfer(sender.getUUID(), target.getUUID(), amount);
        if (success) {
            source.sendSystemMessage(AdventureTranslator.toNative(
                "&aSent &e" + amount + " " + symbol + " &ato &e" + target.getName().getString()
            ));
            // Notify the target player
            target.sendSystemMessage(AdventureTranslator.toNative(
                "&aYou received &e" + amount + " " + symbol + " &afrom &e" + sender.getName().getString() + "&a!"
            ));
        } else {
            source.sendSystemMessage(AdventureTranslator.toNative(
                "&cYou don't have enough " + symbol + "! Your balance: &e" + EconomyApi.getVirtualBalance(sender.getUUID()) + " " + symbol
            ));
        }
        return success ? 1 : 0;
    }
}
