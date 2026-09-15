# AC1 Final - Educação Continuada Gamificada

## Descrição do estudo de caso

O estudo de caso desenvolvido nesta atividade é o **Educação Continuada Gamificada**, apresentado em aula.

A proposta consiste no desenvolvimento de uma aplicação relacionada à educação continuada, utilizando práticas de desenvolvimento orientadas a testes (TDD).

---

## User Stories (US)

Cada integrante do grupo ficou responsável por uma User Story.

| # | Integrante responsável | User Story |
|---|---|---|
| 1 | Guilherme Luiz Ifanger | COMO aluno assinante do plano básico, QUERO concluir um curso com média ≥ 7,0, PARA ter direito a realizar mais 3 cursos |
| 2 | Kauan Freitas | COMO aluno que atingiu 12 cursos concluídos, QUERO ter meu plano atualizado automaticamente para "Premium", PARA receber voucher de participação em projetos reais |
| 3 | Giovane Zanzarini | COMO aluno da plataforma, QUERO ser notificado quando meu plano mudar para "Premium", PARA saber que já posso resgatar os vouchers e as moedas |
| 4 | Enrico Affonso | COMO aluno assinante do plano básico, QUERO ter acesso liberado automaticamente aos 3 cursos extras ao atingir média ≥ 7,0, PARA continuar estudando sem depender de aprovação manual |

## User Story escolhida

A User Story escolhida para desenvolvimento foi a **US 2**, relacionada à **conclusão de 12 cursos pelo aluno**.

A regra implementada determina que, ao concluir 12 cursos:

- O plano do aluno passa de **BASICO** para **PREMIUM**;
- O aluno passa a possuir um **voucher**.

---

## BDDs escritos pelos integrantes

### Kauan Freitas (US 2)

BDDs relacionados à User Story escolhida:

- Alterar o plano do aluno para PREMIUM ao concluir 12 cursos.
- Conceder um voucher ao aluno ao concluir 12 cursos.

Testes implementados:

```java
deveAlterarPlanoParaPremiumAoConcluir12Cursos()
```

```java
deveReceberVoucherAoConcluir12Cursos()
```

### Guilherme Luiz Ifanger (US 1)

BDD relacionado à User Story:

- Conceder ao aluno o direito a mais 3 cursos quando concluir um curso com média maior ou igual a 7.

Teste implementado:

```java
deveGanharDireitoAMaisTresCursosQuandoAlunoConcluirCursoComMediaMaiorOuIgualA7()
```

### Giovane Zanzarini (US 3)

BDD relacionado à User Story:

- Receber notificação de melhoria de plano quando a aplicação identificar a mudança para PREMIUM.

Teste implementado:

```java
deveReceberNotificacaoDeMelhoriaDePlanoQuandoAplicacaoIdentificarMudancaParaPremium()
```

### Enrico Affonso (US 4)

BDD relacionado à User Story:

- Liberar automaticamente 3 cursos extras para o aluno do plano básico com média maior ou igual a 7.

Teste implementado:

```java
deveLiberarTresCursosExtrasParaAlunoDoPlanoBasicoComMediaMaiorOuIgualA7()
```

---

## Ferramentas utilizadas

Para a implementação do TDD foram utilizados:

- **IntelliJ IDEA Ultimate**
- **Spring Boot**
- **Java 21**
- **Maven**
- **JUnit 5**
- **JaCoCo**

---

## Dependências do Spring Boot

O projeto utiliza as dependências solicitadas para a atividade:

- Spring Web
- Spring Data JPA
- Banco de dados H2
- Banco de dados PostgreSQL
- PGAdmin para administração do banco via container
- JaCoCo para análise da cobertura dos testes

---

# TDD

## Domain

Foi criado o pacote `Domain` dentro do projeto, contendo a classe correspondente ao domínio da aplicação:

```text
src/main/java/com/grupo7/ac1final/Domain/Aluno.java
```

A classe `Aluno` representa o domínio do aluno e contém as regras de negócio relacionadas às User Stories.

---

## DomainTest

Foi criado o pacote `DomainTest` para os testes da classe de domínio, seguindo a estrutura utilizada no exercício de calculadora realizado em aula.

```text
src/test/java/com/grupo7/ac1final/DomainTest/AlunoTest.java
```

---

## RED

Na etapa **RED** do TDD, foram criados os testes referentes aos comportamentos esperados das User Stories antes da implementação completa das regras de negócio.

Para a User Story escolhida (US 2), foram criados os seguintes testes:

```java
@Test
void deveAlterarPlanoParaPremiumAoConcluir12Cursos() {
    Aluno aluno = new Aluno();

    for (int i = 0; i < 12; i++) {
        aluno.concluirCurso();
    }

    assertEquals("PREMIUM", aluno.getPlano());
}
```

```java
@Test
void deveReceberVoucherAoConcluir12Cursos() {
    Aluno aluno = new Aluno();

    for (int i = 0; i < 12; i++) {
        aluno.concluirCurso();
    }

    assertTrue(aluno.isPossuiVoucher());
}
```

Nesta etapa, os testes foram executados antes da implementação das regras necessárias, apresentando falhas.

---

## GREEN

Na etapa **GREEN**, foram implementadas as regras necessárias para que os testes passassem.

A regra principal da User Story foi implementada na classe `Aluno`:

```java
if (cursosConcluidos >= 12) {
    plano = "PREMIUM";
    possuiVoucher = true;
}
```

Após a implementação, os testes passaram com sucesso.

---

## BLUE

Na etapa **BLUE**, todos os testes foram executados novamente e a cobertura do código foi analisada utilizando o **JaCoCo**.

Resultado final dos testes:

```text
Tests run: 8
Failures: 0
Errors: 0
Skipped: 0

BUILD SUCCESS
```

A cobertura obtida foi de **100%**, sem pontos em vermelho ou amarelo:

- **Instructions:** 100%
- **Branches:** 100%
- **Lines:** 100%
- **Methods:** 100%
- **Classes:** 100%

Com isso, a etapa BLUE foi concluída com todos os testes passando e cobertura de 100%.
