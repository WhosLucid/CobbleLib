/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 */
package com.whoslucid.cobblelib.Model;

import lombok.Generated;

public class EconomyUse {
    private String EconomyId;
    private String currency;

    public EconomyUse() {
        this.EconomyId = "XP";
        this.currency = "";
    }

    public EconomyUse(String EconomyId, String currency) {
        this.EconomyId = EconomyId;
        this.currency = currency;
    }

    @Generated
    public String getEconomyId() {
        return this.EconomyId;
    }

    @Generated
    public String getCurrency() {
        return this.currency;
    }

    @Generated
    public void setEconomyId(String EconomyId) {
        this.EconomyId = EconomyId;
    }

    @Generated
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof EconomyUse)) {
            return false;
        }
        EconomyUse other = (EconomyUse)o;
        if (!other.canEqual(this)) {
            return false;
        }
        String this$EconomyId = this.getEconomyId();
        String other$EconomyId = other.getEconomyId();
        if (this$EconomyId == null ? other$EconomyId != null : !this$EconomyId.equals(other$EconomyId)) {
            return false;
        }
        String this$currency = this.getCurrency();
        String other$currency = other.getCurrency();
        return !(this$currency == null ? other$currency != null : !this$currency.equals(other$currency));
    }

    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof EconomyUse;
    }

    @Generated
    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $EconomyId = this.getEconomyId();
        result = result * 59 + ($EconomyId == null ? 43 : $EconomyId.hashCode());
        String $currency = this.getCurrency();
        result = result * 59 + ($currency == null ? 43 : $currency.hashCode());
        return result;
    }

    @Generated
    public String toString() {
        return "EconomyUse(EconomyId=" + this.getEconomyId() + ", currency=" + this.getCurrency() + ")";
    }
}

