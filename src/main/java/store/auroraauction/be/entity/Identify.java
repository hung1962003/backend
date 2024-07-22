package store.auroraauction.be.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "identify")
public class Identify {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long identifyid;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="client-id",referencedColumnName = "id")
    private  Account account;
    //private int Accountid;
    private String frontID_image;
    private String backID_image;

}
