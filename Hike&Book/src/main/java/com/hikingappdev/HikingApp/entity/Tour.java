package com.hikingappdev.HikingApp.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "tours")
public class Tour {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tourType;          // Tip ture (npr. Jednodnevna, Višednevna, Zimska)
    private BigDecimal tourPrice;     // Cena ture
    private String tourPhotoUrl;     // URL slike ture
    private String tourDescription;  // Opis ture
    private String destination;      // Destinacija ture (npr. Tara, Kopaonik)
    private String difficultyLevel;  // Nivo težine (npr. Lako, Srednje, Teško)

    @OneToMany(mappedBy = "tour", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Booking> bookings = new ArrayList<>();

    @Override
    public String toString() {
        return "Tour{" +
                "id=" + id +
                ", tourType='" + tourType + '\'' +
                ", tourPrice=" + tourPrice +
                ", tourPhotoUrl='" + tourPhotoUrl + '\'' +
                ", tourDescription='" + tourDescription + '\'' +
                ", destination='" + destination + '\'' +
                ", difficultyLevel='" + difficultyLevel + '\'' +
                '}';
    }
}