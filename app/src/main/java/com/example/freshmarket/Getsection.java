package com.example.freshmarket;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Getsection {
    public ArrayList<Section> GetData(){
        Database db= new Database();
        db.ConnectDB();
        ResultSet rs=db.RunSearch("select * from sections");
        ArrayList<Section>data=new ArrayList();
        try {
            while (rs.next()){
                Section section=new Section();
                section.setSecno(rs.getString(1));
                section.setSecname(rs.getString(2));
                section.setImage(rs.getString(3));
                data.add(section);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return data;
    }
}
