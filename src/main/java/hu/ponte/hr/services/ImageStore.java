package hu.ponte.hr.services;

import hu.ponte.hr.controller.ImageMeta;
import hu.ponte.hr.entity.ImageMetaEntity;
import hu.ponte.hr.repository.ImageMetaEntityRepository;
import hu.ponte.hr.util.FileSizeUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ImageStore {

	private final ImageMetaEntityRepository imageMetaEntityRepository;
	private final SignService signService;

	public void uploadPicture(MultipartFile file) throws Exception {
		if (Optional.ofNullable(file).map(f -> FileSizeUtil.isPictureAcceptable(f.getSize())).orElse(false)) {
			ImageMetaEntity imageMetaEntity = getImageMetaEntity(file);
			imageMetaEntityRepository.save(imageMetaEntity);
		}
	}

	public List<ImageMeta> getAllImages() {
		return imageMetaEntityRepository.findAll().stream()
				.map(this::getImageMeta)
				.collect(Collectors.toList());
	}

	public byte[] getPictureBinaryStreamById(String id) {
		Long imageId = getId(id);
		if (nonNull(imageId)) {
			return imageMetaEntityRepository.findById(imageId)
					.map(ImageMetaEntity::getFileContent)
					.orElse(null);
		}
		return null;
	}

	private Long getId(String id) {
		try {
			return Long.valueOf(id);
		} catch (Exception e) {
			log.error("ID must be only a number!");
			return null;
		}
	}

	private ImageMetaEntity getImageMetaEntity(MultipartFile file) throws IOException {
		return ImageMetaEntity.builder()
				.fileContent(file.getBytes())
				.name(file.getOriginalFilename())
				.mimeType(file.getContentType())
				.digitalSign(signService.signSHA256RSA(file.getBytes()))
				.size(file.getSize())
				.build();
	}

	private ImageMeta getImageMeta(ImageMetaEntity imageMetaEntity) {
		return ImageMeta.builder()
				.id(String.valueOf(imageMetaEntity.getId()))
				.name(imageMetaEntity.getName())
				.mimeType(imageMetaEntity.getMimeType())
				.size(imageMetaEntity.getSize())
				.digitalSign(imageMetaEntity.getDigitalSign())
				.build();
	}
}
