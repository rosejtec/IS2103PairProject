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
    //@ManyToOne
    private AirportEntity origin;
    //@ManyToOne
    private AirportEntity destination;
    @OneToMany
    private List<FlightEntity> flights;
    //@ManyToOne
    //private AirportEntity airport;
    //IS THIS CORRRECT
    @OneToOne
    private FlightRouteEntity complementaryReturnRoute;
    private boolean disabled;
    //how to do the loop thingy

    public FlightRouteEntity(AirportEntity origin, AirportEntity destination, List<FlightEntity> flightEntity, FlightRouteEntity complementaryRoute, boolean disabled) {
        this.origin = origin;
        this.destination = destination;
        this.flights = flightEntity;
        this.complementaryReturnRoute = complementaryRoute;
        this.disabled = disabled;
        
    }

    public FlightRouteEntity(AirportEntity origin, AirportEntity destination) {
        this.origin = origin;
        this.destination = destination;
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
    
    public List<FlightEntity> getFlights() {
        return flights;
    }

    public void setFlights(List<FlightEntity> flights) {
        this.flights = flights;
    }

    public FlightRouteEntity getComplementaryReturnRoute() {
        return complementaryReturnRoute;
    }

    public void setComplementaryReturnRoute(FlightRouteEntity complementaryReturnRoute) {
        this.complementaryReturnRoute = complementaryReturnRoute;
    }


    public void setDisabled(boolean disabled) {
        this.disabled = true;
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
