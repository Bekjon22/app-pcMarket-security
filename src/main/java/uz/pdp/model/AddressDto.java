package uz.pdp.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class AddressDto {

    @NotNull(message = "street cannot be null!")
    private String street;

    @NotNull(message = "region cannot be null!")
    private String region;

    @NotNull(message = "district cannot be null!")
    private String district;
}
