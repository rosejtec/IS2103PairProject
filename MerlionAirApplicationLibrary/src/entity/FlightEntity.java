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
 * @author leahr
 */
@Entity
public class FlightEntity implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String flightNum;
    @OneToMany(mappedBy = "flight")
    private List<FlightSchedulePlanEntity> scheduledFlights;

    @ManyToOne
    FlightRouteEntity flightRoute;
    
    @OneToOne
    AircraftConfigurationEntity  aircraftConfiguration;

    public FlightEntity(String flightNum, List<FlightSchedulePlanEntity> scheduledFlights, FlightRouteEntity flightRoute, AircraftConfigurationEntity aircraftConfiguration) {
        this.flightNum = flightNum;
        this.scheduledFlights = scheduledFlights;
        this.flightRoute = flightRoute;
        this.aircraftConfiguration = aircraftConfiguration;
    }

    public FlightEntity() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFlightNum() {
        return flightNum;
    }

    public void setFlightNum(String flightNum) {
        this.flightNum = flightNum;
    }

    public List<FlightSchedulePlanEntity> getScheduledFlights() {
        return scheduledFlights;
    }

    public void setScheduledFlights(List<FlightSchedulePlanEntity> scheduledFlights) {
        this.scheduledFlights = scheduledFlights;
    }

    public FlightRouteEntity getFlightRoute() {
        return flightRoute;
    }

    public void setFlightRoute(FlightRouteEntity flightRoute) {
        this.flightRoute = flightRoute;
    }

    public AircraftConfigurationEntity getAircraftConfiguration() {
        return aircraftConfiguration;
    }

    public void setAircraftConfiguration(AircraftConfigurationEntity aircraftConfiguration) {
        this.aircraftConfiguration = aircraftConfiguration;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FlightEntity)) {
            return false;
        }
        FlightEntity other = (FlightEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.FlightEntity[ id=" + id + " ]";
    }
    
}
