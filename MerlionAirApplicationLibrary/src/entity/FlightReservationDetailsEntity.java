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
public class FlightReservationDetailsEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long flightReservationDetailId;
    private String cabinClass;
    @OneToMany
    private List<FlightScheduleEntity> schedule;
    private List<List<String>> seatNum;

    public FlightReservationDetailsEntity() {
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
    
}