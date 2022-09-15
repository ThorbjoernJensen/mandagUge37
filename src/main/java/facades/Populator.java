/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import javax.persistence.EntityManagerFactory;
import utils.EMF_Creator;

/**
 *
 * @author tha
 */
public class Populator {
    public static void populate(){
        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
        FacadeExample fe = FacadeExample.getFacadeExample(emf);
//        fe.create(new RenameMeDTO(new RenameMe("First 1", "Last 1")));
//        fe.create(new RenameMeDTO(new RenameMe("First 2", "Last 2")));
//        fe.create(new RenameMeDTO(new RenameMe("First 3", "Last 3")));

        EmployeeFacade employeeFacade = EmployeeFacade.getFacadeInstance(emf);
//        Employee e1 = employeeFacade.createEmployee("John", 43000);
//        employeeFacade.createEmployee("Fætter Højben", 47000);
//        employeeFacade.createEmployee("Fætter Vims", 5000);

        employeeFacade.addCustomer(1, "Poul Ole");
    }
    
    public static void main(String[] args) {
        populate();
    }
}
