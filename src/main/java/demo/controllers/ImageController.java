package demo.controllers;

import demo.entity.ImageEntity;
import demo.services.IImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping("/api/images")
public class ImageController {

    @Autowired
    IImageService service;

    @GetMapping(value = "/{image_id}")
    public ResponseEntity<byte[]> getImage (@PathVariable("image_id") Long id) {
        ImageEntity entity = service.getById(id);
        if (entity == null) {
            return null;
        }
        byte [] image = decodeImage(entity);
        return getEntity(image);
    }

    private ResponseEntity<byte[]> getEntity(byte[] image) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setCacheControl(CacheControl.noCache().getHeaderValue());
        httpHeaders.setContentType(MediaType.IMAGE_JPEG);
        httpHeaders.setContentLength(image.length);
        return new ResponseEntity<>(image, httpHeaders, HttpStatus.OK);
    }

    private byte[] decodeImage(ImageEntity entity) {
        byte [] encodedImage = entity.getImage();
        return Base64.getDecoder().decode(encodedImage);
    }

    @GetMapping(value = "/all")
    public List<String> getImageUrls() {
        List<Long> ids = service.getAllIds();
        List<String> imageUrls = new ArrayList<>();
        for (Long id : ids) {
            String url = "/api/images/" + id;
            imageUrls.add(url);
        }
        return imageUrls;
    }

}
