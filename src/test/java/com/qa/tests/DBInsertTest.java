package com.qa.tests;

import com.qa.utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class DBInsertTest {

    public static void main(String[] args) {

        String insertQuery =
                "INSERT INTO employees(emp_name, email) VALUES (?, ?)";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(insertQuery)) {

            //ps.setString(3, "Sunil1");
           // ps.setString(4, "sunil1.test@gmail.com");

            ps.setString(1, "shyam");
            ps.setString(2, "shyam.test@gmail.com");

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("✅ Record inserted successfully");
            } else {
                System.out.println("❌ Insert failed");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
