package com.tallonkh.enchanting_colors;

import net.minecraft.world.item.enchantment.Enchantment;

import java.util.HashMap;
import java.util.Map;

public record EnchantInfo (String enchantId, int color, Enchantment enchant){
    public static final Map<String, EnchantInfo> ENCHANT_INFOS = new HashMap<>();
}
