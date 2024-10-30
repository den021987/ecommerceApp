package com.luv2code.ecommerce.service;


import com.luv2code.ecommerce.entity.Country;

import java.util.List;

public interface CountryService {

    List<Country> getAllCountries ();
    Country findByCode(String code);
}
