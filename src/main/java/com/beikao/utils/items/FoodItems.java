package com.beikao.utils.items;

import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.LivingEntity;

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
