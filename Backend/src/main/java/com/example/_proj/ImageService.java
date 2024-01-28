package com.example._proj;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Service
public class ImageService {
    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private FileRepository fileRepository;

    private final String FILE_PATH = "C:/Users/batur/Desktop/Images";

    public String uploadImage(MultipartFile file) throws IOException {
        ImageSource img = imageRepository.save(ImageSource.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .imageData(Utils.compressImage(file.getBytes())).build());
        if (img != null) {
            return "success" + file.getOriginalFilename();
        }
        return null;
    }

    public byte[] downloadImage(String fileName) {
        Optional<ImageSource> dbImageData = imageRepository.findByName(fileName);
        byte[] images = Utils.decompressImage(dbImageData.get().getImageData());
        return images;
    }
}
