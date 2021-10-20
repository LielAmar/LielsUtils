//package com.lielamar.lielsutils.bukkit.items;
//
//import org.bukkit.Material;
//import org.bukkit.enchantments.Enchantment;
//import org.bukkit.inventory.ItemStack;
//import org.bukkit.inventory.meta.ItemMeta;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Map;
//
//public class ItemBuilder {
//
//    private ItemStack itemStack;
//
//    public ItemBuilder(Material material) { this(material, 1); }
//    public ItemBuilder(ItemStack itemStack) { this.itemStack = itemStack; }
//    public ItemBuilder(Material material, int amount) { this.itemStack = new ItemStack(material, amount); }
//
//    public ItemBuilder clone() { return new ItemBuilder(this.itemStack); }
//
//    public ItemBuilder setName(String name) {
//        ItemMeta meta = this.itemStack.getItemMeta();
//        if(meta != null) {
//            meta.setDisplayName(name);
//            this.itemStack.setItemMeta(meta);
//        }
//        return this;
//    }
//
//    public ItemBuilder addUnsafeEnchantment(Enchantment enchantment, int level) {
//        this.itemStack.addUnsafeEnchantment(enchantment, level);
//        return this;
//    }
//
//    public ItemBuilder removeEnchantment(Enchantment enchantment) {
//        this.itemStack.removeEnchantment(enchantment);
//        return this;
//    }
//
//    public ItemBuilder addEnchant(Enchantment enchantment, int level) {
//        ItemMeta meta = this.itemStack.getItemMeta();
//        if(meta != null) {
//            meta.addEnchant(enchantment, level, true);
//            this.itemStack.setItemMeta(meta);
//        }
//        return this;
//    }
//
//    public ItemBuilder addEnchantments(Map<Enchantment, Integer> enchantments) {
//        this.itemStack.addEnchantments(enchantments);
//        return this;
//    }
//
//    public ItemBuilder setLore(String... lore){
//        ItemMeta meta = this.itemStack.getItemMeta();
//        if(meta != null) {
//            meta.setLore(Arrays.asList(lore));
//            this.itemStack.setItemMeta(meta);
//        }
//        return this;
//    }
//
//    public ItemBuilder setLore(List<String> lore) {
//        ItemMeta meta = this.itemStack.getItemMeta();
//        if(meta != null) {
//            meta.setLore(lore);
//            this.itemStack.setItemMeta(meta);
//        }
//        return this;
//    }
//
//    public ItemBuilder removeLoreLine(String line) {
//        ItemMeta meta = this.itemStack.getItemMeta();
//        if(meta != null && meta.getLore() != null) {
//            List<String> lore = new ArrayList<>(meta.getLore());
//            if(!lore.contains(line)) return this;
//
//            lore.remove(line);
//            meta.setLore(lore);
//            this.itemStack.setItemMeta(meta);
//        }
//        return this;
//    }
//
//    public ItemBuilder removeLoreLine(int index) {
//        ItemMeta meta = this.itemStack.getItemMeta();
//
//        if(meta != null && meta.getLore() != null) {
//            List<String> lore = new ArrayList<>(meta.getLore());
//            if(index < 0 || index > lore.size()) return this;
//
//            lore.remove(index);
//            meta.setLore(lore);
//            this.itemStack.setItemMeta(meta);
//        }
//        return this;
//    }
//
//    public ItemBuilder addLoreLine(String line) {
//        ItemMeta meta = this.itemStack.getItemMeta();
//
//        if(meta != null && meta.hasLore() && meta.getLore() != null) {
//            List<String> lore = new ArrayList<>();
//            if(meta.hasLore())
//                lore = new ArrayList<>(meta.getLore());
//
//            lore.add(line);
//            meta.setLore(lore);
//            this.itemStack.setItemMeta(meta);
//        }
//        return this;
//    }
//
//    public ItemBuilder addLoreLine(String line, int pos) {
//        ItemMeta meta = this.itemStack.getItemMeta();
//
//        if(meta != null && meta.getLore() != null) {
//            List<String> lore = new ArrayList<>(meta.getLore());
//            lore.set(pos, line);
//            meta.setLore(lore);
//            this.itemStack.setItemMeta(meta);
//        }
//        return this;
//    }
//
//    public ItemBuilder setNBTTag(String tag, NBTBase value) {
//        net.minecraft.server.v1_16_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(this.itemStack);
//
//        NBTTagCompound tagCompound = (nmsItem.hasTag()) ? nmsItem.getTag() : new NBTTagCompound();
//        if(tagCompound == null) return this;
//
//        tagCompound.set(tag, value);
//        nmsItem.setTag(tagCompound);
//
//        this.itemStack = CraftItemStack.asBukkitCopy(nmsItem);
//        return this;
//    }
//
//
//    public ItemStack toItemStack(){
//        return this.itemStack;
//    }
//}
