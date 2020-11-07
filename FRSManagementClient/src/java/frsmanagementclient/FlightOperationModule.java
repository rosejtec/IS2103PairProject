/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frsmanagementclient;

import ejb.session.stateless.FlightSchedulePlanSessionBeanRemote;
import ejb.session.stateless.FlightSessionBeanRemote;
import entity.FlightEntity;
import entity.FlightScheduleEntity;
import entity.FlightSchedulePlanEntity;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import util.enumeration.ScheduleEnum;
import util.exception.FlightNotFoundException;

/**
 *
 * @author quahjingxin
 */
public class FlightOperationModule {
    
    @EJB
    private static FlightSessionBeanRemote flightSessionBeanRemote;
    
    @EJB
    private static FlightSchedulePlanSessionBeanRemote flightSchedulePlanSessionBeanRemote;
    
    public void createFlightSchedulePlan() {
        Scanner sc = new Scanner(System.in);
        FlightSchedulePlanEntity fsp = new FlightSchedulePlanEntity();
        System.out.println(" Select type of FlightScedulePlan");
        System.out.println("1. SINGLE, 2. MULTIPLE, 3. RECURRENT DAY, 4. RECURRENT WEEK");
        int type= sc.nextInt();
        
        List<FlightScheduleEntity> list = new ArrayList<FlightScheduleEntity>(); 
  
  
        if(type==1) {
            fsp.setSchedule(ScheduleEnum.SINGLE);
             System.out.println("Enter local depature date >");
          LocalDateTime d =LocalDateTime.of(2017, 1, 14, 10, 34);
           System.out.println("Enter estimated flight duration >");
         int duration = sc.nextInt();
            list.add(new FlightScheduleEntity(d,duration));
            
        } else if( type==2) {
            fsp.setSchedule(ScheduleEnum.MULTIPLE);
            System.out.println("Enter the number of schedules");
            int n = sc.nextInt();
            
            for(int i=0 ; i < n; i++){
                 System.out.println("Enter local depature date >");
         LocalDateTime d =LocalDateTime.of(2017, 1, 14, 10, 34);
           System.out.println("Enter estimated flight duration >");
         int duration = sc.nextInt();
         list.add(new FlightScheduleEntity(d,duration));
            }
            
        } else if( type ==3){
            fsp.setSchedule(ScheduleEnum.RECURRENTDAY);
              System.out.println("Enter every n days");
            int n = sc.nextInt();
            System.out.println("Enter end date >");
         LocalDateTime d =LocalDateTime.of(2017, 1, 14, 10, 34);
           System.out.println("Enter duration");
         int duration = sc.nextInt();
        fsp.setEnd(d);
         fsp.setN(n);
         for(int i =0 ;i<n;i++){
            list.add(new FlightScheduleEntity(d,duration));
             d = d.plusDays(n);
         }
        } else {
              
     fsp.setSchedule(ScheduleEnum.RECURRENTWEEK);
            System.out.println("Enter end date >");
         LocalDateTime d =LocalDateTime.of(2017, 1, 14, 10, 34);
           System.out.println("Enter duration");
         int duration = sc.nextInt();
         LocalDateTime end= d;
        
         fsp.setEnd(end);
         while (d.isBefore(end) || d.isEqual(end)){
            list.add(new FlightScheduleEntity(d,duration));
             d = d.plusWeeks(1);
         }
       }
        
        System.out.println("Enter flight num");
        String flightNum = sc.nextLine().trim();
        
        FlightEntity newFlightEntity;
        try {
            newFlightEntity = flightSessionBeanRemote.retrieveFlightByFlightNumber(flightNum);
            fsp.setFlight(newFlightEntity);
        } catch (FlightNotFoundException ex) {
            Logger.getLogger(FlightOperationModule.class.getName()).log(Level.SEVERE, null, ex);
        }
        
       flightSchedulePlanSessionBeanRemote.createFlightSchedulePlan(fsp, list);
    }

   

    
    
}
