package facades;

import entities.Employee;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.*;

class EmployeeFacadeTest {
    private static EntityManagerFactory emf;
    private static EmployeeFacade facade;
    private Employee e1,e2,e3;


    @BeforeAll
            public static void setUpClass()
//    void setUp()
    {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = EmployeeFacade.getFacadeInstance(emf);
    }

    @BeforeEach
    void setUp() {

        facade = EmployeeFacade.getFacadeInstance(emf);
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Query query = em.createQuery("delete from Employee e");
        query.executeUpdate();
        em.getTransaction().commit();
        em.close();
        e1= facade.createEmployee("John", 43000);
        e2= facade.createEmployee("Fætter Højben", 47000);
        e3=facade.createEmployee("Fætter Vims", 5000);
    }

    @Test
    void getEmployee() {
        Employee actual = facade.getEmployeeById(e1.getId());
        Employee expected = e1;
        assertEquals(expected, actual);

    }

    @Test
    void getEmployeesByName() {
        List<Employee> actual = facade.getEmployeesByName("John");
        assertEquals(1, actual.size());
        assertEquals(e1, actual.get(0));

    }


    @Test
    void getAllEmployees() {
        List<Employee> actual = facade.getAllEmployees();
        assertEquals(3, actual.size());
        assertThat(actual, containsInAnyOrder(e1,e2,e3));
    }

    @Test
    void getEmployeesWithHighestSalery() {
        List<Employee> actual = facade.getEmployeesWithHighestSalery();
        assertEquals(1, actual.size());
        assertEquals(actual.get(0), e2);
    }

    @Test
    void createEmployee() {
        Employee employee = facade.createEmployee("Peter", 30000);
        List<Employee> actual = facade.getAllEmployees();
        assertEquals(4, actual.size());

        assertThat(actual, containsInAnyOrder(e1,e2,e3, employee));


    }

    @Test
    void create() {
    }

    @Test
    void getById() {
    }
}