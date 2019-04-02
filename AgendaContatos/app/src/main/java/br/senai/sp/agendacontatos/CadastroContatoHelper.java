package br.senai.sp.agendacontatos;

import android.icu.lang.UCharacterEnums;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import br.senai.sp.conversores.Imagem;
import br.senai.sp.modelo.Contato;

public class CadastroContatoHelper {

    private EditText txtNome;
    private EditText txtEndereco;
    private EditText txtTelefone;
    private EditText txtEmail;
    private EditText txtLinkedin;
    private Contato contato;
    private ImageView imgFoto;
    private TextInputLayout layoutTxtNome;
    private TextInputLayout layoutTxtEndereco;
    private TextInputLayout layoutTxtTelefone;
    private TextInputLayout layoutTxtEmail;
    private TextInputLayout layoutTxtLinkedin;

    public CadastroContatoHelper(CadastroActivity activity){

        layoutTxtNome = activity.findViewById(R.id.layout_txt_nome);
        layoutTxtEndereco = activity.findViewById(R.id.layout_txt_endereco);
        layoutTxtTelefone = activity.findViewById(R.id.layout_txt_telefone);
        layoutTxtEmail = activity.findViewById(R.id.layout_txt_email);
        layoutTxtLinkedin = activity.findViewById(R.id.layout_txt_linkedin);

        txtNome = activity.findViewById(R.id.txt_nome);
        txtEndereco = activity.findViewById(R.id.txt_endereco);
        txtTelefone = activity.findViewById(R.id.txt_telefone);
        txtEmail = activity.findViewById(R.id.txt_email);
        txtLinkedin = activity.findViewById(R.id.txt_linkedin);
        imgFoto = activity.findViewById(R.id.img_contato_cadastro);

        contato = new Contato();
    }

    public Contato getContato(){

        contato.setNome(txtNome.getText().toString());
        contato.setEndereco(txtEndereco.getText().toString());
        contato.setTelefone(txtTelefone.getText().toString());
        contato.setEmail(txtEmail.getText().toString());
        contato.setLinkedin(txtLinkedin.getText().toString());
        contato.setFoto(Imagem.bitmapToArray(imgFoto.getDrawable()));

        return contato;
    }

    public void preencherFormulario(Contato contato){

        txtNome.setText(contato.getNome());
        txtEndereco.setText(contato.getEndereco());
        txtTelefone.setText(contato.getTelefone());
        txtEmail.setText(contato.getEmail());
        txtLinkedin.setText(contato.getLinkedin());

        if(contato.getFoto() != null){
            imgFoto.setImageBitmap(Imagem.arrayToBitmap(contato.getFoto()));
        }
        this.contato = contato;
    }

    public boolean validar(){

        boolean validado = true;

        if (txtNome.getText().toString().isEmpty()){
            layoutTxtNome.setErrorEnabled(true);
            layoutTxtNome.setError("Por favor, digite o nome");
            validado = false;
        } else{
            layoutTxtNome.setErrorEnabled(false);
        }

        if(txtEndereco.getText().toString().isEmpty()){
            layoutTxtEndereco.setErrorEnabled(true);
            layoutTxtEndereco.setError("Por favor, digite o endereço");
            validado = false;
        } else {
            layoutTxtEndereco.setErrorEnabled(false);
        }

        if(txtTelefone.getText().toString().isEmpty()){
            layoutTxtTelefone.setErrorEnabled(true);
            layoutTxtTelefone.setError("Por favor, digite o telefone");
            validado = false;
        } else {
            layoutTxtTelefone.setErrorEnabled(false);
        }

        if(txtEmail.getText().toString().isEmpty()){
            layoutTxtEmail.setErrorEnabled(true);
            layoutTxtEmail.setError("Por favor, digite o email");
            validado = false;
        } else {
            layoutTxtEmail.setErrorEnabled(false);
        }

        if(txtLinkedin.getText().toString().isEmpty()){
            layoutTxtLinkedin.setErrorEnabled(true);
            layoutTxtLinkedin.setError("Por favor, digite o endereço do Linkedin");
            validado = false;
        } else {
            layoutTxtLinkedin.setErrorEnabled(false);
        }

        return  validado;
    }

    public void limparCampos(){

        txtNome.setText(null);
        txtEndereco.setText(null);
        txtTelefone.setText(null);
        txtEmail.setText(null);
        txtLinkedin.setText(null);
        layoutTxtNome.setErrorEnabled(false);
        layoutTxtEndereco.setErrorEnabled(false);
        layoutTxtTelefone.setErrorEnabled(false);
        layoutTxtEmail.setErrorEnabled(false);
        layoutTxtLinkedin.setErrorEnabled(false);
        txtNome.requestFocus();

    }
}
