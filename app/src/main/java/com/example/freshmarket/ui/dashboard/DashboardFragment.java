package com.example.freshmarket.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.freshmarket.Adapterproduct;
import com.example.freshmarket.Database;
import com.example.freshmarket.Getproducts;
import com.example.freshmarket.LoginActivity;
import com.example.freshmarket.PicassoClient;
import com.example.freshmarket.R;
import com.example.freshmarket.databinding.FragmentDashboardBinding;
import com.example.freshmarket.product;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.sql.Connection;
import java.util.ArrayList;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private FragmentDashboardBinding binding;
    ListView listView;
    Getproducts getproducts=new Getproducts();
    Adapterproduct adapterproduct;
    com.example.freshmarket.product product;
    ArrayList<product> list;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        listView=root.findViewById(R.id.lstpro);
        list=new ArrayList<>(getproducts.GetData());
        adapterproduct=new Adapterproduct(getActivity(),list);
        listView.setAdapter(adapterproduct);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                product=list.get(position);
                LayoutInflater layoutInflater=LayoutInflater.from(getActivity());
                View view1=layoutInflater.inflate(R.layout.details,null);
                TextView txtname=view1.findViewById(R.id.txtname);
                TextView txtdetail=view1.findViewById(R.id.txtdetails);
                TextView txtprice=view1.findViewById(R.id.txtprice2);
                EditText txtqty=view1.findViewById(R.id.txtqty);
                ImageView imageView=view1.findViewById(R.id.imgproduct);
                Button button=view1.findViewById(R.id.btnadd);
                txtname.setText("Name:"+product.getName());
                txtdetail.setText("Details:"+product.getDetails());
                txtprice.setText("Price:"+product.getPrice());
                PicassoClient.downloadImage(getActivity(),product.getImage(),imageView);
                BottomSheetDialog dialog=new BottomSheetDialog(getActivity());
                dialog.setContentView(view1);
                dialog.show();
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Database database=new Database();
                       Connection conn= database.ConnectDB();
                        if (conn==null){
                            Toast.makeText(getActivity(),"No Internet connection ",Toast.LENGTH_LONG).show();

                        }
                        else {
                            String rs=database.RunDML("insert into carts values ('"+product.getName()+"','"+product.getPrice()+"','"+product.getPrice()+"','"+product.getImage()+"','"+txtqty.getText()+"','"+product.getId()+"','"+ LoginActivity.id +"' )");
                            Toast.makeText(getActivity(),"Item '"+product.getName()+"' has been add to cart ",Toast.LENGTH_LONG).show();
                        }

                        dialog.dismiss();

                    }
                });

            }
        });


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}