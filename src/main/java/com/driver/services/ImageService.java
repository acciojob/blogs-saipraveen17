package com.driver.services;

import com.driver.models.*;
import com.driver.repositories.BlogRepository;
import com.driver.repositories.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ImageService {
    @Autowired
    ImageRepository imageRepository2;
    @Autowired
    private BlogRepository blogRepository1;

    public Image createAndReturn(Blog blog, String description, String dimensions){
        //create an image based on given parameters and add it to the imageList of given blog
        Image image = new Image(description, dimensions);
        image.setBlog(blog);
        List<Image> imageList = blog.getImageList();
        if(imageList==null) imageList = new ArrayList<>();
        imageList.add(image);
        blog.setImageList(imageList);
        blogRepository1.save(blog);
        return image;
    }

    public void deleteImage(Image image){

        //Blog blog = image.getBlog();
        imageRepository2.delete(image);
//        blog.getImageList().remove(image);
//        blogRepository.save(blog);
    }

    public Image findById(int id) {

        return imageRepository2.findById(id).get();
    }

    public int countImagesInScreen(Image image, String screenDimensions) {
        //Find the number of images of given dimensions that can fit in a screen having `screenDimensions`
        //In case the image is null, return 0
        String imageDimensions = image.getDimensions();
        int countImages = 0;
        int imageLength, imageBreadth, screenLength, screenBreadth;
        int[] imageDim = getDimensions(imageDimensions);
        int[] screenDim = getDimensions(screenDimensions);
        imageLength = imageDim[0];
        imageBreadth = imageDim[1];
        screenLength = screenDim[0];
        screenBreadth = screenDim[1];
        int lenCount = screenLength/imageLength;
        int bdtCount = screenBreadth/imageBreadth;
        countImages = lenCount*bdtCount;
        return countImages;
    }
    public int[] getDimensions(String dimension) {

        int imageLength, imageBreadth;
        StringBuilder sb = new StringBuilder();
        int i;
        for(i=0; i<dimension.length(); i++) {
            if(Character.isDigit(dimension.charAt(i))) {
                sb.append(dimension.charAt(i));
            }
            else break;
        }
        imageLength = Integer.valueOf(sb.toString());
        sb = new StringBuilder();
        for(;i<dimension.length(); i++) {
            if(Character.isDigit(dimension.charAt(i))) {
                sb.append(dimension.charAt(i));
            }
        }
        imageBreadth = Integer.valueOf(sb.toString());
        return new int[]{imageLength, imageBreadth};
    }
}
