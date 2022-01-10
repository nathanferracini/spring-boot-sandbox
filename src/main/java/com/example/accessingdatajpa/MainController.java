package com.example.accessingdatajpa;

import com.example.accessingdatajpa.dto.CustomerDTO;
import com.example.accessingdatajpa.dto.TotalResultDTO;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller // This means that this class is a Controller
@RequestMapping(path="/demo") // This means URL's start with /demo (after Application path)
public class MainController {
    @Autowired // This means to get the bean called userRepository
    // Which is auto-generated by Spring, we will use it to handle the data
    private CustomerRepository customerRepository;

    private final ModelMapper modelMapper = new ModelMapper();

    @PostMapping(path="/add") // Map ONLY POST Requests
    public @ResponseBody String addNewUser (@RequestParam String firstName
            , @RequestParam String lastName) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request

        Customer n = new Customer(firstName, lastName);
        customerRepository.save(n);
        return "Saved";
    }

    @GetMapping(path="/find")
    public @ResponseBody TotalResultDTO getUser(@RequestParam String lastName) {
        // This returns a JSON or XML with the users
        List<Customer> customerList = customerRepository.findByLastName(lastName);
        List<CustomerDTO> customerDTOS = customerList.stream().map(cust -> modelMapper.map(cust, CustomerDTO.class)).collect(Collectors.toList());
        TotalResultDTO totalResultDTO = new TotalResultDTO();
        totalResultDTO.setCustomerDTOList(customerDTOS);
        totalResultDTO.setTotal(customerDTOS.size());
        return totalResultDTO;
    }

    @GetMapping(path="/all")
    public @ResponseBody Iterable<Customer> getAllUsers() {
        // This returns a JSON or XML with the users
        return customerRepository.findAll();
    }

    @GetMapping(path="/allXml", produces = MediaType.APPLICATION_XML_VALUE)
    public @ResponseBody Iterable<Customer> getAllUsersXml() {
        // This returns a JSON or XML with the users
        log.info("Will generate an XML response");
        return customerRepository.findAll();
    }

    private static final Logger log = LoggerFactory.getLogger(MainController.class);

}