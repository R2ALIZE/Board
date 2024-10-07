package BoardProject.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor (access = AccessLevel.PRIVATE) //인스턴스화 될 일이 없음
public class TimeUtil {


  public static String getNowAsUtcZero() {

      ZonedDateTime utcTime = Instant.now().atZone(ZoneOffset.UTC);

      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

      return utcTime.format(formatter);

    }
}
