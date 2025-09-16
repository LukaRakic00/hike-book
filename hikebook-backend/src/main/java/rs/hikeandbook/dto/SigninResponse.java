package rs.hikeandbook.dto;

import java.time.LocalDateTime;

public class SigninResponse {
    
    private String token;
    private UserData user;
    private boolean success;
    private String message;
    
    // User data inner class
    public static class UserData {
        private Integer id;
        private String name;
        private String email;
        private String phone;
        private String role;
        private boolean isAdmin;
        private LocalDateTime registrationDate;
        private Boolean active;
        
        // Constructors
        public UserData() {}
        
        public UserData(Integer id, String name, String email, String phone, String role, 
                       LocalDateTime registrationDate, Boolean active) {
            this.id = id;
            this.name = name;
            this.email = email;
            this.phone = phone;
            this.role = role;
            this.isAdmin = "admin".equals(role);
            this.registrationDate = registrationDate;
            this.active = active;
        }
        
        // Getters and Setters
        public Integer getId() {
            return id;
        }
        
        public void setId(Integer id) {
            this.id = id;
        }
        
        public String getName() {
            return name;
        }
        
        public void setName(String name) {
            this.name = name;
        }
        
        public String getEmail() {
            return email;
        }
        
        public void setEmail(String email) {
            this.email = email;
        }
        
        public String getPhone() {
            return phone;
        }
        
        public void setPhone(String phone) {
            this.phone = phone;
        }
        
        public String getRole() {
            return role;
        }
        
        public void setRole(String role) {
            this.role = role;
            this.isAdmin = "admin".equals(role);
        }
        
        public boolean isAdmin() {
            return isAdmin;
        }
        
        public void setAdmin(boolean admin) {
            isAdmin = admin;
        }
        
        public LocalDateTime getRegistrationDate() {
            return registrationDate;
        }
        
        public void setRegistrationDate(LocalDateTime registrationDate) {
            this.registrationDate = registrationDate;
        }
        
        public Boolean getActive() {
            return active;
        }
        
        public void setActive(Boolean active) {
            this.active = active;
        }
    }
    
    // Constructors
    public SigninResponse() {}
    
    public SigninResponse(String token, UserData user, boolean success, String message) {
        this.token = token;
        this.user = user;
        this.success = success;
        this.message = message;
    }
    
    public static SigninResponse success(String token, UserData user) {
        return new SigninResponse(token, user, true, "Login successful");
    }
    
    public static SigninResponse error(String message) {
        return new SigninResponse(null, null, false, message);
    }
    
    // Getters and Setters
    public String getToken() {
        return token;
    }
    
    public void setToken(String token) {
        this.token = token;
    }
    
    public UserData getUser() {
        return user;
    }
    
    public void setUser(UserData user) {
        this.user = user;
    }
    
    public boolean isSuccess() {
        return success;
    }
    
    public void setSuccess(boolean success) {
        this.success = success;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
} 