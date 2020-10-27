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
public class FlightReservationEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long flightReservationId;
    
    @OneToMany
    private List<FlightReservationDetailsEntity> inBound;
    @OneToMany
    private List<FlightReservationDetailsEntity> outBound;
    private Integer totalPassengers;
    @OneToMany
    private List<PassengerEntity> passenger;
    private Integer totalAmount;
    private boolean connecting;
    private boolean returnFlight;

    public FlightReservationEntity() {
    }
    
    
    
    public Long getFlightReservationId() {
        return flightReservationId;
    }

    public void setFlightReservationId(Long flightReservationId) {
        this.flightReservationId = flightReservationId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (flightReservationId != null ? flightReservationId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the flightReservationId fields are not set
        if (!(object instanceof FlightReservationEntity)) {
            return false;
        }
        FlightReservationEntity other = (FlightReservationEntity) object;
        if ((this.flightReservationId == null && other.flightReservationId != null) || (this.flightReservationId != null && !this.flightReservationId.equals(other.flightReservationId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.FlightReservationEntity[ id=" + flightReservationId + " ]";
    }
    
}
