package com.tallonkh.enchanting_colors.mixins;

import com.tallonkh.enchanting_colors.ClientConfig;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(value = EnchantedBookItem.class)
public abstract class EnchantedBookMixin {
    /**
     * @author TallonKH
     * @reason disable enchantment glint to make custom colors more visible.
     */
    @Overwrite
    public boolean isFoil(ItemStack p_41166_) {
        return ClientConfig.ENCHANTED_BOOK_GLINT.get();
    }
}