package com.example.demo.infrastructure.service.storage;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import com.example.demo.domain.service.FotoStorageService;

@Service
public class LocalFotoStorageService implements FotoStorageService {

	@Value("${gftFood.storage.local.diretorio-fotos}")
	private Path diretorioFotos;

	@Override
	public void armazenar(NovaFoto novaFoto) {
		try {
			Path arquivoPath = getArquivoPath(novaFoto.getNomeArquivo());

			FileCopyUtils.copy(novaFoto.getInputStream(), Files.newOutputStream(arquivoPath));
		} catch (Exception e) {
			throw new StorageException("Não foi possível armazenar arquivo.", e);
		}
	}

	@Override
	public void remover(String nomeArquivo) {

		Path arquivoPath = getArquivoPath(nomeArquivo);

		Path arquivoFoto = Paths.get("C:\\Users\\rebg\\Desktop\\catalogo", nomeArquivo);

		try {
			Files.deleteIfExists(arquivoPath);
		} catch (Exception e) {
			throw new StorageException("Não foi possivel excluir arquivo.", e);
		}

	}

	private Path getArquivoPath(String nomeArquivo) {

		//Path arquivoFoto = Paths.get("C:\\Users\\rebg\\Desktop\\catalogo", nomeArquivo);
		//Path arquivoFoto2 = diretorioFotos.resolve(Path.of(nomeArquivo));
		Path arquivoFoto2 = diretorioFotos.resolve(nomeArquivo);
		return arquivoFoto2;
	}

	@Override
	public InputStream recuperar(String nomeArquivo) {

		try {
			Path arquivoPath = getArquivoPath(nomeArquivo);

			return Files.newInputStream(arquivoPath);
		} catch (Exception e) {
			throw new StorageException("Não foi possivel recuperar arquivo.", e);
		}
	}

}