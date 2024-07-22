package store.auroraauction.be.Models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.ModelAttribute;

@Getter
@Setter
public class CategoryRequest {
    String category_name;
}
