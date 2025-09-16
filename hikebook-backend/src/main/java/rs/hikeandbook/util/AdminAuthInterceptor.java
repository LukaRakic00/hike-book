package rs.hikeandbook.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import rs.hikeandbook.util.JwtUtil;
import java.util.HashMap;
import java.util.Map;

@Component
public class AdminAuthInterceptor implements HandlerInterceptor {
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try {
            System.out.println("AdminAuthInterceptor: Processing request to " + request.getRequestURI());
            
            String authHeader = request.getHeader("Authorization");
            System.out.println("AdminAuthInterceptor: Auth header: " + (authHeader != null ? "present" : "missing"));
            
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                System.out.println("AdminAuthInterceptor: No valid auth header");
                sendErrorResponse(response, "Access token required", HttpStatus.UNAUTHORIZED);
                return false;
            }
            
            String token = authHeader.substring(7);
            System.out.println("AdminAuthInterceptor: Token extracted, length: " + token.length());
            
            String role = jwtUtil.getRoleFromToken(token);
            System.out.println("AdminAuthInterceptor: Role from token: " + role);
            
            if (role == null || !"admin".equals(role)) {
                System.out.println("AdminAuthInterceptor: Not admin role");
                sendErrorResponse(response, "Admin access required", HttpStatus.FORBIDDEN);
                return false;
            }
            
            System.out.println("AdminAuthInterceptor: Admin access granted");
            return true;
            
        } catch (Exception e) {
            System.err.println("AdminAuthInterceptor: Error: " + e.getMessage());
            e.printStackTrace();
            sendErrorResponse(response, "Invalid token", HttpStatus.UNAUTHORIZED);
            return false;
        }
    }
    
    private void sendErrorResponse(HttpServletResponse response, String message, HttpStatus status) throws Exception {
        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("success", false);
        errorResponse.put("message", message);
        
        objectMapper.writeValue(response.getWriter(), errorResponse);
    }
} 