package com.beikao.utils.datagen;

import com.beikao.utils.BeikaoUtils;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {

    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, BeikaoUtils.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        // Gera o modelo básico simples
        basicItem(BeikaoUtils.BEIKAO.get());
    }

    /**
     * Caso você queira usar manualmente um item customizado via helper
     */
    protected void simpleHandheldItem(String name) {
        withExistingParent(
                name,
                ResourceLocation.fromNamespaceAndPath("item", "handheld")
        ).texture(
                "layer0",
                ResourceLocation.fromNamespaceAndPath(BeikaoUtils.MODID, "item/" + name)
        );
    }
}
