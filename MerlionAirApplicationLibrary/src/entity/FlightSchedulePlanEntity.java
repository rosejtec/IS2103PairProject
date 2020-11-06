/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author leahr
 */
@Entity
public class FlightSchedulePlanEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fightSchedulePlanId;
    private String flightNum;
    @OneToMany
    private List<FlightScheduleEntity> flightSchedules;
    @OneToMany
    private List<FareEntity> fare;

    public FlightSchedulePlanEntity(String flightNum, List<FlightScheduleEntity> flightSchedules) {
        this.flightNum = flightNum;
        this.flightSchedules = flightSchedules;
    }

    public FlightSchedulePlanEntity(List<FareEntity> fare) {
        this.fare = fare;
    }
    
    public Long getFightSchedulePlanId() {
        return fightSchedulePlanId;
    }

    public void setFightSchedulePlanId(Long fightSchedulePlanId) {
        this.fightSchedulePlanId = fightSchedulePlanId;
    }

    public String getFlightNum() {
        return flightNum;
    }

    public void setFlightNum(String flightNum) {
        this.flightNum = flightNum;
    }

    public List<FlightScheduleEntity> getFlightSchedules() {
        return flightSchedules;
    }

    public void setFlightSchedules(List<FlightScheduleEntity> flightSchedules) {
        this.flightSchedules = flightSchedules;
    }

    public List<FareEntity> getFare() {
        return fare;
    }

    public void setFare(List<FareEntity> fare) {
        this.fare = fare;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (fightSchedulePlanId != null ? fightSchedulePlanId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the fightSchedulePlanId fields are not set
        if (!(object instanceof FlightSchedulePlanEntity)) {
            return false;
        }
        FlightSchedulePlanEntity other = (FlightSchedulePlanEntity) object;
        if ((this.fightSchedulePlanId == null && other.fightSchedulePlanId != null) || (this.fightSchedulePlanId != null && !this.fightSchedulePlanId.equals(other.fightSchedulePlanId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.FlightSchedulePlanEntity[ id=" + fightSchedulePlanId + " ]";
    }
    
}
