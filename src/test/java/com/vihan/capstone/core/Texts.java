package com.vihan.capstone.core;

public final class Texts {
    private Texts() {}

    public static String normalize(String s) {
        return s == null ? "" : s.replace('\u00A0', ' ').trim().replaceAll("\\s+", "");
    }
}