package uz.pdp.model;

import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
public class OrderDto implements Serializable {

    @NotNull(message = "client id cannot be null!")
    private Long clientId;

    @NotNull(message = "products id cannot be null!")
    private Set<Long> productsId;

}
