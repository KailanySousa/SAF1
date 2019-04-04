package br.senai.sp.agendacontatos;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.security.PrivateKey;
import java.util.List;

import br.senai.sp.adapter.ContatosAdapter;
import br.senai.sp.dao.ContatoDAO;
import br.senai.sp.modelo.Contato;

public class MainActivity extends AppCompatActivity {

    private ImageButton btnNovo;
    private ListView listaContatos;
    private ImageView btnLigar;
    private TextView txtTelefone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnNovo = findViewById(R.id.bt_novo_contato);
        listaContatos = findViewById(R.id.list_contatos);
        btnLigar = findViewById(R.id.btn_ligar_lista);
        txtTelefone = findViewById(R.id.txt_telefone);

        btnNovo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent abrirCadastro = new Intent(MainActivity.this, CadastroActivity.class);
                startActivity(abrirCadastro);
            }
        });

        registerForContextMenu(listaContatos);

        listaContatos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Contato contato = (Contato) listaContatos.getItemAtPosition(position);

                Intent cadastro = new Intent(MainActivity.this, CadastroActivity.class);

                cadastro.putExtra("contato", contato);

                startActivity(cadastro);
            }
        });
    }

    @Override
    protected void onResume() {
        carregarLista();
        super.onResume();
    }

    private void carregarLista(){
        ContatoDAO dao = new ContatoDAO(this);
        List<Contato> contatos = dao.getContatos();
        dao.close();

        // *** Criação do Adapter que carrega os filmes na ListView
        // O primeiro parâmetro é qual activity será utilizado
        //*** utilizando um layout pronto (simple_list_item_1) - padrão

        //ArrayAdapter<Contato> ListaContatosAdapter = new ArrayAdapter<Contato>(this, android.R.layout.simple_list_item_1,contatos);

        ContatosAdapter ListaContatosAdapter = new ContatosAdapter(this, contatos);
        listaContatos.setAdapter(ListaContatosAdapter);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        MenuInflater inflar = getMenuInflater();
        inflar.inflate(R.menu.menu_context_lista_contatos, menu);

    }

    @Override
    public boolean onContextItemSelected(MenuItem item){

        switch (item.getItemId()){
            case R.id.menu_deletar:

                final ContatoDAO dao = new ContatoDAO(MainActivity.this);

                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

                final  Contato contato = (Contato) listaContatos.getItemAtPosition(info.position);

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Excluir Contato");

                builder.setMessage("Confirma a exclusão do contato " + contato.getNome() + "?" );

                builder.setPositiveButton("sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dao.excluir(contato);
                        Toast.makeText(MainActivity.this, "Excluído com sucesso", Toast.LENGTH_SHORT).show();
                        dao.close();
                        carregarLista();
                    }
                });

                builder.setNegativeButton("Não", null);
                builder.create().show();

            case R.id.menu_ligar:

                String numero = "tel:" + txtTelefone.getText().toString();

                startActivity(new Intent(Intent.ACTION_DIAL));
                startActivity(new Intent(Intent.ACTION_DIAL));




        }


        return super.onContextItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){


    }

    @Override
    protected void onPause() {
        super.onPause();
    }

}
