/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pojos;

import java.io.Serializable;
import java.time.LocalDate;

/**
 *
 * @author annys
 */
public class DiseaseUser implements Serializable{
    String nameUser;
    String nameDisease;
    String nameTest;
    float percentage;
    int points;
    LocalDate ld; 

    public DiseaseUser(String nameUser, String nameDisease, String nameTest, float percentage, int points, LocalDate ld){
        this.nameUser = nameUser;
        this.nameDisease = nameDisease;
        this.nameTest = nameTest;
        this.percentage = percentage;
        this.points = points;
        this.ld = ld;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public void setNameDisease(String nameDisease) {
        this.nameDisease = nameDisease;
    }

    public void setNameTest(String nameTest) {
        this.nameTest = nameTest;
    }

    public void setPercentage(float percentage) {
        this.percentage = percentage;
    }

    public void setPoints(int points) {
        this.points = points;
    } 

    public void setLd(LocalDate ld) {
        this.ld = ld;
    }
    
    
    public String getNameUser() {
        return nameUser;
    }

    public String getNameDisease() {
        return nameDisease;
    }

    public String getNameTest() {
        return nameTest;
    }

    public float getPercentage() {
        return percentage;
    }

    public int getPoints() {
        return points;
    }
        
    public LocalDate getLd() {
        return ld;
    }
    
}
