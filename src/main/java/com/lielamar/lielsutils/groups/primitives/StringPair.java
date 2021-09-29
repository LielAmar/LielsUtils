package com.lielamar.lielsutils.groups.primitives;

import com.lielamar.lielsutils.groups.Pair;
import org.jetbrains.annotations.Nullable;

public class StringPair extends Pair<String,String> {

    public StringPair(@Nullable String a, @Nullable String b) {
        super(a, b);
    }


    public boolean equals(@Nullable String str) {
        return toString().equals(str);
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if(this == obj) return true;
        if(!(obj instanceof StringPair)) return false;
        if(((StringPair) obj).getA().equals(getA()) && ((StringPair) obj).getB().equals(getB())) return true;

        return super.equals(obj);
    }

    @Override
    public String toString() {
        return getA()+ ":" + getB();
    }
}