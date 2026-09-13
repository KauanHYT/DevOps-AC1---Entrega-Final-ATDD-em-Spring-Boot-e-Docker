package com.grupo7.ac1final.DomainTest;

import com.grupo7.ac1final.Domain.Aluno;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AlunoTest {

    @Test
    void deveAlterarPlanoParaPremiumAoConcluir12Cursos () {

        Aluno aluno = new Aluno();

        for(int i = 0; i < 12; i++) {
            aluno.concluirCurso();
        }

        assertEquals("PREMIUM", aluno.getPlano());
    }

    @Test
    void deveReceberVoucherAoConcluir12Cursos() {

        Aluno aluno = new Aluno();

        for (int i = 0; i < 12; i++) {
            aluno.concluirCurso();
        }

        assertTrue(aluno.isPossuiVoucher());
    }
}
