package com.hikingappdev.HikingApp.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TourDTO {

    private Long id;
    private String tourType;          // Tip ture (npr. Jednodnevna, Višednevna, Zimska)
    private BigDecimal tourPrice;     // Cena ture
    private String tourPhotoUrl;     // URL slike ture
    private String tourDescription;  // Opis ture
    private String destination;      // Destinacija ture (npr. Tara, Kopaonik)
    private String difficultyLevel;  // Nivo težine (npr. Lako, Srednje, Teško)
    private List<BookingDTO> bookings; // Lista rezervacija za ovu turu
}