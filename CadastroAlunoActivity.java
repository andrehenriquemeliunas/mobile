package com.example.ac2atv5;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CadastroAlunoActivity extends AppCompatActivity {

    private EditText editTextRA, editTextNome, editTextCEP, editTextLogradouro, editTextComplemento, editTextBairro, editTextCidade, editTextUF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_aluno);
        editTextRA = findViewById(R.id.editTextRA);
        editTextNome = findViewById(R.id.editTextNome);
        editTextCEP = findViewById(R.id.editTextCEP);
        editTextLogradouro = findViewById(R.id.editTextLogradouro);
        editTextComplemento = findViewById(R.id.editTextComplemento);
        editTextBairro = findViewById(R.id.editTextBairro);
        editTextCidade = findViewById(R.id.editTextCidade);
        editTextUF = findViewById(R.id.editTextUF);
        Button buttonSalvar = findViewById(R.id.buttonSalvar);
        Button buttonBuscarCEP = findViewById(R.id.buttonBuscarCEP);
        buttonSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarAluno();
            }
        });

        buttonBuscarCEP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscarCEP();
            }
        });
    }

    private void buscarCEP() {
        String cep = editTextCEP.getText().toString();
        ServiceCep service = new ServiceCep();
        service.buscarCEP(cep, this, new ServiceCep.ViaCEPResponseListener() {
            @Override
            public void onCEPSuccess(respostaCep response) {
                if (response != null) {
                    editTextLogradouro.setText(response.getLogradouro());
                    editTextComplemento.setText(response.getComplemento());
                    editTextBairro.setText(response.getBairro());
                    editTextCidade.setText(response.getLocalidade());
                    editTextUF.setText(response.getUF());
                } else {
                    Toast.makeText(CadastroAlunoActivity.this, "CEP não encontrado", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCEPError(String error) {
                Toast.makeText(CadastroAlunoActivity.this, "Erro ao consultar CEP: " + error, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void salvarAluno() {
        int ra = Integer.parseInt(editTextRA.getText().toString());
        String nome = editTextNome.getText().toString();
        String cep = editTextCEP.getText().toString();
        String logradouro = editTextLogradouro.getText().toString();
        String complemento = editTextComplemento.getText().toString();
        String bairro = editTextBairro.getText().toString();
        String cidade = editTextCidade.getText().toString();
        String uf = editTextUF.getText().toString();

        Aluno aluno = new Aluno(ra, nome, cep, logradouro, complemento, bairro, cidade, uf);

        MockApi.salvarAluno(aluno, new MockApi.OnSaveAlunoListener() {
            @Override
            public void onSaveAlunoComplete(boolean success) {
                if (success) {
                    Toast.makeText(CadastroAlunoActivity.this, "Aluno salvo com sucesso!", Toast.LENGTH_SHORT).show();
                    editTextRA.setText("");
                    editTextNome.setText("");
                    editTextCEP.setText("");
                    editTextLogradouro.setText("");
                    editTextComplemento.setText("");
                    editTextBairro.setText("");
                    editTextCidade.setText("");
                    editTextUF.setText("");
                } else {
                    Toast.makeText(CadastroAlunoActivity.this, "Falha ao salvar aluno", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}