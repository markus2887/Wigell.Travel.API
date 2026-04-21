package com.MarkusE.Wigell_Travel_API.config;

import com.MarkusE.Wigell_Travel_API.entity.Address;
import com.MarkusE.Wigell_Travel_API.entity.Customer;
import com.MarkusE.Wigell_Travel_API.entity.Destination;
import com.MarkusE.Wigell_Travel_API.repo.AddressRepository;
import com.MarkusE.Wigell_Travel_API.repo.AppUserRepository;
import com.MarkusE.Wigell_Travel_API.repo.CustomerRepository;
import com.MarkusE.Wigell_Travel_API.repo.DestinationRepository;
import com.MarkusE.Wigell_Travel_API.security.AppUser;
import com.MarkusE.Wigell_Travel_API.security.Role;
import com.MarkusE.Wigell_Travel_API.service.CustomerService;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class DataInitializer implements CommandLineRunner {

    private final CustomerRepository customerRepository;
    private final AddressRepository addressRepository;
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final DestinationRepository destinationRepository;

    public DataInitializer(CustomerRepository customerRepository, CustomerService customerService, AddressRepository addressRepository, AppUserRepository appUserRepository, PasswordEncoder passwordEncoder, DestinationRepository destinationRepository) {
        this.customerRepository = customerRepository;
        this.addressRepository = addressRepository;
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.destinationRepository = destinationRepository;
    }

    @Override
    public void run(String... args) {

        Address emptyAddress = addressRepository.save(new Address("", "", ""));
        Address address = addressRepository.save(new Address("Granvägen 28", "432 33", "Varberg"));
        Address addressTwo = addressRepository.save(new Address("Stengatan 10", "432 45", "Göteborg"));

        customerRepository.save(new Customer("Markus", "Emanuelsson", "Markus234", address, "markus.emanuelsson@gmail.com", "0768444036", LocalDate.of(1985, 9, 11)));
        customerRepository.save(new Customer("Adam", "Olsson", "Olsson246", addressTwo, "adam.olsson@gmail.com", "076345323", LocalDate.of(1988, 5, 22)));
        customerRepository.save(new Customer("Kalle", "Andersson", "And99", addressTwo, "kalle.andersson@gmail.com", "073543534", LocalDate.of(1999, 12, 4)));
        customerRepository.save(new Customer("Sara", "Eliasson", "Eliasson777", addressTwo, "sara.eliasson@gmail.com", "0705345344", LocalDate.of(1987, 2, 16)));
        customerRepository.save(new Customer("Thomas", "Nilsson", "Tompa83", addressTwo, "thomas.nilsson@gmail.com", "0734534541", LocalDate.of(1983, 6, 15)));

        destinationRepository.save(new Destination(new BigDecimal(19000),"Hilton Budapest", "Budapest", "Hungary"));
        destinationRepository.save(new Destination(new BigDecimal(13000),"Budapest Inn", "Budapest", "Hungary"));
        destinationRepository.save(new Destination(new BigDecimal(50000),"Jumeira Rotana", "Dubai", "United Arab Emirates"));
        destinationRepository.save(new Destination(new BigDecimal(35000),"Holiday Inn", "Dubai", "United Arab Emirates"));
        destinationRepository.save(new Destination(new BigDecimal(3000),"Hostel Para Tapok", "Moscow", "Russia"));
        destinationRepository.save(new Destination(new BigDecimal(999),"Sunny Inn", "Moscow", "Russia"));
        destinationRepository.save(new Destination(new BigDecimal(6000),"Hilton Kyiv", "Kyiv", "Ukraine"));
        destinationRepository.save(new Destination(new BigDecimal(3500),"Happy Holiday Inn", "Kyiv", "Ukraine"));
        destinationRepository.save(new Destination(new BigDecimal(49000),"Hotel Meigetsu", "Tokyo", "Japan"));
        destinationRepository.save(new Destination(new BigDecimal(30000),"Sleepy Inn", "Tokyo", "Japan"));

        AppUser admin = new AppUser("admin", passwordEncoder.encode("admin"), Role.ADMIN);
        AppUser user = new AppUser("user", passwordEncoder.encode("user"), Role.USER);
        appUserRepository.save(admin);
        appUserRepository.save(user);

    }


}