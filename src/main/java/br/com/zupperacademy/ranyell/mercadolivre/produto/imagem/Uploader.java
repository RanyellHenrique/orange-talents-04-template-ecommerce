package br.com.zupperacademy.ranyell.mercadolivre.produto.imagem;

import org.springframework.web.multipart.MultipartFile;

public interface Uploader {

    String upload(MultipartFile file);
}
