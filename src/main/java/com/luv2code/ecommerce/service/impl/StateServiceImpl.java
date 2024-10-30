package com.luv2code.ecommerce.service.impl;

import com.luv2code.ecommerce.entity.State;
import com.luv2code.ecommerce.repository.StateRepository;
import com.luv2code.ecommerce.service.StateService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor
public class StateServiceImpl implements StateService {

    private final StateRepository stateRepository;


    @Override
    public List<State> findStatesByCountryCode(String code) {
        List<State> states = stateRepository.findByCountryCode(code);
        System.out.println("States found: " + states);

        return states;
    }


    @Override
    public State findStateById(Long stateId) {
        return stateRepository.findById(Math.toIntExact(stateId)).get();
    }
}
