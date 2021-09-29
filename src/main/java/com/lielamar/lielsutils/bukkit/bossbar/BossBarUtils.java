package com.lielamar.lielsutils.bukkit.bossbar;

import com.lielamar.packetmanager.PacketManager;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class BossBarUtils {

    private static final int DISTANCE = 32;

    private static Class<?> entityWitherClass, worldClass, craftWorldClass, packetPlayOutSpawnEntityLivingClass, packetPlayOutEntityDestroyClass;
    private static Method setLocationMethod, setCustomNameMethod, setInvisibleMethod, setHealthMethod, getHealthMethod, getIdMethod;
    private static Constructor<?> packetPlayOutSpawnEntityLivingConstructor, packetPlayOutEntityDestroyConstructor;

    private Object wither;

    public BossBarUtils(@NotNull String message, @NotNull Location location) {
        this(message, location, 1F);
    }

    public BossBarUtils(@NotNull String message, @NotNull Location location, float health) {
        if(location.getWorld() == null) return;

        location = location.getDirection().multiply(DISTANCE).add(location.toVector()).toLocation(location.getWorld());

        try {
            if(entityWitherClass == null)
                entityWitherClass = PacketManager.getNMSClass("EntityWither");

            if(worldClass == null)
                worldClass = PacketManager.getNMSClass("World");

            if(craftWorldClass == null)
                craftWorldClass = PacketManager.getClass("org.bukkit.craftbukkit", "CraftWorld");

            this.wither = entityWitherClass
                    .getConstructor(new Class[] { worldClass })
                    .newInstance(craftWorldClass.getMethod("getHandle").invoke(location.getWorld()));

            if(setLocationMethod == null)
                setLocationMethod = this.wither.getClass().getMethod("setLocation", double.class, double.class, double.class, float.class, float.class);
            setLocationMethod.invoke(this.wither, location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());

            if(setCustomNameMethod == null)
                setCustomNameMethod = this.wither.getClass().getMethod("setCustomName", String.class);
            setCustomNameMethod.invoke(this.wither, message);

            if(setInvisibleMethod == null)
                setInvisibleMethod = this.wither.getClass().getMethod("setInvisible", boolean.class);
            setInvisibleMethod.invoke(this.wither, true);

            if(setHealthMethod == null)
                setHealthMethod = this.wither.getClass().getMethod("setHealth", float.class);
            if(getHealthMethod == null)
                getHealthMethod = this.wither.getClass().getMethod("getMaxHealth");
            setHealthMethod.invoke(this.wither, health*(float)getHealthMethod.invoke(this.wither));

            if(getIdMethod == null)
                getIdMethod = this.wither.getClass().getMethod("getId");
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates the bossbar
     *
     * @param message    Message to set
     * @param location   Location of the bossbar
     */
    public void update(@NotNull String message, @NotNull Location location) {
        if(location.getWorld() == null) return;

        location = location.getDirection().multiply(-DISTANCE).add(location.toVector()).toLocation(location.getWorld());

        try {
            setLocationMethod.invoke(this.wither, location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
            setCustomNameMethod.invoke(this.wither, message);
        } catch (InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * Displays the boss bar
     *
     * @param player        Player to display the bossbar to
     */
    public void display(@NotNull Player player) {
        if(packetPlayOutSpawnEntityLivingClass == null || packetPlayOutSpawnEntityLivingConstructor == null) {
            try {
                packetPlayOutSpawnEntityLivingClass = PacketManager.getNMSClass("PacketPlayOutSpawnEntityLiving");
                packetPlayOutSpawnEntityLivingConstructor = packetPlayOutSpawnEntityLivingClass
                        .getConstructor(PacketManager.getNMSClass("EntityLiving"));
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }

        Object packet;

        try {
            packet = packetPlayOutSpawnEntityLivingConstructor.newInstance(this.wither);

            PacketManager.sendPacket(player, packet);
        } catch (IllegalAccessException | InvocationTargetException | InstantiationException | IllegalArgumentException | SecurityException e) {
            e.printStackTrace();
        }
    }

    /**
     * Destroys the bossbar
     *
     * @param player        Player to destroy the bossbar to
     */
    public void destroy(@NotNull Player player) {
        if(packetPlayOutEntityDestroyClass == null || packetPlayOutEntityDestroyConstructor == null) {
            try {
                packetPlayOutEntityDestroyClass = PacketManager.getNMSClass("PacketPlayOutEntityDestroy");
                packetPlayOutEntityDestroyConstructor = packetPlayOutEntityDestroyClass.getConstructor(int[].class);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }

        Object packet;

        try {
            if(this.wither == null || getIdMethod == null) return;
            packet = packetPlayOutEntityDestroyConstructor.newInstance((Object) new int[] { (int)getIdMethod.invoke(this.wither) });

            PacketManager.sendPacket(player, packet);
        } catch (IllegalAccessException | InvocationTargetException | InstantiationException | IllegalArgumentException | SecurityException e) {
            e.printStackTrace();
        }
    }
}
