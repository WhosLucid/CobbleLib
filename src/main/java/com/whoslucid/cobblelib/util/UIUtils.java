/*
 * Decompiled with CFR 0.152.
 */
package com.whoslucid.cobblelib.util;

public class UIUtils {
    public static boolean isInside(Integer slot, int rows) {
        if (slot == null) {
            return false;
        }
        return slot >= 0 && slot < rows * 9;
    }
}

