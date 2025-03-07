package com.tallonkh.enchanting_colors;

import com.mojang.logging.LogUtils;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

import static com.tallonkh.enchanting_colors.EnchantUtil.getEnchantsTag;
import static com.tallonkh.enchanting_colors.EnchantUtil.hasMultipleEnchants;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(EnchantingColors.MODID)
public class EnchantingColors {
    public static final String MODID = "enchanting_colors";
    private static final Logger LOGGER = LogUtils.getLogger();

    public EnchantingColors() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register the commonSetup method for mod loading.
        modEventBus.addListener(this::commonSetup);

        // Register for server and game events.
        MinecraftForge.EVENT_BUS.register(this);

        // Tell forge to create & load configs.
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ClientConfig.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            event.enqueueWork(() ->
            {
                // Book texture variations.
                ItemProperties.register(
                    Items.ENCHANTED_BOOK,
                    new ResourceLocation(MODID, "big"),
                    (stack, level, entity, id) -> ClientConfig.USE_LARGE_BOOK.get() && hasMultipleEnchants(getEnchantsTag(stack, false)) ? 1.0f : 0.0f
                );
            });
        }
    }
}
