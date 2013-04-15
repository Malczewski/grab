package edu.sl.grabalyze.main;

import edu.sl.grabalyze.processing.stemming.RussianStemmer;
import edu.sl.grabalyze.processing.stemming.Stemmer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        /*ApplicationContext context =
                new ClassPathXmlApplicationContext("edu/sl/grabalyze/config/beans.xml");

        GrabberExecutor executor = (GrabberExecutor) context.getBean("harvester");

        executor.execute();*/
        Stemmer s = new RussianStemmer();
    }
}