package org.example.yalla_api.common.entities.transfer;

import jakarta.persistence.*;
import lombok.Data;
import org.example.yalla_api.common.entities.core.Airport;
import org.example.yalla_api.common.entities.core.Area;
import org.example.yalla_api.common.entities.core.Hotel;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
public class TransferClientRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate requestDate;

    @ManyToOne(optional = false,fetch = FetchType.EAGER)
    @JoinColumn(name = "hotel_id", nullable = false)
    private Hotel hotel; // Airport or Hotel
    @ManyToOne(optional = false,fetch = FetchType.EAGER)
    @JoinColumn(name = "airport_id", nullable = false)
    private Airport airport;   // Airport or Hotel

    private Integer numberOfAdults;

    @ElementCollection
    private List<Integer> childrenAges;

//    @ManyToMany
//    private List<TransferExtra> extras;

    private TransferType transferType;


    public Area getStartArea (){
       return transferType.equals(TransferType.ARRIVAL) ? airport.getArea() : hotel.getArea();
    }

    public Area getEndArea (){
        return transferType.equals(TransferType.DEPARTURE) ? airport.getArea() : hotel.getArea();
    }

}