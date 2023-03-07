package com.example.oraclelibrary;

import javax.annotation.PostConstruct;
import javax.ejb.*;
import java.sql.*;
import oracle.jdbc.OracleConnection;
import oracle.jdbc.pool.OracleDataSource;


/**
 * This class will illustrate how to connect Oracle database in singleton session bean
 *
 * @author Gusto Htar Htar Hlaing
 */
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
@Singleton(name = "OracleClientProviderEJB")
public class OracleClientProviderBean {

    final static String DB_URL="jdbc:oracle:thin:@localhost:1521:xe";
    final static String DB_USER = "HHH";
    final static String DB_PASSWORD = "Gusto123";

    public OracleClientProviderBean() {
    }

    private Connection oracleClient = null;

    @Lock(LockType.READ)
    public Connection getOracleClient(){
        return oracleClient;
    }

    @PostConstruct
    public void init()
    {
        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
            oracleClient = DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
            if(oracleClient != null)
            {
                System.out.println("****************************************8");
                System.out.println("Oracle Cloud DB Connection is Success!");
                printCustomers(oracleClient);
            }
            else{
                System.out.println("****************************************8");
                System.out.println("Oracle Cloud DB Connection is fail!");
            }
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    public  void printCustomers(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement
                    .executeQuery("select * from book fetch first 2 rows only ")) {

                while (resultSet.next())
                    System.out.println(resultSet.getString(1) + " " + resultSet.getString(2)+ " "+ resultSet.getString(3) + " ");
            }
        }
    }
}