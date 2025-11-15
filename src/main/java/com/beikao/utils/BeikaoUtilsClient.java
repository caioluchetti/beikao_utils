package com.beikao.utils;

import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

public class BeikaoUtilsClient {

    public static void initClient(ModContainer container) {
        // Register config GUI
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }

    public static void onClientSetup(final FMLClientSetupEvent event) {
        BeikaoUtils.LOGGER.info("HELLO FROM CLIENT SETUP");
        BeikaoUtils.LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
    }
}
