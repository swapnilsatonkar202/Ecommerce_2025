package com.qa.tests;

import com.qa.utils.DBUtil;
import java.sql.Connection;

public class DBConnectionTest {

    public static void main(String[] args) {

        Connection con = DBUtil.getConnection();

        if (con != null) {
            System.out.println("✅ Database Connected Successfully");
        } else {
            System.out.println("❌ Database Connection Failed");
        }
    }
}
