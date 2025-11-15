package com.beikao.utils.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import java.util.concurrent.CompletableFuture;

import com.beikao.utils.BeikaoUtils;

@EventBusSubscriber(
        modid = BeikaoUtils.MODID,
        bus = EventBusSubscriber.Bus.MOD // ← REQUIRED for datagen
)
public class DataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {

        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper fileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookup = event.getLookupProvider();

        // --- Client data (models, lang files) ---
        if (event.includeClient()) {
            generator.addProvider(
                    true,
                    new ModItemModelProvider(packOutput, fileHelper)
            );

            generator.addProvider(
                    true,
                    new ModLanguageProvider(packOutput)
            );
        }

        // --- Server data (recipes, loot tables, tags...) ---
        if (event.includeServer()) {
            generator.addProvider(
                    true,
                    new ModRecipeProvider(packOutput, lookup)
            );
        }
    }
}
