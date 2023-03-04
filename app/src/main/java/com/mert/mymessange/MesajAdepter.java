package com.mert.mymessange;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mert.mymessange.databinding.RecRowBinding;

import java.util.ArrayList;

public class MesajAdepter extends RecyclerView.Adapter<MesajAdepter.MesajHolder> {
    private ArrayList<Mesaj> mesajArrayList;

    public MesajAdepter(ArrayList<Mesaj> mesajArrayList) {
        this.mesajArrayList = mesajArrayList;
    }


    @NonNull
    @Override
    public MesajHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       RecRowBinding recRowBinding = RecRowBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
       return new MesajHolder(recRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MesajHolder holder, int position) {
        holder.recRowBinding.textView.setText(mesajArrayList.get(position).message);
    }

    @Override
    public int getItemCount() {
        return mesajArrayList.size();
    }

    class MesajHolder extends RecyclerView.ViewHolder{
        RecRowBinding recRowBinding;
        public MesajHolder(RecRowBinding recRowBinding) {
            super(recRowBinding.getRoot());
            this.recRowBinding = recRowBinding;
        }
    }
}
