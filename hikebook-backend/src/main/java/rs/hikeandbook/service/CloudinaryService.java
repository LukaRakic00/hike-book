package rs.hikeandbook.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryService {

    @Autowired
    private Cloudinary cloudinary;

    public String uploadImage(MultipartFile file) throws IOException {
        Map<String, Object> options = ObjectUtils.asMap(
            "folder", "hikeBook/pictureOfDestination",
            "resource_type", "auto"
        );
        
        Map<?, ?> result = cloudinary.uploader().upload(file.getBytes(), options);
        return (String) result.get("secure_url");
    }

    public String uploadImage(MultipartFile file, String folder) throws IOException {
        Map<String, Object> options = ObjectUtils.asMap(
            "folder", "hikeBook/" + folder,
            "resource_type", "auto"
        );
        
        Map<?, ?> result = cloudinary.uploader().upload(file.getBytes(), options);
        return (String) result.get("secure_url");
    }

    public void deleteImage(String publicId) throws IOException {
        cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
    }

    public String getImageUrl(String publicId) {
        return cloudinary.url().generate(publicId);
    }

    public String getImageUrl(String publicId, Map<String, Object> transformations) {
        // For now, return basic URL without transformations
        // TODO: Implement transformations when needed
        return cloudinary.url().generate(publicId);
    }
} 