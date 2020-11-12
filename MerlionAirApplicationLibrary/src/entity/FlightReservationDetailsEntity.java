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
    private Long flightReservationDetailId;
    @Enumerated(EnumType.STRING)
    private CabinClassType cabinClass;
    @OneToOne 
    private FlightScheduleEntity schedule;
    private List<String> seatNum;
    @ManyToOne
    private FlightReservationEntity reservation;

    public FlightReservationDetailsEntity() {
    }

    public FlightReservationDetailsEntity(CabinClassType cabinClass, FlightScheduleEntity schedule) {
        this.cabinClass = cabinClass;
        this.schedule = schedule;
    }

    public FlightReservationEntity getReservation() {
        return reservation;
    }

    public void setReservation(FlightReservationEntity reservation) {
        this.reservation = reservation;
    }



    
    public Long getFlightReservationDetailId() {
        return flightReservationDetailId;
    }

    public void setFlightReservationDetailId(Long flightReservationDetailId) {
        this.flightReservationDetailId = flightReservationDetailId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (flightReservationDetailId != null ? flightReservationDetailId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the flightReservationDetailId fields are not set
        if (!(object instanceof FlightReservationDetailsEntity)) {
            return false;
        }
        FlightReservationDetailsEntity other = (FlightReservationDetailsEntity) object;
        if ((this.flightReservationDetailId == null && other.flightReservationDetailId != null) || (this.flightReservationDetailId != null && !this.flightReservationDetailId.equals(other.flightReservationDetailId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.FlightReservationDetailsEntity[ id=" + flightReservationDetailId + " ]";
    }

    public CabinClassType getCabinClass() {
        return cabinClass;
    }

    public void setCabinClass(CabinClassType cabinClass) {
        this.cabinClass = cabinClass;
    }

    public FlightScheduleEntity getSchedule() {
        return schedule;
    }

    public void setSchedule(FlightScheduleEntity schedule) {
        this.schedule = schedule;
    }

    public List<String> getSeatNum() {
        return seatNum;
    }

    public void setSeatNum(List<String> seatNum) {
        this.seatNum = seatNum;
    }
    
}
