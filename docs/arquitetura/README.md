# Decisões Arquiteturais — Desafio 1 (AC2)
**Sistema:** Educação Continuada Gamificada (Grupo 7)

---

## 1. Contexto Inicial e ASRs Identificados
* **Cenário atual:** Monólito em Java 21 + Spring Boot, banco PostgreSQL, API REST, interface VueJS, rodando em containers Docker, com 6 desenvolvedores e 1.000 usuários cadastrados.
* **Requisitos Arquiteturalmente Significativos (ASRs):**
  * **Escalabilidade:** Capacidade de absorver o crescimento de consultas de ranking (cenário de 100.000 usuários).
  * **Deploy Independente e Interoperabilidade:** Necessidade de atualizar o modelo de recomendação em Python em ciclo próprio.
  * **Tolerância a Falhas / Resiliência:** Garantir que a indisponibilidade do serviço de notificação não impeça o aluno de concluir cursos e receber suas recompensas (US 3 do grupo).

---

## 2. Decisão 1: Migrar agora para microsserviços?
* **Posição adotada:** **(B) Não / Ainda não.**
* **Justificativa:** Com uma equipe de 6 desenvolvedores e o volume inicial, a complexidade operacional de microsserviços (orquestração de rede, consistência eventual, debugging distribuído) superaria os benefícios. O monólito modular atual atende perfeitamente ao estágio do MVP.

---

## 3. Decisão 2: O crescimento para 100.000 usuários e o gargalo do Ranking
* **Alternativas consideradas:**
  1. *Manter o monólito atual:* Simples, mas o alto volume de consultas ao ranking poderia estrangular os recursos das demais funcionalidades (como conclusão de cursos).
  2. *Monólito Modular (Escolha do Grupo):* Manter a base de código unificada em pacotes bem delimitados (`Domain`, `Service`, `Controller`), permitindo isolar a lógica de ranking e preparar futuras extrações sem o custo imediato de uma arquitetura distribuída.
  3. *Serviços independentes:* Descartado por ora pelo overhead de infraestrutura para o time atual.
* **Trade-off aceito:** Aceita-se menor isolamento físico absoluto de infraestrutura em troca de simplicidade de desenvolvimento e menor custo operacional.

---

## 4. Decisão 3: Onde hospedar a capacidade de recomendação em IA (Python)?
* **Decisão adotada:** Como **serviço independente em Python**, integrado via API REST ou mensageria com o Core Spring Boot.
* **ASRs sustentadores:** *Tecnologia* ( ecossistema de IA é nativo de Python) e *Deploy independente* (o modelo precisa de ciclos de re-treinamento e deploy frequentes sem derrubar o backend principal de educação).

---

## 5. Decisão 4: Comunicação e Falha no fluxo de notificação (US 3)
* **Decisão adotada:** Utilizar **comunicação assíncrona** (ex: mensageria) para o envio de notificações e atualização de planos/vouchers (US 2 e US 3).
* **Justificativa:** Se o serviço de notificação (responsabilidade do Giovane no grupo) ficar indisponível, a transação principal do aluno (concluir o curso ou subir para o plano *Premium*) **não pode ser bloqueada**. O evento é enfileirado e processado assim que o serviço se restabelecer.

---

## 6. ADR (Architecture Decision Record) Resumido
* **Status:** Aceito
* **Contexto:** Evolução do sistema de 1.000 para 100.000 usuários, entrada de IA em Python e resiliência nas notificações.
* **Decisão:** Manter a base estruturada em monólito modular em Spring Boot para o core transacional, isolando a IA em microsserviço Python e desacoplando notificações por mensageria assíncrona.
* **Consequências:** Escalabilidade direcionada onde realmente importa (ranking/IA) sem inflar a complexidade de todo o sistema.

---

## 7. Respostas para o Fechamento da Atividade
1. **Qual foi o requisito que mais influenciou a decisão arquitetural?** 
   * A necessidade de resiliência e independência de ciclo da IA em Python e o isolamento de falhas nas notificações (garantindo que o aluno conclua cursos mesmo com falhas periféricas).
2. **O sistema precisa realmente de microsserviços neste momento?** 
   * Não totalmente. Apenas partes específicas que exigem escala/tecnologia distinta (como a IA) justificam serviços externos; o core transacional permanece coeso.
3. **Qual decisão mudaria se o requisito de escala independente desaparecesse?** 
   * Todo o sistema poderia continuar estritamente como um monólito tradicional acoplado, sem necessidade de filas ou serviços de IA isolados.
4. **Qual trade-off sua equipe aceitou conscientemente?** 
   * Introduzir complexidade de mensageria assíncrona e comunicação entre linguagens (Java/Python) em troca de resiliência operacional e escalabilidade seletiva.
5. **Que evidência futura poderia comprovar que a decisão foi adequada?** 
   * O sistema suportar 100k usuários com o ranking ativo, atualizações de IA sem downtime no backend, e zero perda de conclusão de cursos em instabilidades de notificação.
