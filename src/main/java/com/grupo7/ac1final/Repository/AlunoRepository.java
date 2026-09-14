package com.grupo7.ac1final.Repository;

import com.grupo7.ac1final.Entity.AlunoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlunoRepository extends JpaRepository<AlunoEntity, Long> {
}