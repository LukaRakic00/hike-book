package rs.hikeandbook.dto;

public class UploadResponse {
    
    private String url;
    private String publicId;
    private String message;
    private boolean success;
    
    // Constructors
    public UploadResponse() {}
    
    public UploadResponse(String url, String publicId, String message, boolean success) {
        this.url = url;
        this.publicId = publicId;
        this.message = message;
        this.success = success;
    }
    
    public static UploadResponse success(String url, String publicId) {
        return new UploadResponse(url, publicId, "Image uploaded successfully", true);
    }
    
    public static UploadResponse error(String message) {
        return new UploadResponse(null, null, message, false);
    }
    
    // Getters and Setters
    public String getUrl() {
        return url;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }
    
    public String getPublicId() {
        return publicId;
    }
    
    public void setPublicId(String publicId) {
        this.publicId = publicId;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public boolean isSuccess() {
        return success;
    }
    
    public void setSuccess(boolean success) {
        this.success = success;
    }
} 