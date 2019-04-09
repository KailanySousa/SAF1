package br.senai.sp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.senai.sp.agendacontatos.R;
import br.senai.sp.conversores.Imagem;
import br.senai.sp.modelo.Contato;

public class ContatosAdapter extends BaseAdapter {

    private Context context;
    private List<Contato> contatos;

    public ContatosAdapter(Context context, List<Contato> contatos){
        //esse contexto recebe o contexto que será passado quando algo chamar esse metoco
        this.context = context;

        //a lista de contatos recebe a lista que será passada quando algo chamar esse metodo
        this.contatos = contatos;
    }
    @Override
    public int getCount() {
        return contatos.size(); //retorna o tamanho da lista de contatos, para que o getCount saiba a quantidade de linhas
    }

    @Override
    public Object getItem(int position) {
        return contatos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return contatos.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Contato contato = contatos.get(position); //pega um contato da lista na posição que foi passada pelo getView

        //inflar o layout dentro desse contexto
        LayoutInflater inflarLayout = LayoutInflater.from(context);

        View view = inflarLayout.inflate(R.layout.lista_contatos, null);

        TextView txtNome = view.findViewById(R.id.txt_nome);
        txtNome.setText(contato.getNome());

        TextView txtEmail = view.findViewById(R.id.txt_email);
        txtEmail.setText(contato.getEmail());

        TextView txtTelefone = view.findViewById(R.id.txt_telefone_lista);
        txtTelefone.setText(contato.getTelefone());

        if(contato.getFoto() != null) {
            ImageView foto = view.findViewById(R.id.img_contato);
            foto.setImageBitmap(Imagem.arrayToBitmap(contato.getFoto()));
        }

        return view;
    }
}
