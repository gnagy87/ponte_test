package hu.ponte.hr.services;

import hu.ponte.hr.controller.ImageMeta;
import hu.ponte.hr.entity.ImageMetaEntity;
import hu.ponte.hr.repository.ImageMetaEntityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class ImageStoreTest {

	private static final byte[] FILE_CONTENT = "fileContent".getBytes(StandardCharsets.UTF_8);
	private static final String NAME = "name";
	private static final String MIME_TYPE = "mimeType";
	private static final String DIGITAL_SIGN = "digitalSign";
	private static final Long ID = 1L;
	private static final long SIZE = 1;

	private MultipartFile file;

	@Mock
	ImageMetaEntityRepository imageMetaEntityRepository;

	@Mock
	SignService signService;

	@InjectMocks
	ImageStore imageStore;

	@BeforeEach
	void init() {
		file = new MockMultipartFile("dummy", "dummy", "dummy", "fileContent".getBytes(StandardCharsets.UTF_8));
	}

	@Test
	void testUploadPicture() throws IOException {
		imageStore.uploadPicture(file);
		verify(imageMetaEntityRepository, times(1)).save(any());
		verify(signService, times(1)).signSHA256RSA(any());
	}

	@Test
	void testGetAllImages() {
		ImageMetaEntity imageMetaEntity = getImageMetaEntity();
		when(imageMetaEntityRepository.findAll()).thenReturn(List.of(imageMetaEntity));
		List<ImageMeta> allImages = imageStore.getAllImages();

		verify(imageMetaEntityRepository, times(1)).findAll();

		assertEquals(1, allImages.size());
		allImages.forEach(image -> {
			assertEquals(String.valueOf(imageMetaEntity.getId()), image.getId());
			assertEquals(imageMetaEntity.getName(), image.getName());
			assertEquals(imageMetaEntity.getMimeType(), image.getMimeType());
			assertEquals(imageMetaEntity.getSize(), image.getSize());
			assertEquals(imageMetaEntity.getDigitalSign(), image.getDigitalSign());
		});
	}

	@Test
	void testGetPictureBinaryStreamById() {
		when(imageMetaEntityRepository.findById(eq(1L))).thenReturn(Optional.of(getImageMetaEntity()));
		byte[] pictureBinaryStreamById = imageStore.getPictureBinaryStreamById("1");
		String expected = "fileContent";
		String actual = new String(pictureBinaryStreamById);

		assertNotNull(pictureBinaryStreamById);
		assertEquals(expected, actual);
	}

	private ImageMetaEntity getImageMetaEntity() {
		return ImageMetaEntity.builder()
				.id(ID)
				.fileContent(FILE_CONTENT)
				.name(NAME)
				.mimeType(MIME_TYPE)
				.size(SIZE)
				.digitalSign(DIGITAL_SIGN)
				.build();
	}
}
