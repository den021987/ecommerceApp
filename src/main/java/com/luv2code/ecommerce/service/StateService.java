package com.luv2code.ecommerce.service;

import com.luv2code.ecommerce.entity.State;

import java.util.List;

public interface StateService {

    List<State> findStatesByCountryCode(String code);

    State findStateById(Long stateId);
}
