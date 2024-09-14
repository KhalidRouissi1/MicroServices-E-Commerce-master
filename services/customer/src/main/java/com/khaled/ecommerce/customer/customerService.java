package com.khaled.ecommerce.customer;

import com.ctc.wstx.util.StringUtil;
import com.khaled.ecommerce.exception.CustomerNotFoundExecption;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class customerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper mapper;

    public String createCustomer(CustomerRequest request){
        var customer = customerRepository.save(mapper.toCustomer(request));
        return customer.getId();
    }

    public void updateCustomer(@Valid CustomerRequest request) {
        var customer = customerRepository.findById(request.id())
                .orElseThrow(()->new CustomerNotFoundExecption(format("Cannot update customer::Customer with id [%s] not found", request.id())));
        mergerCustomer(customer,request);
        customerRepository.save(customer);
    }

    private void mergerCustomer(Customer customer, @Valid CustomerRequest request) {
        if(StringUtils.isBlank(request.firstName())){
            customer.setFirstName(request.firstName());
        }
        if(StringUtils.isBlank(request.lastName())){
            customer.setLastName(request.lastName());
        }
        if(request.email() != null){
            customer.setEmail(request.email());
        }
    }

    public List<CustomerResponse> findAllCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(mapper::fromCustomer)
                .collect(Collectors.toList());
    }

    public Boolean exitsById(String customerId) {
        return customerRepository.findById(customerId).isPresent();
    }

    public CustomerResponse findById(String customerId) {
        return customerRepository.findById(customerId).map(
                mapper::fromCustomer
        ).orElseThrow(()->new CustomerNotFoundExecption(format("No Customer found with id [%s]", customerId)));
    }

    public void deleteCustomer(String customerId) {
        customerRepository.deleteById(customerId);
    }
}
