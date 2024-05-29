package com.example.ac2atv5;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class AlunoAdapter extends RecyclerView.Adapter<AlunoAdapter.AlunoViewHolder> {
    private ArrayList<Aluno> alunosList;

    public AlunoAdapter(ArrayList<Aluno> alunosList) {
        this.alunosList = alunosList;
    }

    @NonNull
    @Override
    public AlunoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_aluno, parent, false);
        return new AlunoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlunoViewHolder holder, int position) {
        Aluno aluno = alunosList.get(position);
        holder.textViewRA.setText(String.valueOf(aluno.getRa()));
        holder.textViewNome.setText(aluno.getNome());
    }

    @Override
    public int getItemCount() {
        return alunosList.size();
    }

    public static class AlunoViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewRA;
        public TextView textViewNome;

        public AlunoViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewRA = itemView.findViewById(R.id.textViewRA);
            textViewNome = itemView.findViewById(R.id.textViewNome);
        }
    }

}
