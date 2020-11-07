/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
    ScheduleEnum schedule;
  
    LocalDateTime end;
    Integer n;
    @ManyToOne
    private FlightEntity flight;
    private String flightNum;
    @OneToMany
    private List<FareEntity> fares;
    @OneToOne
    private FlightSchedulePlanEntity complementaryFsp;
    private boolean disabled;

    public FlightSchedulePlanEntity(String flightNum, List<FlightScheduleEntity> flightSchedules) {
        this.flightNum = flightNum;
        this.flightSchedules = flightSchedules;
    }

    public FlightSchedulePlanEntity(List<FlightScheduleEntity> flightSchedule, FlightEntity flight) {
        this.flightSchedules = flightSchedule;
        this.flight = flight;
    }

    public FlightSchedulePlanEntity() {   
    }

    public ScheduleEnum getSchedule() {
        return schedule;
    }

    public void setSchedule(ScheduleEnum schedule) {
        this.schedule = schedule;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public Integer getN() {
        return n;
    }

    public void setN(Integer n) {
        this.n = n;
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

    public FlightSchedulePlanEntity getComplementaryFlightSchedulePlan() {
        return complementaryFsp;
    }

    public void setComplementaryFsp(FlightSchedulePlanEntity complementaryFsp) {
        this.complementaryFsp = complementaryFsp;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }
    
}
