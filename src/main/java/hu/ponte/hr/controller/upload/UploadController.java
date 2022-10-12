package hu.ponte.hr.controller.upload;

import hu.ponte.hr.services.ImageStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Component
@RequestMapping("api/file")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UploadController {

    private final ImageStore imageStore;

    @RequestMapping(value = "post", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> handleFormUpload(@RequestParam("file") MultipartFile file) {
        try {
            imageStore.uploadPicture(file);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("Error happened during picture uploading", e);
            return ResponseEntity.badRequest().build();
        }
    }
}
