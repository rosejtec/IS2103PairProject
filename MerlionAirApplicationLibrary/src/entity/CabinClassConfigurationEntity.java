/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
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
    private Integer numOfAisles;
    private Integer numOfRoles;
    private Integer numOfSeatsAbreast;
    private String seatConfiguration;
    @ManyToOne
    private AircraftConfigurationEntity aircraftConfigurationEntity;
    @Enumerated(EnumType.STRING)
    private CabinClassType cabinClassType;

    public CabinClassConfigurationEntity(Integer numOfAisles, Integer numOfRoles, Integer numOfSeatsAbreast, String seatConfiguration, AircraftConfigurationEntity aircraftConfigurationEntity, CabinClassType cabinClassType) {
        this.numOfAisles = numOfAisles;
        this.numOfRoles = numOfRoles;
        this.numOfSeatsAbreast = numOfSeatsAbreast;
        this.seatConfiguration = seatConfiguration;
        this.aircraftConfigurationEntity = aircraftConfigurationEntity;
        this.cabinClassType = cabinClassType;
    }

    public CabinClassConfigurationEntity() {
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

    public Integer getNumOfRoles() {
        return numOfRoles;
    }

    public void setNumOfRoles(Integer numOfRoles) {
        this.numOfRoles = numOfRoles;
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

    public AircraftConfigurationEntity getAircraftConfigurationEntity() {
        return aircraftConfigurationEntity;
    }

    public void setAircraftConfigurationEntity(AircraftConfigurationEntity aircraftConfigurationEntity) {
        this.aircraftConfigurationEntity = aircraftConfigurationEntity;
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
    
}
