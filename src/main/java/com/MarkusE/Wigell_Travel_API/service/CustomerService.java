package com.MarkusE.Wigell_Travel_API.service;

import com.MarkusE.Wigell_Travel_API.dto.AddressDto;
import com.MarkusE.Wigell_Travel_API.dto.AddressResponseDto;
import com.MarkusE.Wigell_Travel_API.dto.CreateCustomerDto;
import com.MarkusE.Wigell_Travel_API.dto.CustomerResponseDto;
import com.MarkusE.Wigell_Travel_API.entity.Address;
import com.MarkusE.Wigell_Travel_API.entity.Customer;
import com.MarkusE.Wigell_Travel_API.mapper.AddressMapper;
import com.MarkusE.Wigell_Travel_API.mapper.CustomerMapper;
import com.MarkusE.Wigell_Travel_API.repo.AddressRepository;
import com.MarkusE.Wigell_Travel_API.repo.CustomerRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CustomerService {

    private static final Logger log = LogManager.getLogger(CustomerService.class);

    private final CustomerRepository customerRepo;
    private final AddressRepository addressRepo;
    private final AddressMapper addressMapper;
    private final CustomerMapper customerMapper;

    public CustomerService(CustomerRepository customerRepo, AddressRepository addressRepo, AddressMapper addressMapper, CustomerMapper customerMapper) {
        this.customerRepo = customerRepo;
        this.addressRepo = addressRepo;
        this.addressMapper = addressMapper;
        this.customerMapper = customerMapper;
    }

    @Transactional(readOnly = true)
    public List<CustomerResponseDto> findAll() {
        List<Customer> customers = customerRepo.findAll();

        return customers.stream()
                .map(customerMapper::toResponseDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public Customer findById(Long id) {
        return customerRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Member not found with id " + id));
    }

    @Transactional
    public CustomerResponseDto create(CreateCustomerDto dto) {

        Address address = getAddress(dto.addressId());

        Customer customer = customerMapper.toEntity(dto, address);

        log.info("Admin created customer {}", customer.getUserName());

        Customer saved = customerRepo.save(customer);

        return customerMapper.toResponseDto(saved);
    }

    @Transactional
    public CustomerResponseDto save(Customer customer) {

        Customer saved = customerRepo.save(customer);

        log.info("Admin updated customer {}", saved.getUserName());

        return customerMapper.toResponseDto(saved);
    }

    @Transactional
    public CustomerResponseDto update(Long id, CreateCustomerDto dto) {

        Customer existing = findById(id);

        Address address = getAddress(dto.addressId());

        customerMapper.updateCustomer(dto, existing, address);

        Customer saved = customerRepo.save(existing);

        log.info("Admin updated customer {}", saved.getUserName());

        return customerMapper.toResponseDto(saved);
    }

    @Transactional
    public void delete(Long id) {
        Customer existing = findById(id);
        customerRepo.delete(existing);

        log.info("Admin deleted customer with id={}", id);
    }





    //Adressmetoder

    @Transactional(readOnly = true)
    public Address getAddress(Long id) {
        return addressRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Address not found with id " + id));
    }

    @Transactional
    public AddressResponseDto saveAddress(Long customerId, AddressDto dto) {

        Customer customer = customerRepo.findById(customerId)
                .orElseThrow();

        Address address = addressRepo
                .findByStreetAndPostalCodeAndCity(dto.street(), dto.postalCode(), dto.city())
                .orElseGet(() -> {

                    Address newAddress = new Address(dto.street(), dto.postalCode(), dto.city());
                    return addressRepo.save(newAddress);
                });

        customer.setAddress(address);
        customerRepo.save(customer);

        log.info("Admin created address that belongs to customerId={} with street={} and city={}", customerId, dto.street(), dto.city());

        return addressMapper.toDto(address);
    }

    @Transactional
    public void removeAddressFromCustomer(Long customerId, Long addressId) {

        Customer customer = customerRepo.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Address address = customer.getAddress();

        if (address == null) {
            throw new RuntimeException("Customer has no address");
        }

        if (!address.getAddressId().equals(addressId)) {
            throw new RuntimeException("Wrong address");
        }

        Address emptyAddress = addressRepo.findById(1L)
                .orElseThrow(() -> new RuntimeException("Address not found"));

        customer.setAddress(emptyAddress);
        customerRepo.save(customer);

        long count = customerRepo.countByAddress_AddressId(addressId);

        if (count == 0) {
            addressRepo.deleteById(addressId);
        }

        log.info("Admin deleted addressId={} from customerId={}", addressId, customerId);
    }
}
