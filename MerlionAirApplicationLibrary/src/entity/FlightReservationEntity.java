/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author leahr
 */

@Entity
public class FlightReservationEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long flightReservationId;
    
    @OneToMany(mappedBy = "flightReservation")
    private List<FlightReservationDetailsEntity> inBound;
    
    @OneToMany(mappedBy = "flightReservation")
    private List<FlightReservationDetailsEntity> outBound;
    private Integer totalPassengers;
    @OneToMany
    private List<PassengerEntity> passenger;
   
    @ManyToOne
    FlightScheduleEntity scheduleEntity;
    
    @OneToOne
    private CreditCardEntity card;
    private Integer totalAmount;
    private boolean connecting;
    private boolean returnFlight;
    @ManyToOne
    private CustomerEntity customer;

    public FlightReservationEntity() {
    }

    public List<FlightReservationDetailsEntity> getInBound() {
        return inBound;
    }

    public CreditCardEntity getCard() {
        return card;
    }

    public void setCard(CreditCardEntity card) {
        this.card = card;
    }

    public void setInBound(List<FlightReservationDetailsEntity> inBound) {
        this.inBound = inBound;
    }

    public List<FlightReservationDetailsEntity> getOutBound() {
        return outBound;
    }

    public void setOutBound(List<FlightReservationDetailsEntity> outBound) {
        this.outBound = outBound;
    }

    public Integer getTotalPassengers() {
        return totalPassengers;
    }

    public void setTotalPassengers(Integer totalPassengers) {
        this.totalPassengers = totalPassengers;
    }

    public List<PassengerEntity> getPassenger() {
        return passenger;
    }

    public void setPassenger(List<PassengerEntity> passenger) {
        this.passenger = passenger;
    }

    public Integer getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
    }

    public boolean isConnecting() {
        return connecting;
    }

    public void setConnecting(boolean connecting) {
        this.connecting = connecting;
    }

    public boolean isReturnFlight() {
        return returnFlight;
    }

    public void setReturnFlight(boolean returnFlight) {
        this.returnFlight = returnFlight;
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

    public CustomerEntity getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerEntity customer) {
        this.customer = customer;
    }
    
}
