/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import util.enumeration.ScheduleEnum;

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
    @OneToMany(mappedBy = "flightSchedulePlan")
    private List<FlightScheduleEntity> flightSchedules;
    @Enumerated(EnumType.STRING)
    private ScheduleEnum schedule;
    @ManyToOne
    private FlightEntity flight;
    @OneToMany (mappedBy = "flightSchedulePlan")
    
    private List<FareEntity> fares;
    //@OneToOne
    //private FlightSchedulePlanEntity complementaryFsp;
    private boolean disabled;

    public FlightSchedulePlanEntity(FlightEntity flight) {
        this.flight = flight;
    }

    public FlightSchedulePlanEntity(List<FlightScheduleEntity> flightSchedules, FlightEntity flight, List<FareEntity> fares) {
        this.flightSchedules = flightSchedules;
        this.flight = flight;
        this.fares = fares;
    }

    public FlightSchedulePlanEntity() {   
    }

    public ScheduleEnum getSchedule() {
        return schedule;
    }

    public void setSchedule(ScheduleEnum schedule) {
        this.schedule = schedule;
    }

    public FlightSchedulePlanEntity(FlightEntity flight,ScheduleEnum schedule) {
        this.schedule = schedule;
        this.flight = flight;
    }

  
  

    public FlightEntity getFlight() {
        return flight;
    }

    public void setFlight(FlightEntity flight) {
        this.flight = flight;
    }

    
    public Long getFightSchedulePlanId() {
        return fightSchedulePlanId;
    }

    public void setFightSchedulePlanId(Long fightSchedulePlanId) {
        this.fightSchedulePlanId = fightSchedulePlanId;
    }


    public List<FlightScheduleEntity> getFlightSchedules() {
        return flightSchedules;
    }

    public void setFlightSchedules(List<FlightScheduleEntity> flightSchedules) {
        this.flightSchedules = flightSchedules;
    }

    public List<FareEntity> getFares() {
        return fares;
    }

    public void setFares(List<FareEntity> fares) {
        this.fares = fares;
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
/*
    public FlightSchedulePlanEntity getComplementaryFlightSchedulePlan() {
        return complementaryFsp;
    }

    public void setComplementaryFsp(FlightSchedulePlanEntity complementaryFsp) {
        this.complementaryFsp = complementaryFsp;
    }
*/
    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }
    
}
