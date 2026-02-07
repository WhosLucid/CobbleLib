/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  ca.landonjw.gooeylibs2.api.button.Button
 *  ca.landonjw.gooeylibs2.api.button.GooeyButton
 *  ca.landonjw.gooeylibs2.api.button.PlaceholderButton
 *  ca.landonjw.gooeylibs2.api.template.types.ChestTemplate
 *  lombok.Generated
 */
package com.whoslucid.cobblelib.Model;

import ca.landonjw.gooeylibs2.api.button.Button;
import ca.landonjw.gooeylibs2.api.button.GooeyButton;
import ca.landonjw.gooeylibs2.api.button.PlaceholderButton;
import ca.landonjw.gooeylibs2.api.template.types.ChestTemplate;
import java.util.List;
import lombok.Generated;

public class Rectangle {
    private int startRow;
    private int startColumn;
    private int length;
    private int width;

    public Rectangle() {
        this.startRow = 0;
        this.startColumn = 0;
        this.length = 5;
        this.width = 9;
    }

    public Rectangle(int rows) {
        this.startRow = 0;
        this.startColumn = 0;
        this.length = rows - 1;
        this.width = 9;
    }

    public Rectangle(int startRow, int startColumn, int length, int width) {
        this.startRow = startRow;
        this.startColumn = startColumn;
        this.length = length;
        this.width = width;
    }

    public void apply(ChestTemplate template) {
        template.rectangle(this.startRow, this.startColumn, this.length, this.width, (Button)new PlaceholderButton());
    }

    public void apply(ChestTemplate template, List<GooeyButton> buttons) {
        int max = buttons.size();
        int index = 0;
        int maxRow = this.startRow + this.length;
        int maxColumn = this.startColumn + this.width;
        for (int row = this.startRow; row < maxRow; ++row) {
            for (int column = this.startColumn; column < maxColumn; ++column) {
                if (index >= max) {
                    return;
                }
                template.set(row, column, (Button)buttons.get(index++));
            }
        }
    }

    public int getSlotsFree(int rows) {
        int totalSlots = rows * 9;
        int occupiedSlots = this.length * this.width;
        return totalSlots - occupiedSlots;
    }

    public int getTotalSlots() {
        return this.length * this.width;
    }

    @Generated
    public int getStartRow() {
        return this.startRow;
    }

    @Generated
    public int getStartColumn() {
        return this.startColumn;
    }

    @Generated
    public int getLength() {
        return this.length;
    }

    @Generated
    public int getWidth() {
        return this.width;
    }

    @Generated
    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }

    @Generated
    public void setStartColumn(int startColumn) {
        this.startColumn = startColumn;
    }

    @Generated
    public void setLength(int length) {
        this.length = length;
    }

    @Generated
    public void setWidth(int width) {
        this.width = width;
    }

    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Rectangle)) {
            return false;
        }
        Rectangle other = (Rectangle)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (this.getStartRow() != other.getStartRow()) {
            return false;
        }
        if (this.getStartColumn() != other.getStartColumn()) {
            return false;
        }
        if (this.getLength() != other.getLength()) {
            return false;
        }
        return this.getWidth() == other.getWidth();
    }

    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof Rectangle;
    }

    @Generated
    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + this.getStartRow();
        result = result * 59 + this.getStartColumn();
        result = result * 59 + this.getLength();
        result = result * 59 + this.getWidth();
        return result;
    }

    @Generated
    public String toString() {
        return "Rectangle(startRow=" + this.getStartRow() + ", startColumn=" + this.getStartColumn() + ", length=" + this.getLength() + ", width=" + this.getWidth() + ")";
    }
}

