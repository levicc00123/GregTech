package gregtech.api.recipes.crafttweaker;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.liquid.ILiquidDefinition;
import crafttweaker.api.liquid.ILiquidStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.mc1120.item.MCItemStack;
import crafttweaker.mc1120.liquid.MCLiquidStack;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMap;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@ZenClass("mods.gregtech.recipe.Recipe")
@ZenRegister
public class CTRecipe {

    private final RecipeMap<?> recipeMap;
    private final Recipe backingRecipe;

    public CTRecipe(RecipeMap<?> recipeMap, Recipe backingRecipe) {
        this.recipeMap = recipeMap;
        this.backingRecipe = backingRecipe;
    }

    @ZenGetter("inputs")
    public List<InputIngredient> getInputs() {
        return this.backingRecipe.getInputs().stream()
            .map(InputIngredient::new)
            .collect(Collectors.toList());
    }

    @ZenGetter("outputs")
    public List<IItemStack> getOutputs() {
        return this.backingRecipe.getOutputs().stream()
            .map(MCItemStack::new)
            .collect(Collectors.toList());
    }

    @ZenMethod
    public List<IItemStack> getResultItemOutputs(long randomSeed) {
        return this.backingRecipe.getResultItemOutputs(new Random(randomSeed)).stream()
            .map(MCItemStack::new)
            .collect(Collectors.toList());
    }

    @ZenGetter("changedOutputs")
    public List<ChancedEntry> getChancedOutputs() {
        ArrayList<ChancedEntry> result = new ArrayList<>();
        this.backingRecipe.getChancedOutputs().forEachEntry((stack, chance) ->
            result.add(new ChancedEntry(new MCItemStack(stack), chance)));
        return result;
    }

    @ZenGetter("fluidInputs")
    public List<ILiquidStack> getFluidInputs() {
        return this.backingRecipe.getFluidInputs().stream()
            .map(MCLiquidStack::new)
            .collect(Collectors.toList());
    }

    @ZenMethod
    public boolean hasInputFluid(ILiquidDefinition liquidDefinition) {
        return this.backingRecipe.hasInputFluid(CraftTweakerMC.getFluid(liquidDefinition));
    }

    @ZenGetter("fluidOutputs")
    public List<ILiquidStack> getFluidOutputs() {
        return this.backingRecipe.getFluidOutputs().stream()
            .map(MCLiquidStack::new)
            .collect(Collectors.toList());
    }

    @ZenGetter("duration")
    public int getDuration() {
        return this.backingRecipe.getDuration();
    }

    @ZenGetter("EUt")
    public int getEUt() {
        return this.backingRecipe.getEUt();
    }

    @ZenGetter("hidden")
    public boolean isHidden() {
        return this.backingRecipe.isHidden();
    }

    @ZenGetter
    public boolean canBeBuffered() {
        return this.backingRecipe.canBeBuffered();
    }

    @ZenGetter
    public boolean needsEmptyOutput() {
        return this.backingRecipe.needsEmptyOutput();
    }

    @ZenGetter("propertyKeys")
    public List<String> getPropertyKeys() {
        return new ArrayList<>(this.backingRecipe.getPropertyKeys());
    }

    @ZenMethod
    public Object getProperty(String key) {
        return this.backingRecipe.getProperty(key);
    }

    @ZenMethod
    public boolean remove() {
        return this.recipeMap.removeRecipe(this.backingRecipe);
    }

}