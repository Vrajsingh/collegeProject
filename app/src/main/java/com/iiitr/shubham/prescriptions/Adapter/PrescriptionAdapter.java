package com.iiitr.shubham.prescriptions.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.iiitr.shubham.prescriptions.Classes.PrescriptionObject;
import com.iiitr.shubham.prescriptions.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PrescriptionAdapter extends RecyclerView.Adapter{

    private ArrayList<PrescriptionObject> items;
    private ItemClickListener listener;

    public PrescriptionAdapter(ArrayList<PrescriptionObject> list,ItemClickListener listener)
    {
        items = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.prescription_cardview,null);
        return new PrescriptionHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((PrescriptionHolder)holder).bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    private class PrescriptionHolder extends RecyclerView.ViewHolder
    {
        public TextView nameTv,ageTv,genderTv;
        View view;
        public PrescriptionHolder(View itemView) {
            super(itemView);
            nameTv = itemView.findViewById(R.id.name_view);
            ageTv = itemView.findViewById(R.id.age_view);
            genderTv = itemView.findViewById(R.id.gender_view);
            view = itemView;
        }
        public void bind(final PrescriptionObject item)
        {
            item.setAge(ageTv.getText().toString());
            item.setGender(genderTv.getText().toString());
            item.setName(nameTv.getText().toString());
            items.add(item);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClicked(item);
                }
            });
        }

    }
    public interface ItemClickListener{
        void onItemClicked(PrescriptionObject v);
    };
}
