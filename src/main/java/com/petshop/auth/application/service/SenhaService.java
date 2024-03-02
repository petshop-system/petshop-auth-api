package com.petshop.auth.application.service;

import com.petshop.auth.application.domain.SenhaDomain;
import com.petshop.auth.application.port.input.SenhaUsercase;
import com.petshop.auth.application.port.output.repository.SenhaCacheRepository;
import com.petshop.auth.application.port.output.repository.SenhaDatabaseRespository;

public class SenhaService implements SenhaUsercase {

    private final SenhaCacheRepository senhaCacheRepository;

    private final SenhaDatabaseRespository senhaDatabaseRespository;

    public SenhaService (SenhaCacheRepository senhaCacheRepository, SenhaDatabaseRespository senhaDatabaseRespository) {
        this.senhaCacheRepository = senhaCacheRepository;
        this.senhaDatabaseRespository = senhaDatabaseRespository;
    }

    @Override
    public SenhaDomain getByID(Long id) throws Exception {
        return null;
    }

    @Override
    public SenhaDomain create(SenhaDomain senhaDomain) throws Exception {

        senhaDomain = this.senhaDatabaseRespository.Save(senhaDomain);
        this.senhaCacheRepository.set(senhaDomain);

        return senhaDomain;
    }

    @Override
    public SenhaDomain getByIdUsuario(Long idUsuario) throws Exception {
        return null;
    }

    @Override
    public SenhaDomain getByUsuarioAndSenha(String usuario, String senha) throws Exception {
        return null;
    }
}
