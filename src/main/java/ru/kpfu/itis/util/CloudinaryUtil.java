package ru.kpfu.itis.util;

import com.cloudinary.Cloudinary;

import java.util.HashMap;
import java.util.Map;

public class CloudinaryUtil {

    private static Cloudinary cloudinary;

    public static Cloudinary getInstance() {
        if (cloudinary == null) {
            Map<String, String> configMap = new HashMap<>();
            configMap.put("cloud_name", "dkiovijcy");
            configMap.put("api_key", "398688584614783");
            configMap.put("api_secret", "3ZS9h40QAT5xzKqBhS39AKHra0o");
            cloudinary = new Cloudinary(configMap);
        }
        return cloudinary;
    }
}
