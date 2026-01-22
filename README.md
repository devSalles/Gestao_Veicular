# 🚚 Sistema de Gestão de Transporte

## 📌 Visão Geral

O **Sistema de Gestão de Transporte** é uma API REST desenvolvida em **Java com Spring Boot**, responsável pelo gerenciamento de **veículos**, **motoristas** e **viagens**, aplicando regras de negócio rigorosas, validações consistentes e controle transacional.

O projeto foi construído com foco em **boas práticas**, **integridade dos dados**, **organização em camadas** e **cenários reais de negócio**, sendo adequado para fins acadêmicos e portfólio profissional.

---

## 🧱 Tecnologias Utilizadas

- Java 17+
- Spring Boot
- Spring Web
- Spring Data JPA
- Jakarta Bean Validation
- Lombok
- Swagger / OpenAPI
- Maven
- Banco de Dados Relacional (MySQL / PostgreSQL / H2)

---

## 🧩 Domínios do Sistema

### 🚛 Veículo
- Placa válida e única (armazenada sem formatação)
- Campos obrigatórios: marca, modelo e ano
- Status:
  - `DISPONÍVEL`
  - `EM_VIAGEM`
  - `MANUTENÇÃO`
  - `INDISPONÍVEL`
- Relação muitos-para-muitos com motoristas
- Não pode ser excluído se possuir viagens
- Apenas veículos `DISPONÍVEL` podem iniciar viagens
- Alteração automática de status

---

### 👨‍✈️ Motorista
- Nome, CPF válido e CNH válida
- CPF e CNH únicos
- Status:
  - `ATIVO`
  - `INATIVO`
  - `SUSPENSO`
- Relação muitos-para-muitos com veículos
- Deve possuir CNH compatível com o veículo
- Não pode ser excluído se possuir viagens em andamento ou agendadas

---

### 🧭 Viagem
- Vinculada a um motorista e um veículo existentes
- Requisitos para iniciar:
  - Motorista ATIVO
  - Veículo DISPONÍVEL
  - Compatibilidade CNH x veículo
- Campos obrigatórios:
  - Origem
  - Destino
  - Data/hora de saída
  - Data/hora de chegada prevista
- Regras de datas:
  - Saída não pode ser no passado
  - Chegada prevista deve ser posterior à saída
- Finalização:
  - Registro da chegada real
  - Cálculo de atraso
  - Atualização de km do veículo
  - Veículo retorna para `DISPONÍVEL`
- Cancelamento permitido se não finalizada

---

## ⚠️ Regras Gerais

- Nenhum campo obrigatório pode ser nulo ou vazio
- Um motorista não pode estar em duas viagens simultâneas
- Um veículo não pode participar de mais de uma viagem ao mesmo tempo
- Exclusões respeitam integridade referencial
- Operações críticas são transacionais
- Erros retornam mensagens claras e objetivas

---

## 🔁 Cálculos e Ações Automáticas

- Atualização do KM do viagem
- Cálculo automático de atraso
- Alteração automática de status

---

## 📌 Rotas da API

### 🚛 Veículo — `/veiculo`

| Método | Rota | Descrição |
|------|------|-----------|
| POST | `/salvar-veiculo` | Cadastra um novo veículo |
| PUT | `/atualizar-veiculo/{id}` | Atualiza dados do veículo |
| PATCH | `/colocar-em-manutencao/{id}` | Coloca veículo em manutenção |
| PATCH | `/retirar-da-manutencao/{id}` | Retira veículo da manutenção |
| GET | `/mostar-todos` | Lista todos os veículos |
| GET | `/exibir-por-placa/{placa}` | Busca veículo por placa |
| GET | `/exibir-por-status/{statusVeiculo}` | Lista veículos por status |
| DELETE | `/desativar-veiculo/{idMotorista}` | Desativa veículo |

---

### 👨‍✈️ Motorista — `/motorista`

| Método | Rota | Descrição |
|------|------|-----------|
| POST | `/salvar-motorista` | Cadastra um motorista |
| POST | `/vincular-motorista-veiculo` | Vincula motorista a veículo |
| PUT | `/atualizar-motorista/{id}` | Atualiza dados do motorista |
| GET | `/listar-todos` | Lista todos os motoristas |
| GET | `/exibir-por-id/{id}` | Busca motorista por ID |
| GET | `/exibir-CPF/{cpf}` | Busca motorista por CPF |
| DELETE | `/excluir/{idMotorista}` | Desativa motorista |

---

### 🧭 Viagem — `/viagem`

| Método | Rota | Descrição |
|------|------|-----------|
| POST | `/agendar-viagem` | Agenda uma nova viagem |
| POST | `/iniciar-viagem` | Inicia uma viagem |
| PUT | `/finalizar-viagem/{idViagem}` | Finaliza uma viagem |
| GET | `/listar-viagens` | Lista todas as viagens |
| GET | `/buscar-id/{idViagem}` | Busca viagem por ID |
| GET | `/buscar-veiculo/{idVeiculo}` | Busca viagens por veículo |
| GET | `/buscar-motorista/{idMotorista}` | Busca viagens por motorista |
| GET | `/consultar-por-status/{statusViagem}` | Busca viagens por status |
| GET | `/consulta-periodo-por-data-saida` | Consulta por período de saída |
| GET | `/consulta-periodo-data-chegada-prevista` | Consulta por chegada prevista |
| GET | `/consulta-periodo-data-chegada-real` | Consulta por chegada real |
| DELETE | `/cancelar-viagem/{idViagem}` | Cancela uma viagem |

---

## 📚 Documentação

A API possui documentação interativa via **Swagger/OpenAPI**, permitindo testar todos os endpoints diretamente pelo navegador.

---

## 🎯 Objetivo do Projeto

- Aplicar regras de negócio reais
- Demonstrar domínio de Spring Boot
- Garantir integridade e consistência de dados
- Estrutura preparada para ambiente corporativo

---
