package com.br.smallmanager.apismallManager.exeptions;

public class ErroAutenticacao extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ErroAutenticacao(String msg) {
		super(msg);
	}
}
