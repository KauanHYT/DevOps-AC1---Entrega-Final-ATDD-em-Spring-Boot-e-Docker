package com.grupo7.ac1final.Service;

import com.grupo7.ac1final.Entity.AlunoEntity;
import com.grupo7.ac1final.Repository.AlunoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlunoService {

    private final AlunoRepository alunoRepository;

    public AlunoService(AlunoRepository alunoRepository) {
        this.alunoRepository = alunoRepository;
    }

    public AlunoEntity salvar(AlunoEntity aluno) {
        return alunoRepository.save(aluno);
    }

    public List<AlunoEntity> listarTodos() {
        return alunoRepository.findAll();
    }

    public Optional<AlunoEntity> buscarPorId(Long id) {
        return alunoRepository.findById(id);
    }

    public void deletar(Long id) {
        alunoRepository.deleteById(id);
    }
}