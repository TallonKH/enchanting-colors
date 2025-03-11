package com.tallonkh.enchanting_colors;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

import static com.tallonkh.enchanting_colors.EnchantInfo.ENCHANT_INFOS;
import static com.tallonkh.enchanting_colors.MathUtil.parseHexCode;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Forge's config APIs
@Mod.EventBusSubscriber(modid = EnchantingColors.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientConfig
{
    public static enum ColorMode {
        cover,
        ribbon,
    }
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec.ConfigValue<Boolean> ENCHANTED_BOOK_GLINT = BUILDER
            .comment("Display the vanilla glint effect on enchanted books. If false, there will be no glint.")
            .define("enchantedBookGlint", false);
    public static final ForgeConfigSpec.ConfigValue<Boolean> USE_CUSTOM_SEAL = BUILDER
            .comment("When true, books with all enchants at max-level will have a different color seal.")
            .define("maxedBookSeal", true);
    public static final ForgeConfigSpec.ConfigValue<ColorMode> COLOR_MODE = BUILDER
            .comment("Which part of the book should be colored by enchantment (cover|ribbon).")
            .define("colorMode", ColorMode.cover);
    public static final ForgeConfigSpec.ConfigValue<Boolean> USE_LARGE_BOOK = BUILDER
            .comment("When true, books with multiple enchants will appear larger.")
            .define("multiBookLarge", true);
    public static final ForgeConfigSpec.ConfigValue<List<String>> ENCHANT_COLORS = BUILDER
            .comment("List of colors in format 'mod:enchant_id=0xAARRGGBB'- color should be in (A)RGB hexcode format.")
            .define("enchantColors", accumulateColorStrings(Defaults.ENCHANT_COLORS));

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event)
    {
        List<String> colorConfigStrings = event.getConfig().getConfigData().get("enchantColors");

        for(String configString : colorConfigStrings){
            int split = configString.indexOf('=');
            if(split == -1){
                continue;
            }

            String enchantId = configString.substring(0, split);
            MathUtil.RgbColor color = MathUtil.RgbColor.fromArgbHex(parseHexCode(configString.substring(split+1)));

            Enchantment enchant = ForgeRegistries.ENCHANTMENTS.getValue(ResourceLocation.tryParse(enchantId));

            ENCHANT_INFOS.put(enchantId, new EnchantInfo(enchantId, color, enchant));
        }
    }

    public static final ForgeConfigSpec SPEC = BUILDER.build();

    private static List<String> accumulateColorStrings(List<Defaults.EnchantColor> pairs){
        List<String> strings = new ArrayList<>(pairs.size());
        for(Defaults.EnchantColor pair : pairs){
            strings.add(pair.toConfigString());
        }
        return strings;
    }
}
