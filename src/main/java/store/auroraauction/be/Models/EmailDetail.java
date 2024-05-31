package store.auroraauction.be.Models;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmailDetail {
    private String recipient;
    private String msgBody;
    private String subject;
    private String attachment;
}
