package src.main.java.keibatool.entity;

import lombok.Data;

@Data @Entity
public class RaceResult {
 private int order;
 private int waku;
 private int ban;
 private String horseCode;
 private int sex;
 private int age;
 private int amount;//斤量;
 private String jockeyCode;

}
