package com.example.accessingdatajpa.dto;

import java.util.List;

public class TotalResultDTO {

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
