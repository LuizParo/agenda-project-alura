package br.com.alura.agenda;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class ListaAlunosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_alunos);

        String[] alunos = {"Daniel", "Ronaldo", "Jeferson", "Felipe"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, alunos);

        ListView listaAlunos = (ListView) this.findViewById(R.id.lista_alunos);
        listaAlunos.setAdapter(adapter);

        Button novoAluno = (Button) this.findViewById(R.id.novo_aluno);
        novoAluno.setOnClickListener((view) -> {
            Intent vaiProFormulario = new Intent(ListaAlunosActivity.this, FormularioActivity.class);
            this.startActivity(vaiProFormulario);
        });
    }
}