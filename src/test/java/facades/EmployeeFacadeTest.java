package facades;

import entities.Employee;
import entities.dtos.EmployeeDTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
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
    private Employee e1, e2, e3;
    private EmployeeDTO e1DTO, e2DTO, e3DTO;

@Disabled
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
        e1 = facade.createEmployee("John", 43000);
        e2 = facade.createEmployee("Fætter Højben", 47000);
        e3 = facade.createEmployee("Fætter Vims", 5000);
        e1DTO = new EmployeeDTO(e1);
        e2DTO = new EmployeeDTO(e2);
        e3DTO = new EmployeeDTO(e3);

    }

    @Test
    void getEmployee() {
        EmployeeDTO actual = facade.getEmployeeById(e1DTO.getId());
        EmployeeDTO expected = e1DTO;
        assertEquals(expected, actual);

    }

    @Test
    void getEmployeesByName() {
        List<EmployeeDTO> actual = facade.getEmployeesByName("John");
        assertEquals(1, actual.size());
        assertEquals(e1DTO, actual.get(0));

    }


    @Test
    void getAllEmployees() {
        List<EmployeeDTO> actual = facade.getAllEmployees();
        assertEquals(3, actual.size());
        assertThat(actual, containsInAnyOrder(e1DTO, e2DTO, e3DTO));
    }

    @Test
    void getEmployeesWithHighestSalery() {
        List<EmployeeDTO> actual = facade.getEmployeesWithHighestSalery();
        assertEquals(1, actual.size());
        assertEquals(actual.get(0), e2DTO);
    }

    @Test
    void createEmployee() {
        Employee employee = facade.createEmployee("Peter", 30000);
        EmployeeDTO employeeDTO = new EmployeeDTO(employee);
        List<EmployeeDTO> actual = facade.getAllEmployees();
        assertEquals(4, actual.size());

        assertThat(actual, containsInAnyOrder(e1DTO, e2DTO, e3DTO, employeeDTO));


    }

    @Test
    void create() {
    }

    @Test
    void getById() {
    }
}