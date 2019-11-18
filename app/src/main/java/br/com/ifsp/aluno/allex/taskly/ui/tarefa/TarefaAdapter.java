package br.com.ifsp.aluno.allex.taskly.ui.tarefa;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import br.com.ifsp.aluno.allex.taskly.R;
import br.com.ifsp.aluno.allex.taskly.enums.EStatusTarefa;
import br.com.ifsp.aluno.allex.taskly.model.Tarefa;

public class TarefaAdapter extends RecyclerView.Adapter<TarefaAdapter.ViewHolder> {

    private OnTarefaStatusChangedListener onTarefaStatusChangedListener;
    private OnTarefaLongClickListener onTarefaLongClickListener;

    public interface OnTarefaStatusChangedListener {
        void onTarefaStatusChanged(View view, int position, boolean isChecked, Tarefa tarefa);
    }

    public interface OnTarefaLongClickListener {
        void onTarefaLongClicked(View view, int position, Tarefa tarefa);
    }

    public void setOnTarefaStatusChangedListener(OnTarefaStatusChangedListener onTarefaStatusChangedListener) {
        this.onTarefaStatusChangedListener = onTarefaStatusChangedListener;
    }

    public void setOnLongClickListener(OnTarefaLongClickListener onLongClickListener) {
        this.onTarefaLongClickListener = onLongClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public CheckBox cbTarefaConcluida;
        public TextView tvDescricaoTarefa;
        public TextView tvDataTarefa;
        public ImageView ivContaTarefa;

        public ViewHolder(View itemView) {
            super(itemView);

            cbTarefaConcluida = (CheckBox) itemView.findViewById(R.id.cbTarefaConcluida);
            tvDescricaoTarefa = (TextView) itemView.findViewById(R.id.tvDescricaoTarefa);
            tvDataTarefa = (TextView) itemView.findViewById(R.id.tvDataTarefa);
            ivContaTarefa = (ImageView) itemView.findViewById(R.id.ivContaTarefa);

            cbTarefaConcluida.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(onTarefaStatusChangedListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            onTarefaStatusChangedListener.onTarefaStatusChanged(buttonView, position, isChecked, tarefas.get(position));
                        }
                    }
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if(onTarefaLongClickListener != null) {
                        int position = getAdapterPosition();

                        if (position != RecyclerView.NO_POSITION) {
                            onTarefaLongClickListener.onTarefaLongClicked(v, position, tarefas.get(position));
                            return true;
                        }
                    }

                    return false;
                }
            });
        }
    }

    private List<Tarefa> tarefas;

    public TarefaAdapter(List<Tarefa> tarefas) {
        this.tarefas = tarefas;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.item_tarefa, parent, false);

        return new ViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Tarefa tarefa = tarefas.get(position);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());

        holder.cbTarefaConcluida.setChecked(EStatusTarefa.CONCLUIDA.equals(tarefa.getStatus()));

        holder.tvDescricaoTarefa.setText(tarefa.getDescricao());
        holder.tvDataTarefa.setText(sdf.format(tarefa.getDia()));

        if(tarefa.isSincronizada()){
            //TODO: Exibir foto da conta google sincronizada
        }
    }

    @Override
    public int getItemCount() {
        return tarefas.size();
    }

}
