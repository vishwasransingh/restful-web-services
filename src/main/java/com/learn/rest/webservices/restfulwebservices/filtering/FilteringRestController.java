package com.learn.rest.webservices.restfulwebservices.filtering;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

@RestController
public class FilteringRestController {
	
//	@GetMapping("/filtering")
//	public SomeBean staticFiltering() {
//		return new SomeBean("val1", "val2", "val3");
//	}
//	
	@GetMapping("/filtering")
	public MappingJacksonValue dynamicFiltering() {
		
		SomeBean someBean = new SomeBean("val1", "Val2", "Val3");
		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(someBean);
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("field1", "field3");
		FilterProvider filters = new SimpleFilterProvider().addFilter("SomeBean Filter", filter);
		mappingJacksonValue.setFilters(filters);
		return mappingJacksonValue;
		
	}
	
}
