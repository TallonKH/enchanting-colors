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

    public static final int BASE_COVER_COLOR_HEX = 0xFF654b17;
    public static final RgbColor BASE_COVER_COLOR = RgbColor.fromArgbHex(BASE_COVER_COLOR_HEX);
    public static final int BASE_RIBBON_COLOR_HEX = 0xFFc51339;
    public static final RgbColor BASE_RIBBON_COLOR = RgbColor.fromArgbHex(BASE_RIBBON_COLOR_HEX);

    public static final int SEAL_COLOR_EMPTY_HEX = 0xffc6cbcc;
    public static final int SEAL_COLOR_NORMAL_HEX = 0xffe8b73a;
    public static final int SEAL_COLOR_MAXED_HEX = 0xff7ae0ff;

    public static final ItemColor computeEnchantmentItemColor = (stack, layerIndex) -> {
        ListTag enchants = getEnchantsTag(stack, false);
        ClientConfig.ColorMode colorMode = ClientConfig.COLOR_MODE.get();
        return switch (layerIndex) {
            case 1 -> colorMode.equals(ClientConfig.ColorMode.cover) ? computeEnchantsColor(enchants, BASE_COVER_COLOR) : BASE_COVER_COLOR_HEX;
            case 2 -> computeSealColor(enchants);
            case 3 -> colorMode.equals(ClientConfig.ColorMode.ribbon) ? computeEnchantsColor(enchants, BASE_RIBBON_COLOR) : BASE_RIBBON_COLOR_HEX;
            default -> -1;
        };
    };

    public static int computeEnchantsColor(ListTag enchants, RgbColor baseColor){
        if (enchants == null) {
            // Debug color.
            return RgbColor.fromArgbHex(BASE_COVER_COLOR_HEX).toHexArgb();
        }

        float rSum = 0f;
        float gSum = 0f;
        float bSum = 0f;

        float lvlSum = 0f;
        float maxLvlSum = 0f;

        for (Tag tag : enchants) {
            CompoundTag enchant = (CompoundTag) tag;
            String enchantId = enchant.getString("id");
            EnchantInfo enchantInfo = ENCHANT_INFOS.get(enchantId);
            if (enchantInfo != null) {
                int lvl = enchant.getInt("lvl");
                RgbColor color = enchantInfo.color();

                int maxLvl = enchantInfo.enchant().getMaxLevel();
                maxLvlSum += maxLvl;
                lvlSum += lvl;


                rSum += color.r() * lvl;
                gSum += color.g() * lvl;
                bSum += color.b() * lvl;
            }
        }

        if (lvlSum < 0.1) {
            return BASE_COVER_COLOR_HEX;
        }

        RgbColor target = new RgbColor(rSum / lvlSum, gSum / lvlSum, bSum / lvlSum);
        float strength = lerpf(0.2f,1.0f, Math.min(1.0F, lvlSum / maxLvlSum));
        return RgbColor.lerp(baseColor, target, strength).toHexArgb();
    }

    public static int computeSealColor(ListTag enchants){
        if(!ClientConfig.USE_CUSTOM_SEAL.get()){
            return SEAL_COLOR_NORMAL_HEX;
        }

        if (enchants == null) {
            return SEAL_COLOR_EMPTY_HEX;
        }

        int lvlSum = 0;

        for (Tag tag : enchants) {
            CompoundTag enchant = (CompoundTag) tag;
            String enchantId = enchant.getString("id");
            EnchantInfo enchantInfo = ENCHANT_INFOS.get(enchantId);
            if (enchantInfo != null) {
                int lvl = enchant.getInt("lvl");
                int maxLvl = enchantInfo.enchant().getMaxLevel();
                if(lvl < maxLvl){
                    return SEAL_COLOR_NORMAL_HEX;
                }

                lvlSum += lvl;
            }
        }

        if(lvlSum == 0){
            return SEAL_COLOR_EMPTY_HEX;
        }

        return SEAL_COLOR_MAXED_HEX;
    }
}
