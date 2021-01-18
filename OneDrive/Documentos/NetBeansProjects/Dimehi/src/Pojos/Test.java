/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pojos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Test implements Serializable{
    
    String nameDisease;
    String testName;
    String userName;
    List <Integer> answerList = new ArrayList <Integer>();


    
    public Test(String nameDisease, String testName, String userName, List <Integer> answerList){
        this.nameDisease = nameDisease;
        this.testName = testName;
        this.userName = userName;
        this.answerList = answerList;
        
    }
    
    public void setNameDisease(String nameDisease) {
        this.nameDisease = nameDisease;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setAnswerList(List<Integer> answerList) {
        this.answerList = answerList;
    }

    public String getNameDisease() {
        return nameDisease;
    }

    public String getTestName() {
        return testName;
    }

    public String getUserName() {
        return userName;
    }

    public List<Integer> getAnswerList() {
        return answerList;
    }

    
}
