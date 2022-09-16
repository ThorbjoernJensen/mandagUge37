package facades;

import entities.dtos.CustomerDTO;
import entities.dtos.EmployeeDTO;
import entities.dtos.RenameMeDTO;
import entities.Customer;
import entities.Employee;
import entities.RenameMe;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.ws.rs.WebApplicationException;

//import errorhandling.RenameMeNotFoundException;


public class EmployeeFacade {

    private static EmployeeFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private EmployeeFacade() {
    }


    public static EmployeeFacade getFacadeInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new EmployeeFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public EmployeeDTO getEmployeeById(int id) {
        EntityManager em = getEntityManager();
        try {
            Employee e = em.find(Employee.class, id);


            if (e != null) {


                return new EmployeeDTO(e);
            } else {
                throw new WebApplicationException("employee doesnt exits!");
            }

        } finally {
            em.close();
        }

    }

    public List<EmployeeDTO> getEmployeesByName(String name) {

        EntityManager em = getEntityManager();
        try {
            TypedQuery<Employee> query = em.createQuery("Select e From Employee e Where e.name = :name", Employee.class);
            query.setParameter("name", name);

            return EmployeeDTO.getDTOList(query.getResultList());
        } finally {
            em.close();
        }
    }

    public List<EmployeeDTO> getEmployeesWithHighestSalery() {

        EntityManager em = getEntityManager();
        try {
            TypedQuery<Employee> query = em.createQuery(
                    "Select e From Employee e where " +
                            "e.salary = (Select max(e.salary) from Employee e)", Employee.class);
//            "Select e From Employee e where " +
//                            "e.salary >= (Select e.salary from Employee e)" , Employee.class);
            return EmployeeDTO.getDTOList(query.getResultList());
        } finally {
            em.close();
        }
    }

    public List<EmployeeDTO> getAllEmployees() {

        EntityManager em = getEntityManager();
        try {
            TypedQuery<Employee> query = em.createQuery("Select e From Employee e", Employee.class);
            return EmployeeDTO.getDTOList(query.getResultList());
        } finally {
            em.close();
        }
    }

    public Employee createEmployee(String name, int salery) {
        EntityManager em = getEntityManager();
        try {
            if (getEmployeesByName(name).size() > 0) {
                throw new WebApplicationException("Employee with name: " + name + " exists already.");
            }
            Employee newEmployee = new Employee(name, salery);
            em.getTransaction().begin();
            em.persist(newEmployee);
            em.getTransaction().commit();
            return newEmployee;

        } finally {
            em.close();
        }
    }

    public Employee addCustomer(int employeeId, String customerName) {
        EntityManager em = getEntityManager();

        try {
            Employee employee = em.find(Employee.class, employeeId);
            if (employee == null) {
                throw new WebApplicationException("employee doesnt exits!");
            }
            em.getTransaction().begin();
            Customer customer = new Customer(customerName);
            employee.addCustomer(customer);
            em.persist(customer);
//        merge?
            em.getTransaction().commit();
            return employee;
        } finally {

            em.close();
        }
    }

    public List<CustomerDTO> getCustomersByEmployeeId(int id) {
        EntityManager em = getEntityManager();
        TypedQuery<Customer> query = em.createQuery("SELECT c FROM Customer c JOIN c.employees e WHERE e.id = :id", Customer.class);
        query.setParameter("id", id);
        List<CustomerDTO> customerDTOs = CustomerDTO.getCustomerDTOs(query.getResultList());
        return customerDTOs;
    }

    public List<EmployeeDTO> getEmployeeByCustomerId(int id) {
        EntityManager em = getEntityManager();
        TypedQuery<Employee> query = em.createQuery("SELECT e FROM Employee e JOIN e.customers c WHERE c.id = :id", Employee.class);
        query.setParameter("id", id);
        List<EmployeeDTO> employeeDTOs = EmployeeDTO.getDTOList(query.getResultList());
        return employeeDTOs;

    }


//    public RenameMeDTO create(RenameMeDTO rm) {
//        RenameMe rme = new RenameMe(rm.getDummyStr1(), rm.getDummyStr2());
//        EntityManager em = getEntityManager();
//        try {
//            em.getTransaction().begin();
//            em.persist(rme);
//            em.getTransaction().commit();
//        } finally {
//            em.close();
//        }
//        return new RenameMeDTO(rme);
//    }
//
//    public RenameMeDTO getById(long id) { //throws RenameMeNotFoundException {
//        EntityManager em = emf.createEntityManager();
//        RenameMe rm = em.find(RenameMe.class, id);
////        if (rm == null)
////            throw new RenameMeNotFoundException("The RenameMe entity with ID: "+id+" Was not found");
//        return new RenameMeDTO(rm);
//    }
//
//    //TODO Remove/Change this before use
//    public long getRenameMeCount() {
//        EntityManager em = getEntityManager();
//        try {
//            long renameMeCount = (long) em.createQuery("SELECT COUNT(r) FROM RenameMe r").getSingleResult();
//            return renameMeCount;
//        } finally {
//            em.close();
//        }
//    }
//
//    public List<RenameMeDTO> getAll() {
//        EntityManager em = emf.createEntityManager();
//        TypedQuery<RenameMe> query = em.createQuery("SELECT r FROM RenameMe r", RenameMe.class);
//        List<RenameMe> rms = query.getResultList();
//        return RenameMeDTO.getDtos(rms);
//    }

}
