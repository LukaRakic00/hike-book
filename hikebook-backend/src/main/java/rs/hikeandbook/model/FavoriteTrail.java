package rs.hikeandbook.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "favorite_trails")
public class FavoriteTrail {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "SERIAL")
    private Integer id;
    
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trail_id", nullable = false)
    private Trail trail;
    
    @Column(name = "added_date")
    private LocalDateTime addedDate;
    
    // Constructors
    public FavoriteTrail() {}
    
    public FavoriteTrail(User user, Trail trail) {
        this.user = user;
        this.trail = trail;
    }
    
    // Getters and Setters
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    public Trail getTrail() {
        return trail;
    }
    
    public void setTrail(Trail trail) {
        this.trail = trail;
    }
    
    public LocalDateTime getAddedDate() {
        return addedDate;
    }
    
    public void setAddedDate(LocalDateTime addedDate) {
        this.addedDate = addedDate;
    }
    
    @PrePersist
    protected void onCreate() {
        addedDate = LocalDateTime.now();
    }
} 