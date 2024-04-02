package es.codeurj.mortez365.DTO;

import jakarta.persistence.Column;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.List;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class WalletDataDTO {


    @Column(name = "OWNER")
    private String Owner;

    @Column(name = "MONEY")
    private double money;

    @Column(name = "CARD_NUMBER")
    private String card_number;


    public WalletDataDTO(String owner, double money, String card_number) {
            super();
        Owner = owner;
        this.money = money;
        this.card_number = card_number;
    }

    public WalletDataDTO() {
            super();
        }





    }
