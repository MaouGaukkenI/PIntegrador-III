package com.senac.atividades.data;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author TheDe
 */
@Repository
public interface HistoricoRepository extends JpaRepository<HistoricoEntity, Integer> {

    @Query("SELECT h.id FROM HistoricoEntity h")
    List<Integer> findAllIds();

    @Query("SELECT u FROM HistoricoEntity u WHERE u.id = :id")
    HistoricoEntity findHistoricoById(@Param("id") Integer id);

    @Modifying
    @Query("DELETE FROM HistoricoEntity u WHERE u.userid = :userid")
    void deleteHistoricosByUserid(@Param("userid") Integer userid);

    @Query("SELECT u FROM HistoricoEntity u WHERE u.userid = :userid")
    List<HistoricoEntity> findHistoricosByUserid(@Param("userid") Integer userid);

    @Query("SELECT CASE WHEN COUNT(h) > 0 THEN true ELSE false END FROM HistoricoEntity h WHERE h.userid = :userid")
    boolean existsByUserid(@Param("userid") Integer userid);

}
