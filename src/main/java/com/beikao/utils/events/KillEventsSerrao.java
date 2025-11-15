package com.beikao.utils.events;

import com.beikao.utils.BeikaoUtils; // Import your main class
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

// We DON'T need an @Mod.EventBusSubscriber annotation
// because we will register it manually in the main class.
public class KillEventsSerrao {

    // This method does NOT need to be static
    @SubscribeEvent
    public void onPlayerKill(LivingDeathEvent event) {
        // 1. Check if the killer is a Player
        if (event.getSource().getEntity() instanceof Player player) {
            
            // 2. Only run this on the server
            if (!player.level().isClientSide) {
                
                // 3. Get the item in the player's main hand
                ItemStack heldItem = player.getMainHandItem();
                
                // 4. Check if the item is your custom saw
                // We access SERRAO from the main class
                if (heldItem.getItem() == BeikaoUtils.SERRAO.get()) {
                    
                    // 5. Get the entity that was killed
                    LivingEntity victim = event.getEntity();
                        
                    if (victim instanceof Player){
                        // --- 6. Call your webhook sender ---
                        try {
                            // Make sure your WebhookSender class and method are correct
                            WebhookSender.sendWebhook(
                            "🪚☠️ **" + victim.getName().getString() + "** morreu para o **Flávio Serrão**!"
                          );
                        } catch (Exception e) {
                            // Use the logger from the main class
                            BeikaoUtils.LOGGER.error("Failed to send kill webhook: " + e.getMessage());
                        }
                    }
                       
                }
            }
        }
    }
}