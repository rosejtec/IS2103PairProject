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
    private Long flightId;
    private String flightNumber;
    @OneToMany(mappedBy = "flight")
    private List<FlightSchedulePlanEntity> flightSchedulePlans;
    @ManyToOne
    private FlightRouteEntity flightRoute;
    @OneToOne
    private AircraftConfigurationEntity aircraftConfiguration;
    private boolean disabled;

    public FlightEntity(String flightNumber, FlightRouteEntity flightRoute, AircraftConfigurationEntity aircraftConfiguration) {
        this.flightNumber = flightNumber;
        this.flightRoute = flightRoute;
        this.aircraftConfiguration = aircraftConfiguration;
    }

    public FlightEntity(List<FlightSchedulePlanEntity> flightSchedulePlans) {
        this.flightSchedulePlans = flightSchedulePlans;
    }

    public FlightEntity(String flightNumber, List<FlightSchedulePlanEntity> flightSchedulePlans, List<FlightRouteEntity> flightRoutes, AircraftConfigurationEntity aircraftConfiguration, boolean disabled) {
        this.flightNumber = flightNumber;
        this.flightSchedulePlans = flightSchedulePlans;
        this.flightRoute = flightRoute;
        this.aircraftConfiguration = aircraftConfiguration;
        this.disabled = disabled;
    }
    
    public Long getFlightId() {
        return flightId;
    }

    public void setFlightId(Long flightId) {
        this.flightId = flightId;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public List<FlightSchedulePlanEntity> getFlightSchedulePlans() {
        return flightSchedulePlans;
    }

    public void setFlightSchedulePlans(List<FlightSchedulePlanEntity> flightSchedulePlans) {
        this.flightSchedulePlans = flightSchedulePlans;
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
        hash += (flightId != null ? flightId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the flightId fields are not set
        if (!(object instanceof FlightEntity)) {
            return false;
        }
        FlightEntity other = (FlightEntity) object;
        if ((this.flightId == null && other.flightId != null) || (this.flightId != null && !this.flightId.equals(other.flightId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.FlightEntity[ id=" + flightId + " ]";
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }
    
}
