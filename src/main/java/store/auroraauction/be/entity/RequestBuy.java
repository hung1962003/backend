package store.auroraauction.be.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import store.auroraauction.be.enums.RequestBuyEnum;


@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "requestbuy")
public class RequestBuy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(columnDefinition = "TEXT") // create a column in sql with data  type is TEXT
    private String description;

    private String title;

    @Enumerated(EnumType.STRING)
    private RequestBuyEnum RequestBuyEnum;

    private String image;
//    @ManyToMany
//    @JoinTable(name = "request_process",
//            joinColumns = @JoinColumn(name="request_id"),
//            inverseJoinColumns = @JoinColumn(name="process_id"))
////    @JsonManagedReference
//    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class , property = "id")
//    Set<Process> process;
    @OneToOne(mappedBy ="requestBuy")
    @JsonIgnore
    private RequestCompany requestCompany;

    @OneToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    @JoinColumn(name="jewelry_id",referencedColumnName = "id")
    private Jewelry jewelry;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private Account account;


}
