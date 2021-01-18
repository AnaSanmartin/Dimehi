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
public class User implements Serializable{
    
    String name;
    String password;
    String userName;
    int age;

    public User(String name, String password, String userName, int age) {
        this.name = name;
        this.password = password;
        this.userName = userName;
        this.age = age;
    }

    
    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setAge(int age) {
        this.age = age;
    }
    
    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getUserName() {
        return userName;
    }

    public int getAge() {
        return age;
    }
    
    
}
