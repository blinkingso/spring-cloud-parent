package com.yz;

import org.junit.Test;
import org.springframework.core.io.FileSystemResource;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;

public class Test_Spring_Resource {

    @Test
    public void test_Resource() throws FileNotFoundException {
        FileSystemResource fsr = new FileSystemResource("C:\\Users\\yaphets\\IdeaProjects\\spring-cloud-parent\\boot-samples\\src\\main\\java\\com\\yz\\bean\\Color.java");
        System.out.println(fsr.getFilename());
        try(InputStreamReader isr = new InputStreamReader(fsr.getInputStream())
        ;
            BufferedReader br = new BufferedReader(isr)
        ) {
            String line = br.readLine();
            while(line != null) {
                System.out.println(line);
                line = br.readLine();
            }
        } catch (Exception e) {
            System.out.println("error caused");
            e.printStackTrace();
        }
    }
}
