package com.example.freshmarket;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.ArrayList;

public class Adaptersection extends ArrayAdapter<Section> {

    Context c;
    ArrayList<Section> ass;

    public Adaptersection(Context context, ArrayList<Section> cont) {
        super(context, R.layout.section,cont);
        c=context;
        ass=cont;
    }

    class Holder
    {
        ImageView imgcategory;
        TextView txtname ;


    }

    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position

        final Section data = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view

        final Holder viewHolder; // view lookup cache stored in tag

        if (convertView == null) {

            viewHolder = new Holder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.section, parent, false);

            viewHolder.txtname = (TextView) convertView.findViewById(R.id.txtsection);
            viewHolder.imgcategory = (ImageView) convertView.findViewById(R.id.imgsection);



            convertView.setTag(viewHolder);

        } else {
            viewHolder = (Holder) convertView.getTag();
        }
        PicassoClient.downloadImage(c,data.getImage(),viewHolder.imgcategory);
        viewHolder.txtname.setText(data.getSecname());







        return convertView;
    }


}
