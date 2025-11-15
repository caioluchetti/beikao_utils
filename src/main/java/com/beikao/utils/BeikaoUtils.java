package com.beikao.utils;

import com.beikao.utils.items.FoodItems;
import com.mojang.logging.LogUtils;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.network.chat.Component;
import net.minecraft.core.registries.Registries;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLDedicatedServerSetupEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.common.NeoForge;


import org.slf4j.Logger;

@Mod(BeikaoUtils.MODID)
public class BeikaoUtils {

    public static final String MODID = "beikaoutils";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(Registries.ITEM, MODID);

    public static final DeferredRegister<CreativeModeTab> TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    // Items aqui!
    public static final DeferredHolder<Item, Item> BEIKAO =
            ITEMS.register("beikao",
                    () -> new Item(new Item.Properties().food(FoodItems.BEIKAO_FOOD_PROPERTIES)));

    // Creative tab
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> BEIKAO_TAB =
        TABS.register("beikao_tab", () ->
            CreativeModeTab.builder()
                .title(Component.literal("Beikao"))
                .icon(() -> new ItemStack(BEIKAO.get()))
                .displayItems((params, output) -> {
                    output.accept(BEIKAO.get());
                })
                .build()
        );

    public BeikaoUtils(IEventBus bus, ModContainer container) {
        ITEMS.register(bus);
        TABS.register(bus);

        bus.addListener(this::commonSetup);
        NeoForge.EVENT_BUS.register(this);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        LOGGER.info("HELLO FROM COMMON SETUP");
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.info("HELLO from server starting");
    }
}
