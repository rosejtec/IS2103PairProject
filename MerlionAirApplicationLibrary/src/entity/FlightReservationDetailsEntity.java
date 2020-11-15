/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import util.enumeration.CabinClassType;

/**
 *
 * @author leahr
 */
@Entity
public class FlightReservationDetailsEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long flightReservationDetailsId;
  
    @OneToOne 
    private FlightScheduleEntity flightSchedule;
    @ManyToOne
    private FlightReservationEntity flightReservation;
    @OneToOne
    private List<SeatPassengeEntity> seats;
    public FlightReservationDetailsEntity() {
    }

    public List<SeatPassengeEntity> getSeats() {
        return seats;
    }

    public void setSeats(List<SeatPassengeEntity> seats) {
        this.seats = seats;
    }

 
    public FlightReservationEntity getFlightReservation() {
        return flightReservation;
    }

    public void setFlightReservation(FlightReservationEntity flightReservation) {
        this.flightReservation = flightReservation;
    }

    public FlightReservationDetailsEntity(FlightScheduleEntity flightSchedule) {
        this.flightSchedule = flightSchedule;
    }



    
    public Long getFlightReservationDetailsId() {
        return flightReservationDetailsId;
    }

    public void setFlightReservationDetailsId(Long flightReservationDetailsId) {
        this.flightReservationDetailsId = flightReservationDetailsId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (flightReservationDetailsId != null ? flightReservationDetailsId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the flightReservationDetailsId fields are not set
        if (!(object instanceof FlightReservationDetailsEntity)) {
            return false;
        }
        FlightReservationDetailsEntity other = (FlightReservationDetailsEntity) object;
        if ((this.flightReservationDetailsId == null && other.flightReservationDetailsId != null) || (this.flightReservationDetailsId != null && !this.flightReservationDetailsId.equals(other.flightReservationDetailsId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.FlightReservationDetailsEntity[ id=" + flightReservationDetailsId + " ]";
    }

    

    public FlightScheduleEntity getFlightSchedule() {
        return flightSchedule;
    }

    public void setFlightSchedule(FlightScheduleEntity flightSchedule) {
        this.flightSchedule = flightSchedule;
    }
    
}
