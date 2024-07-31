package store.auroraauction.be.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import store.auroraauction.be.enums.RequestBuyEnum;

import java.util.List;
import java.util.Set;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "process")
public class Process {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    RequestBuyEnum requestBuyEnum;

    double min;

    double max;


    @Column(name="staff_ID")
    long staffID;


    @Column(name = "manager_Id")
    long managerID;

//    @Column(columnDefinition = "TEXT") // create a column in sql with data  type is TEXT
//    private String reasonReject;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "requestBuy_id")
    RequestBuy requestBuy;



//    @ManyToMany(mappedBy ="process",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
//    @JsonBackReference
//    Set<RequestBuy> requests;
@OneToOne(cascade = CascadeType.ALL)
@JoinColumn(name = "jewelry_id",referencedColumnName = "id")

private Jewelry jewelry;


}
