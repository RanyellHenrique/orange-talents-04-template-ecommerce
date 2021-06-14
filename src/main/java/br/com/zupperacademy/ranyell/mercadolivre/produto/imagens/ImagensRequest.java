package br.com.zupperacademy.ranyell.mercadolivre.produto.imagens;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

public class ImagensRequest {

    @Size(min = 1)
    private List<MultipartFile> imagens;

    public ImagensRequest(List<MultipartFile> imagens) {
        this.imagens = imagens;
    }

    public List<MultipartFile> getImagens() {
        return imagens;
    }
}
