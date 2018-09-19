package jasminezhu.productviewerdemo;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.flexbox.FlexboxLayout;

import java.util.List;

import jasminezhu.productviewerdemo.adapter.CustomAdapter;
import jasminezhu.productviewerdemo.adapter.CustomItemClickListener;
import jasminezhu.productviewerdemo.model.Product;
import jasminezhu.productviewerdemo.model.ProductResponse;
import jasminezhu.productviewerdemo.network.GetDataService;
import jasminezhu.productviewerdemo.network.RetrofitClientInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    public static final String FILTER_TAG = "jasminezhu.productviewerdemo.FILTER_TAG";

    private CustomAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);


        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<ProductResponse> call = service.getAllProducts();
        call.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                generateDataList(response.body());
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void generateDataList(ProductResponse productResponse){
        recyclerView = findViewById(R.id.customRecyclerView);
        final List<Product> products = productResponse.getProducts();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new CustomAdapter(this, products, new CustomItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                ViewGroup filterView = findViewById(R.id.filter);
                filterView.removeAllViews();
                Product product = products.get(position);
                Intent intent = new Intent(v.getContext(), ProductDetailActivity.class);
                intent.putExtra(ProductDetailActivity.PRODUCT_DETAIL, product);
                startActivityForResult(intent, 1);

            }
        });
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                String filter_tag= data.getStringExtra(FILTER_TAG);
                FlexboxLayout relativeLayout = findViewById(R.id.filter);
                View v = getLayoutInflater().inflate(R.layout.single_tag, relativeLayout, false);
                TextView tagView = v.findViewById(R.id.tag);
                tagView.setText(filter_tag);
                relativeLayout.addView(v);

//                Toast.makeText(getApplicationContext(), "filter "+ filter_tag, Toast.LENGTH_SHORT).show();
                Filter filter = adapter.getFilter();
                filter.filter(filter_tag);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }//onActivityResult
}
