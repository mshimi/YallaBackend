package org.example.yalla_api.common.enums;

public enum Language {
    DE,
    EN,
    FR;



    public static Language fromString (String langString){
        try {
            return Language.valueOf(langString.toUpperCase()); // Converts to uppercase to ensure case insensitivity
        } catch (IllegalArgumentException e) {
            return Language.EN;
        }
    }
}
