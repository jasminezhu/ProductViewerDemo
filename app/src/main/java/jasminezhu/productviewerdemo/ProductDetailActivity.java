package jasminezhu.productviewerdemo;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.flexbox.FlexboxLayout;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import jasminezhu.productviewerdemo.model.Product;

public class ProductDetailActivity extends AppCompatActivity implements  View.OnClickListener {

    public static final String PRODUCT_DETAIL = "jasminezhu.productviewerdemo.PRODUCT_DETAIL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_product_detail);

        Product product = null;
        if (getIntent().hasExtra(PRODUCT_DETAIL)){
            product = (Product) getIntent().getSerializableExtra(PRODUCT_DETAIL);
        }
        else {
            throw new IllegalArgumentException("Activity cannot find  extras " + PRODUCT_DETAIL);
        }

        if (product != null){
            TextView title = findViewById(R.id.title);
            title.setText(product.getTitle());

            TextView vendor = findViewById(R.id.vendor);
            vendor.setText(product.getVendor());

            TextView inventory = findViewById(R.id.inventory);
            inventory.setText("In Stock: " + product.getInventory().toString());

            ImageView image = findViewById(R.id.image);
            Picasso.Builder builder = new Picasso.Builder(this);
            builder.downloader(new OkHttp3Downloader(this));
            builder.build().load(product.getImageInfo().getImageUrl())
                    .into(image);


            String[] tags = product.getTags().split(", ");
            FlexboxLayout tagsLayout = findViewById(R.id.tags);
            for(String t: tags){
                View v = getLayoutInflater().inflate(R.layout.single_tag, tagsLayout, false);
                TextView tagView = v.findViewById(R.id.tag);
                tagView.setText(t);
                tagView.setOnClickListener(this);
                tagsLayout.addView(v);
            }

        }

    }

    @Override
    public void onClick(View v){

        Intent returnIntent = new Intent();
        returnIntent.putExtra(MainActivity.FILTER_TAG,((TextView)v).getText().toString());
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }
}
