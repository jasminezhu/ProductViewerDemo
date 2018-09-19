package jasminezhu.productviewerdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import jasminezhu.productviewerdemo.R;
import jasminezhu.productviewerdemo.model.Product;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> implements Filterable{
    private List<Product> dataList;
    private List<Product> dataListFiltered;
    private Context context;

    CustomItemClickListener listener;

    public CustomAdapter(Context context, List<Product> dataList, CustomItemClickListener listener){
        this.context = context;
        this.dataList = dataList;
        this.dataListFiltered = dataList;
        this.listener = listener;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder{

        public final View mView;

        TextView title;
        TextView inventory;
        TextView tags;
        private ImageView thumbnail;

        CustomViewHolder(View itemView){
            super(itemView);
            mView = itemView;

            title = mView.findViewById(R.id.title);
            inventory = mView.findViewById(R.id.inventory);
            tags = mView.findViewById(R.id.tags);
            thumbnail = mView.findViewById(R.id.thumbnail);
        }

    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.product_row, parent, false);
        final CustomViewHolder holder = new CustomViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v, holder.getLayoutPosition());
            }
        });
        return holder;

    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        holder.title.setText(dataListFiltered.get(position).getTitle());
        holder.inventory.setText("In Stock: " + dataListFiltered.get(position).getInventory().toString());
        holder.tags.setText(dataListFiltered.get(position).getTags());


        Picasso.Builder builder = new Picasso.Builder(context);
        builder.downloader(new OkHttp3Downloader(context));
        builder.build().load(dataListFiltered.get(position).getImageInfo().getImageUrl())
                .into(holder.thumbnail);

    }

    @Override
    public int getItemCount() {
        return dataListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String tagString = constraint.toString();
                if (tagString.isEmpty()){
                    dataListFiltered = dataList;
                }
                else{
                    List<Product> filteredList = new ArrayList<>();
                    for (Product p:dataList){
                        if (p.getTagsList().contains(tagString)){
                            filteredList.add(p);
                        }
                    }
                    dataListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = dataListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                dataListFiltered = (ArrayList<Product>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
