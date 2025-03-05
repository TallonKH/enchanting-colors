package com.tallonkh.enchanting_colors;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Arrays;
import java.util.List;

import static com.tallonkh.enchanting_colors.EnchantInfo.ENCHANT_INFOS;
import static com.tallonkh.enchanting_colors.MathUtil.colorFromHexCode;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Forge's config APIs
@Mod.EventBusSubscriber(modid = EnchantingColors.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientConfig
{
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec.ConfigValue<Boolean> ENCHANTED_BOOK_GLINT = BUILDER
            .comment("Display the vanilla glint effect on enchanted books. If false, there will be no glint.")
            .define("enchantedBookGlint", false);
    // TODO put the default colors in an array.
    public static final ForgeConfigSpec.ConfigValue<List<String>> ENCHANT_COLORS = BUILDER
            .comment("List of colors in format 'mod:enchant_id=0xhexcode'.")
            .define("enchantColors", Arrays.asList(
                    "hammer_enchant:wide_shape=0x446688",
                    "hammer_enchant:deep_shape=0x448866",

                    "minecraft:vanishing_curse=0x000000",
                    "minecraft:binding_curse=0x151111",
                    "minecraft:mending=0xFFFF00",
                    "minecraft:unbreaking=0x333333",
                    "minecraft:fortune=0x55FF88",
                    "minecraft:silk_touch=0xf5d7a6",
                    "minecraft:infinity=0x740070",
                    "minecraft:feather_falling=0xEEEEEE",
                    "minecraft:sweeping=0x550000",
                    "minecraft:efficiency=0xFFEE99",
                    "minecraft:looting=0xCCEE00",
                    "minecraft:fire_aspect=0xFFAA00",
                    "minecraft:sharpness=0xFF4544",
                    "minecraft:knockback=0xFF77BB",
                    "minecraft:smite=0x330000",
                    "minecraft:bane_of_arthropods=0x330044",
                    "minecraft:flame=0xff0000",
                    "minecraft:power=0xFF3377",
                    "minecraft:punch=0xFF7733",
                    "minecraft:swift_sneak=0x002555",
                    "minecraft:soul_speed=0x33281c",
                    "minecraft:aqua_affinity=0x70DDFF",
                    "minecraft:depth_strider=0x0030D0",
                    "minecraft:respiration=0x70AAFF",
                    "minecraft:protection=0x4040AA",
                    "minecraft:thorns=0x300070",
                    "minecraft:frost_walker=0xA0EAFF",
                    "minecraft:fire_protection=0xFFBB44",
                    "minecraft:projectile_protection=0xD0F0AA",
                    "minecraft:blast_protection=0xAA4010",
                    "minecraft:channeling=0xFFFFBB",
                    "minecraft:riptide=0x000080",
                    "minecraft:loyalty=0xF660FF",
                    "minecraft:impaling=0x003280",
                    "minecraft:quick_charge=0x7732BB",
                    "minecraft:piercing=0xA53040",
                    "minecraft:multishot=0x44BB22",
                    "minecraft:lure=0xf6c5fa",
                    "minecraft:luck_of_the_sea=0xb196ff"
            ));

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
            int color = colorFromHexCode(configString.substring(split+1));

            Enchantment enchant = ForgeRegistries.ENCHANTMENTS.getValue(ResourceLocation.tryParse(enchantId));

            ENCHANT_INFOS.put(enchantId, new EnchantInfo(enchantId, color, enchant));
        }
    }

    public static final ForgeConfigSpec SPEC = BUILDER.build();
}
