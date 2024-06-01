package store.auroraauction.be.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Identify {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int identifyid;
    //private int Accountid;
    private String frontID_image;
    private String backID_image;
}
