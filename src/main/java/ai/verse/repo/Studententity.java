package ai.verse.repo;


import jakarta.persistence.*;


@Entity
@Table(name = "STUDENT")
public class Studententity {


    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private int rollno;
    private String name;
    private String classname;

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRollno() {
        return rollno;
    }

    public void setRollno(int rollno) {
        this.rollno = rollno;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Studententity{" +
                "id=" + id +
                ", rollno=" + rollno +
                ", name='" + name + '\'' +
                ", classname='" + classname + '\'' +
                '}';
    }
}
