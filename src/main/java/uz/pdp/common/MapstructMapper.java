package uz.pdp.common;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;
import uz.pdp.domain.Address;
import uz.pdp.domain.Category;
import uz.pdp.domain.Client;
import uz.pdp.domain.Product;
import uz.pdp.model.*;

import static org.mapstruct.ReportingPolicy.IGNORE;

@Component
@Mapper(componentModel = "spring", unmappedTargetPolicy = IGNORE)
public interface MapstructMapper {


    Category toCategory(CategoryAddDto dto);
    Category toCategory(@MappingTarget Category category, CategoryDto dto);

    Address toAddress(AddressDto dto);
    Address toAddress(@MappingTarget Address address,AddressDto dto);

    Client toClient(ClientDto dto);
    Client toClient(@MappingTarget Client client,ClientDto dto);

    Product toProduct(ProductAddDto dto);
    Product toProduct(@MappingTarget Product product,ProductDto dto);



}
