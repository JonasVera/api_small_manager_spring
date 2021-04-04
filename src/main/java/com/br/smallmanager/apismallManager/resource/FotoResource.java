package com.br.smallmanager.apismallManager.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.br.smallmanager.apismallManager.entity.Fotos;
import com.br.smallmanager.apismallManager.exeptions.RegraNegocioException;
import com.br.smallmanager.apismallManager.service.FotoService;
 
import com.br.smallmanager.apismallManager.utils.FotoUploadDisco;

@RestController
@RequestMapping("api/empresa/foto")
public class FotoResource {

	@Autowired
	private FotoService service;
	  
	 	@PostMapping
	    public ResponseEntity<?> registrarReceita(@RequestParam MultipartFile file){
	 		 Fotos foto = new Fotos();
	 		 FotoUploadDisco uploadDisco = new FotoUploadDisco();
	 		  
	    	  String tma = Long.toString(file.getSize());
	    	  foto.setTamanho(tma);
	    	  foto.setNome(file.getOriginalFilename());
	    	  foto.setContexto("Pro");
	    	  uploadDisco.salvarFoto(file);
	    	  try {
	    		  Fotos ftSalva =   service.adicioanarFoto(foto);
	  			return new ResponseEntity<Fotos>(ftSalva, HttpStatus.CREATED);
	  		}catch (RegraNegocioException e) {
	  			return ResponseEntity.badRequest().body(e.getMessage());
	  		}
	    } 
	 
}
