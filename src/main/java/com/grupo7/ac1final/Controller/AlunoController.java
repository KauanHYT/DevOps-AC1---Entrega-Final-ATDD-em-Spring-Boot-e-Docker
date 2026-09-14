package com.grupo7.ac1final.Controller;

import com.grupo7.ac1final.DTO.AlunoDTO;
import com.grupo7.ac1final.Entity.AlunoEntity;
import com.grupo7.ac1final.Service.AlunoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alunos")
public class AlunoController {

    private final AlunoService alunoService;

    public AlunoController(AlunoService alunoService) {
        this.alunoService = alunoService;
    }

    @PostMapping
    public ResponseEntity<AlunoDTO> criar(@RequestBody AlunoDTO dto) {

        AlunoEntity aluno = new AlunoEntity();

        aluno.setPlano(dto.getPlano());
        aluno.setPossuiVoucher(dto.isPossuiVoucher());
        aluno.setCursosConcluidos(dto.getCursosConcluidos());
        aluno.setCursosAdicionaisPermitidos(dto.getCursosAdicionaisPermitidos());
        aluno.setNotificadoMelhoriaPlano(dto.isNotificadoMelhoriaPlano());

        AlunoEntity salvo = alunoService.salvar(aluno);

        return ResponseEntity.ok(converterParaDTO(salvo));
    }

    @GetMapping
    public ResponseEntity<List<AlunoDTO>> listarTodos() {

        List<AlunoDTO> alunos = alunoService.listarTodos()
                .stream()
                .map(this::converterParaDTO)
                .toList();

        return ResponseEntity.ok(alunos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlunoDTO> buscarPorId(@PathVariable Long id) {

        return alunoService.buscarPorId(id)
                .map(aluno -> ResponseEntity.ok(converterParaDTO(aluno)))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {

        if (alunoService.buscarPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        alunoService.deletar(id);

        return ResponseEntity.noContent().build();
    }

    private AlunoDTO converterParaDTO(AlunoEntity aluno) {

        AlunoDTO dto = new AlunoDTO();

        dto.setId(aluno.getId());
        dto.setPlano(aluno.getPlano());
        dto.setPossuiVoucher(aluno.isPossuiVoucher());
        dto.setCursosConcluidos(aluno.getCursosConcluidos());
        dto.setCursosAdicionaisPermitidos(aluno.getCursosAdicionaisPermitidos());
        dto.setNotificadoMelhoriaPlano(aluno.isNotificadoMelhoriaPlano());

        return dto;
    }
}