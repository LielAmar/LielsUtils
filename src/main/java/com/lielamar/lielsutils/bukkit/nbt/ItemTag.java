package com.lielamar.lielsutils.bukkit.nbt;

import com.lielamar.connections.serializable.Serializable;
import com.lielamar.connections.serializable.SerializableDocument;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ItemTag implements Serializable {

    public String tagKey;
    public Object tagValue;

    public String tagTypeName;
    public PersistentDataType tagType;

    public ItemTag(@NotNull String tagKey, @NotNull Object tagValue, @NotNull String tagType) {
        this(tagKey, tagValue, getPersistentDataTypeName(tagType));
    }

    public ItemTag(@NotNull String tagKey, @NotNull Object tagValue, @NotNull PersistentDataType tagType) {
        this.tagKey = tagKey;
        this.tagValue = tagValue;
        this.tagTypeName = tagType.getComplexType().getTypeName();
        this.tagType = tagType;
    }


    @Override
    public @NotNull SerializableDocument write() {
        SerializableDocument document = new SerializableDocument();
        document.put("tag_key", tagKey);
        document.put("tag_value", tagValue);
        document.put("tag_type", tagTypeName);

        return document;
    }

    @Override
    public void read(@Nullable SerializableDocument document) {
        if(document == null) return;

        this.tagKey = document.getOrDefault("tag_key", tagKey);
        this.tagValue = document.getOrDefault("tag_value", tagValue);
        this.tagTypeName = document.getOrDefault("tag_type", tagTypeName);
        this.tagType = getPersistentDataTypeName(tagTypeName);
    }



    public static @NotNull PersistentDataType<?, ?> getPersistentDataTypeByType(@NotNull Object type) {
        if(type instanceof Byte) return PersistentDataType.BYTE;
        if(type instanceof Byte[]) return PersistentDataType.BYTE_ARRAY;
        if(type instanceof Short) return PersistentDataType.SHORT;
        if(type instanceof Integer) return PersistentDataType.INTEGER;
        if(type instanceof Integer[]) return PersistentDataType.INTEGER_ARRAY;
        if(type instanceof Long) return PersistentDataType.LONG;
        if(type instanceof Long[]) return PersistentDataType.LONG_ARRAY;
        if(type instanceof Double) return PersistentDataType.DOUBLE;
        if(type instanceof Float) return PersistentDataType.FLOAT;
        if(type instanceof PersistentDataContainer) return PersistentDataType.TAG_CONTAINER;
        if(type instanceof PersistentDataContainer[]) return PersistentDataType.TAG_CONTAINER_ARRAY;

        return PersistentDataType.STRING;
    }

    public static @NotNull PersistentDataType getPersistentDataTypeName(@NotNull String typeName) {
        switch (typeName.toUpperCase()) {
            case "BYTE":
                return PersistentDataType.BYTE;
            case "BYTE_ARRAY":
                return PersistentDataType.BYTE_ARRAY;
            case "SHORT":
                return PersistentDataType.SHORT;
            case "INTEGER":
                return PersistentDataType.INTEGER;
            case "INTEGER_ARRAY":
                return PersistentDataType.INTEGER_ARRAY;
            case "LONG":
                return PersistentDataType.LONG;
            case "LONG_ARRAY":
                return PersistentDataType.LONG_ARRAY;
            case "DOUBLE":
                return PersistentDataType.DOUBLE;
            case "FLOAT":
                return PersistentDataType.FLOAT;
            case "TAG_CONTAINER":
                return PersistentDataType.TAG_CONTAINER;
            case "TAG_CONTAINER_ARRAY":
                return PersistentDataType.TAG_CONTAINER_ARRAY;
            default:
                return PersistentDataType.STRING;
        }
    }
}
