package app.com.bugdroidbuilder.paulo.emergencyhelper.view.components;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import app.com.bugdroidbuilder.paulo.emergencyhelper.R;
import app.com.bugdroidbuilder.paulo.emergencyhelper.model.TelefoneUtil;

/**
 * Created by paulo on 28/06/16.
 */
public class TelefonesAdapter extends RecyclerView.Adapter<TelefonesAdapter.MyViewHolder> {

    private List<TelefoneUtil> listaTelefones;

    public TelefonesAdapter(List<TelefoneUtil> listaTelefones) {
        this.listaTelefones = listaTelefones;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_telefone, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        TelefoneUtil telefoneUtil = listaTelefones.get(position);
        holder.descricao.setText(telefoneUtil.getDescricao());
    }

    @Override
    public int getItemCount() {
        return listaTelefones.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView descricao;

        public MyViewHolder(View view) {
            super(view);
            descricao = (TextView) view.findViewById(R.id.telefones_descricao);

        }
    }
}