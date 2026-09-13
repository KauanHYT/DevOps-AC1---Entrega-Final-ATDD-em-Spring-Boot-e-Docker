package com.grupo7.ac1final.Domain;

public class Aluno {

    private int cursosAdicionaisPermitidos = 0;

    public void concluirCurso() {
    }

    public String getPlano() {
        return "BASICO";
    }

    public boolean isPossuiVoucher() {
        return false;
    }

    public void concluirCurso(double mediaAluno) {

        if (mediaAluno >= 7.0) {
            this.cursosAdicionaisPermitidos = 3;
        }
    }

    public int getCursosAdicionaisPermitidos() {
        return this.cursosAdicionaisPermitidos;
    }

    public void identificarMudancaDePlano() {
    }

    public boolean isNotificadoMelhoriaPlano() {
        return false;
    }
}