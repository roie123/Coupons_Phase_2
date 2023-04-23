package com.RoieIvri.CouponsPhase2.COUPON;

import com.RoieIvri.CouponsPhase2.COMPANY.Company;
import com.RoieIvri.CouponsPhase2.CategoryType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.StringTokenizer;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private int amount;
    private double price;
    private String image;

    private boolean isActive = true;
    @Enumerated()
    private CategoryType category = CategoryType.StupidFace;


    @ManyToOne()
    @ToString.Exclude
    @JoinColumn
    @JsonIgnore
    private Company company;

    public Coupon(String title, String description, LocalDate startDate, LocalDate endDate, int amount, double price, String image) {
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.amount = amount;
        this.price = price;
        this.image = image;

    }

    public Coupon(Coupon other) {
        this.title = other.getTitle();
        this.company = other.getCompany();
        this.amount = other.getAmount();
        this.image = other.getImage();
        this.isActive = other.isActive;
        this.description = other.description;
        this.startDate = other.startDate;
        this.endDate = other.getEndDate();

    }

    @Override
    public String toString() {
        return "Coupon{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", amount=" + amount +
                ", price=" + price +
                ", image='" + image + '\'' +
                ", category" + category +
                '}';
    }
}
