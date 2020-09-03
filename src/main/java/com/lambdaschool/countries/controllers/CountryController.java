package com.lambdaschool.countries.controllers;

import com.lambdaschool.countries.models.Country;
import com.lambdaschool.countries.repositories.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CountryController {

    @Autowired
    private CountryRepository countryrepos;

    private List<Country> findCountries(List<Country> myList, CheckCountry tester){
        List<Country> tempList = new ArrayList<>();

        for(Country c: myList) {
            if(tester.test(c)){
                tempList.add(c);
            }
        }
        return tempList;
    }


    //http://localhost:2019/names/all
    //invokes method when the route is the same
    @GetMapping(value = "/names/all", produces = {"application/json"})
    public ResponseEntity<?> listAllCountries(){
        //create list
        List<Country> myList = new ArrayList<>();
        //found all employees and added to created list
        countryrepos.findAll().iterator().forEachRemaining(myList::add);
        //sort list by last name
        myList.sort((e1,e2) -> e1.getName().compareToIgnoreCase(e2.getName()));
        //print to console
        for(Country e : myList){
            System.out.println(e);
        }
        //return to client
        return new ResponseEntity<>(myList, HttpStatus.OK);
    }

}
