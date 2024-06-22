package store.auroraauction.be.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import store.auroraauction.be.Models.ImageRequest;
import store.auroraauction.be.entity.Image;
import store.auroraauction.be.entity.Process;
import store.auroraauction.be.repository.ImageRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class ImageService {
    @Autowired
    private ImageRepository imageRepository;
    public Image add(ImageRequest newimage ) {
        Image image = new Image();
        Set<String> list_images = newimage.getImage_url();
        for (String url : list_images){
            image.setImage_url(url);
            //image.setJewelry(newimage.getJewelry_id());
            //list_images.add();
        }

        imageRepository.save(image);
        return image;
    }
    public String deleteProcess(long id) {
        imageRepository.deleteById(id) ;
        return "image delteted";
    }

    public List<Image> getAllProcess(){
        List<Image> image = imageRepository.findAll();
        return image;
    }
//    public Image getProcess(long id) {
//        Process image = imageRepository.findById(id).get();
//        return image;
//    }
//    public Image updateProcess(long id, Image newimage ) {
//        Image image = imageRepository.findById(id).get();
//        image.setName( newprocess.getName() );
//
//        imageRepository.save(image);
//        return image;
//    }
}
