package ai.verse.repo;

import jakarta.persistence.*;


@Entity
@Table(name = "USER_ORDER")
public class CartEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "user_id", nullable = false)
    private Integer userid;

    @Column(name = "cart_summary", nullable = false)
    private String cart_summary;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getCart_summary() {
        return cart_summary;
    }

    public void setCart_summary(String cart_summary) {
        this.cart_summary = cart_summary;
    }
}
