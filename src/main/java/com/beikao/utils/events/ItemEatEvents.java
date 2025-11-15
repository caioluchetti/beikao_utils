package com.beikao.utils.events;

import java.util.Map;
import java.util.HashMap;
import java.util.UUID;

import net.minecraft.world.entity.player.Player;

import com.beikao.utils.BeikaoUtils;
import com.beikao.utils.items.FoodItems;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;

@EventBusSubscriber(modid = BeikaoUtils.MODID)
public class ItemEatEvents {

    private static final Map<UUID, Long> lastEatTime = new HashMap<>();

    @SubscribeEvent
    public static void onItemEaten(LivingEntityUseItemEvent.Finish event) {
        LivingEntity entity = event.getEntity();
        ItemStack eaten = event.getItem();

        // Only players
        if (!(entity instanceof Player player)) return;

        if (eaten.getItem() == BeikaoUtils.BEIKAO.get()) {

            long now = System.currentTimeMillis();
            UUID id = player.getUUID();

            long last = lastEatTime.getOrDefault(id, 0L);
            if (now - last < 300) return;

            lastEatTime.put(id, now);

            WebhookSender.sendWebhook(
                    "🍔 **" + player.getName().getString() + "** acabou de comer um **Beikão**!"
            );
        }
    }
}
