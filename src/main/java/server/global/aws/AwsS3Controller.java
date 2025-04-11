package server.global.aws;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/file")
public class AwsS3Controller {

    private final AwsS3Service awsS3Service;

    // 이미지 업로드
    @PostMapping("/upload")
    public ResponseEntity<AwsS3DTO> uploadFile(@RequestPart("image") MultipartFile multipartFile) {
        AwsS3DTO response = awsS3Service.uploadFile(multipartFile);
        return ResponseEntity.ok(response);
    }

    // 이미지 삭제
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteFile(@RequestParam String fileName) {
        awsS3Service.deleteFile(fileName);
        return ResponseEntity.ok("삭제 완료: " + fileName);
    }
}