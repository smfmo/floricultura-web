package com.floriculturamonteiro.floricultura.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class ArmazenamentoImgService {

    private final Path rootLocation = Paths.get("uploads");

    public String armazenarImg(MultipartFile arquivo) throws IOException {
        if(arquivo.isEmpty()){
            throw new IOException("Arquivo vazio, não há imagem!");
        }

        //aqui gera nomes únicos para o arquivo
        String nomeArquivo = UUID.randomUUID() + arquivo.getOriginalFilename();
        Path destinoArquivo = rootLocation.resolve(nomeArquivo);

        //vai criar um dirétorio se não existir
        if (!Files.exists(rootLocation)) {
            Files.createDirectories(rootLocation);
        }

        //salva os arquivos no sistema de arquivos
        Files.copy(arquivo.getInputStream(), destinoArquivo);

        //aqui vai retornar o nome do arquivo
        return nomeArquivo;
    }
}
