package ir.elegam.doctor.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;
import java.util.Random;

import ir.elegam.doctor.Classes.Variables;
import ir.elegam.doctor.R;


public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    List<String> ItemsList;
    Typeface fontTypeFave;
    Context mContext;

    public CategoryAdapter(List<String> row, Typeface San, Context context) {
        this.ItemsList = row;
        this.fontTypeFave = San;
        this.mContext = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle;
        ImageView ivFlesh,ivIcon;

        ViewHolder(View itemView) {
            super(itemView);
            ivFlesh = (ImageView) itemView.findViewById(R.id.ivFlesh_rowcategory);
            ivIcon = (ImageView) itemView.findViewById(R.id.ivIcon_rowcategory);
            txtTitle = (TextView) itemView.findViewById(R.id.txtTitle_rowcategory);

            txtTitle.setTypeface(fontTypeFave);

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
                .inflate(R.layout.row_category, viewGroup, false);

        ViewHolder pvh = new ViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder RowViewHolder, final int position) {
        RowViewHolder.txtTitle.setText(ItemsList.get(position));
        Log.i(Variables.Tag,"ItemList: "+ItemsList.get(position));
        RowViewHolder.ivFlesh.setImageResource(RandomFleshIcon());
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void add(String ob) {
        ItemsList.add(ob);
        notifyDataSetChanged();
    }

    public void clear() {
        ItemsList.clear();
        notifyDataSetChanged();
    }

    private int RandomFleshIcon(){

        int[] fleshList = {
                R.drawable.c1,R.drawable.c2,R.drawable.c3,R.drawable.c4
                ,R.drawable.c5,R.drawable.c6,R.drawable.c7
        };

        Random random = new Random();

        return fleshList[random.nextInt(7-1)+1];
    }// end RandomFleshIcon()

}// end class

