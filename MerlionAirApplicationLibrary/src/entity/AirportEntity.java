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
import javax.persistence.OneToMany;

/**
 *
 * @author quahjingxin
 */
@Entity
public class AirportEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long airportId;
    private String name;
    @Column(unique = true)
    private String code;
    private String city;
    private String state;
    private String country;
    private Integer timeZone;
    @OneToMany(mappedBy = "origin")
    private List<FlightRouteEntity> flightRoutesInbound;
    @OneToMany(mappedBy = "destination")
    private List<FlightRouteEntity> flightRoutesOutbound;

    public AirportEntity(String name, String code, String city, String state, String country, Integer timeZone) {
        this.name = name;
        this.code = code;
        this.city = city;
        this.state = state;
        this.country = country;
        this.timeZone = timeZone;
    }

    public AirportEntity() {
    }

    public List<FlightRouteEntity> getFlightRoutesInbound() {
        return flightRoutesInbound;
    }

    public void setFlightRoutesInbound(List<FlightRouteEntity> flightRoutesInbound) {
        this.flightRoutesInbound = flightRoutesInbound;
    }

    public List<FlightRouteEntity> getFlightRoutesOutbound() {
        return flightRoutesOutbound;
    }

    public void setFlightRoutesOutbound(List<FlightRouteEntity> flightRoutesOutbound) {
        this.flightRoutesOutbound = flightRoutesOutbound;
    }
    
    public Long getAirportId() {
        return airportId;
    }

    public void setAirportId(Long airportId) {
        this.airportId = airportId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (airportId != null ? airportId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the airportId fields are not set
        if (!(object instanceof AirportEntity)) {
            return false;
        }
        AirportEntity other = (AirportEntity) object;
        if ((this.airportId == null && other.airportId != null) || (this.airportId != null && !this.airportId.equals(other.airportId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.AirportEntity[ id=" + airportId + " ]";
    }

    public Integer getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(Integer timeZone) {
        this.timeZone = timeZone;
    }
    
}
