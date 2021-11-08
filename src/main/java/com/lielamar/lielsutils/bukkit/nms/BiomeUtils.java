package com.lielamar.lielsutils.bukkit.nms;

import net.minecraft.core.BlockPosition;
import net.minecraft.core.IRegistry;
import net.minecraft.core.IRegistryWritable;
import net.minecraft.network.protocol.game.PacketPlayOutMapChunk;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.world.level.World;
import net.minecraft.world.level.biome.BiomeBase;
import net.minecraft.world.level.biome.BiomeFog;
import net.minecraft.world.level.biome.BiomeSettingsGeneration;
import net.minecraft.world.level.biome.BiomeSettingsMobs;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.craftbukkit.v1_17_R1.CraftChunk;
import org.bukkit.craftbukkit.v1_17_R1.CraftServer;
import org.bukkit.craftbukkit.v1_17_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;

public class BiomeUtils {
    private DedicatedServer dedicatedserver;
    private BiomeBase.a newBiome;
    private BiomeFog.a newFog;

    public void createNewBiome(String group, String name, String copiedGroup, String copiedName) throws NoSuchFieldException, IllegalAccessException {
        Server server = Bukkit.getServer();
        CraftServer craftserver = (CraftServer)server;
        dedicatedserver = craftserver.getServer();
        ResourceKey<BiomeBase> newKey = ResourceKey.a(IRegistry.aO, new MinecraftKey(group, name));

        ResourceKey<BiomeBase> oldKey = ResourceKey.a(IRegistry.aO, new MinecraftKey(copiedGroup, copiedName));
        IRegistryWritable<BiomeBase> registrywritable = dedicatedserver.getCustomRegistry().b(IRegistry.aO);
        BiomeBase forestbiome = registrywritable.a(oldKey);

        newBiome = new BiomeBase.a();
        newBiome.a(forestbiome.t());
        newBiome.a(forestbiome.c());

        Field biomeSettingMobsField = BiomeBase.class.getDeclaredField("m");
        biomeSettingMobsField.setAccessible(true);
        BiomeSettingsMobs biomeSettingMobs = (BiomeSettingsMobs) biomeSettingMobsField.get(forestbiome);
        newBiome.a(biomeSettingMobs);

        Field biomeSettingGenField = BiomeBase.class.getDeclaredField("l");
        biomeSettingGenField.setAccessible(true);
        BiomeSettingsGeneration biomeSettingGen = (BiomeSettingsGeneration) biomeSettingGenField.get(forestbiome);
        newBiome.a(biomeSettingGen);
    }

    public BiomeBase.a makeBiomeSettings(float depth, float scale, float temperature, float downfall, BiomeBase.TemperatureModifier modifier) {
        newBiome = new BiomeBase.a();
        newBiome.a(depth); //Depth of biome
        newBiome.b(scale); //Scale of biome
        newBiome.c(temperature); //Temperature of biome
        newBiome.d(downfall); //Downfall of biome
        newBiome.a(modifier); //BiomeBase.TemperatureModifier.a will make your biome normal, BiomeBase.TemperatureModifier.b will make your biome frozen
        return newBiome;
    }

    public void setBiomeFog(BiomeFog.GrassColor grassColor, String[] colors) {
        if (colors.length > 6) {
            return;
        }
        newFog = new BiomeFog.a();
        newFog.a(grassColor); //This doesn't affect the actual final grass color, just leave this line as it is or you will get errors

        newFog.a(Integer.parseInt(colors[0],16)); //fogcolor
        newFog.b(Integer.parseInt(colors[1],16)); //water color
        newFog.c(Integer.parseInt(colors[2],16)); //water fog color
        newFog.d(Integer.parseInt(colors[3],16)); //sky color

        //Unnecessary values; can be removed safely if you don't want to change them
        newFog.e(Integer.parseInt(colors[4],16)); //foliage color (leaves, fines and more)
        newFog.f(Integer.parseInt(colors[5],16)); //grass blocks color
    }

    public boolean setBiome(String newBiomeName, Chunk c) {

        BiomeBase base;
        IRegistryWritable<BiomeBase> registrywritable = dedicatedserver.getCustomRegistry().b(IRegistry.aO);

        ResourceKey<BiomeBase> rkey = ResourceKey.a(IRegistry.aO, new MinecraftKey(newBiomeName.toLowerCase()));
        base = registrywritable.a(rkey);
        if(base == null) {
            if(newBiomeName.contains(":")) {
                ResourceKey<BiomeBase> newrkey = ResourceKey.a(IRegistry.aO, new MinecraftKey(newBiomeName.split(":")[0].toLowerCase(), newBiomeName.split(":")[1].toLowerCase()));
                base = registrywritable.a(newrkey);
                if(base == null) {
                    return false;
                }
            } else {
                return false;
            }
        }

        World w = ((CraftWorld)c.getWorld()).getHandle();

        for (int x = 0; x <= 15; x++) {
            for (int z = 0; z <= 15; z++) {
                for(int y = 0; y <= c.getWorld().getMaxHeight(); y++) {

                    setBiome(c.getX() * 16 + x, y, c.getZ() * 16 + z, w, base);
                }
            }
        }
        refreshChunksForAll(c);
        return true;
    }

    public boolean setBiome(String newBiomeName, Location l) {
        BiomeBase base;
        IRegistryWritable<BiomeBase> registrywritable = dedicatedserver.getCustomRegistry().b(IRegistry.aO);

        ResourceKey<BiomeBase> rkey = ResourceKey.a(IRegistry.aO, new MinecraftKey(newBiomeName.toLowerCase()));
        base = registrywritable.a(rkey);
        if(base == null) {
            if(newBiomeName.contains(":")) {
                ResourceKey<BiomeBase> newrkey = ResourceKey.a(IRegistry.aO, new MinecraftKey(newBiomeName.split(":")[0].toLowerCase(), newBiomeName.split(":")[1].toLowerCase()));
                base = registrywritable.a(newrkey);
                if(base == null) {
                    return false;
                }
            } else {
                return false;
            }
        }

        setBiome(l.getBlockX(), l.getBlockY(), l.getBlockZ(), ((CraftWorld)l.getWorld()).getHandle(), base);
        refreshChunksForAll(l.getChunk());
        return true;
    }

    private void setBiome(int x, int y, int z, World w, BiomeBase bb) {
        BlockPosition pos = new BlockPosition(x, 0, z);

        if (w.isLoaded(pos)) {

            net.minecraft.world.level.chunk.Chunk chunk = w.getChunkAtWorldCoords(pos);
            if (chunk != null) {

                chunk.getBiomeIndex().setBiome(x >> 2, y >> 2, z >> 2, bb);
                chunk.markDirty();
            }
        }
    }

    private void refreshChunksForAll(Chunk chunk) {
        net.minecraft.world.level.chunk.Chunk c = ((CraftChunk)chunk).getHandle();
        for (Player player : chunk.getWorld().getPlayers()) {
            if (player.isOnline()) {
                if((player.getLocation().distance(chunk.getBlock(0, 0, 0).getLocation()) < (Bukkit.getServer().getViewDistance() * 16))) {
                    ((CraftPlayer) player).getHandle().b.sendPacket(new PacketPlayOutMapChunk(c));
                }
            }
        }
    }
}
