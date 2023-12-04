package org.steven;

import lombok.Builder;
import lombok.Data;

// Including lombok shortcut so no need to declare getters, setters, constructors
@Data
@Builder
public class Customer {

    private String customerRef;
    private String customerName;
    private String addressLine1;
    private String addressLine2;
    private String town;
    private String county;
    private String country;
    private String postcode;

}
