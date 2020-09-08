package com.example.demo.infrastructure.service.storage;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;

import com.example.demo.core.storage.StorageProperties;
import com.example.demo.domain.service.FotoStorageService;

public class LocalFotoStorageService implements FotoStorageService {
//
//	@Value("${gftFood.storage.local.diretorio-fotos}")
//	private Path diretorioFotos;

	@Autowired
	private StorageProperties storageProperties;

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

		// Path arquivoFoto = Paths.get("C:\\Users\\rebg\\Desktop\\catalogo",
		// nomeArquivo);
		// Path arquivoFoto2 = diretorioFotos.resolve(Path.of(nomeArquivo));
		// Path arquivoFoto2 = diretorioFotos.resolve(nomeArquivo);
		Path arquivoFoto2 = storageProperties.getLocal().getDiretorioFotos().resolve(nomeArquivo);

		return arquivoFoto2;
	}

	@Override
	public FotoRecuperada recuperar(String nomeArquivo) {

		try {
			Path arquivoPath = getArquivoPath(nomeArquivo);

			FotoRecuperada fotoRecuperada = FotoRecuperada.builder().inputStream(Files.newInputStream(arquivoPath))
					.build();

			return fotoRecuperada;
		} catch (Exception e) {
			throw new StorageException("Não foi possivel recuperar arquivo.", e);
		}
	}

}