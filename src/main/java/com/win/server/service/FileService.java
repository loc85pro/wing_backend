package com.win.server.service;

import java.io.*;

import com.win.server.exception.myexception.FileGeneralException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
@Service
public class FileService {
    public void saveFile(MultipartFile file, String filename, String path) {
        try {
            File parentFolder = new File(path);
            boolean isExist = parentFolder.mkdirs();
            File myFile = new File(path + '/' + filename);
            FileOutputStream out = new FileOutputStream(myFile);
            out.write(file.getBytes());
        } catch (Exception ex) {
            throw new FileGeneralException();
        }

    }

    public byte[] loadAvatar(String userId) throws FileNotFoundException , IOException{
        String path = "src/main/resources/public/user/" + userId + "/avatar.png";
        File defaultAvatar = new File(path); //Personal avatar
        System.out.println(defaultAvatar.getAbsolutePath());
        FileInputStream in;
        if (!defaultAvatar.exists())   // User's avatar not exsit => default avatar
            in = new FileInputStream("src/main/resources/public/static/general/default_avatar.png");
        else
            in = new FileInputStream(path);
        return in.readAllBytes();
    }
}