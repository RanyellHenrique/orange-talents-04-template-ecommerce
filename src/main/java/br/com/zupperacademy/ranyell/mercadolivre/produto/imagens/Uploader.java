package br.com.zupperacademy.ranyell.mercadolivre.produto.imagens;

import org.springframework.web.multipart.MultipartFile;

public interface Uploader {

    String upload(MultipartFile file);
}
