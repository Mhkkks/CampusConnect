package ai.verse.repo;
import jakarta.persistence.*;


@Entity
@Table(name = "PRODUCT")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "quantity_select") // Ensure the column name matches the database
    private int quantity_select;

    private String name;

    private String product_description;

    private String image_url;

    private int price_before;

    private int price_net;

    private int categoryid;

    public int getQuantity_select() {
        return quantity_select;
    }

    public void setQuantity_select(int quantity_select) {
        this.quantity_select = quantity_select;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProduct_description() {
        return product_description;
    }

    public void setProduct_description(String product_description) {
        this.product_description = product_description;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public int getPrice_before() {
        return price_before;
    }

    public void setPrice_before(int price_before) {
        this.price_before = price_before;
    }

    public int getPrice_net() {
        return price_net;
    }

    public void setPrice_net(int price_net) {
        this.price_net = price_net;
    }

    public int getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(int categoryid) {
        this.categoryid = categoryid;
    }


}
