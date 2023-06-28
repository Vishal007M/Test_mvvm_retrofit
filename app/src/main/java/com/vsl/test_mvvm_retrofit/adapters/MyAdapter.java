package com.vsl.test_mvvm_retrofit.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vsl.test_mvvm_retrofit.databinding.SingleRowBinding;
import com.vsl.test_mvvm_retrofit.model.Student;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.myViewHolder> {

    //USING FOR LISTVIEW  --LINEAR LAYOUT
    List<Student> studentList;

    public MyAdapter(List<Student> studentList) {
        this.studentList = studentList;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        SingleRowBinding binding = SingleRowBinding.inflate(inflater, parent, false);
        return new myViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        Student sCurrent = studentList.get(position);
        holder.singleRowBinding.setStudentObj(sCurrent);
    }

    @Override
    public int getItemCount() {
        if (this.studentList != null) {
            return this.studentList.size();
        }
        return 0;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateStudentList(List<Student> list) {
        this.studentList = list;
        notifyDataSetChanged();
    }

    static class myViewHolder extends RecyclerView.ViewHolder {
        SingleRowBinding singleRowBinding;

        public myViewHolder(@NonNull SingleRowBinding singleRowBinding) {
            super(singleRowBinding.getRoot());
            this.singleRowBinding = singleRowBinding;
        }
    }

}