package br.senai.sp.agendacontatos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import br.senai.sp.dao.ContatoDAO;
import br.senai.sp.modelo.Contato;

public class CadastroActivity extends AppCompatActivity {

    private  CadastroContatoHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        helper = new CadastroContatoHelper(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_cadastro_contatos, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public  boolean onOptionsItemSelected(MenuItem item){

        switch (item.getItemId()){
            case R.id.menu_salvar:

                Contato contato = helper.getContato();
                ContatoDAO dao = new ContatoDAO(this);

                if(contato.getId() == 0){
                    dao.salvar(contato);
                } else {
                    dao.atualizar(contato);
                }

                dao.close();
                finish();
                break;

            case R.id.menu_deletar:
                break;

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
