package cc.api;


import cc.api.repo.ProductEntity;
import cc.api.repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Because I want to create API for posts I created Post controller
// Controller is helping us to create an API. Here we are API Producer or API Creator

@RestController
@RequestMapping("api")
public class ProductController {

    @Autowired
    ProductRepository productRepository;
    @RequestMapping(value = "/product", method = RequestMethod.GET)
    public ResponseEntity<List> getProduct() {
        List<ProductEntity> list = productRepository.findAll();
        System.out.println("-------------------LIST IS :" + list);
        return new ResponseEntity(list, HttpStatus.OK);
    }



    @GetMapping(value = "/checklist")
    ResponseEntity checkList() {
        List<String> list = new ArrayList();
        list.add("London");
        list.add("Singapore");
        list.add("Dubai");
        return new ResponseEntity(list, HttpStatus.OK);
    }


    @GetMapping(value = "/checkmap")
    ResponseEntity checkMap() {
        Map<String, String> result = new HashMap<String, String>();
        result.put("1", "London");
        result.put("2", "Singapore");
        result.put("3", "Dubai");
        System.out.println(result);
        return new ResponseEntity(result, HttpStatus.OK);
    }

}



