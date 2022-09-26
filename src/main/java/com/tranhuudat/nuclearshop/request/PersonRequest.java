package com.tranhuudat.nuclearshop.request;

import com.tranhuudat.nuclearshop.type.Gender;
import com.tranhuudat.nuclearshop.util.anotation.BirthDate;
import com.tranhuudat.nuclearshop.util.anotation.PhoneNumber;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class PersonRequest {

    @BirthDate
    private Date birthDate;

    @NotNull(message = "lastName cannot be null")
    @NotBlank(message = "Please enter your last name")
    private String lastName;

    @NotNull(message = "firstName cannot be null")
    @NotBlank(message = "Please enter your first name")
    private String firstName;

    @NotNull(message = "gender cannot be null")
    private Gender gender;

    @PhoneNumber
    private String phoneNumber;

    private String idNumber;

    private String idNumberIssueBy;

    private Date idNumberIssueDate;
}
