package pl.adcom.bookaro.uploads.domain;

import lombok.Value;
import java.time.LocalDateTime;

@Value
public class Upload {

    String id;
    byte[] file; //content
    String contentType;
    String filename;
    LocalDateTime createdAt;
}
