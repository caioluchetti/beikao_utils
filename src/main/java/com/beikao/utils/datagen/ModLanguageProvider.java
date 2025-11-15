package com.beikao.utils.datagen;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;
import com.beikao.utils.BeikaoUtils;

public class ModLanguageProvider extends LanguageProvider {

    public ModLanguageProvider(PackOutput output) {
        super(output, BeikaoUtils.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {
        add("item.beikaoutils.beikao", "Beikao");
        add("item.beikaoutils.serrao","Flavio Serrao");
    }
}
