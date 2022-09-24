package entities.dtos;

import entities.Customer;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A DTO for the {@link Customer} entity
 */
public class CustomerDTO implements Serializable {
    private final Integer id;
    //    @Size(max = 45)
    private final String name;

    public CustomerDTO(Customer customer) {
        this.id = customer.getId();
        this.name = customer.getName();
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerDTO entity = (CustomerDTO) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.name, entity.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "name = " + name + ")";
    }

    public static List<CustomerDTO> getCustomerDTOs(List<Customer> customerList) {
        List<CustomerDTO> customerDTOs = new ArrayList<>();
        customerList.forEach(customer -> customerDTOs.add(new CustomerDTO(customer)));
        return customerDTOs;
    }
}
