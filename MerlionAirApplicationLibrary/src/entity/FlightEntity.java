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
    private String flightNum;
    @OneToMany(mappedBy = "flight")
    private List<FlightSchedulePlanEntity> flightSchedulePlans;
    private boolean disabled;

    @ManyToOne
    FlightRouteEntity flightRoute;
   
    boolean isComplemntary;
    
    @OneToOne
    FlightEntity complentary;
    
    @OneToOne
    AircraftConfigurationEntity  aircraftConfiguration;

    public FlightEntity(String flightNum, FlightRouteEntity flightRoute) {
        this.flightNum = flightNum;
        this.flightRoute = flightRoute;
 
    }

    public boolean isIsComplemntary() {
        return isComplemntary;
    }

    public void setIsComplemntary(boolean isComplemntary) {
        this.isComplemntary = isComplemntary;
    }

    public FlightEntity getComplentary() {
        return complentary;
    }

    public void setComplentary(FlightEntity complentary) {
        this.complentary = complentary;
    }

    public FlightEntity(String flightNum, FlightRouteEntity flightRoute, boolean isComplemntary, AircraftConfigurationEntity aircraftConfiguration) {
        this.flightNum = flightNum;
        this.flightRoute = flightRoute;
        this.isComplemntary = isComplemntary;
        this.aircraftConfiguration = aircraftConfiguration;
    }
    
    

    public FlightEntity(String flightNum, boolean disabled, FlightRouteEntity flightRoute, AircraftConfigurationEntity aircraftConfiguration) {
        this.flightNum = flightNum;
        this.disabled = disabled;
        this.flightRoute = flightRoute;
        this.aircraftConfiguration = aircraftConfiguration;
    }

    public FlightEntity() {
    }

    public FlightEntity(List<FlightSchedulePlanEntity> flightSchedulePlans) {
        this.flightSchedulePlans = flightSchedulePlans;
    }

    public FlightEntity(String flightNumber, List<FlightSchedulePlanEntity> flightSchedulePlans, List<FlightRouteEntity> flightRoutes, AircraftConfigurationEntity aircraftConfiguration, boolean disabled) {
        this.flightNum = flightNum;
        this.flightSchedulePlans = flightSchedulePlans;
        this.flightRoute = flightRoute;
        this.aircraftConfiguration = aircraftConfiguration;
        this.disabled = disabled;
    }

    public FlightEntity(String concat, boolean b, Long flightRoute, Long aircraftConfiguration) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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

    public String getFlightNum() {
        return flightNum;
    }

    public void setFlightNum(String flightNum) {
        this.flightNum = flightNum;
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
