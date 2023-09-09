package com.example.freshmarket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListView;
import android.widget.TextView;

import com.example.freshmarket.ui.home.HomeFragment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class SearchActivity extends AppCompatActivity {
 private SearchView searchView;
 private TextView textView;
 private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        searchView=findViewById(R.id.search);
        textView=findViewById(R.id.text);
        listView=(ListView) findViewById(R.id.list);
        listView.setVisibility(View.VISIBLE);
        ArrayList<product> list=new ArrayList<>();
        Adapter adapter=new Adapter(this,list);
        listView.setAdapter(adapter);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                list.clear();
                Database db= new Database();
                db.ConnectDB();
                String[] words= query.toLowerCase().split(" ");
                for(String word:words)
                {

                    ResultSet rs=db.RunSearch("select * from products where section='"+word+"' or name='"+word+"'");
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
                            list.add(p);
                        }
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    if(word.equals(words[words.length-1]))
                    {
                        if(list.size()==0)
                        {
                            textView.setVisibility(View.VISIBLE);
                            listView.setVisibility(View.GONE);
                        }else
                        { textView.setVisibility(View.GONE);
                            listView.setVisibility(View.VISIBLE);
                            adapter.getFilter().filter(query);
                        }
                    }
                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }
    class Adapter extends Adapterproduct implements Filterable
    {

        public Adapter(Context context, ArrayList<product> cont) {
            super(context, cont);
        }
        @NonNull
        @Override
        public Filter getFilter() {
            return new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence charSequence) {
                    return null;
                }

                @Override
                protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                      notifyDataSetChanged();
                }
            };
        }
    }
}