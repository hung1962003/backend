package store.auroraauction.be.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private int id;
    private String name;

//    @ManyToMany(mappedBy ="process",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
//    @JsonBackReference
//    Set<RequestBuy> requests;

    @JsonIgnore
    @OneToMany(mappedBy = "process", cascade = CascadeType.ALL)
    private List<RequestCompany> requestCompanies;
}
