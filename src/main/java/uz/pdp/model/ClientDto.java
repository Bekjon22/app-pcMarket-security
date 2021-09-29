package uz.pdp.model;

import lombok.Getter;
import lombok.Setter;
import uz.pdp.domain.Address;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class ClientDto {

    @NotNull(message = "fullName cannot be null!")
    private String fullName;

    @Email(message = "Email incorrect!")
    @NotNull(message = "email cannot be null!")
    private String email;

    @Size(max = 13,min = 9,message = "number should be between 9-13! ")
    @NotNull(message = "number cannot be null!")
    private String phoneNumber;

    @NotNull(message = "address cannot be null!")
    private Long addressId;
}
