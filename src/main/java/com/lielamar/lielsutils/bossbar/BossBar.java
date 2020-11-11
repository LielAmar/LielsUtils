package com.lielamar.lielsutils.bossbar;

import com.packetmanager.lielamar.PacketManager;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class BossBar {

    private static final int DISTANCE = 32;

    private static Class<?> entityWitherClass, worldClass, craftWorldClass, packetPlayOutSpawnEntityLivingClass, packetPlayOutEntityDestroyClass;
    private static Method setLocationMethod, setCustomNameMethod, setInvisibleMethod, setHealthMethod, getHealthMethod, getIdMethod;
    private static Constructor<?> packetPlayOutSpawnEntityLivingConstructor, packetPlayOutEntityDestroyConstructor;

    private Object wither;

    public BossBar(String message, Location loc) {
        this(message, loc, 1F);
    }

    public BossBar(String message, Location loc, float health) {
        loc = loc.getDirection().multiply(DISTANCE).add(loc.toVector()).toLocation(loc.getWorld());

        try {
            if(entityWitherClass == null)
                entityWitherClass = PacketManager.getNMSClass("EntityWither");

            if(worldClass == null)
                worldClass = PacketManager.getNMSClass("World");

            if(craftWorldClass == null)
                craftWorldClass = PacketManager.getClass("org.bukkit.craftbukkit", "CraftWorld");

            this.wither = entityWitherClass
                    .getConstructor(new Class[] { worldClass })
                    .newInstance(craftWorldClass.getMethod("getHandle").invoke(loc.getWorld()));

            if(setLocationMethod == null)
                setLocationMethod = this.wither.getClass().getMethod("setLocation", double.class, double.class, double.class, float.class, float.class);
            setLocationMethod.invoke(this.wither, loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());

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
     * @param message        Message to set
     * @param loc            Location of the bossbar
     */
    public void update(String message, Location loc) {
        loc = loc.getDirection().multiply(-DISTANCE).add(loc.toVector()).toLocation(loc.getWorld());

        try {
            setLocationMethod.invoke(this.wither, loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
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
    public void display(Player player) {
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
    public void destroy(Player player) {
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
