package ai.verse.scheduler;


import ai.verse.Sentiment;
import ai.verse.repo.PostEntity;
import ai.verse.repo.PostRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@Component
public class Sched {


    @Autowired
    PostRepository postRepository;


    @Scheduled(fixedRate = 10000)
    public void scheduledMethod() {
        System.out.println(" --------------- scheduledMethod Called on:" + new Date());
        List<PostEntity> list = postRepository.findAll();  // Get all posts from database
      //  System.out.println(list);


        //////////////////////////// This is for first post for demo ////////////////////////////////////////////
        // This is for first row in Sentiment
//        PostEntity onePost = list.get(0); // This is will get the first post (complete row of database)
//        Sentiment sm = new Sentiment();   // This creates instance of a class (OOPS concept)  so that you can use it here
//        String sentiment = sm.getSentimentOfText(onePost.getPost());
      //  System.out.println(sentiment);
        ///////////////////////// this is for ///////////////////////////////////////////////


//        PostEntity neww = new PostEntity();
//        neww.setPost("This is from java");
//        postRepository.save(neww);

      // Here we are iterating over all the posts and calling sentimen class
        for(int h=0; h<list.size(); h++ )
        { Sentiment sm = new Sentiment();
            PostEntity post = list.get(h);   // This will get post in a loop
            String sentm = sm.getSentimentOfText(post.getPost());
            System.out.println(post);
            System.out.println(sentm);
        }



    }





}