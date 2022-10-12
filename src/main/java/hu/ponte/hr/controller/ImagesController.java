package hu.ponte.hr.controller;

import hu.ponte.hr.services.ImageStore;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController()
@RequestMapping("api/images")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ImagesController {

    private final ImageStore imageStore;

    @GetMapping("meta")
    public List<ImageMeta> listImages() {
    	return imageStore.getAllImages();
    }

    @GetMapping("preview/{id}")
    public void getImage(@PathVariable("id") String id, HttpServletResponse response) {
			Optional.ofNullable(imageStore.getPictureBinaryStreamById(id)).ifPresent(binaryStream -> {
				try {
					StreamUtils.copy(binaryStream, response.getOutputStream());
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
    }
}
