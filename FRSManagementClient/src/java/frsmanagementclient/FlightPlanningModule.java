/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frsmanagementclient;

import entity.AircraftConfigurationEntity;
import entity.CabinClassConfigurationEntity;
import entity.EmployeeEntity;
import java.util.Scanner;
import util.enumeration.EmployeeAccessRight;
import util.exception.InvalidAccessRightException;

/**
 *
 * @author quahjingxin
 */
public class FlightPlanningModule {

    //call all the session beans
    private EmployeeEntity currentEmployeeEntity;
    
    public FlightPlanningModule() 
    {
    }
     //insert constructor
    public void menuFlightPlanning() throws InvalidAccessRightException
    {
        if(currentEmployeeEntity.getAccessRight() != EmployeeAccessRight.FLEETMANAGER || currentEmployeeEntity.getAccessRight() != EmployeeAccessRight.ROUTEPLANNER)
        {
            throw new InvalidAccessRightException("You don't have FLEET MANAGER or ROUTE PLANNER rights to access the flight planning module.");
        }    
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;
        
        while(true)
        {
            System.out.println("*** FRS Management Client :: Flight Planning ***\n");
            System.out.println("1: Create Aircraft Configuration");
            System.out.println("2: View All Aircraft Configurations");
            System.out.println("3: View Aircraft Configuration Details");
            System.out.println("4: Create Flight Route");
            System.out.println("5: View All Flight Routes");
            System.out.println("6: Delete Fight Route");
            System.out.println("7: Back\n");
            response = 0;
            
            while(response < 1 || response > 6)
            {
                System.out.print("> ");

                response = scanner.nextInt();

                if(response == 1)
                {
                  //  doCreateAircraftConfiguration();
                }
                /*
                else if(response == 2)
                {
                    doViewAllAircraftConfigurations();
                }
                else if(response == 3)
                {
                    doViewAircraftConfigurationDetails();
                }
                else if(response == 4)
                {
                    doCreateFlightRoute();
                }
                else if(response == 5)
                {
                    doViewAllFlightRoutes();
                }
                else if(response == 6)
                {
                    doDeleteFightRoute();
                }
                else if(response == 7)
                {
                    break;
                }
                else
                {
                    System.out.println("Invalid option, please try again!\n");                
                }
            }
            
            if(response == 7)
            {
                break;
            }
*/
        }
    }
   /* 
    public void doCreateAircraftConfiguration()
    {
        Scanner scanner = new Scanner(System.in);
        AircraftConfigurationEntity newAircraftConfiguration = new AircraftConfigurationEntity();
        
        System.out.println("\"*** FRS Management Client :: Flight Planning :: Create New Aircraft Configuration ***\n");
        System.out.print("Enter Name> ");
        newAircraftConfiguration.setName(scanner.nextLine().trim());
        System.out.print("Enter Number Of Cabin Classes (1 to 4) > ");
        Integer numOfCabinClasses = scanner.nextInt();
        newAircraftConfiguration.setNumOfCabinClass(numOfCabinClasses);
        
        while (numOfCabinClasses >= 1) {
            CabinClassConfigurationEntity newCabinClassConfiguration = new CabinClassConfigurationEntity();
             System.out.print("Enter Name> ");
            
        }
        */
        
    }
}
