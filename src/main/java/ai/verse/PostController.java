package ai.verse;


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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Because I want to create API for posts I created Post controller

@RestController
@RequestMapping("api")
public class PostController {

    // Controller is helping us to create an API. Here we are API Producer or API Creator

    @Autowired
    PostRepository postRepository;


    @RequestMapping(value = "/posts", method = RequestMethod.GET)
    public ResponseEntity<List> getPosts() {
        List<PostEntity> list = postRepository.findAll();  //
        System.out.println("-------------------LIST IS :" + list);
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }


    @GetMapping("/check")
    public String check() {
        return "OK";
    }


    @GetMapping(value = "/checkmap")
    ResponseEntity<Map<String, String>> checkMap() {
        Map<String, String> result = new HashMap<String, String>();
        result.put("1", "London");
        result.put("2", "Singapore");
        result.put("3", "Dubai");
        System.out.println(result);
        return new ResponseEntity<Map<String, String>>(result, HttpStatus.OK);
    }


    @GetMapping(value = "/checklist")
    ResponseEntity checkList() {
       List<String> list = new ArrayList();
        list.add("London");
        list.add("Singapore");
       list.add("Dubai");
        return new ResponseEntity(list, HttpStatus.OK);
    }
}


