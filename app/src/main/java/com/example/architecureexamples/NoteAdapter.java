package com.example.architecureexamples;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends ListAdapter<Note, NoteAdapter.NoteHolder> {
    //list of notes
    //private List<Note> notes = new ArrayList<>();
    OnItemClickListener listener;

    protected NoteAdapter() {

        super(diffCallback);
    }

    private static final DiffUtil.ItemCallback<Note> diffCallback = new DiffUtil.ItemCallback<Note>() {
        @Override
        public boolean areItemsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            if(oldItem.getId() == newItem.getId()){
                return true;
            }else {
                return false;
            }
        }

        @Override
        public boolean areContentsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.getTitle().equals(newItem.getTitle()) && oldItem.getDescription().equals(newItem.getDescription())
                    && oldItem.getPriority() == newItem.getPriority();
        }
    };

    class NoteHolder extends RecyclerView.ViewHolder{
        TextView textViewTitle;
        TextView textViewDescription, textViewPrioty;

        public NoteHolder(View itemView){
            super(itemView);
            textViewDescription = itemView.findViewById(R.id.textView_description);
            textViewPrioty = itemView.findViewById(R.id.textView_priority);
            textViewTitle = itemView.findViewById(R.id.textView_Title);

            final int positionView = getAdapterPosition();
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null && positionView != RecyclerView.NO_POSITION);
                    listener.onItemClick(getItem(positionView));
                }
            });
        }
    }
    /**
     * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent
     * an item.
     * <p>
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     * <p>
     * The new ViewHolder will be used to display items of the adapter using
     * {@link #onBindViewHolder(ViewHolder, int, List)}. Since it will be re-used to display
     * different items in the data set, it is a good idea to cache references to sub views of
     * the View to avoid unnecessary {@link View#findViewById(int)} calls.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     * @see #getItemViewType(int)
     * @see #onBindViewHolder(ViewHolder, int)
     */
    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false);
        return new NoteHolder(view);
    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the {@link ViewHolder#itemView} to reflect the item at the given
     * position.
     * <p>
     * Note that unlike {@link ListView}, RecyclerView will not call this method
     * again if the position of the item changes in the data set unless the item itself is
     * invalidated or the new position cannot be determined. For this reason, you should only
     * use the <code>position</code> parameter while acquiring the related data item inside
     * this method and should not keep a copy of it. If you need the position of an item later
     * on (e.g. in a click listener), use {@link ViewHolder#getAdapterPosition()} which will
     * have the updated adapter position.
     * <p>
     * Override {@link #onBindViewHolder(ViewHolder, int, List)} instead if Adapter can
     * handle efficient partial bind.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {
        Note currentNote = getItem(position);
        holder.textViewTitle.setText(currentNote.getTitle());
        holder.textViewDescription.setText(currentNote.getDescription());
        holder.textViewPrioty.setText(String.valueOf(currentNote.getPriority()));
    }

    /*public void setNotes(List<Note> notes){
        this.notes = notes;
        notifyDataSetChanged();
    }*/

    public Note getNotAt(int position){
        return getItem(position);
    }

    public interface OnItemClickListener{
        //in interface, we don't provide implementation for methods, we only end at declaration
        //and when implementing interface we can implement this method too
        void onItemClick(Note note);
    }
    //to call methods from our adapter unto OnItemClickLister we need a reference to it
    public void setOnItemClickListenerR(OnItemClickListener listener){//we pass our OnItemClickListener we declared aboved. also, we need to saved our listener as a member variable
        this.listener = listener;
    }
}
