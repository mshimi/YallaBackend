package org.example.yalla_api.common.dtos.image;

import lombok.Data;


public class ImageDTO {
    private String type; // e.g., image/jpeg, image/png
    private byte[] image; // Binary image data

    // Getters and Setters
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}