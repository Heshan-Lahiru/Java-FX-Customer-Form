package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class item {

    private String ItemCode;
    private String Description;
    private String PackSize;
    private String UnitPrice;
    private String QtyOnHand;
}
