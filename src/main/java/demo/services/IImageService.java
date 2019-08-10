package demo.services;

import demo.entity.ImageEntity;

import java.io.IOException;
import java.util.List;

public interface IImageService {
    public List<Long> getAllIds();
    public ImageEntity getById(long id);
    public void addImages() throws IOException;
}
