package jasminezhu.productviewerdemo.network;

import java.util.List;

import jasminezhu.productviewerdemo.model.ProductResponse;
import retrofit2.Call;
import retrofit2.http.GET;

public interface GetDataService {

    String token = "c32313df0d0ef512ca64d5b336a0d7c6";
    @GET("/admin/products.json?page=1&access_token=" + token)
    Call<ProductResponse> getAllProducts();

}
