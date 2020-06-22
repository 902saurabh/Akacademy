package com.example.akacademy.Model;


public class MyTest {
 private String answer;
 private String image;

 public MyTest(){

 }

    public MyTest(String answer, String image) {
        this.answer = answer;
        this.image = image;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
