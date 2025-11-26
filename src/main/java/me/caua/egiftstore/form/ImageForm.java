package me.caua.egiftstore.form;

import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.core.MediaType;
import org.jboss.resteasy.annotations.providers.multipart.PartType;

public class ImageForm {

    @FormParam("imageName")
    @PartType(MediaType.TEXT_PLAIN)
    private String imageName;
    @FormParam("image")
    @PartType("application/octet-stream")
    private byte[] image;

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
