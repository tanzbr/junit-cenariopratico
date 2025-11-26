# Trabalho Prático: Testes de Software com JUnit e REST Assured

Este repositório contém o código-fonte e a implementação prática dos testes automatizados desenvolvidos para o Trabalho Avaliativo da disciplina de Qualidade de Software. O objetivo é demonstrar a aplicação do framework **JUnit 5** integrado ao **REST Assured** em um cenário real de backend.

* Veja todas as classes de testes [AQUI](https://github.com/tanzbr/junit-cenariopratico/tree/main/src/test/java/me/caua/egiftstore/resource).

## 📋 Sobre o Projeto Alvo

O sistema testado é o **eGiftStore**, uma API REST desenvolvida em Java com Quarkus para gerenciamento de uma loja de Gift Cards. O sistema abrange funcionalidades como:

  * Gerenciamento de Usuários (Clientes e Funcionários);
  * Catálogo de Gift Cards e Empresas;
  * Processamento de Pedidos e Pagamentos;
  * Autenticação e Autorização (JWT).

## 🛠️ Ferramentas Utilizadas

  * **Linguagem:** Java 17
  * **Framework:** Quarkus
  * **Testes Unitários/Integração:** JUnit 5
  * **Testes de API:** REST Assured
  * **Gerenciamento de Dependências:** Maven

## 🧪 Cenários de Teste Implementados

Os testes focam na validação dos *Resources* (Endpoints) da aplicação, garantindo que as operações CRUD e as regras de negócio respondam corretamente.

O principal exemplo de teste pode ser encontrado em `src/test/java/me/caua/egiftstore/resource/GiftCardResourceTest.java`.

**Exemplo de fluxo testado (Criação de Gift Card):**

1.  **Setup:** Criação prévia de uma empresa parceira (GiftCompany).
2.  **Ação:** Envio de uma requisição `POST` autenticada para `/giftcard` com o payload do produto.
3.  **Verificação:** Validação do Status HTTP `201 Created` e conferência dos dados retornados no corpo da resposta.

Outras classes de teste incluídas:

  * `AuthResourceTest`: Validação de login e geração de token.
  * `OrderResourceTest`: Fluxo de criação de pedidos.
  * `UserResourceTest`: Atualização de dados cadastrais.

## 🚀 Como Executar os Testes

Para rodar a suíte de testes automatizados, certifique-se de ter o Java (JDK 17+) instalado e execute o comando abaixo na raiz do projeto:

**Linux/macOS:**

```bash
./mvnw test
```

**Windows:**

```cmd
mvnw.cmd test
```

O Maven irá baixar as dependências, compilar o projeto e executar todos os testes definidos, gerando um relatório de sucesso ou falha no console.

## 👥 Integrantes do Grupo

  * Cauã Melo
  * Filipe Batista
  * Gisele Veloso
  * João Vittor Oliveira
  * Matheus Pontieri

-----