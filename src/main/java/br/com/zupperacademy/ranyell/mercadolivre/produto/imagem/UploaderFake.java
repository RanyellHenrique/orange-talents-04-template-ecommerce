package br.com.zupperacademy.ranyell.mercadolivre.produto.imagem;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class UploaderFake implements  Uploader{


    @Override
    public String upload(MultipartFile file) {
        return "https://fake.com/" + file.getOriginalFilename();
    }
}
