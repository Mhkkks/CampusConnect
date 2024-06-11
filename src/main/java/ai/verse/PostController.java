package ai.verse;


import ai.verse.repo.PostEntity;
import ai.verse.repo.PostRepository;
import ai.verse.repo.StudentRepository;
import ai.verse.repo.Studententity;
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
// Controller is helping us to create an API. Here we are API Producer or API Creator

@RestController
@RequestMapping("api")
public class PostController {



    @Autowired
    PostRepository postRepository;


    @Autowired
    StudentRepository studentRepository;

    @RequestMapping(value = "/getStudents", method = RequestMethod.GET)
    public ResponseEntity<List<Studententity>> getStudents() {
        List<Studententity> students = studentRepository.findAll();
        System.out.println("-------------------STUDENT LIST IS :" + students);
        return ResponseEntity.status(HttpStatus.OK).body(students);
    }




    @GetMapping("/check")
    public String check() {
        return "OK";
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





    @RequestMapping(value = "/posts", method = RequestMethod.GET)
    public ResponseEntity<List> getPosts() {
        List<PostEntity> list = postRepository.findAll();
        System.out.println("-------------------LIST IS :" + list);
        return new ResponseEntity(list, HttpStatus.OK);
    }


    @RequestMapping(value = "/graphdata", method = RequestMethod.GET)
    public ResponseEntity<List> getGraphData() {
        List list = postRepository.findAggregateData();
        System.out.println("-------------------LIST IS :" + list);
        return new ResponseEntity(list, HttpStatus.OK);
    }




















}


