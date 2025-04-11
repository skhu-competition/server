package server.global.aws;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AwsS3DTO {
    private String fileName;
    private String fileUrl;
}
