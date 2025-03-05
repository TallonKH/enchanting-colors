package com.tallonkh.enchanting_colors;

import net.minecraft.client.color.item.ItemColor;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.tallonkh.enchanting_colors.EnchantInfo.ENCHANT_INFOS;
import static com.tallonkh.enchanting_colors.EnchantUtil.getEnchantsTag;
import static com.tallonkh.enchanting_colors.EnchantingColors.MODID;
import static com.tallonkh.enchanting_colors.MathUtil.*;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ItemOverlayColor {
    @SubscribeEvent
    public static void registerItemColors(RegisterColorHandlersEvent.Item event) {
        event.register(computeEnchantmentItemColor, Items.ENCHANTED_BOOK);
    }

    public static final int BASE_COLOR = 0xFF654b17;
    public static final int[] BASE_COLOR_PARTS = breakColor(BASE_COLOR);

    public static final ItemColor computeEnchantmentItemColor = (stack, layerIndex) -> {
        if (layerIndex == 0) {
            return -1;
        }
        ListTag enchants = getEnchantsTag(stack, false);
        if (enchants == null) {
            // Debug color.
            return BASE_COLOR;
        } else {
            float r = 0F;
            float g = 0F;
            float b = 0F;
            float totalLevels = 0F; // Used to average colors.
            int totalMaxLevels = 0; // Used to weigh against the base unenchanted color.

            for (Tag tag : enchants) {
                CompoundTag enchant = (CompoundTag) tag;
                String enchantId = enchant.getString("id");
                EnchantInfo enchantInfo = ENCHANT_INFOS.get(enchantId);
                if (enchantInfo != null) {
                    int lvl = enchant.getInt("lvl");
                    int[] parts = breakColor(enchantInfo.color());

                    int maxLvl = enchantInfo.enchant().getMaxLevel();
                    totalMaxLevels += maxLvl;
                    totalLevels += lvl;

                    r += parts[1] * lvl;
                    g += parts[2] * lvl;
                    b += parts[3] * lvl;
                }
            }

            if (totalLevels == 0) {
                return BASE_COLOR;
            }

            float enchantedness = lerpf(0.2f, 1.0f, Math.min(1.0F, totalLevels / totalMaxLevels));

            return makeColor(
                    0xFF,
                    (int) Math.floor(lerpf(BASE_COLOR_PARTS[1], r / totalLevels, enchantedness)),
                    (int) Math.floor(lerpf(BASE_COLOR_PARTS[2], g / totalLevels, enchantedness)),
                    (int) Math.floor(lerpf(BASE_COLOR_PARTS[3], b / totalLevels, enchantedness))
            );
        }
    };
}
