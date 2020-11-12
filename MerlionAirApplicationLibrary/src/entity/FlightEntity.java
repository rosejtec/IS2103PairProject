/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.ArrayList;
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
public class FlightEntity implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long flightId;
    @Column(length = 6, nullable = false, unique = true)
    private String flightNum;
    @OneToMany(mappedBy = "flight")
    private List<FlightSchedulePlanEntity> flightSchedulePlans;    
    private boolean disabled;
    @ManyToOne
    FlightRouteEntity flightRoute;
    @Column(name="COMP")
    private boolean complementary;
    @OneToOne
    FlightEntity complentaryFlight;    
    @OneToOne
    AircraftConfigurationEntity  aircraftConfiguration;

    public FlightEntity(String flightNum, FlightRouteEntity flightRoute) {
        this.flightNum = flightNum;
        this.flightRoute = flightRoute;
        this.flightSchedulePlans = new ArrayList<>();
 
    }

    public FlightEntity(String flightNum, FlightRouteEntity flightRoute, AircraftConfigurationEntity aircraftConfiguration) {
        this.flightNum = flightNum;
        this.flightRoute = flightRoute;
        this.aircraftConfiguration = aircraftConfiguration;
    }

    
    
    public boolean isComplementary() {
        return complementary;
    }

    public void setComplementary(boolean complementary) {
        this.complementary = complementary;
    }

    public FlightEntity getComplementaryFlight() {
        return complentaryFlight;
    }

    public void setComplentaryFlight(FlightEntity complentaryFlight) {
        this.complentaryFlight = complentaryFlight;
    }

    public FlightEntity(String flightNum, FlightRouteEntity flightRoute, boolean isComplementary, AircraftConfigurationEntity aircraftConfiguration) {
        this.flightNum = flightNum;
        this.flightRoute = flightRoute;
        this.complementary = isComplementary;
        this.aircraftConfiguration = aircraftConfiguration;
    }
    
    

    public FlightEntity(String flightNum, boolean disabled, FlightRouteEntity flightRoute, AircraftConfigurationEntity aircraftConfiguration) {
        this.flightNum = flightNum;
        this.disabled = disabled;
        this.flightRoute = flightRoute;
        this.aircraftConfiguration = aircraftConfiguration;
        this.flightSchedulePlans = new ArrayList<>();
    }

    public FlightEntity() {
    }

    public FlightEntity(List<FlightSchedulePlanEntity> flightSchedulePlans) {
        this.flightSchedulePlans = flightSchedulePlans;
    }

    public FlightEntity(String flightNum, List<FlightSchedulePlanEntity> flightSchedulePlans, boolean disabled, FlightRouteEntity flightRoute, boolean complementary, FlightEntity complentaryFlight, AircraftConfigurationEntity aircraftConfiguration) {
        this.flightNum = flightNum;
        this.flightSchedulePlans = flightSchedulePlans;
        this.disabled = disabled;
        this.flightRoute = flightRoute;
        this.complementary = complementary;
        this.complentaryFlight = complentaryFlight;
        this.aircraftConfiguration = aircraftConfiguration;
    }
    
 
    
    public Long getFlightId() {
        return flightId;
    }

    public void setFlightId(Long flightId) {
        this.flightId = flightId;
    }

    public String getFlightNumber() {
        return flightNum;
    }

    public void setFlightNumber(String flightNum) {
        this.flightNum = flightNum;
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
