package com.lielamar.lielsutils.bukkit.items;

import com.lielamar.lielsutils.bukkit.color.ColorUtils;
import com.lielamar.lielsutils.bukkit.nbt.ItemTag;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ItemUtils {

    public static class Builder {

        @Nullable private final Plugin plugin;
        private final ItemStack itemStack;

        public Builder(@NotNull Material material) {
            this(material, 1);
        }

        public Builder(@NotNull ItemStack itemStack) {
            this(null, itemStack);
        }

        public Builder(@NotNull Material material, int amount) {
            this(null, material, amount);
        }

        public Builder(@Nullable Plugin plugin, @NotNull Material material) {
            this(plugin, material, 1);
        }

        public Builder(@Nullable Plugin plugin, @NotNull ItemStack itemStack) {
            this.plugin = plugin;
            this.itemStack = itemStack;
        }

        public Builder(@Nullable Plugin plugin, @NotNull Material material, int amount) {
            this.plugin = plugin;
            this.itemStack = new ItemStack(material, amount);
        }


        public @NotNull Builder clone() { return new Builder(this.itemStack); }

        public @NotNull Builder setName(@NotNull String name) {
            name = ColorUtils.translateAlternateColorCodes('&', name);

            ItemMeta meta = this.itemStack.getItemMeta();

            if(meta != null) {
                meta.setDisplayName(name);
                this.itemStack.setItemMeta(meta);
            }
            return this;
        }

        public @NotNull Builder addUnsafeEnchantment(@NotNull Enchantment enchantment, int level) {
            this.itemStack.addUnsafeEnchantment(enchantment, level);
            return this;
        }

        public @NotNull Builder removeEnchantment(@NotNull Enchantment enchantment) {
            this.itemStack.removeEnchantment(enchantment);
            return this;
        }

        public @NotNull Builder addEnchant(@NotNull Enchantment enchantment, int level) {
            ItemMeta meta = this.itemStack.getItemMeta();
            if(meta != null) {
                meta.addEnchant(enchantment, level, true);
                this.itemStack.setItemMeta(meta);
            }
            return this;
        }

        public @NotNull Builder addEnchantments(@NotNull Map<Enchantment, Integer> enchantments) {
            this.itemStack.addEnchantments(enchantments);
            return this;
        }

        public @NotNull Builder setEnchanted(boolean value) {
            return setItemTag(new ItemTag("ench", "", "STRING"));
        }

        public @NotNull Builder setLore(@NotNull String... lore) {
            for(int i = 0; i < lore.length; i++)
                lore[i] = ColorUtils.translateAlternateColorCodes('&', lore[i]);

            ItemMeta meta = this.itemStack.getItemMeta();

            if(meta != null) {
                meta.setLore(Arrays.asList(lore));
                this.itemStack.setItemMeta(meta);
            }
            return this;
        }

        public @NotNull Builder setLore(@NotNull List<String> lore) {
            for(int i = 0; i < lore.size(); i++)
                lore.set(i, ColorUtils.translateAlternateColorCodes('&', lore.get(i)));

            ItemMeta meta = this.itemStack.getItemMeta();

            if(meta != null) {
                meta.setLore(lore);
                this.itemStack.setItemMeta(meta);
            }
            return this;
        }

        public @NotNull Builder removeLoreLine(@NotNull String line) {
            ItemMeta meta = this.itemStack.getItemMeta();

            if(meta != null && meta.getLore() != null) {
                List<String> lore = new ArrayList<>(meta.getLore());
                if(!lore.contains(line)) return this;

                lore.remove(line);
                meta.setLore(lore);
                this.itemStack.setItemMeta(meta);
            }
            return this;
        }

        public @NotNull Builder removeLoreLine(int index) {
            ItemMeta meta = this.itemStack.getItemMeta();

            if(meta != null && meta.getLore() != null) {
                List<String> lore = new ArrayList<>(meta.getLore());
                if(index < 0 || index > lore.size()) return this;

                lore.remove(index);
                meta.setLore(lore);
                this.itemStack.setItemMeta(meta);
            }
            return this;
        }

        public @NotNull Builder addLoreLine(@NotNull String line) {
            ItemMeta meta = this.itemStack.getItemMeta();

            if(meta != null && meta.hasLore() && meta.getLore() != null) {
                List<String> lore = new ArrayList<>();
                if(meta.hasLore())
                    lore = new ArrayList<>(meta.getLore());

                lore.add(line);
                meta.setLore(lore);
                this.itemStack.setItemMeta(meta);
            }
            return this;
        }

        public @NotNull Builder addLoreLine(@NotNull String line, int pos) {
            ItemMeta meta = this.itemStack.getItemMeta();

            if(meta != null && meta.getLore() != null) {
                List<String> lore = new ArrayList<>(meta.getLore());
                lore.set(pos, line);
                meta.setLore(lore);
                this.itemStack.setItemMeta(meta);
            }
            return this;
        }

        public @NotNull Builder setItemTag(@NotNull ItemTag itemTag) {
            if(plugin == null)
                return this;

            ItemMeta meta = this.itemStack.getItemMeta();

            if(meta != null) {
                NamespacedKey namespacedKey = new NamespacedKey(plugin, itemTag.tagKey);
                meta.getPersistentDataContainer().set(namespacedKey, itemTag.tagType, itemTag.tagValue);

                this.itemStack.setItemMeta(meta);
            }

            return this;
        }


        public @NotNull ItemStack toItemStack(){
            return this.itemStack;
        }
    }


    /**
     * Returns the tag of an Item
     *
     * @param plugin         Plugin instance
     * @param itemStack      ItemStack to get the tag of
     * @param key            Key of the tag
     * @param defaultValue   Default value of the tag
     * @param <T>            Type of the value
     * @return               Tag value
     */
    public static @NotNull <T> T getItemTag(@NotNull Plugin plugin, @NotNull ItemStack itemStack, @NotNull String key, @NotNull T defaultValue) {
        ItemMeta meta = itemStack.getItemMeta();

        if(meta != null)
            return getItemTag(plugin, meta, key, defaultValue);

        return defaultValue;
    }

    public static @NotNull <T> T getItemTag(@NotNull Plugin plugin, @NotNull ItemMeta meta, @NotNull String key, @NotNull T defaultValue) {
        PersistentDataType<T, T> dataType = (PersistentDataType<T, T>) ItemTag.getPersistentDataTypeByType(defaultValue);
        PersistentDataContainer container = meta.getPersistentDataContainer();
        NamespacedKey namespacedKey = new NamespacedKey(plugin, key);

        if(container.get(namespacedKey, dataType) == null)
            container.set(namespacedKey, dataType, defaultValue);

        T value = container.get(namespacedKey, dataType);

        return value == null ? defaultValue : value;
    }


    @Deprecated
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

}
