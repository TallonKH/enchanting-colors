package com.tallonkh.enchanting_colors;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.IItemDecorator;
import net.minecraftforge.client.event.RegisterItemDecorationsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.tallonkh.enchanting_colors.EnchantingColors.MODID;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ItemOverlay implements IItemDecorator {
    public static ItemOverlay INSTANCE = new ItemOverlay();
    @SubscribeEvent
    public static void registerItemOverlay(RegisterItemDecorationsEvent event) {
        event.register(Items.ENCHANTED_BOOK, INSTANCE);
    }

    @Override
    public boolean render(GuiGraphics guiGraphics, Font font, ItemStack itemStack, int x, int y) {
//        guiGraphics.fill(x,y,x+8,y+8, 0xFFFF0000);
//        return true;
        return false;
    }
}
