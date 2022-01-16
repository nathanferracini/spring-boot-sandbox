package com.example.accessingdatajpa.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class TotalResultDTO {

    @JsonProperty("userList")
    List<CustomerDTO> customerDTOList;
    Integer total;

    public List<CustomerDTO> getCustomerDTOList() {
        return customerDTOList;
    }

    public void setCustomerDTOList(List<CustomerDTO> customerDTOList) {
        this.customerDTOList = customerDTOList;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
