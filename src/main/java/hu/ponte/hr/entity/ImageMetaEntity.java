package hu.ponte.hr.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "image_meta")
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ImageMetaEntity {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;
	@Column(name = "file_content", nullable = false, length = Integer.MAX_VALUE)
	private byte[] fileContent;
	@Column(name = "name")
	private String name;
	@Column(name = "mime_type")
	private String mimeType;
	@Column(name = "size")
	private long size;
	@Column(name = "digital_sign", nullable = false, length = Integer.MAX_VALUE)
	private String digitalSign;

}
