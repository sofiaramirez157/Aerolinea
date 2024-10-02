package com.example.Aerolinea.dto;

import com.example.Aerolinea.model.Destination;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class DestinationDTO {
    private Long id;
    private String name;
    private String country;

    public static DestinationDTO fromDestination(Destination destination){
        return new DestinationDTO(
                destination.getId(),
                destination.getName(),
                destination.getCountry()
        );
    }
}
