package me.caua.egiftstore.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import me.caua.egiftstore.model.GiftCard;
import me.caua.egiftstore.model.GiftCompany;
import me.caua.egiftstore.repository.GiftCardRepository;
import me.caua.egiftstore.repository.GiftCompanyRepository;
import me.caua.egiftstore.validation.ValidationException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class GiftCompanyFileServiceImpl implements FileService {

    @Inject
    public GiftCompanyRepository giftCompanyRepository;

    private final String PATH_USER = System.getProperty("user.home")
            + File.separator + "quarkus"
            + File.separator + "images"
            + File.separator + "giftcard" + File.separator;

    @Transactional
    @Override
    public void save(Long id, String imageName, byte[] image) {
        GiftCompany giftCompany = giftCompanyRepository.findById(id);

        try {
            giftCompany.setImageName(saveImage(imageName, image));
        } catch (IOException e) {
            throw new ValidationException("null", e.getMessage());
        }
    }

    private String saveImage(String imageName, byte[] image) throws IOException {
        String mimeType = Files.probeContentType(new File(imageName).toPath());
        List<String> listMimeType = Arrays.asList("image/jpg", "image/gif", "image/png", "image/jpeg");

        if (!listMimeType.contains(mimeType)) {
            throw new IOException("Image type not supported");
        }

        if (image.length > 1024 * 1024 * 10) {
            throw new IOException("Image cannot be larger than 10MB");
        }

        File diretorio = new File(PATH_USER);
        if (!diretorio.exists()) diretorio.mkdirs();

        String nomeArquivo = UUID.randomUUID() + "." + mimeType.substring(mimeType.lastIndexOf("/")+1);
        String path = PATH_USER + nomeArquivo;

        File file = new File(path);
        if (file.exists()) {
            throw new IOException("Image already exists");
        }

        file.createNewFile();
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(image);
        fos.flush();
        fos.close();

        return nomeArquivo;
    }

    @Override
    public File download(String imageName) {
        return new File(PATH_USER+imageName);
    }

}
