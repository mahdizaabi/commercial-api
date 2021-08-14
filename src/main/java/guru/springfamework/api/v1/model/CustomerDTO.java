package guru.springfamework.api.v1.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {

    private Long id;

    private String firstname;
    private String lastname;
    /* when jackson convert DTO TO JSON, OR JSON to DTO
    * Jackson use customex_url as a propeerty on the JSON body instead
    * of the default DTO property name
    *   */
    @JsonProperty("customer_url")
    private String customerUrl;
}
