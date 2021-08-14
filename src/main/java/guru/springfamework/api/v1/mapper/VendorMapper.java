package guru.springfamework.api.v1.mapper;

import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.domain.Vendor;

public interface VendorMapper {

    public Vendor vendorDTOtoVendor(VendorDTO vendorDto);

    public VendorDTO vendorToVendorDTO(Vendor vendor);


}
