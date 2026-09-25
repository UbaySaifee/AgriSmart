package com.agrismart.backend.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "farmer_profiles",
        indexes = {
                @Index(name = "idx_farmer_profile_district", columnList = "district"),
                @Index(name = "idx_farmer_profile_state", columnList = "state"),
                @Index(name = "idx_farmer_profile_pincode", columnList = "pincode")
        }
)
public class FarmerProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "user_id",
            nullable = false,
            unique = true
    )
    private User user;

    @Column(length = 120)
    private String village;

    @Column(length = 120)
    private String district;

    @Column(length = 120)
    private String state;

    @Column(length = 10)
    private String pincode;

    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    private FarmingType farmingType;

    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    private IrrigationType irrigationType;

    @Column
    private Double totalLandAcres;

    @Column
    private Integer farmingExperienceYears;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public FarmingType getFarmingType() {
        return farmingType;
    }

    public void setFarmingType(FarmingType farmingType) {
        this.farmingType = farmingType;
    }

    public IrrigationType getIrrigationType() {
        return irrigationType;
    }

    public void setIrrigationType(IrrigationType irrigationType) {
        this.irrigationType = irrigationType;
    }

    public Double getTotalLandAcres() {
        return totalLandAcres;
    }

    public void setTotalLandAcres(Double totalLandAcres) {
        this.totalLandAcres = totalLandAcres;
    }

    public Integer getFarmingExperienceYears() {
        return farmingExperienceYears;
    }

    public void setFarmingExperienceYears(Integer farmingExperienceYears) {
        this.farmingExperienceYears = farmingExperienceYears;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
