/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pojos;

import java.io.Serializable;

/**
 *
 * @author annys
 */
public class Disease implements Serializable {
    
    String nameDisease;
    String testName;


    public Disease(String nameDisease, String testName){
        this.nameDisease = nameDisease;
        this.testName = testName;
    }
    
    public void setNameDisease(String nameDisease) {
        this.nameDisease = nameDisease;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getNameDisease() {
        return nameDisease;
    }

    public String getTestName() {
        return testName;
    }

    
    
}
