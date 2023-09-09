package com.example.freshmarket;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;

public class AdapterAllProducts extends ArrayAdapter<product> {
    Context c;
    ArrayList<product> ass;

    public AdapterAllProducts(Context context, ArrayList<product> cont) {
        super(context, R.layout.cartdetials,cont);
        c=context;
        ass=cont;
    }

    class Holder
    {
        public ImageView imagepro,imgdel,imgedit;
        public TextView textname ,textprice,textttotal,textqty;

    }

    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position

        final product data = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view

        final Holder viewHolder; // view lookup cache stored in tag

        if (convertView == null) {

            viewHolder = new Holder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.cartdetials, parent, false);
            viewHolder.textname = (TextView) convertView.findViewById(R.id.textname);
            viewHolder.textprice = (TextView) convertView.findViewById(R.id.textprice);
            viewHolder.textttotal = (TextView) convertView.findViewById(R.id.texttotal);
            viewHolder.textqty = (TextView) convertView.findViewById(R.id.textqty);
            viewHolder.imagepro= (ImageView) convertView.findViewById(R.id.imagepro);
            viewHolder.imgdel= (ImageView) convertView.findViewById(R.id.imgdel);
            viewHolder.imgedit= (ImageView) convertView.findViewById(R.id.imgedit);

            convertView.setTag(viewHolder);

        } else {
            viewHolder = (Holder) convertView.getTag();
        }
        PicassoClient.downloadImage(c,data.getImage(),viewHolder.imagepro);
        viewHolder.textname.setText(data.getName());
        viewHolder.textprice.setText(data.getPrice()+"LE");
        viewHolder.textqty.setText(data.getSection());
        viewHolder.textttotal.setText(String.valueOf(data.getAvailablequantity()));
        viewHolder.imgdel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Database db=new Database();
                db.ConnectDB();
                String rs=db.RunDML("delete from products where name='"+viewHolder.textname.getText()+"'");
                if (rs.equals("Ok"))
                    Toast.makeText(c, "Product deleted", Toast.LENGTH_SHORT).show();
            }
        });
        viewHolder.imgedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Database db=new Database();
                db.ConnectDB();
                LayoutInflater inflater=LayoutInflater.from(c);
                View vv=inflater.inflate(R.layout.edit,null);
                EditText editText=vv.findViewById(R.id.quan);
                AlertDialog.Builder builder=new AlertDialog.Builder(c);
                builder.setView(vv);
                builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String rs=db.RunDML("update products set quantity="+editText.getText()+" where name='"+viewHolder.textname.getText()+"'");
                        if(rs.equals("ok"))
                            Toast.makeText(c, "Updated successfully", Toast.LENGTH_SHORT).show();
                    }
                }).setNegativeButton("Thanks",null);
                builder.create().show();


            }
        });
        return convertView;
    }
}

