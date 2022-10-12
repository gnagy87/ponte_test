package hu.ponte.hr.controller;

import lombok.Builder;
import lombok.Getter;

/**
 * @author zoltan
 */
@Getter
@Builder
public class ImageMeta {

	private final String id;
	private final String name;
	private final String mimeType;
	private final long size;
	private final String digitalSign;

}
