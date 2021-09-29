package com.lielamar.lielsutils.bukkit.ai;

public enum MoveOptions {

    WALKING     ((byte) 0b00000001),
    JUMPING     ((byte) 0b00000010),
    FLING       ((byte) 0b00000100),
    GLIDING     ((byte) 0b00001000),
    SWIMMING    ((byte) 0b00010000),
    CLAIMING    ((byte) 0b00100000),
    FLOATING    ((byte) 0b01000000),
    DROPPING    ((byte) 0b10000000);

    private final byte id;

    MoveOptions(byte id) {
        this.id = id;
    }

    public byte add(byte b) {
        return (byte) (b | id);
    }
    public boolean can(byte b) {
        return (id & b) > 0;
    }
}