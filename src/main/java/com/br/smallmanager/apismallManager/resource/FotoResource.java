package com.br.smallmanager.apismallManager.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.br.smallmanager.apismallManager.entity.Fotos;
import com.br.smallmanager.apismallManager.entity.Produto;
import com.br.smallmanager.apismallManager.exeptions.RegraNegocioException;
import com.br.smallmanager.apismallManager.service.FotoService; 
import com.br.smallmanager.apismallManager.utils.FotoUploadDisco;

@RestController
@RequestMapping("api/empresa/foto")
@CrossOrigin(origins = "*")
public class FotoResource {

	@Autowired
	private FotoService service;
	@Autowired
	 private com.br.smallmanager.apismallManager.service.S3StorageService serviceS3;
	
 
	 	@PostMapping("/uploadFotoProduto/{id_produto}")
	 	public ResponseEntity<?> uploadFoto(@RequestParam MultipartFile file, @PathVariable( "id_produto") Long id_produto){
	 		 Fotos foto = new Fotos();
	 		 
	 		  Produto prodUpload = new Produto();
	 		  prodUpload.setId(id_produto);
	 		  foto.setProduto(prodUpload);
	    	  String tma = Long.toString(file.getSize());
	    	  foto.setTamanho(tma); 
	    	  foto.setContexto("Produto");
	    	  foto.setNome(serviceS3.uploadFile(file)); 
	    	  
	    	  try {
	    		  Fotos ftSalva =   service.adicioanarFoto(foto);
	  			return new ResponseEntity<Fotos>(ftSalva, HttpStatus.CREATED);
	  		}catch (RegraNegocioException e) {
	  			return ResponseEntity.badRequest().body(e.getMessage());
	  		}
	    } 
		
}
