package com.lielamar.lielsutils.bossbar;

import com.packetmanager.lielamar.PacketManager;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class BossBar {

    private static final int DISTANCE = 32;

    private Object wither;
    private Method setLocationMethod, setCustomNameMethod, setInvisibleMethod, setHealthMethod, getHealthMethod, getIdMethod;

    private Class<?> packetPlayOutSpawnEntityLivingClass, packetPlayOutEntityDestroyClass;
    private Constructor<?> packetPlayOutSpawnEntityLivingConstructor, packetPlayOutEntityDestroyConstructor;

    public BossBar(String message, Location loc) {
        this(message, loc, 1F);
    }

    public BossBar(String message, Location loc, float health) {
        loc = loc.getDirection().multiply(DISTANCE).add(loc.toVector()).toLocation(loc.getWorld());

        Class<?> entityWitherClass = PacketManager.getNMSClass("EntityWither");
        Class<?> worldClass = PacketManager.getNMSClass("World");
        Class<?> craftWorldClass = PacketManager.getClass("org.bukkit.craftbukkit", "CraftWorld");

        try {
            this.wither = entityWitherClass
                    .getConstructor(new Class[] { worldClass })
                    .newInstance(craftWorldClass.getMethod("getHandle").invoke(loc.getWorld()));

            this.setLocationMethod = this.wither.getClass().getMethod("setLocation", double.class, double.class, double.class, float.class, float.class);
            this.setLocationMethod.invoke(this.wither, loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());

            this.setCustomNameMethod = this.wither.getClass().getMethod("setCustomName", String.class);
            this.setCustomNameMethod.invoke(this.wither, message);

            this.setInvisibleMethod = this.wither.getClass().getMethod("setInvisible", boolean.class);
            this.setInvisibleMethod.invoke(this.wither, true);

            this.setHealthMethod = this.wither.getClass().getMethod("setHealth", float.class);
            this.getHealthMethod = this.wither.getClass().getMethod("getMaxHealth");
            this.setHealthMethod.invoke(this.wither, health*(float)this.getHealthMethod.invoke(this.wither));

            this.getIdMethod = this.wither.getClass().getMethod("getId");
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
        loc = loc.getDirection().multiply(DISTANCE).add(loc.toVector()).toLocation(loc.getWorld());

        try {
            this.setLocationMethod.invoke(this.wither, loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
            this.setCustomNameMethod.invoke(this.wither, message);
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
                this.packetPlayOutSpawnEntityLivingClass = PacketManager.getNMSClass("PacketPlayOutSpawnEntityLiving");
                this.packetPlayOutSpawnEntityLivingConstructor = packetPlayOutSpawnEntityLivingClass
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
                this.packetPlayOutEntityDestroyClass = PacketManager.getNMSClass("PacketPlayOutEntityDestroy");
                this.packetPlayOutEntityDestroyConstructor = packetPlayOutEntityDestroyClass.getConstructor(int[].class);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }

        Object packet;

        try {
            if(this.wither == null || this.getIdMethod == null) return;
            packet = packetPlayOutEntityDestroyConstructor.newInstance((Object) new int[] { (int)this.getIdMethod.invoke(this.wither) });

            PacketManager.sendPacket(player, packet);
        } catch (IllegalAccessException | InvocationTargetException | InstantiationException | IllegalArgumentException | SecurityException e) {
            e.printStackTrace();
        }
    }
}
