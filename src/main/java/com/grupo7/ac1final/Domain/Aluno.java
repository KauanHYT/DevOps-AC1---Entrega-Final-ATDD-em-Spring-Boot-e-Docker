package com.grupo7.ac1final.Domain;

public class Aluno {

    private int cursosAdicionaisPermitidos = 0;
    private int cursosConcluidos = 0;
    private String plano = "BASICO";
    private boolean possuiVoucher = false;
    private boolean notificadoMelhoriaPlano = false;

    public void concluirCurso() {
        cursosConcluidos++;

        if (cursosConcluidos >= 12) {
            plano = "PREMIUM";
            possuiVoucher = true;
        }
    }

    public String getPlano() {
        return plano;
    }

    public boolean isPossuiVoucher() {
        return possuiVoucher;
    }

    public void concluirCurso(double mediaAluno) {

        if (mediaAluno >= 7.0)
            this.cursosAdicionaisPermitidos = 3;
        else
            this.cursosAdicionaisPermitidos = 0;
    }

    public int getCursosAdicionaisPermitidos() {
        return this.cursosAdicionaisPermitidos;
    }

    public void identificarMudancaDePlano() {
        if ("PREMIUM".equals(plano)) {
            notificadoMelhoriaPlano = true;
        }
    }

    public boolean isNotificadoMelhoriaPlano() {
        return notificadoMelhoriaPlano;
    }
}