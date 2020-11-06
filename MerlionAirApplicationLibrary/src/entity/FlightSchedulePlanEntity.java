/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import util.enumeration.ScheduleEnum;

/**
 *
 * @author leahr
 */
@Entity
public class FlightSchedulePlanEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
     @OneToMany(mappedBy = "Plan")
    private List<FlightScheduleEntity> flightSchedule;
    ScheduleEnum schedule;
    @Temporal(javax.persistence.TemporalType.DATE)
    Date end;
    Integer n;
    @ManyToOne
    private FlightEntity flight;

    public FlightSchedulePlanEntity() {
    }

    public FlightSchedulePlanEntity(List<FlightScheduleEntity> flightSchedule, FlightEntity flight) {
        this.flightSchedule = flightSchedule;
        this.flight = flight;
    }

   

    public List<FlightScheduleEntity> getFlightSchedule() {
        return flightSchedule;
    }

    public void setFlightSchedule(List<FlightScheduleEntity> flightSchedule) {
        this.flightSchedule = flightSchedule;
    }

    public FlightEntity getFlight() {
        return flight;
    }

    public void setFlight(FlightEntity flight) {
        this.flight = flight;
    }

    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        if (!(object instanceof FlightSchedulePlanEntity)) {
            return false;
        }
        FlightSchedulePlanEntity other = (FlightSchedulePlanEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.FlightSchedulePlanEntity[ id=" + id + " ]";
    }
    
}
