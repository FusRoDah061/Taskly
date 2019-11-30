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

import java.util.List;

import br.com.ifsp.aluno.allex.taskly.Constantes;
import br.com.ifsp.aluno.allex.taskly.R;
import br.com.ifsp.aluno.allex.taskly.enums.EStatusTarefa;
import br.com.ifsp.aluno.allex.taskly.events.OnTarefaClickListener;
import br.com.ifsp.aluno.allex.taskly.events.OnTarefaLongClickListener;
import br.com.ifsp.aluno.allex.taskly.events.OnTarefaStatusChangedListener;
import br.com.ifsp.aluno.allex.taskly.model.Tarefa;

public class TarefaRecyclerViewAdapter extends RecyclerView.Adapter<TarefaRecyclerViewAdapter.ViewHolder> {

    private OnTarefaStatusChangedListener onTarefaStatusChangedListener;
    private OnTarefaLongClickListener onTarefaLongClickListener;
    private OnTarefaClickListener onTarefaClickListener;

    private List<Tarefa> tarefas;

    public TarefaRecyclerViewAdapter(List<Tarefa> tarefas) {
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

        holder.cbTarefaConcluida.setChecked(EStatusTarefa.CONCLUIDA.equals(tarefa.getStatus()));

        holder.tvDescricaoTarefa.setText(tarefa.getDescricao());
        holder.tvDataTarefa.setText(Constantes.DATE_TIME_FORMAT.format(tarefa.getDataLimite()));

        if(tarefa.isSincronizada())
            holder.ivTarefaSincronizada.setVisibility(View.VISIBLE);
        else
            holder.ivTarefaSincronizada.setVisibility(View.INVISIBLE);
    }

    @Override
    public int getItemCount() {
        return tarefas.size();
    }

    public void setOnTarefaStatusChangedListener(OnTarefaStatusChangedListener onTarefaStatusChangedListener) {
        this.onTarefaStatusChangedListener = onTarefaStatusChangedListener;
    }

    public void setOnLongClickListener(OnTarefaLongClickListener onLongClickListener) {
        this.onTarefaLongClickListener = onLongClickListener;
    }

    public void setOnTarefaClickListener(OnTarefaClickListener onTarefaClickListener) {
        this.onTarefaClickListener = onTarefaClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements CompoundButton.OnCheckedChangeListener, View.OnLongClickListener, View.OnClickListener {

        public CheckBox cbTarefaConcluida;
        public TextView tvDescricaoTarefa;
        public TextView tvDataTarefa;
        public ImageView ivTarefaSincronizada;

        public ViewHolder(View itemView) {
            super(itemView);

            cbTarefaConcluida = (CheckBox) itemView.findViewById(R.id.cbTarefaConcluida);
            tvDescricaoTarefa = (TextView) itemView.findViewById(R.id.tvDescricaoTarefa);
            tvDataTarefa = (TextView) itemView.findViewById(R.id.tvDataTarefa);
            ivTarefaSincronizada = (ImageView) itemView.findViewById(R.id.ivTarefaSincronizada);

            cbTarefaConcluida.setOnCheckedChangeListener(this);

            itemView.setOnLongClickListener(this);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(onTarefaStatusChangedListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    onTarefaStatusChangedListener.onTarefaStatusChanged(buttonView, position, isChecked, tarefas.get(position));
                }
            }
        }

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

        @Override
        public void onClick(View v) {
            if(onTarefaClickListener != null) {
                int position = getAdapterPosition();

                if (position != RecyclerView.NO_POSITION) {
                    onTarefaClickListener.onTarefaClicked(v, position, tarefas.get(position));
                }
            }
        }
    }
}
