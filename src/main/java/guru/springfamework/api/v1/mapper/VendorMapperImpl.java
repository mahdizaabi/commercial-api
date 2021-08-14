package guru.springfamework.api.v1.mapper;

import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.domain.Vendor;
import org.springframework.stereotype.Repository;


@Repository
public class VendorMapperImpl implements VendorMapper{


    @Override
    public Vendor vendorDTOtoVendor(VendorDTO vendorDto) {
       Vendor vendor = new Vendor();
       vendor.setId(vendorDto.getId());
       vendor.setName(vendorDto.getName());
       return vendor;
    }

    @Override
    public VendorDTO vendorToVendorDTO(Vendor vendor) {
       VendorDTO vendorDTO = new VendorDTO();
       vendorDTO.setId(vendor.getId());
       vendorDTO.setName(vendor.getName());
       return vendorDTO;
    }
}
