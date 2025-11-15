package com.beikao.utils.items;

import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.SimpleTier; // <-- THIS IS THE LINE

public class ModToolTiers {
    
    public static final Tier SAW = new SimpleTier(
            BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 
            250,                         
            7.0f,
                            
            2.0f,                       
            30,                         
            () -> Ingredient.of(Items.DIAMOND)
    );
}