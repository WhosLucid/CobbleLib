/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package com.whoslucid.cobblelib.util;

import java.util.HashMap;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UtilsLogger {
    private static final Map<String, Logger> loggers = new HashMap<String, Logger>();
    private Logger logger;

    private Logger getLogger(String modId) {
        return loggers.computeIfAbsent(modId, LogManager::getLogger);
    }

    public UtilsLogger() {
        this.logger = LogManager.getLogger((String)"CobbleLib");
    }

    public UtilsLogger(String name) {
        this.logger = LogManager.getLogger((String)name);
    }

    public void info(String message) {
        this.logger.info(message);
    }

    public void info(String modId, String message) {
        this.getLogger(modId).info(message);
    }

    public void error(String message) {
        this.logger.error(message);
    }

    public void error(String modId, String message) {
        this.getLogger(modId).error(message);
    }

    public void fatal(String message) {
        this.logger.fatal(message);
    }

    public void fatal(String modId, String message) {
        this.getLogger(modId).fatal(message);
    }

    public void warn(String message) {
        this.logger.warn(message);
    }

    public void warn(String modId, String message) {
        this.getLogger(modId).warn(message);
    }
}

