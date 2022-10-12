package hu.ponte.hr.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UtilityClass
public class FileSizeUtil {

	private final long maxAllowedSize = 2097152;

	public boolean isPictureAcceptable(long size) {
		if (size > maxAllowedSize) {
			log.warn("Max picture size limit is 2MB. Current picture's size is: {}MB", String.format("%.2f", convertByteToMb(size)));
			return false;
		}
		return true;
	}

	public Double convertByteToMb(long size) {
		return (double) size / 1048576;
	}
}
