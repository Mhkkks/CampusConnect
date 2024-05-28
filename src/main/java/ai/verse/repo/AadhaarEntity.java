package ai.verse.repo;


import jakarta.persistence.*;


@Entity
@Table(name = "AADHAAR")
public class AadhaarEntity {


    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;


    private String aadhaarnumber;


    private String name;

    private String address;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAadhaarnumber() {
        return aadhaarnumber;
    }

    public void setAadhaarnumber(String aadhaarnumber) {
        this.aadhaarnumber = aadhaarnumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    @Override
    public String toString() {
        return "AadhaarEntity{" +
                "id=" + id +
                ", aadhaarnumber='" + aadhaarnumber + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}