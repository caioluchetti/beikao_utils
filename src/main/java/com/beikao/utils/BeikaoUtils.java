package com.beikao.utils;

import com.beikao.utils.items.FoodItems;
import com.beikao.utils.items.ModToolTiers;
import com.beikao.utils.items.FoodItems;
import com.mojang.logging.LogUtils;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.AxeItem;
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
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.UseAnim; // <-- This is for the animation
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.tags.BlockTags; // <-- This is to check for logs
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3; // <-- ADD THIS
import net.minecraft.world.entity.EquipmentSlot; // <-- ADD THIS
import com.beikao.utils.events.KillEventsSerrao;


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

    public static class SerraoItem extends AxeItem {
        public SerraoItem(Tier tier, Item.Properties props, float attackDamage, float attackSpeed) {
            super(tier, props.attributes(AxeItem.createAttributes(tier, attackDamage, attackSpeed)));
        }

        @Override
        public UseAnim getUseAnimation(ItemStack pStack) {
            return UseAnim.BRUSH;
        }


        @Override
        public int getUseDuration(ItemStack pStack, LivingEntity pUser) {
            return 72000;
        }


        @Override
        public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
            ItemStack itemstack = pPlayer.getItemInHand(pHand);
            pPlayer.startUsingItem(pHand); // Starts the animation
            return InteractionResultHolder.consume(itemstack);
        }

        /**
         * 4. DOES THE BREAKING (THE "CHAINSAW" LOGIC)
         * This is called every tick you are holding down the right-click button.
         */
        @Override
        public void onUseTick(Level pLevel, LivingEntity pLivingEntity, ItemStack pStack, int pRemainingUseDuration) {
            // Only run this on the server and if the entity is a player
            if (pLevel.isClientSide || !(pLivingEntity instanceof Player player)) {
                return;
            }
            
            // --- This part breaks a block every 4 ticks (5 times a second) ---
                int ticksUsed = this.getUseDuration(pStack, pLivingEntity) - pRemainingUseDuration;           
                 if (ticksUsed > 0 && ticksUsed % 4 == 0) {
                
                // --- THIS IS THE FIX ---
                // Find what block the player is looking at (up to 5 blocks away)
                Vec3 eyePos = player.getEyePosition();
                Vec3 lookVec = player.getLookAngle();
                Vec3 endPos = eyePos.add(lookVec.scale(5.0D)); // 5.0D is the reach
                
                ClipContext context = new ClipContext(
                    eyePos, 
                    endPos, 
                    ClipContext.Block.OUTLINE, // How to handle blocks
                    ClipContext.Fluid.NONE,    // Don't hit fluids
                    player
                );
                BlockHitResult hitResult = pLevel.clip(context);
                // --- END OF FIX ---

                if (hitResult.getType() == HitResult.Type.BLOCK) {
                    BlockPos pos = hitResult.getBlockPos();
                    BlockState state = pLevel.getBlockState(pos);

                    // --- CHECK IF THE BLOCK IS A LOG ---
                    if (state.is(BlockTags.LOGS)) {
                        // Break the block (and drop items)
                        pLevel.destroyBlock(pos, true, player);
                        // Damage the saw
                        // --- THIS IS THE FIXED CODE ---
                        // Get the correct EquipmentSlot from the InteractionHand
                        EquipmentSlot slot = player.getUsedItemHand() == InteractionHand.MAIN_HAND 
                            ? EquipmentSlot.MAINHAND 
                            : EquipmentSlot.OFFHAND;

                        // Call the correct hurtAndBreak method
                        pStack.hurtAndBreak(1, player, slot);                
                    }}}}   
        }

    public static final DeferredHolder<Item, Item> SERRAO =
    ITEMS.register("serrao",
        () -> new SerraoItem(
                ModToolTiers.SAW,
                new Item.Properties(),
                15.0f,   // attack damage
                -2.8f    // attack speed
        ));



    // Creative tab
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> BEIKAO_TAB =
        TABS.register("beikao_tab", () ->
            CreativeModeTab.builder()
                .title(Component.literal("Beikao"))
                .icon(() -> new ItemStack(BEIKAO.get()))
                .displayItems((params, output) -> {
                    output.accept(BEIKAO.get());
                    output.accept(SERRAO.get());
                })
                .build()
        );

    public BeikaoUtils(IEventBus bus, ModContainer container) {
        ITEMS.register(bus);
        TABS.register(bus);

        bus.addListener(this::commonSetup);
        NeoForge.EVENT_BUS.register(this);


        NeoForge.EVENT_BUS.register(new KillEventsSerrao());
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        LOGGER.info("HELLO FROM COMMON SETUP");
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.info("HELLO from server starting");
    }
}
