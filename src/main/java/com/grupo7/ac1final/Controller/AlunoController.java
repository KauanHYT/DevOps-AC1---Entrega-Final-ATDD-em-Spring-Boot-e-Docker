package com.grupo7.ac1final.Controller;

import com.grupo7.ac1final.DTO.AlunoDTO;
import com.grupo7.ac1final.Entity.AlunoEntity;
import com.grupo7.ac1final.Service.AlunoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
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

    @PutMapping("/{id}/concluir-curso")
    public ResponseEntity<AlunoDTO> concluirCurso(@PathVariable Long id) {
        return alunoService.buscarPorId(id)
                .map(aluno -> {
                    int cursos = aluno.getCursosConcluidos() + 1;
                    aluno.setCursosConcluidos(cursos);

                    if (cursos >= 12) {
                        aluno.setPlano("PREMIUM");
                        aluno.setPossuiVoucher(true);
                    }

                    AlunoEntity salvo = alunoService.salvar(aluno);
                    return ResponseEntity.ok(converterParaDTO(salvo));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/liberar-cursos")
    public ResponseEntity<AlunoDTO> liberarCursos(@PathVariable Long id) {
        return alunoService.buscarPorId(id)
                .map(aluno -> {
                    aluno.setCursosAdicionaisPermitidos(3);

                    AlunoEntity salvo = alunoService.salvar(aluno);
                    return ResponseEntity.ok(converterParaDTO(salvo));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/notificar")
    public ResponseEntity<AlunoDTO> notificarMudancaPlano(@PathVariable Long id) {
        return alunoService.buscarPorId(id)
                .map(aluno -> {
                    if ("PREMIUM".equals(aluno.getPlano())) {
                        aluno.setNotificadoMelhoriaPlano(true);
                    }

                    AlunoEntity salvo = alunoService.salvar(aluno);
                    return ResponseEntity.ok(converterParaDTO(salvo));
                })
                .orElse(ResponseEntity.notFound().build());
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