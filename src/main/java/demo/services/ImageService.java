package demo.services;

import demo.entity.ImageEntity;
import demo.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ImageService implements IImageService {
    String [] imagePaths = {
            "/Users/annaluck/Desktop/ProjectSnezha/imageservice/src/main/java/demo/services/1.jpg",
            "/Users/annaluck/Desktop/ProjectSnezha/imageservice/src/main/java/demo/services/2.jpg",
            "/Users/annaluck/Desktop/ProjectSnezha/imageservice/src/main/java/demo/services/3.jpg",
            "/Users/annaluck/Desktop/ProjectSnezha/imageservice/src/main/java/demo/services/4.jpeg",
            "/Users/annaluck/Desktop/ProjectSnezha/imageservice/src/main/java/demo/services/5.jpg",
            "/Users/annaluck/Desktop/ProjectSnezha/imageservice/src/main/java/demo/services/6.jpg",

    };

    @Autowired
    ImageRepository repository;

    @PostConstruct
    @Transactional
    public void addImages() throws IOException {
        for (String imagePath : imagePaths) {
            byte [] image = getImageOnBytes(imagePath);
            ImageEntity entity = getEntityWithImage(image);
            repository.save(entity);
        }

    }

    private ImageEntity getEntityWithImage(byte[] image) {
        byte [] encodedImage = Base64.getEncoder().encode(image);
        ImageEntity entity = new ImageEntity();
        entity.setImage(encodedImage);
        return entity;
    }

    private byte[] getImageOnBytes(String imagePath) throws IOException {
        FileInputStream input = new FileInputStream(imagePath);
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        while (input.available() > 0) {
            int i = input.read();
            output.write(i);
        }
        byte [] image = output.toByteArray();
        input.close();
        output.close();
        return image;
    }

    public ImageEntity getById(long id) {
        ImageEntity entity = repository.findById(id).orElse(null);
        return entity;
    }

    public List<Long> getAllIds() {
        List<ImageEntity> ids = repository.findAll();
        return ids.stream().map(ImageEntity::getId).collect(Collectors.toList());
    }

}
