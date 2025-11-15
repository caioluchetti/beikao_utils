package com.beikao.utils.datagen;

import com.beikao.utils.BeikaoUtils;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraft.tags.ItemTags; // <-- ADD THIS

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider {

    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookup) {
        super(output, lookup);
    }

    @Override
    protected void buildRecipes(RecipeOutput output) {

        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD,BeikaoUtils.BEIKAO.get())
                .pattern("BBB")
                .pattern("MCP")
                .pattern("BBB")
                .define('B', Items.BREAD)
                .define('M', Items.COOKED_BEEF)
                .define('C', Items.COOKED_CHICKEN)
                .define('P', Items.COOKED_PORKCHOP)
                .unlockedBy("has_beef", has(Items.BREAD))
                .save(output);
                
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, BeikaoUtils.SERRAO.get())
                .pattern("dRO")
                .pattern("ddO")
                .define('d', Items.DIAMOND)
                .define('R', Items.REDSTONE)
                .define('O', ItemTags.PLANKS)
                .unlockedBy("has_iron", has(Items.DIAMOND))
                .save(output);  

}
}