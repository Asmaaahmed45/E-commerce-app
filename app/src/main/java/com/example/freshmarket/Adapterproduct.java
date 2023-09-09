package com.example.freshmarket;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class Adapterproduct extends ArrayAdapter<product> {
    Context c;
    ArrayList<product> ass;

    public Adapterproduct(Context context, ArrayList<product> cont) {
        super(context, R.layout.product_layout,cont);
        c=context;
        ass=cont;
    }

    class Holder
    {
        public ImageView imgpro;
       public TextView txtname ,txtprice,txtdetails,txtsection;

    }

    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position

        final product data = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view

        final Holder viewHolder; // view lookup cache stored in tag

        if (convertView == null) {

            viewHolder = new Holder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.product_layout, parent, false);

            viewHolder.txtname = (TextView) convertView.findViewById(R.id.name_of_item);
            viewHolder.txtprice = (TextView) convertView.findViewById(R.id.price_of_item);
            viewHolder.txtdetails= (TextView) convertView.findViewById(R.id.Description_of_item);
            viewHolder.txtsection = (TextView) convertView.findViewById(R.id.section_of_item);
            viewHolder.imgpro= (ImageView) convertView.findViewById(R.id.search_image);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (Holder) convertView.getTag();
        }
        PicassoClient.downloadImage(c,data.getImage(),viewHolder.imgpro);
        viewHolder.txtname.setText("Name:"+data.getName());
        if (Float.parseFloat(data.getDiscount())==0)
            viewHolder.txtprice.setText("Price:"+data.getPrice()+"LE");
        else {
            double price=Float.parseFloat(data.getPrice()) * ((100-Float.parseFloat(data.getDiscount()))*0.01);
            //data.setPrice(String.valueOf(price));
            viewHolder.txtprice.setText("Price:"+String.valueOf(price)+"LE");
        }
        viewHolder.txtdetails.setText("Details:"+data.getDetails());
        viewHolder.txtsection.setText("Section:"+data.getSection());
        return convertView;
    }

}
