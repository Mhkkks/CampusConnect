package controller;


import ai.verse.repo.PostEntity;
import ai.verse.repo.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Because I want to create API for posts I created Post controller
@SpringBootApplication
@RestController
public class PostController {

    @Autowired
    PostRepository postRepository;

// Controller is helping us to create an API. Here we are API Producer or API Creator
    @GetMapping("/posts/all")
@RequestMapping(value="/get",method= RequestMethod.GET)
    public ResponseEntity<List> getPosts() {
        List<PostEntity> list = postRepository.findAll();  //
        System.out.println("-------------------LIST IS :"+ list);
        //    HttpStatus.BAD_REQUEST;
        //      HttpStatus.UNAUTHORIZED;
         return ResponseEntity.status(HttpStatus.OK).body(list);
    }


//    @GetMapping("/check2")
//    public String  getPosts2() {
//
//        return "OK";
//    }






//    @GetMapping(value = "/check", produces = MediaType.APPLICATION_JSON_VALUE)
//    ResponseEntity<Map<String, String>> checkApplication() {
//        Map<String, String> result = new HashMap<String, String>();
//        result.put("OK", "OK");
//        System.out.println(result);
//        return new ResponseEntity<Map<String, String>>(result, HttpStatus.OK);
//    }
}


