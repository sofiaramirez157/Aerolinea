package com.example.Aerolinea.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DestinationDTO {
    private Long id;
    private String country;
    private String code;
}