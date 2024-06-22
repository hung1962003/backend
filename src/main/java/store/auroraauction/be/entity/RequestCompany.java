package store.auroraauction.be.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "requestconpany")
public class RequestCompany {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private double estimate_price_Min;

    private double estimate_price_Max;

    private boolean sendtoCompany_status;

    @ManyToOne
    @JoinColumn(name="manager-id")
    private Account manager;

    @ManyToOne
    @JoinColumn(name="staff-id")
    private Account staff;

    @ManyToOne
    @JoinColumn(name = "process-id")
    @JsonIgnore
    private  Process process ;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="requestBuy_id",referencedColumnName = "id")
    private RequestBuy requestBuy;
}
