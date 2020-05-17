package com.VU.PSKProject.Utils;

import java.nio.charset.StandardCharsets;
import java.util.Random;

public class PasswordUtils {
    public static String generateRandomString(int length)
    {
        byte[] array = new byte[length];
        new Random().nextBytes(array);
        return new String(array, StandardCharsets.UTF_8);
    }
}
