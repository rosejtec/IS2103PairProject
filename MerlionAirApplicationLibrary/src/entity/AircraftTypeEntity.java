/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author quahjingxin
 */
@Entity
public class AircraftTypeEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long aircraftTypeId;
    private String name;
    private Integer maxCapacity; 

    public AircraftTypeEntity(String name, Integer maxCapacity) {
        this.name = name;
        this.maxCapacity = maxCapacity;
    }

    public AircraftTypeEntity() {
    }

    
    public Long getAircraftTypeId() {
        return aircraftTypeId;
    }

    public void setAircraftTypeId(Long aircraftTypeId) {
        this.aircraftTypeId = aircraftTypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(Integer maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (aircraftTypeId != null ? aircraftTypeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the aircraftTypeId fields are not set
        if (!(object instanceof AircraftTypeEntity)) {
            return false;
        }
        AircraftTypeEntity other = (AircraftTypeEntity) object;
        if ((this.aircraftTypeId == null && other.aircraftTypeId != null) || (this.aircraftTypeId != null && !this.aircraftTypeId.equals(other.aircraftTypeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.AircraftTypeEntity[ id=" + aircraftTypeId + " ]";
    }
    
}
