package com.br.smallmanager.apismallManager.utils;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

public class FotoUploadDisco {

	 
	private String raiz = "C:\\testeupload";
	 
	 
	private String diretorioFotos = "\\fotos";
	
	public void salvarFoto(MultipartFile foto) {
		this.salvar(this.diretorioFotos, foto);
	}
	
	public void salvar(String diretorio, MultipartFile arquivo) {
		Path diretorioPath = Paths.get(this.raiz, diretorio);
		Path arquivoPath = diretorioPath.resolve(arquivo.getOriginalFilename());
		  
		try {
			Files.createDirectories(diretorioPath);
			 
			arquivo.transferTo(arquivoPath.toFile());	
		 
			
		} catch (IOException e) {
			throw new RuntimeException("Problemas na tentativa de salvar arquivo.", e);
		}		
	}
	public  String encriptarHash (String texto) {
		 
		try {
			Date data = new Date();
			SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy"); 
  
			formatador.format( data );
			
			texto = texto + formatador.format(data).toString();
			
			MessageDigest md = null;
			
			md = MessageDigest.getInstance("MD5");
			
			BigInteger hash = new BigInteger(1,md.digest(texto.getBytes()));
		    texto =  hash.toString(16);
			
		} catch (NoSuchAlgorithmException e) {
			 e.printStackTrace();
		}
	
		System.out.println("\nPRINT = "+texto);
		return texto;
	}
}
