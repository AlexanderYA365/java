package com.getjavajob.training.yakovleva.web.controllers.utils;

import com.getjavajob.training.yakovleva.common.Account;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

public class SocialNetworkUtils {

    public String loadPhoto(Account account) {
        String encodedPhoto = "";
        byte[] photo = account.getPhoto();
        if (photo != null) {
            byte[] encodedPhotoBytes = Base64.getEncoder().encode(photo);
            try {
                encodedPhoto = new String(encodedPhotoBytes, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                System.out.println("Error load photo - " + e);
            }
        }
        return encodedPhoto;
    }

}
