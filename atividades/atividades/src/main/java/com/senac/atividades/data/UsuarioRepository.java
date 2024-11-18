package com.senac.atividades.data;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author TheDe
 */
@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Integer> {

    @Query("SELECT u.id FROM UsuarioEntity u")
    List<Integer> findAllIds();

    @Query("SELECT u FROM UsuarioEntity u WHERE u.login = :login")
    UsuarioEntity findUsuarioByLogin(@Param("login") String login);

    @Query("SELECT u.login FROM UsuarioEntity u")
    List<String> findAllLogins();
}
