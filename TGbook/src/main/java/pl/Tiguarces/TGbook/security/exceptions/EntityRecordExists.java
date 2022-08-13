package pl.Tiguarces.TGbook.security.exceptions;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class EntityRecordExists extends RuntimeException {
    public EntityRecordExists(@NotNull(message = "Name cannot be null")
                              @NotBlank(message = "Name cannot be blank") final String name) {
        super("Record with name: " + name + " exist in the database");
    }
}
