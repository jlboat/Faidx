/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Faidx;

import java.io.File;
import java.io.FileReader;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author J. Lucas Boatwright
 */
public class FaidxTest {
    
    public FaidxTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of main method, of class Faidx.
     * @throws java.lang.Exception
     */
    @Test
    public void testMain() throws Exception {
        System.out.println("main");
        String[] name = new String[1];
        name[0] = "src/test/resources/com/github/" + 
                "jlboat/faidx/DNA.fasta";
        Faidx.main(name);
        Stream<String> controlFile = Files.lines(Paths.get("src/test/resources/com/github/" + 
                "jlboat/faidx/Control.fai"), StandardCharsets.UTF_8);
        Stream<String> testFile = Files.lines(Paths.get(name[0] + ".fai"), StandardCharsets.UTF_8);
        Assert.assertArrayEquals(controlFile.toArray(), testFile.toArray());
        //fail("The test case is a prototype.");
    }
    
}
