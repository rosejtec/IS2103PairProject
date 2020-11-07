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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author quahjingxin
 */
@Entity
public class AircraftConfigurationEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long aircraftConfigurationId;
    private String name;
    private Integer numOfCabinClass;
    @ManyToOne
    private AircraftTypeEntity aircraftType;
    @OneToOne
    private FlightEntity flightEntity;
    @OneToMany
    private List<CabinClassConfigurationEntity> cabinClassConfigurations;

    public AircraftConfigurationEntity() {
    }

    
    
    
    public AircraftConfigurationEntity(String name, Integer numOfCabinClass, AircraftTypeEntity aircraftTypeEntity, List<CabinClassConfigurationEntity> cabinClassConfigurationEntity) {
        this.name = name;
        this.numOfCabinClass = numOfCabinClass;
        this.cabinClassConfigurations = cabinClassConfigurations;
    }
    
    public Long getAircraftConfigurationId() {
        return aircraftConfigurationId;
    }

    public void setAircraftConfigurationId(Long aircraftConfigurationId) {
        this.aircraftConfigurationId = aircraftConfigurationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumOfCabinClass() {
        return numOfCabinClass;
    }

    public void setNumOfCabinClass(Integer numOfCabinClass) {
        this.numOfCabinClass = numOfCabinClass;
    }

    public AircraftTypeEntity getAircraftType() {
        return aircraftType;
    }

    public void setAircraftType(AircraftTypeEntity aircraftType) {
        this.aircraftType = aircraftType;
    }

    public List<CabinClassConfigurationEntity> getCabinClassConfigurations() {
        return cabinClassConfigurations;
    }

    public void setCabinClassConfigurations(List<CabinClassConfigurationEntity> cabinClassConfigurations) {
        this.cabinClassConfigurations = cabinClassConfigurations;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (aircraftConfigurationId != null ? aircraftConfigurationId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the aircraftConfigurationId fields are not set
        if (!(object instanceof AircraftConfigurationEntity)) {
            return false;
        }
        AircraftConfigurationEntity other = (AircraftConfigurationEntity) object;
        if ((this.aircraftConfigurationId == null && other.aircraftConfigurationId != null) || (this.aircraftConfigurationId != null && !this.aircraftConfigurationId.equals(other.aircraftConfigurationId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.AircraftConfigurationEntity[ id=" + aircraftConfigurationId + " ]";
    }
    
}
