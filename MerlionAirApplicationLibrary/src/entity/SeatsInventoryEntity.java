/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 *
 * @author quahjingxin
 */
@Entity
public class SeatsInventoryEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seatsInventoryId;
    private Integer availableSeats;
    private Integer reservedSeats;
    private Integer balanceSeats;
    @OneToOne
    private FlightScheduleEntity flightSchedule;

    public SeatsInventoryEntity() {
    }

    public SeatsInventoryEntity(Long seatsInventoryId, Integer availableSeats, Integer reservedSeats, Integer balanceSeats) {
        this.seatsInventoryId = seatsInventoryId;
        this.availableSeats = availableSeats;
        this.reservedSeats = reservedSeats;
        this.balanceSeats = balanceSeats;
    }
    
    
    public Long getSeatsInventoryId() {
        return seatsInventoryId;
    }

    public void setSeatsInventoryId(Long seatsInventoryId) {
        this.seatsInventoryId = seatsInventoryId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (seatsInventoryId != null ? seatsInventoryId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the seatsInventoryId fields are not set
        if (!(object instanceof SeatsInventoryEntity)) {
            return false;
        }
        SeatsInventoryEntity other = (SeatsInventoryEntity) object;
        if ((this.seatsInventoryId == null && other.seatsInventoryId != null) || (this.seatsInventoryId != null && !this.seatsInventoryId.equals(other.seatsInventoryId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.SeatsInventoryEntity[ id=" + seatsInventoryId + " ]";
    }

    public Integer getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(Integer availableSeats) {
        this.availableSeats = availableSeats;
    }

    public Integer getReservedSeats() {
        return reservedSeats;
    }

    public void setReservedSeats(Integer reservedSeats) {
        this.reservedSeats = reservedSeats;
    }

    public Integer getBalanceSeats() {
        return balanceSeats;
    }

    public void setBalanceSeats(Integer balanceSeats) {
        this.balanceSeats = balanceSeats;
    }

    public FlightScheduleEntity getFlightSchedule() {
        return flightSchedule;
    }

    public void setFlightSchedule(FlightScheduleEntity flightSchedule) {
        this.flightSchedule = flightSchedule;
    }
    
}
