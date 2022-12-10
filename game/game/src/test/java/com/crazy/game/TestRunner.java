package com.crazy.game;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestRunner {
    public static void main(String[] args) {

        int count=0;
        Result result = JUnitCore.runClasses(TestSuite.class);

        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
            count+=1;
        }

        if(count == 0) {
            System.out.println("\n\n\nAll tests Passed");
        }
        else
        {
            System.out.println("\n\n\nSome tests passed");
        }
    }
}
