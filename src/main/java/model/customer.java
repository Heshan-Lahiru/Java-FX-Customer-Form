package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class customer {


    private String id;
    private String title;
    private String name;
    private LocalDate date;
    private double salary;

    private String address;



    private String city;

    private String province;


    private String postalcode;
}
