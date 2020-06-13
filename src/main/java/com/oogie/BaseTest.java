package com.oogie;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BaseTest {
    static final String JDBC_DIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/movies";
    static final String USER = "root";
    static final String PASS = "admin";
    static protected Connection conn = null;

    @BeforeAll
    public static void setUp() {
        try {
            Class.forName(JDBC_DIVER);
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterAll
    public static void cleanUp() {
        try {
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
                ;
            } //end finally try
        } //end try
    }
}
