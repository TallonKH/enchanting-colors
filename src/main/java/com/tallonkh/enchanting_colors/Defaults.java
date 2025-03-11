package com.tallonkh.enchanting_colors;

import java.util.Arrays;
import java.util.List;

public class Defaults {
    public record EnchantColor(String id, int hexColor){
        public String toConfigString(){
            return id + "=0x" + Integer.toHexString(hexColor);
        }
    }

    public static List<EnchantColor> ENCHANT_COLORS = Arrays.asList(
            new EnchantColor("hammer_enchant:wide_shape", 0xff446688),
            new EnchantColor("hammer_enchant:deep_shape", 0xff448866),
            new EnchantColor("minecraft:vanishing_curse", 0xff000000),
            new EnchantColor("minecraft:binding_curse", 0xff151111),
            new EnchantColor("minecraft:mending", 0xffFFFF00),
            new EnchantColor("minecraft:unbreaking", 0xff333333),
            new EnchantColor("minecraft:fortune", 0xff55FF88),
            new EnchantColor("minecraft:silk_touch", 0xfff5d7a6),
            new EnchantColor("minecraft:infinity", 0xff740070),
            new EnchantColor("minecraft:feather_falling", 0xffEEEEEE),
            new EnchantColor("minecraft:sweeping", 0xff550000),
            new EnchantColor("minecraft:efficiency", 0xffFFEE99),
            new EnchantColor("minecraft:looting", 0xffCCEE00),
            new EnchantColor("minecraft:fire_aspect", 0xffFFAA00),
            new EnchantColor("minecraft:sharpness", 0xffFF4544),
            new EnchantColor("minecraft:knockback", 0xffFF77BB),
            new EnchantColor("minecraft:smite", 0xff330000),
            new EnchantColor("minecraft:bane_of_arthropods", 0xff330044),
            new EnchantColor("minecraft:flame", 0xffff0000),
            new EnchantColor("minecraft:power", 0xffFF3377),
            new EnchantColor("minecraft:punch", 0xffFF7733),
            new EnchantColor("minecraft:swift_sneak", 0xff002555),
            new EnchantColor("minecraft:soul_speed", 0xff33281c),
            new EnchantColor("minecraft:aqua_affinity", 0xff70DDFF),
            new EnchantColor("minecraft:depth_strider", 0xff0030D0),
            new EnchantColor("minecraft:respiration", 0xff70AAFF),
            new EnchantColor("minecraft:protection", 0xff4040AA),
            new EnchantColor("minecraft:thorns", 0xff300070),
            new EnchantColor("minecraft:frost_walker", 0xffA0EAFF),
            new EnchantColor("minecraft:fire_protection", 0xffFFBB44),
            new EnchantColor("minecraft:projectile_protection", 0xffD0F0AA),
            new EnchantColor("minecraft:blast_protection", 0xffAA4010),
            new EnchantColor("minecraft:channeling", 0xffFFFFBB),
            new EnchantColor("minecraft:riptide", 0xff000080),
            new EnchantColor("minecraft:loyalty", 0xffF660FF),
            new EnchantColor("minecraft:impaling", 0xff003280),
            new EnchantColor("minecraft:quick_charge", 0xff7732BB),
            new EnchantColor("minecraft:piercing", 0xffA53040),
            new EnchantColor("minecraft:multishot", 0xff44BB22),
            new EnchantColor("minecraft:lure", 0xfff6c5fa),
            new EnchantColor("minecraft:luck_of_the_sea", 0xffb196f)
    );
}
