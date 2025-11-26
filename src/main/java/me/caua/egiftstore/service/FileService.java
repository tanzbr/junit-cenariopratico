package me.caua.egiftstore.service;

import java.io.File;

public interface FileService {

    void save(Long id, String imageName, byte[] image);
    File download(String imageName);

}
