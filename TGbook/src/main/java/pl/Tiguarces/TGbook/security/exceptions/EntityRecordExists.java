package pl.Tiguarces.TGbook.security.exceptions;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class EntityRecordExists extends RuntimeException {
    public EntityRecordExists(@Valid
                                    @NotNull(message = "Entity cannot be null")
                                    @NotBlank(message = "Entity cannot be blank") String entity,
                              @Valid
                                    @NotNull(message = "Name cannot be null")
                                    @NotBlank(message = "Name cannot be blank") String name,
                              @Valid
                                    @NotNull(message = "Parameter By cannot be null")
                                    @NotBlank(message = "Parameter By cannot be blank") String by) {

        super("%s with %s: %s exists in the database".formatted(entity, by, name));
    }
}
