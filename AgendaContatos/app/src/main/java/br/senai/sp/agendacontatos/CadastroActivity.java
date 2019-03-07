package br.senai.sp.agendacontatos;

import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Icon;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import br.senai.sp.dao.ContatoDAO;
import br.senai.sp.modelo.Contato;

public class CadastroActivity extends AppCompatActivity {

    private  CadastroContatoHelper helper;
    private TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        helper = new CadastroContatoHelper(this);

        text = findViewById(R.id.txt_titulo);

        Intent intent = getIntent();
        Contato contato = (Contato) intent.getSerializableExtra("contato");

        if(contato != null){
            helper.preencherFormulario(contato);
            text.setText("Atualizar Contato");
        } else {
            text.setText("Novo Contato");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_cadastro_contatos, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public  boolean onOptionsItemSelected(MenuItem item){

        final Contato contato = helper.getContato();
        final ContatoDAO dao = new ContatoDAO(this);

        switch (item.getItemId()){

            case R.id.menu_salvar:

                if(helper.validar()){
                    if(contato.getId() == 0){
                        dao.salvar(contato);
                        dao.close();
                        finish();

                    } else {
                        dao.atualizar(contato);
                        dao.close();
                        finish();
                    }
                }
                break;

            case R.id.menu_deletar:

                if(contato.getId() != 0){
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);

                    builder.setTitle("Excluir contato");
                    builder.setMessage("Confirma a exclusão do contato " + contato.getNome() + "?");


                    builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            dao.excluir(contato);

                            Toast.makeText(CadastroActivity.this, "Excluído com sucesso", Toast.LENGTH_LONG).show();

                            dao.close();
                            finish();

                        }
                    });

                    builder.setNegativeButton("Não",null);

                    builder.create().show();
                }


                break;

            case R.id.menu_limpar:
                helper.limparCampos();
                Toast.makeText(CadastroActivity.this, "Campos Limpos", Toast.LENGTH_LONG).show();
                break;

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }



}
