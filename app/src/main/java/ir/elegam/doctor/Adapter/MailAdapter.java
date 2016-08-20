package ir.elegam.doctor.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import ir.elegam.doctor.Helper.ObjectMessage;
import ir.elegam.doctor.Helper.Object_Video;
import ir.elegam.doctor.R;


public class MailAdapter extends RecyclerView.Adapter<MailAdapter.ViewHolder> {

    List<ObjectMessage> ItemsList;
    Typeface fontTypeFave;
    Context mContext;

    public MailAdapter(List<ObjectMessage> row, Typeface San, Context context) {
        this.ItemsList = row;
        this.fontTypeFave = San;
        this.mContext = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtName,txtMessage;
        CardView cvBackground;

        ViewHolder(View itemView) {
            super(itemView);
            txtName = (TextView) itemView.findViewById(R.id.txtName_rowmail);
            txtMessage = (TextView) itemView.findViewById(R.id.txtMessage_rowmaill);
            cvBackground = (CardView) itemView.findViewById(R.id.cv_rowmail);

            txtName.setTypeface(fontTypeFave);
            txtMessage.setTypeface(fontTypeFave);

        }// Cunstrator()
    }// end class ViewHolder{}


    @Override
    public int getItemCount() {
        return ItemsList.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.row_mail, viewGroup, false);

        ViewHolder pvh = new ViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder RowViewHolder, final int position) {
        RowViewHolder.txtName.setText(ItemsList.get(position).getAuthor());
        RowViewHolder.txtMessage.setText(ItemsList.get(position).getMessage());

        if(!ItemsList.get(position).isMe){
            RowViewHolder.cvBackground.setCardBackgroundColor(0xFF07C4B0);
        }else{
            RowViewHolder.cvBackground.setCardBackgroundColor(0xFFFFFFFF);
        }

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void add(ObjectMessage ob) {
        ItemsList.add(ob);
        notifyDataSetChanged();
    }

    public void clear() {
        ItemsList.clear();
        notifyDataSetChanged();
    }
}
