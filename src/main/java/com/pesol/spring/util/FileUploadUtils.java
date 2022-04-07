package com.pesol.spring.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Comparator;

import org.springframework.web.multipart.MultipartFile;

public class FileUploadUtils {

	public static void saveFile(String uploadDir, String fileName, MultipartFile file) throws IOException {
		Path uploadPath = Paths.get(uploadDir);
        
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
         
        try (InputStream inputStream = file.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            System.out.println(filePath.toFile().getAbsolutePath());
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {        
            throw new IOException("Could not save image file: " + fileName, e);
        }      
	}
	
	public static void deleteFile(String uploadDir) {
		Path productImagesPath = Paths.get("product-images");
		Path uploadPath = productImagesPath.resolve(uploadDir);
		
		if (Files.exists(uploadPath)) {
			try {
				Files.walk(uploadPath).sorted(Comparator.reverseOrder())
						.map(Path::toFile)
						.forEach(File::delete);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
