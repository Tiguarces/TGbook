package pl.Tiguarces.TGbook.service.utils;

import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

@Component
public class TimeHelper {

    public String getDate(final Instant instantDate) {
        return instantDate == null
                ? "Undefined"
                : get(instantDate);
    }

    private String get(final Instant instantDate) {
        final Date date = Date.from(instantDate);
        final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return dateFormat.format(date);
    }
}
