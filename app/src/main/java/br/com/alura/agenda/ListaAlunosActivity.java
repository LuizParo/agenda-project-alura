package br.com.alura.agenda;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import br.com.alura.agenda.dao.AlunoDao;
import br.com.alura.agenda.model.Aluno;

public class ListaAlunosActivity extends AppCompatActivity {

    private ListView listaAlunos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_alunos);

        this.listaAlunos = (ListView) this.findViewById(R.id.lista_alunos);
        this.listaAlunos.setOnItemClickListener((lista, item, position, id) -> {
            Aluno aluno = (Aluno) ListaAlunosActivity.this.listaAlunos.getItemAtPosition(position);

            Intent vaiProFormulario = new Intent(ListaAlunosActivity.this, FormularioActivity.class);
            vaiProFormulario.putExtra("aluno", aluno);

            this.startActivity(vaiProFormulario);
        });

        Button novoAluno = (Button) this.findViewById(R.id.novo_aluno);
        novoAluno.setOnClickListener(view -> {
            Intent vaiProFormulario = new Intent(ListaAlunosActivity.this, FormularioActivity.class);
            this.startActivity(vaiProFormulario);
        });

        this.registerForContextMenu(this.listaAlunos);
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.carregaLista();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuItem botaoDeletar = menu.add("Remover");
        botaoDeletar.setOnMenuItemClickListener(item -> {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
            Aluno aluno = (Aluno) ListaAlunosActivity.this.listaAlunos.getItemAtPosition(info.position);

            AlunoDao dao = new AlunoDao(ListaAlunosActivity.this);
            dao.remover(aluno.getId());
            dao.close();

            Toast.makeText(ListaAlunosActivity.this, String.format("Aluno '%s' removido com sucesso!", aluno.getNome()), Toast.LENGTH_SHORT)
                    .show();

            this.carregaLista();
            return false;
        });
    }

    private void carregaLista() {
        AlunoDao dao = new AlunoDao(this);
        List<Aluno> alunos = dao.buscaAlunos();
        dao.close();

        ArrayAdapter<Aluno> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, alunos);
        ListView listaAlunos = (ListView) this.findViewById(R.id.lista_alunos);
        listaAlunos.setAdapter(adapter);
    }
}