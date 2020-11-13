/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author leahr
 */
@Entity
public class FlightScheduleEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long flightScheduleId;
    @Column(nullable = false)
    private LocalDateTime departure;
    @Column(nullable = false)
    private LocalDateTime arrival;
    @Column(nullable = false)
    private Integer duration;
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private FlightSchedulePlanEntity flightSchedulePlan;
   
    
    @OneToMany(mappedBy = "scheduleEntity")
    List<FlightReservationEntity> flightReservations;
    
    @OneToOne (mappedBy = "flightSchedule")
    private SeatsInventoryEntity seatsInventory;
 

    public FlightScheduleEntity() {
    }

    public FlightScheduleEntity(LocalDateTime departure, Integer duration) {
        this.departure = departure;
        this.duration = duration;
    }

    
    public FlightScheduleEntity(LocalDateTime departure, LocalDateTime arrival, Integer duration) {
        this.departure = departure;
        this.arrival = arrival;
        this.duration = duration;
        
    }

    public LocalDateTime getDeparture() {
        return departure;
    }

    public void setDeparture(LocalDateTime departure) {
        this.departure = departure;
    }

    public LocalDateTime getArrival() {
        return arrival;
    }

    public void setArrival(LocalDateTime arrival) {
        this.arrival = arrival;
    }

   
    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public FlightScheduleEntity(FlightSchedulePlanEntity flightSchedulePlan) {
        this.flightSchedulePlan = flightSchedulePlan;
    }
    
    public Long getFlightScheduleId() {
        return flightScheduleId;
    }

    public void setFlightScheduleId(Long flightScheduleId) {
        this.flightScheduleId = flightScheduleId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (flightScheduleId != null ? flightScheduleId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the flightScheduleId fields are not set
        if (!(object instanceof FlightScheduleEntity)) {
            return false;
        }
        FlightScheduleEntity other = (FlightScheduleEntity) object;
        if ((this.flightScheduleId == null && other.flightScheduleId != null) || (this.flightScheduleId != null && !this.flightScheduleId.equals(other.flightScheduleId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.FlightScheduleEntity[ id=" + flightScheduleId + " ]";
    }

   
    public FlightSchedulePlanEntity getFlightSchedulePlan() {
        return flightSchedulePlan;
    }

    public void setFlightSchedulePlan(FlightSchedulePlanEntity flightSchedulePlan) {
        this.flightSchedulePlan = flightSchedulePlan;
    }

    public SeatsInventoryEntity getSeatsInventory() {
        return seatsInventory;
    }

    public void setSeatsInventory(SeatsInventoryEntity seatsInventory) {
        this.seatsInventory = seatsInventory;
    }

    public List<FlightReservationEntity> getFlightReservations() {
        return flightReservations;
    }

    public void setFlightReservations(List<FlightReservationEntity> flightReservations) {
        this.flightReservations = flightReservations;
    }


    
}
