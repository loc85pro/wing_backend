package com.win.server.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
@Service
public class FileService {
    public void saveFile(MultipartFile file, String filename, String path) throws FileNotFoundException, IOException {
        File parentFolder = new File(path);
        boolean isExist = parentFolder.mkdirs();
        File myFile = new File(path + '/' + filename);
        FileOutputStream out = new FileOutputStream(myFile);
        out.write(file.getBytes());
    }

    public File loadAvatar(String username) {
        File defaultAvatar = new File("/src/main/resources/static/data/user/" + username + "/avatar.jpg"); //Personal avatar
        if (!defaultAvatar.exists())
            return new File("/src/main/resources/static/general/default_avatar.png"); //Default avatar
        return defaultAvatar;
    }
}