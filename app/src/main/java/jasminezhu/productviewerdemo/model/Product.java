package jasminezhu.productviewerdemo.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Product implements Serializable{

//    @SerializedName("id")
//    private Integer id;
    @SerializedName("title")
    private String title;
    @SerializedName("tags")
    private String tags;
    @SerializedName("variants")
    private List<Variant> variants = new ArrayList<>();
    @SerializedName("image")
    private ImageInfo imageInfo;
    @SerializedName("vendor")
    private String vendor;

    public class Variant implements Serializable{
//        @SerializedName("id")
//        private Integer id;
        @SerializedName("title")
        private String title;
        @SerializedName("inventory_quantity")
        private Integer inventory;

        public Variant(Integer id, String title, Integer inventory){
//            this.id = id;
            this.title = title;
            this.inventory = inventory;
        }

//        public Integer getId() {
//            return id;
//        }
//
//        public void setId(Integer id) {
//            this.id = id;
//        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Integer getInventory() {
            return inventory;
        }

        public void setInventory(Integer inventory) {
            this.inventory = inventory;
        }
    }

    public class ImageInfo implements Serializable{
        @SerializedName("src")
        private String imageUrl;
        public ImageInfo(String imageUrl){
            this.imageUrl = imageUrl;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public List<Variant> getVariants() {
        return variants;
    }

    public void setVariants(List<Variant> variants) {
        this.variants = variants;
    }

    public ImageInfo getImageInfo() {
        return imageInfo;
    }

    public void setImageInfo(ImageInfo imageInfo) {
        this.imageInfo = imageInfo;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public Integer getInventory(){
        Integer total = 0;
        for(Variant v: variants){
            total += v.getInventory();
        }
        return total;
    }

    public ArrayList<String> getTagsList(){
        return new ArrayList<>(Arrays.asList(tags.split(", ")));
    }
}
