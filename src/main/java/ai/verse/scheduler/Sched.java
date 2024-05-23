package ai.verse.scheduler;


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
        List<PostEntity> list = postRepository.findAll();
        System.out.println(list);


    }
}