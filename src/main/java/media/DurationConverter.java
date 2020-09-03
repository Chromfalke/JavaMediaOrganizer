package media;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.logging.Logger;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply=true)
public class DurationConverter 
    implements AttributeConverter<Duration, Long>
{
    Logger log = Logger.getLogger(DurationConverter.class.getSimpleName());
    
    public Long convertToDatabaseColumn( Duration attribute )
    {
        log.info("Convert to Long");
        if (attribute!=null) {
            return attribute.toNanos();
        }
        else {
            return Long.valueOf(0);
        }
    }
    
    public Duration convertToEntityAttribute( Long duration )
    {
        log.info("Convert to duration");
        return Duration.of(duration, ChronoUnit.NANOS);
    }
}
