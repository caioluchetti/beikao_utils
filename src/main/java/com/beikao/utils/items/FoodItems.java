package com.beikao.utils.items;

import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.tags.BlockTags;

public class FoodItems {

    public static final FoodProperties BEIKAO_FOOD_PROPERTIES =
            new FoodProperties.Builder()
                    .nutrition(20)
                    .saturationModifier(1.0F)
                    .effect(() -> new MobEffectInstance(MobEffects.CONFUSION,10,0), 1.0F)
                    .effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,20,2),1.0F)
                    .alwaysEdible()
                    .build();
                    



    
}
