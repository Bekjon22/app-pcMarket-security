package uz.pdp.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
public class ProductDto implements Serializable {
    @NotNull(message = "name cannot be null!")
    private String name;

    @NotNull(message = "model cannot be null!")
    private String model;

    @NotNull(message = "mark cannot be null!")
    private String mark;

    @NotNull(message = "description cannot be null!")
    private String description;

    boolean active = true;

    @NotNull(message = "category cannot be null!")
    private Long categoryId;

    @NotNull(message = "attachment cannot be null!")
    private Long attachmentId;

}
