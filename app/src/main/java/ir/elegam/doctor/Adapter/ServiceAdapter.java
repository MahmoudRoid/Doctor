package ir.elegam.doctor.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import java.util.ArrayList;
import ir.elegam.doctor.Helper.MyObject;
import ir.elegam.doctor.R;

/**
 * Created by Droid on 8/17/2016.
 */
public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.DataObjectHolder> {
    public Context context;
    public ArrayList<MyObject> myObjectArrayList;

    public ServiceAdapter(Context context,ArrayList<MyObject> arrayList){
        this.myObjectArrayList=arrayList;
        this.context=context;
    }

    public static class DataObjectHolder extends RecyclerView.ViewHolder{

        TextView tv_title;
        ImageView image;

        public DataObjectHolder(View itemView){
            super(itemView);
            tv_title = (TextView) itemView.findViewById(R.id.service_title);
            image = (ImageView) itemView.findViewById(R.id.service_image);
        }

    }


    @Override
    public ServiceAdapter.DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.service_item,parent,false);
        return new DataObjectHolder(view);
    }

    @Override
    public void onBindViewHolder(ServiceAdapter.DataObjectHolder holder, int position) {
        holder.tv_title.setText(myObjectArrayList.get(position).getTitle());
        Glide.with(context).load(myObjectArrayList.get(position).getImage_url())
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .placeholder(R.mipmap.ic_launcher).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return myObjectArrayList.size();
    }

}
