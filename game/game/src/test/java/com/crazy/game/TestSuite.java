package com.crazy.game;



import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
@SelectClasses( { Part1.class, Part2.class, Part3.class } )
public class TestSuite {
}
