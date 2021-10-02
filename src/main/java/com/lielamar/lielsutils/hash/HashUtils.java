package com.lielamar.lielsutils.hash;

import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;

public class HashUtils {

    public String hashSHA256(String plain) throws NoSuchAlgorithmException { return Hashing.sha256().hashString(plain, StandardCharsets.UTF_8).toString(); }
    public String hashSHA384(String plain) throws NoSuchAlgorithmException { return Hashing.sha384().hashString(plain, StandardCharsets.UTF_8).toString(); }
    public String hashSHA512(String plain) throws NoSuchAlgorithmException { return Hashing.sha512().hashString(plain, StandardCharsets.UTF_8).toString(); }
}
