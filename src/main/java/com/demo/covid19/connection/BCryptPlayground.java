package com.demo.covid19.connection;

import org.mindrot.jbcrypt.BCrypt;

public class BCryptPlayground {

    public static void main(String... args) {
        String password = "1";
//        String hashedPassword = "$2a$10$Y9KsSjabinBHJvWVVS3SvuJBWRWRcS75qQ9Dt0HEXMcdr5qDuMLne";
        String hashed = BCrypt.hashpw(password, BCrypt.gensalt());

        if (BCrypt.checkpw(password, hashed)) {
            System.out.println("match");
            System.out.println(hashed);
        } else {
            System.out.println("not");
        }


//      $2a$10$csc58VtsqRM2unFoCwLvOuY3JEQfxvEqRcSpR3FRIxhZzqDMBQvLK 123456

    }
}
