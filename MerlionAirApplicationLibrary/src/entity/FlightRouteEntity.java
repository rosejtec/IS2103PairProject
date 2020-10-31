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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author quahjingxin
 */
@Entity
public class FlightRouteEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long flightRouteId;
    private AirportEntity origin;
    private AirportEntity destination;
    
    @OneToMany(mappedBy = "flightRoute")
    private List<FlightEntity> flightEntity;
    //@ManyToOne
    
    @OneToOne(mappedBy = "complemntary")
    FlightRouteEntity complemntary;
    //how to do the loop thingy

    public FlightRouteEntity(AirportEntity origin, AirportEntity destination, List<FlightEntity> flightEntity,  FlightRouteEntity complemntary) {
        this.origin = origin;
        this.destination = destination;
        this.flightEntity = flightEntity;
        this.complemntary = complemntary;
    }

    public Long getFlightRouteId() {
        return flightRouteId;
    }

    public void setFlightRouteId(Long flightRouteId) {
        this.flightRouteId = flightRouteId;
    }

    public AirportEntity getOrigin() {
        return origin;
    }

    public void setOrigin(AirportEntity origin) {
        this.origin = origin;
    }

    public AirportEntity getDestination() {
        return destination;
    }

    public void setDestination(AirportEntity destination) {
        this.destination = destination;
    }

    public List<FlightEntity> getFlightEntity() {
        return flightEntity;
    }

    public void setFlightEntity(List<FlightEntity> flightEntity) {
        this.flightEntity = flightEntity;
    }

    public FlightRouteEntity getComplemntary() {
        return complemntary;
    }

    public void setComplemntary(FlightRouteEntity complemntary) {
        this.complemntary = complemntary;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (flightRouteId != null ? flightRouteId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the flightRouteId fields are not set
        if (!(object instanceof FlightRouteEntity)) {
            return false;
        }
        FlightRouteEntity other = (FlightRouteEntity) object;
        if ((this.flightRouteId == null && other.flightRouteId != null) || (this.flightRouteId != null && !this.flightRouteId.equals(other.flightRouteId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.FlightRouteEntity[ id=" + flightRouteId + " ]";
    }
    
}
