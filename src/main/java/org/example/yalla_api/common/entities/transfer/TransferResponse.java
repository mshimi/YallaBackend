package org.example.yalla_api.common.entities.transfer;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class TransferResponse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private TransferClientRequest request;

    @ManyToOne
    private Vehicle vehicle;

    private Double totalPrice;
}