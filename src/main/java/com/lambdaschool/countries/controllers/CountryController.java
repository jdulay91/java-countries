package com.lambdaschool.countries.controllers;

import com.lambdaschool.countries.models.Country;
import com.lambdaschool.countries.repositories.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
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
        List<Country> myList = new ArrayList<>();
        countryrepos.findAll().iterator().forEachRemaining(myList::add);
        myList.sort((c1,c2) -> c1.getName().compareToIgnoreCase(c2.getName()));
        //print to console
        for(Country c : myList){
            System.out.println(c);
        }
        //return to client
        return new ResponseEntity<>(myList, HttpStatus.OK);
    }

    //http://localhost:2019/names/start/u
    @GetMapping( value = "/names/start/{letter}", produces = {"application/json"})
    public ResponseEntity<?> listAllByFirstLetter(@PathVariable char letter) {
        List<Country> myList = new ArrayList<>();
        countryrepos.findAll().iterator().forEachRemaining(myList::add);
        List<Country> newList = findCountries(myList, c->c.getName().toLowerCase().charAt(0) == letter);
        return new ResponseEntity<>(newList,HttpStatus.OK);
    }

    @GetMapping( value = "/population/min", produces = {"application/json"})
    public ResponseEntity<?> getMinPopulation() {
        List<Country> myList = new ArrayList<>();
        countryrepos.findAll().iterator().forEachRemaining(myList::add);
        myList.sort((c1, c2) -> (int)(c1.getPopulation() - c2.getPopulation()));
        return new ResponseEntity<>(myList.get(0),HttpStatus.OK);
    }
    @GetMapping( value = "/population/max", produces = {"application/json"})
    public ResponseEntity<?> getMaxPopulation() {
        List<Country> myList = new ArrayList<>();
        countryrepos.findAll().iterator().forEachRemaining(myList::add);
        myList.sort((c1, c2) -> (int)(c1.getPopulation() - c2.getPopulation()));
        return new ResponseEntity<>(myList.get(myList.size()-1),HttpStatus.OK);
    }
}
