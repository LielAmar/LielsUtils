package com.lielamar.lielsutils.bukkit.items;

import com.lielamar.lielsutils.groups.Pair;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Map;

public class ItemUtils {

    /**
     * Creates an ItemStack
     *
     * @param material       Material of the ItemStack
     * @param amount         Amount of the ItemStack
     * @param name           Name of the ItemStack
     * @param lore           Lore of the ItemStack
     * @param enchantments   Enchants of the ItemStack
     * @return               ItemStack created through properties
     */
    public static ItemStack getItem(@NotNull Material material, int amount, @Nullable String name, @Nullable String[] lore, boolean safeEnchantments, @Nullable Map<Enchantment, Integer> enchantments) {
        ItemStack item = new ItemStack(material, amount);

        ItemMeta meta = item.getItemMeta();
        if(name != null && meta != null) meta.setDisplayName(name);
        if(lore != null && meta != null) meta.setLore(Arrays.asList(lore));
        item.setItemMeta(meta);

        if(enchantments != null && enchantments.size() > 0) {
            if(safeEnchantments)
                item.addEnchantments(enchantments);
            else
                item.addUnsafeEnchantments(enchantments);
        }

        return item;
    }

    public static ItemStack getItem(@NotNull Material material, int amount, @Nullable String name, @Nullable String[] lore, boolean safeEnchantments, @Nullable Pair<Enchantment, Integer>... enchantments) {
        ItemStack item = getItem(material, amount, name, lore, safeEnchantments, (Map<Enchantment, Integer>) null);

        if(enchantments == null || enchantments.length == 0) return item;

        for(Pair<Enchantment, Integer> enchantment : enchantments) {
            if(enchantment != null && enchantment.getA() != null && enchantment.getB() != null) {
                if(safeEnchantments)
                    item.addEnchantment(enchantment.getA(), enchantment.getB());
                else
                    item.addUnsafeEnchantment(enchantment.getA(), enchantment.getB());
            }
        }

        return item;
    }

    public static ItemStack getItem(@NotNull Material material, int amount, @Nullable String name, @Nullable String[] lore, boolean safeEnchantments, @Nullable Enchantment... enchantments) {
        ItemStack item = getItem(material, amount, name, lore, safeEnchantments, (Map<Enchantment, Integer>) null);

        if(enchantments == null || enchantments.length == 0) return item;

        for(Enchantment enchantment : enchantments) {
            if(safeEnchantments)
                item.addEnchantment(enchantment, 1);
            else
                item.addUnsafeEnchantment(enchantment, 1);
        }

        return item;
    }


    public static ItemStack getItem(@NotNull Material material, int amount, @Nullable String name, @Nullable String[] lore, @Nullable Pair<Enchantment, Integer>... enchantments) {
        return getItem(material, amount, name, lore, true, enchantments);
    }

    public static ItemStack getItem(@NotNull Material material, int amount, @Nullable String name, @Nullable String[] lore, @Nullable Enchantment... enchantments) {
        return getItem(material, amount, name, lore, true, enchantments);
    }



    public static ItemStack getItem(@NotNull Material material, int amount, @Nullable String name, @Nullable String[] lore) {
        return getItem(material, amount, name, lore, (Pair<Enchantment, Integer>[]) null);
    }

    public static ItemStack getItem(@NotNull Material material, int amount, @Nullable String name) {
        return getItem(material, amount, name, null, (Pair<Enchantment, Integer>[]) null);
    }

    public static ItemStack getItem(@NotNull Material material, int amount) {
        return getItem(material, amount, null, null, (Pair<Enchantment, Integer>[]) null);
    }

    public static ItemStack getItem(@NotNull Material material) {
        return getItem(material, 1, null, null, (Pair<Enchantment, Integer>[]) null);
    }
}
