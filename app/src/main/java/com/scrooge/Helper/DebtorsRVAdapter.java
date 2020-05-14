package com.scrooge.Helper;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.scrooge.Model.Debtor;
import com.scrooge.R;

import java.util.List;

public class DebtorsRVAdapter extends  RecyclerView.Adapter<DebtorsRVAdapter.MyViewHolder> {
    private List<Debtor> debtors;
    private OnDebtorClick onDebtorClick;

    public DebtorsRVAdapter(List<Debtor> debtors, OnDebtorClick onDebtorClick) {
        this.debtors = debtors;
        this.onDebtorClick = onDebtorClick;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType ) {
        View v =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.deptor_layout, parent, false);

        MyViewHolder vh = new MyViewHolder(v,onDebtorClick);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position)  {
        Debtor currentDebtor = debtors.get(position);
        holder.debtorName.setText(currentDebtor.getName());
        holder.debtorSum.setText(String.valueOf(currentDebtor.getDebt()) + " zł");
    }

    @Override
    public int getItemCount() {
        return debtors.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener , View.OnLongClickListener {
        // each data item is just a string in this case
        public TextView debtorName;
        public TextView debtorSum;
        OnDebtorClick onDebtorClick;

        public MyViewHolder(View v,OnDebtorClick onDebtorClick) {
            super(v);
            debtorName = v.findViewById(R.id.debtorTVid);
            debtorSum = v.findViewById(R.id.debtorTVDebtid);
            this.onDebtorClick =onDebtorClick;
            v.setOnClickListener(this);
            v.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onDebtorClick.onDebtClick(getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View v) {
            onDebtorClick.onDebtLongClick(getAdapterPosition());
            return true;
        }
    }

}
