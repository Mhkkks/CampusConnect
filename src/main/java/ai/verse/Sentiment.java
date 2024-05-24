package ai.verse;


import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.util.CoreMap;
//import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

//@Slf4j
public class Sentiment {

   // I declared pipeline on top (as class variable) so that it can be used by all methods
   StanfordCoreNLP pipeline = new StanfordCoreNLP("application.properties");
    // Here pipeline is created at class level

    public static void main(String[] args) {
        // Initialize the Stanford NLP pipeline
        StanfordCoreNLP pipeline = new StanfordCoreNLP("application.properties");

        // List of posts here
        List<String> list = new ArrayList(); // This is how you create empty list in java


        // This is the way you add elements to the list. Posts are strings (varchar in database)  so add double quotes
        list.add("This city is the best place to live. I am happy to be here");
        list.add("I hate this place. I am not coming back here again. I am very disappointed");
        list.add("The weather here is wonderful. I like this place");
        list.add("Horrible traffic, not a good place to visit");
        list.add("Just ok ");
        list.add(" can go there only once");


        // Learning

        // Ctrl + Alt + l --> will format the java code

        System.out.println(list);
        System.out.println("Size of the list is : " + list.size());


        //Iterate over the list using for loop

        // In Begin the value of s =0
        // Run the loop till
        // s++ --> Increment the value by 1 after every loop
        for (int s = 0; s < list.size(); s++) {

            System.out.println(s);
            // System.out.println(list.get(s));

            getSentiment(list.get(s), pipeline);

        }


    }

    //  Method which accepts text and pipeline
    // private method cannot be accessed outside the class
    private static String getSentiment(String text, StanfordCoreNLP pipeline) {
        // Create an Annotation object with the input text
        Annotation annotation = new Annotation(text);

        // Run all the NLP annotators on the text
        pipeline.annotate(annotation);

        // Extract the sentiment from the annotation
        CoreMap sentence = annotation.get(CoreAnnotations.SentencesAnnotation.class).get(0);
        String sentiment = sentence.get(SentimentCoreAnnotations.SentimentClass.class);
        System.out.println("Sentiment Text -->" + text + " --->  " + sentiment);
        return sentiment;
    }



    // public  method CAN be accessed outside the class
    public  String getSentimentOfText(String text) {
        // Create an Annotation object with the input text


        Annotation annotation = new Annotation(text);

        // Run all the NLP annotators on the text
        pipeline.annotate(annotation);

        // Extract the sentiment from the annotation
        CoreMap sentence = annotation.get(CoreAnnotations.SentencesAnnotation.class).get(0);
        String sentiment = sentence.get(SentimentCoreAnnotations.SentimentClass.class);
      //  System.out.println("Sentiment Text -->" + text + " --->  " + sentiment);
        return sentiment;
    }
}