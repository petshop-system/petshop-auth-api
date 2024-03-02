package com.petshop.auth.application.port.output.repository;

import com.petshop.auth.application.domain.SenhaDomain;

public interface SenhaDatabaseRespository {

    SenhaDomain Save (SenhaDomain senhaDomain) throws Exception;

    SenhaDomain getByID(Long id) throws Exception;;

    SenhaDomain getByIdUsuario(Long idUsuario) throws Exception;

    SenhaDomain getByUsuarioAndSenha(String usuario, String senha) throws Exception;

}
