package com.luv2code.ecommerce.service.impl;

import com.luv2code.ecommerce.entity.Country;
import com.luv2code.ecommerce.repository.CountryRepository;
import com.luv2code.ecommerce.service.CountryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;
    @Override
    public List<Country> getAllCountries() {

        return countryRepository.findAll();
    }
    @Override
    public Country findByCode(String code) {
        return countryRepository.findByCode(code)
                .orElseThrow(() -> new RuntimeException("Country not found for code: " + code));
    }
}
