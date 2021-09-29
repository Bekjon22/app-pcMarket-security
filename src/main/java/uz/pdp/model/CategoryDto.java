package uz.pdp.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
@Getter
@Setter
public class CategoryDto {

    @NotNull(message = "name cannot be null!")
    private String name;

    private String active;
}
