package com.example.ac2atv5;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerViewAlunos;
    private AlunoAdapter alunoAdapter;
    private ArrayList<Aluno> alunosList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Corrigido para activity_main

        recyclerViewAlunos = findViewById(R.id.recyclerViewAlunos);
        recyclerViewAlunos.setLayoutManager(new LinearLayoutManager(this));
        Button buttonCadastro = findViewById(R.id.buttonCadastro);

        alunosList = new ArrayList<>();
        alunoAdapter = new AlunoAdapter(alunosList);
        recyclerViewAlunos.setAdapter(alunoAdapter);

        carregarAlunos();

        buttonCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CadastroAlunoActivity.class);
                startActivity(intent);
            }
        });
    }

    private void carregarAlunos() {
        MockApi.getAlunos(new MockApi.OnGetAlunosListener() {
            @Override
            public void onGetAlunos(ArrayList<Aluno> alunos) {
                alunosList.clear();
                alunosList.addAll(alunos);
                alunoAdapter.notifyDataSetChanged();
            }
        });
    }
}
