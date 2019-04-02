package br.senai.sp.agendacontatos;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.FileSystemNotFoundException;

import br.senai.sp.dao.ContatoDAO;
import br.senai.sp.modelo.Contato;

public class CadastroActivity extends AppCompatActivity {

    public static final int CAMERA_REQUEST = 8;
    public static final int GALERIA_REQUEST = 16;
    private  CadastroContatoHelper helper;
    private TextView text;
    private ImageView imgContato;
    private Button btnCamera;
    private Button btnGaleria;
    private String caminhoFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        helper = new CadastroContatoHelper(this);

        text = findViewById(R.id.txt_titulo);

        imgContato = findViewById(R.id.img_contato_cadastro);
        btnCamera = findViewById(R.id.btn_camera);
        btnGaleria = findViewById(R.id.btn_galeria);

        Intent intent = getIntent();
        Contato contato = (Contato) intent.getSerializableExtra("contato");

        if(contato != null){
            helper.preencherFormulario(contato);
            text.setText("Atualizar Contato");
        } else {
            text.setText("Novo Contato");
        }

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent abrirCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                String nomeArquivo = "/IMG_" + System.currentTimeMillis() + ".jpg";

                //caminho da foto, a foto ficara no external paths declarado em R.xml.provider.paths.xml, num arquivo com o nome foto.jpg
                caminhoFoto = getExternalFilesDir(null) + nomeArquivo;

                Log.d("CAMINHO_ARQUIVO", caminhoFoto);

                File arquivoFoto = new File(caminhoFoto);

                Uri fotoUri = FileProvider.getUriForFile(CadastroActivity.this, BuildConfig.APPLICATION_ID + ".provider", arquivoFoto);

                abrirCamera.putExtra(MediaStore.EXTRA_OUTPUT, fotoUri);
                startActivityForResult(abrirCamera, CAMERA_REQUEST);
            }
        });

        btnGaleria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent abrirGaleria = new Intent(Intent.ACTION_GET_CONTENT);
                abrirGaleria.setType("image/*");
                startActivityForResult(abrirGaleria, GALERIA_REQUEST);

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){

        if(resultCode != RESULT_CANCELED){

           try {
               if(requestCode == GALERIA_REQUEST){
                   InputStream inputStream = getContentResolver().openInputStream(data.getData());

                   Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                   imgContato.setImageBitmap(bitmap);
               }

               if (requestCode == CAMERA_REQUEST){
                    Bitmap bitmap = BitmapFactory.decodeFile(caminhoFoto);
                    Bitmap bitmapReduzido = Bitmap.createScaledBitmap(bitmap, 300,300, true);

                    imgContato.setImageBitmap(bitmapReduzido);
               }
           } catch (FileNotFoundException e){
               e.printStackTrace();
           }

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
