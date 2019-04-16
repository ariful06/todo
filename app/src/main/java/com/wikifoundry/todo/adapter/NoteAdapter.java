package com.wikifoundry.todo.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.wikifoundry.todo.R;
import com.wikifoundry.todo.database.entity.Note;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    Context ctx;
    List<Note> noteItems;

    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onOptionClickListener(View view, int position);
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public NoteAdapter(Context context, List<Note> list) {
        this.ctx = context;
        this.noteItems = list;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item_row, parent, false);
        return new NoteViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, final int position) {
        holder.title.setText(noteItems.get(position).getNoteTitle());
        holder.description.setText(noteItems.get(position).getNoteDescription());
        holder.time.setText(noteItems.get(position).getCurrentDateAndTime());
    }


    @Override
    public int getItemCount() {
        return noteItems.size();
    }

    public void removeItem(int position) {
        noteItems.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, noteItems.size());
    }

    public void restoreItem(Note noteItem, int position) {
        noteItems.add(position, noteItem);
        // notify item added by position
        notifyItemInserted(position);
    }


    public void notify(List<Note> list) {
        noteItems = new ArrayList<>();
        if (list != null) {
            noteItems.clear();
            noteItems = list;
        } else {
            noteItems = list;
        }
        notifyDataSetChanged();
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder {

        TextView title, description, option, time;

        public NoteViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
            time = itemView.findViewById(R.id.dateTime);

            option = itemView.findViewById(R.id.option);
            option.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onOptionClickListener(v, position);
                        }
                    }
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(v, position);
                        }
                    }
                }
            });
        }
    }


}
