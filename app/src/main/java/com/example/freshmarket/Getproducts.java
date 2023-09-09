package com.example.freshmarket;

import com.example.freshmarket.ui.home.HomeFragment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Getproducts {
    public ArrayList<product> GetData(){
        Database db= new Database();
        db.ConnectDB();
        ResultSet rs=db.RunSearch("select * from products where section='"+HomeFragment.secname+"' ");
        ArrayList<product>data=new ArrayList();
        try {
            while (rs.next()){
                product p=new product();
                p.setId(rs.getString(1));
                p.setName(rs.getString(2));
                p.setPrice(rs.getString(3));
                p.setDetails(rs.getString(4));
                p.setDiscount(rs.getString(5));
                p.setImage(rs.getString(6));
                p.setSection(rs.getString(7));
                data.add(p);


            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return data;
    }
}
