/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cobblemon.mod.common.pokemon.Pokemon
 *  lombok.Generated
 *  net.objecthunter.exp4j.Expression
 *  net.objecthunter.exp4j.ExpressionBuilder
 */
package com.whoslucid.cobblelib.Model;

import com.cobblemon.mod.common.pokemon.Pokemon;
import com.whoslucid.cobblelib.CobbleLib;
import com.whoslucid.cobblelib.util.PokemonUtils;
import lombok.Generated;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class PokemonFormula {
    private boolean showVariablesInConsole = false;
    private String formula = "0";

    public Expression getPokemonExpression(Pokemon pokemon) {
        try {
            String safeFormula = this.formula != null && !this.formula.isEmpty() ? this.formula : "0";
            ExpressionBuilder builder = new ExpressionBuilder(safeFormula);
            if (safeFormula.contains("level")) {
                builder.variable("level");
            }
            if (safeFormula.contains("ivs_total")) {
                builder.variable("ivs_total");
            }
            if (safeFormula.contains("evs_total")) {
                builder.variable("evs_total");
            }
            if (safeFormula.contains("ivs_avg")) {
                builder.variable("ivs_avg");
            }
            if (safeFormula.contains("evs_avg")) {
                builder.variable("evs_avg");
            }
            if (safeFormula.contains("perfect_ivs")) {
                builder.variable("perfect_ivs");
            }
            if (safeFormula.contains("shiny")) {
                builder.variable("shiny");
            }
            if (safeFormula.contains("legendary")) {
                builder.variable("legendary");
            }
            Expression expression = builder.build();
            if (pokemon != null) {
                if (safeFormula.contains("level")) {
                    expression.setVariable("level", (double)pokemon.getLevel());
                }
                if (safeFormula.contains("ivs_total")) {
                    expression.setVariable("ivs_total", (double)PokemonUtils.getIvsTotal(pokemon.getIvs()));
                }
                if (safeFormula.contains("evs_total")) {
                    expression.setVariable("evs_total", (double)PokemonUtils.getEvsTotal(pokemon.getEvs()));
                }
                if (safeFormula.contains("ivs_avg")) {
                    expression.setVariable("ivs_avg", (double)PokemonUtils.getIvsAverage(pokemon.getIvs()));
                }
                if (safeFormula.contains("evs_avg")) {
                    expression.setVariable("evs_avg", (double)PokemonUtils.getEvsAverage(pokemon.getEvs()));
                }
                if (safeFormula.contains("perfect_ivs")) {
                    expression.setVariable("perfect_ivs", (double)PokemonUtils.getTotalPerfectIvs(pokemon.getIvs()));
                }
                if (safeFormula.contains("shiny")) {
                    expression.setVariable("shiny", pokemon.getShiny() ? 1.0 : 0.0);
                }
                if (safeFormula.contains("legendary")) {
                    expression.setVariable("legendary", PokemonUtils.getRarityS(pokemon).equals("legendary") ? 1.0 : 0.0);
                }
            }
            if (this.showVariablesInConsole && pokemon != null) {
                CobbleLib.LOGGER.info("Formula: " + safeFormula);
                CobbleLib.LOGGER.info("Level: " + pokemon.getLevel());
                CobbleLib.LOGGER.info("IVs Total: " + PokemonUtils.getIvsTotal(pokemon.getIvs()));
                CobbleLib.LOGGER.info("EVs Total: " + PokemonUtils.getEvsTotal(pokemon.getEvs()));
            }
            return expression;
        }
        catch (Exception e) {
            CobbleLib.LOGGER.error("Failed to parse formula: " + this.formula + " - " + e.getMessage());
            return new ExpressionBuilder("0").build();
        }
    }

    public Double getPokemonValue(Pokemon pokemon) {
        try {
            return this.getPokemonExpression(pokemon).evaluate();
        }
        catch (Exception e) {
            return 0.0;
        }
    }

    @Generated
    public boolean isShowVariablesInConsole() {
        return this.showVariablesInConsole;
    }

    @Generated
    public String getFormula() {
        return this.formula;
    }

    @Generated
    public void setShowVariablesInConsole(boolean showVariablesInConsole) {
        this.showVariablesInConsole = showVariablesInConsole;
    }

    @Generated
    public void setFormula(String formula) {
        this.formula = formula;
    }

    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof PokemonFormula)) {
            return false;
        }
        PokemonFormula other = (PokemonFormula)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (this.isShowVariablesInConsole() != other.isShowVariablesInConsole()) {
            return false;
        }
        String this$formula = this.getFormula();
        String other$formula = other.getFormula();
        return !(this$formula == null ? other$formula != null : !this$formula.equals(other$formula));
    }

    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof PokemonFormula;
    }

    @Generated
    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + (this.isShowVariablesInConsole() ? 79 : 97);
        String $formula = this.getFormula();
        result = result * 59 + ($formula == null ? 43 : $formula.hashCode());
        return result;
    }

    @Generated
    public String toString() {
        return "PokemonFormula(showVariablesInConsole=" + this.isShowVariablesInConsole() + ", formula=" + this.getFormula() + ")";
    }
}

