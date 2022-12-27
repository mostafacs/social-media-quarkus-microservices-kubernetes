package org.social.converter;

import io.quarkus.runtime.util.StringUtil;

import javax.persistence.AttributeConverter;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author Mostafa
 * On 12/27/22
 */
public class ImageAttributeConverter implements AttributeConverter<List<String>, String> {


    @Override
    public String convertToDatabaseColumn(List<String> images) {
        if(images == null) return null;
        return String.join(",", images);
    }

    @Override
    public List<String> convertToEntityAttribute(String imagesStr) {
        if(StringUtil.isNullOrEmpty(imagesStr)) {
            return null;
        }
        List<String> imagesList = new ArrayList<>();
        String[] imagesArray = imagesStr.split(",");
        for(String image : imagesArray) {
            imagesList.add(image);
        }
        return imagesList;
    }
}
