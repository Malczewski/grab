package edu.sl.grabalyze.main;

import edu.sl.grabalyze.execution.ProgramExecutor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        ApplicationContext context =
                new ClassPathXmlApplicationContext("edu/sl/grabalyze/config/beans.xml");

        ProgramExecutor executor = (ProgramExecutor) context.getBean("program");
        executor.execute();

    }
}