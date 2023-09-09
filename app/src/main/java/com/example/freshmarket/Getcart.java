package com.example.freshmarket;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Getcart {
    public ArrayList<CartModel> GetData(){
        Database db= new Database();
        db.ConnectDB();
        ResultSet rs=db.RunSearch("select * from carts where cust_id='"+LoginActivity.id+"' ");
        ArrayList<CartModel>data=new ArrayList();
        try {
            while (rs.next()){
                CartModel p=new CartModel();
                p.setOrderno(rs.getString(1));
                p.setName(rs.getString(2));
                p.setPrice(rs.getInt(3));
                p.setTotal(rs.getString(4));
                p.setImage(rs.getString(5));
                p.setQuantity(rs.getInt(6));
                p.setCustid(rs.getString(7));
                data.add(p);


            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return data;
    }
}
