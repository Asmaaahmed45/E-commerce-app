package com.example.freshmarket.ui.gallery;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.freshmarket.Adapterproduct;
import com.example.freshmarket.Adaptersection;
import com.example.freshmarket.Getproducts;
import com.example.freshmarket.Getsection;
import com.example.freshmarket.MainUserActivity;
import com.example.freshmarket.R;
import com.example.freshmarket.Section;
import com.example.freshmarket.databinding.FragmentGalleryBinding;
import com.example.freshmarket.product;

import java.util.ArrayList;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;
    private FragmentGalleryBinding binding;
    ListView listView;
    Getproducts getproducts=new Getproducts();
    Adapterproduct adapterproduct;
    product product;
    ArrayList<product> list;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        listView=root.findViewById(R.id.lstproduct);
        list=new ArrayList<>(getproducts.GetData());
        adapterproduct=new Adapterproduct(getActivity(),list);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                product=list.get(position);
                //startActivity(new Intent(getActivity(), MainUserActivity.class));
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