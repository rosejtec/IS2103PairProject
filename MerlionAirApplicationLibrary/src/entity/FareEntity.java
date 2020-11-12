/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import util.enumeration.CabinClassType;

/**
 *
 * @author quahjingxin
 */
@Entity
public class FareEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fareId;
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private CabinClassConfigurationEntity cabinClassConfiguration;
    @Column(length = 5, nullable = false)
    private String fareBasisCode;
    @Column(length = 7, nullable = false)
    private String fareAmount;
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private FlightSchedulePlanEntity flightSchedulePlan;
    CabinClassType type;
    
    public FareEntity() {
    }

    public FareEntity(CabinClassConfigurationEntity cabinClassConfiguration, String fareBasisCode, String fareAmount) {
        this.cabinClassConfiguration = cabinClassConfiguration;
        this.fareBasisCode = fareBasisCode;
        this.fareAmount = fareAmount;
    }

    public FareEntity( CabinClassType type,String fareBasisCode, String fareAmount) {
        this.fareBasisCode = fareBasisCode;
        this.fareAmount = fareAmount;
        this.type = type;
    }

    
    public Long getFareId() {
        return fareId;
    }

    public void setFareId(Long fareId) {
        this.fareId = fareId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (fareId != null ? fareId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the fareId fields are not set
        if (!(object instanceof FareEntity)) {
            return false;
        }
        FareEntity other = (FareEntity) object;
        if ((this.fareId == null && other.fareId != null) || (this.fareId != null && !this.fareId.equals(other.fareId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.FareEntity[ id=" + fareId + " ]";
    }

    public CabinClassConfigurationEntity getCabinClassConfiguration() {
        return cabinClassConfiguration;
    }

    public void setCabinClassConfiguration(CabinClassConfigurationEntity cabinClassConfiguration) {
        this.cabinClassConfiguration = cabinClassConfiguration;
    }

    public String getFareBasisCode() {
        return fareBasisCode;
    }

    public void setFareBasisCode(String fareBasisCode) {
        this.fareBasisCode = fareBasisCode;
    }

    public String getFareAmount() {
        return fareAmount;
    }

    public void setFareAmount(String fareAmount) {
        this.fareAmount = fareAmount;
    }

    public FlightSchedulePlanEntity getFlightSchedulePlan() {
        return flightSchedulePlan;
    }

    public void setFlightSchedulePlan(FlightSchedulePlanEntity flightSchedulePlan) {
        this.flightSchedulePlan = flightSchedulePlan;
    }
    
}
