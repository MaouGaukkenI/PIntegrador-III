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
public interface TarefaRepository extends JpaRepository<TarefaEntity, Integer> {

    @Query("SELECT t.id FROM TarefaEntity t")
    List<Integer> findAllIds();

    @Query("SELECT t FROM TarefaEntity t WHERE t.id = :id")
    TarefaEntity findTarefaById(@Param("id") Integer id);

    @Modifying
    @Query("DELETE FROM TarefaEntity t WHERE t.userid = :userid")
    void deleteTarefasByUserid(@Param("userid") Integer userid);

    @Query("SELECT t FROM TarefaEntity t WHERE t.userid = :userid")
    List<TarefaEntity> findTarefasByUserid(@Param("userid") Integer userid);
    
    @Query("SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END FROM TarefaEntity t WHERE t.userid = :userid")
    boolean existsByUserid(@Param("userid") Integer userid);
}
