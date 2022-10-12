package hu.ponte.hr.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

@Slf4j
@Service
public class SignService {

	private static final String PRIVATE_KEY_PATH = "src/main/resources/config/keys/key.private";

	public String signSHA256RSA(byte[] input) {
		try {
			byte[] key = Files.readAllBytes(Paths.get(PRIVATE_KEY_PATH));
			PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(key);
			KeyFactory kf = KeyFactory.getInstance("RSA");
			Signature privateSignature = Signature.getInstance("SHA256withRSA");
			privateSignature.initSign(kf.generatePrivate(spec));
			privateSignature.update(input);
			byte[] signed = privateSignature.sign();
			return Base64.getEncoder().encodeToString(signed);
		} catch (Exception e) {
			log.error("Error happened during digital sign process: {}", e.getMessage());
			return null;
		}
	}
}
