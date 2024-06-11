package ai.verse.repo;


import jakarta.persistence.*;


@Entity
@Table(name = "AQI")
public class AQIEntity {


    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;


    private String datetime;


    private String station;

    private String jsondata;

    private String jsondate;

    private Integer jsonavg25;


    public String getJsondate() {
        return jsondate;
    }

    public void setJsondate(String jsondate) {
        this.jsondate = jsondate;
    }

    public Integer getJsonavg25() {
        return jsonavg25;
    }

    public void setJsonavg25(Integer jsonavg25) {
        this.jsonavg25 = jsonavg25;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public String getJsondata() {
        return jsondata;
    }

    public void setJsondata(String jsondata) {
        this.jsondata = jsondata;
    }

    @Override
    public String toString() {
        return "AQIEntity{" +
                "id=" + id +
                ", datetime='" + datetime + '\'' +
                ", station='" + station + '\'' +
                ", jsondata='" + jsondata + '\'' +
                '}';
    }
}