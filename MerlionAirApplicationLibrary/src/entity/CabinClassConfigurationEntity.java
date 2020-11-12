/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import util.enumeration.CabinClassType;

/**
 *
 * @author quahjingxin
 */
@Entity
public class CabinClassConfigurationEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cabinClassConfigurationId;
    @Column(nullable = false)
    private Integer numOfAisles;
    @Column(nullable = false)
    private Integer numOfRows;
    @Column(nullable = false)
    private Integer numOfSeatsAbreast;
    @Column(length = 5, nullable = false)
    private String seatConfiguration;
    @Column(nullable = false)
    private Integer maximum;
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private AircraftConfigurationEntity aircraftConfiguration;
    @Enumerated(EnumType.STRING)
    private CabinClassType cabinClassType;
    @OneToMany(mappedBy = "cabinClassConfiguration")
    private List<FareEntity> fares;

    public CabinClassConfigurationEntity() {
    }

    public CabinClassConfigurationEntity( CabinClassType cabinClassType,Integer numOfAisles, Integer numOfRoles, Integer numOfSeatsAbreast, String seatConfiguration,Integer maximum) {
        this.numOfAisles = numOfAisles;
        this.numOfRows = numOfRoles;
        this.numOfSeatsAbreast = numOfSeatsAbreast;
        this.seatConfiguration = seatConfiguration;
        this.cabinClassType = cabinClassType;
        this.maximum = maximum;
    }



    public Integer getMaximum() {
        return maximum;
    }

    public void setMaximum(Integer maximum) {
        this.maximum = maximum;
    }
    
    public Long getCabinClassConfigurationId() {
        return cabinClassConfigurationId;
    }

    public void setCabinClassConfigurationId(Long cabinClassConfigurationId) {
        this.cabinClassConfigurationId = cabinClassConfigurationId;
    }

    public Integer getNumOfAisles() {
        return numOfAisles;
    }

    public void setNumOfAisles(Integer numOfAisles) {
        this.numOfAisles = numOfAisles;
    }

    public Integer getNumOfRows() {
        return numOfRows;
    }

    public void setNumOfRows(Integer numOfRows) {
        this.numOfRows = numOfRows;
    }

    public Integer getNumOfSeatsAbreast() {
        return numOfSeatsAbreast;
    }

    public void setNumOfSeatsAbreast(Integer numOfSeatsAbreast) {
        this.numOfSeatsAbreast = numOfSeatsAbreast;
    }

    public String getSeatConfiguration() {
        return seatConfiguration;
    }

    public void setSeatConfiguration(String seatConfiguration) {
        this.seatConfiguration = seatConfiguration;
    }

    public AircraftConfigurationEntity getAircraftConfiguration() {
        return aircraftConfiguration;
    }

    public void setAircraftConfiguration(AircraftConfigurationEntity aircraftConfiguration) {
        this.aircraftConfiguration = aircraftConfiguration;
    }

    public CabinClassType getCabinClassType() {
        return cabinClassType;
    }

    public void setCabinClassType(CabinClassType cabinClassType) {
        this.cabinClassType = cabinClassType;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cabinClassConfigurationId != null ? cabinClassConfigurationId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the cabinClassConfigurationId fields are not set
        if (!(object instanceof CabinClassConfigurationEntity)) {
            return false;
        }
        CabinClassConfigurationEntity other = (CabinClassConfigurationEntity) object;
        if ((this.cabinClassConfigurationId == null && other.cabinClassConfigurationId != null) || (this.cabinClassConfigurationId != null && !this.cabinClassConfigurationId.equals(other.cabinClassConfigurationId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.CabinClassConfigurationEntity[ id=" + cabinClassConfigurationId + " ]";
    }

    public List<FareEntity> getFares() {
        return fares;
    }

    public void setFares(List<FareEntity> fares) {
        this.fares = fares;
    }
    
}
