package com.example.freshmarket.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.freshmarket.Adaptersection;
import com.example.freshmarket.Getsection;
import com.example.freshmarket.ProductsActivity;
import com.example.freshmarket.R;
import com.example.freshmarket.Section;
import com.example.freshmarket.databinding.FragmentHomeBinding;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    GridView gridView;
    Getsection getsection=new Getsection();
    Adaptersection adaptersection;
    Section section;
    ArrayList<Section> list;
    public static String secname;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        gridView=root.findViewById(R.id.dep);
        list=new ArrayList<>(getsection.GetData());
        adaptersection=new Adaptersection(getActivity(),list);
        gridView.setAdapter(adaptersection);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                section=list.get(position);
                secname=section.getSecname();
                startActivity(new Intent(getActivity(), ProductsActivity.class));
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