package com.tallonkh.enchanting_colors;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import static com.tallonkh.enchanting_colors.EnchantInfo.ENCHANT_INFOS;
import static com.tallonkh.enchanting_colors.EnchantingColors.MODID;

public class EnchantUtil {
    public static final TagKey<Item> ENCHANT_STORAGE_TARGETS = ItemTags.create(new ResourceLocation(MODID, "enchant_storage_targets"));;

    public static ListTag getEnchantsTag(ItemStack stack, boolean createIfNone) {
        if (!stack.hasTag()) {
            if (createIfNone) {
                stack.getOrCreateTag();
            } else {
                return null;
            }
        }

        CompoundTag tags = stack.getTag();
        assert (tags != null);

        boolean storeEnchant = stack.is(ENCHANT_STORAGE_TARGETS) || tags.contains("StoredEnchantments");
        String listName = storeEnchant ? "StoredEnchantments" : "Enchantments";
        if (tags.contains(listName)) {
            return tags.getList(listName, 10);
        }

        if (createIfNone) {
            ListTag list = new ListTag();
            tags.put(listName, list);
            return list;
        } else {
            return null;
        }
    }

    public static boolean hasMultipleEnchants(ListTag enchants){
        if(enchants == null){
            return false;
        }

        int found = 0;
        for (Tag tag : enchants) {
            CompoundTag enchant = (CompoundTag) tag;
            if(enchant.getInt("lvl") > 0){
                found++;
                if(found >= 2){
                    return true;
                }
            }
        }
        return false;
    }
}
