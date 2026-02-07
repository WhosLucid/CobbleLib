package com.whoslucid.cobblelib;

import com.mojang.brigadier.CommandDispatcher;
import com.whoslucid.cobblelib.api.EconomyApi;
import com.whoslucid.cobblelib.command.CobbleLibCommand;
import com.whoslucid.cobblelib.command.CobbleTokensCommand;
import com.whoslucid.cobblelib.config.Config;
import com.whoslucid.cobblelib.config.Lang;
import com.whoslucid.cobblelib.util.Utils;
import com.whoslucid.cobblelib.util.UtilsLogger;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.MinecraftServer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.event.server.ServerStoppingEvent;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Mod("cobblelib")
public class CobbleLib {
    public static final String MOD_ID = "cobblelib";
    public static final String MOD_NAME = "CobbleLib";
    public static final String PATH = "/config/cobblelib";
    public static final UtilsLogger LOGGER = new UtilsLogger();
    public static MinecraftServer server;
    public static Config config = new Config();
    public static Lang language = new Lang();
    private static final ExecutorService EXECUTOR = Executors.newSingleThreadExecutor(r -> {
        Thread t = new Thread(r, "CobbleLib-Async");
        t.setDaemon(true);
        return t;
    });

    public CobbleLib(IEventBus modEventBus, ModContainer modContainer) {
        NeoForge.EVENT_BUS.register(this);
        LOGGER.info("CobbleLib NeoForge is loading...");
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        server = event.getServer();
        init();
        load();
        LOGGER.info("CobbleLib NeoForge has started.");
    }

    @SubscribeEvent
    public void onServerStopping(ServerStoppingEvent event) {
        EconomyApi.saveBalances();
        EXECUTOR.shutdown();
        LOGGER.info("CobbleLib NeoForge has stopped.");
    }

    @SubscribeEvent
    public void onRegisterCommands(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
        CobbleLibCommand.register(dispatcher);
        CobbleTokensCommand.register(dispatcher);
    }

    public static void init() {
        Utils.getAbsolutePath(PATH).mkdirs();
    }

    public static void load() {
        files();
        EconomyApi.reloadBalances();
    }

    public static void files() {
        config = Config.load();
        language = Lang.load();
    }

    public static void info(String identifier, String github) {
        info(identifier, null, github);
    }

    public static void info(String identifier, String version, String github) {
        LOGGER.info("Loaded " + identifier + (version != null ? " v" + version : "") + " - " + github);
    }

    public static CompletableFuture<Void> runAsync(Runnable runnable) {
        return CompletableFuture.runAsync(runnable, EXECUTOR);
    }

    public static CompletableFuture<Void> runAsync(Runnable runnable, ExecutorService executor) {
        return CompletableFuture.runAsync(runnable, executor);
    }
}
