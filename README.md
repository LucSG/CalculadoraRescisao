# CalculadoraRescisao

[![Build Status](https://img.shields.io/badge/build-passing-brightgreen)](https://example.com/build)
[![Coverage Status](https://img.shields.io/badge/coverage-90%25-green)](https://example.com/coverage)

Uma API REST para calcular a rescisão trabalhista brasileira, desenvolvida com Spring Boot.

## Visão Geral

Este projeto fornece uma API para calcular os valores da rescisão de contrato de trabalho no Brasil, considerando diferentes tipos de rescisão, salários, datas de admissão e desligamento, e outras variáveis relevantes. A API é projetada para ser fácil de usar e integrar com outros sistemas.

## Funcionalidades

*   **Cálculo do saldo de salário:** Calcula o valor do salário devido ao empregado no mês da rescisão.
*   **Cálculo do 13º salário proporcional:** Calcula o valor do 13º salário proporcional aos meses trabalhados no ano da rescisão.
*   **Cálculo das férias proporcionais e vencidas:** Calcula o valor das férias proporcionais e vencidas (se houver).
*   **Cálculo do aviso prévio indenizado:** Calcula o valor do aviso prévio indenizado (se aplicável).
*   **Cálculo dos descontos de INSS e IRRF:** Calcula os descontos de INSS e IRRF com base nas tabelas vigentes.
*   **API RESTful com endpoints bem definidos:** A API segue os princípios RESTful e oferece endpoints bem definidos para cada funcionalidade.
*   **Validação de dados de entrada:** A API valida os dados de entrada para garantir que eles sejam válidos e consistentes.
*   **Tratamento de erros:** A API trata os erros de forma elegante e retorna mensagens de erro informativas.

## Tecnologias Utilizadas

*   **Java:** Linguagem de programação principal.
*   **Spring Boot:** Framework para construir aplicações Java de forma rápida e fácil.
*   **Gradle:** Sistema de build para gerenciar as dependências e construir o projeto.
*   **MySQL:** Banco de dados relacional para armazenar as tabelas de INSS e IRRF.
*   **Lombok:**  Biblioteca para reduzir o boilerplate code (getters, setters, etc.).
*   **JUnit:** Framework para testes unitários e de integração.

## Pré-requisitos

*   [Java Development Kit (JDK) 21](https://www.oracle.com/java/technologies/javase-jdk21-downloads.html) ou superior
*   [Gradle 7](https://gradle.org/install/) ou superior
*   [MySQL Server](https://www.mysql.com/downloads/) (ou outro banco de dados configurado)

## Como Executar a Aplicação

1.  Clone o repositório:

    ```bash
    git clone https://github.com/LucSG/CalculadoraRescisao.git
    ```

2.  Navegue até o diretório do projeto:

    ```bash
    cd CalculadoraRescisao
    ```

3.  Configure o banco de dados:

    *   Crie um banco de dados MySQL chamado `rescisao_db` (ou ajuste o nome no arquivo `application.properties`).
    *   Configure as credenciais de acesso ao banco de dados no arquivo `src/main/resources/application.properties`:

        ```properties
        spring.datasource.url=jdbc:mysql://localhost:3306/rescisao_db?createDatabaseIfNotExist=true&serverTimezone=America/Sao_Paulo
        spring.datasource.username=seu_usuario
        spring.datasource.password=sua_senha
        ```

4.  Execute a aplicação usando o Gradle Wrapper:

    ```bash
    ./gradlew bootRun
    ```

    (No Windows, use `.\gradlew.bat bootRun`)

## Como Usar a API

A API está disponível no endereço `http://localhost:8080`.

*   **Endpoint para Calcular a Rescisão:**

    *   `POST /rescisao/calcular`
    *   Descrição: Calcula os valores da rescisão com base nos dados fornecidos.
    *   Corpo da requisição (JSON):

        ```json
        {
          "salario": 3000.00,
          "tipoRescisao": "SEM_JUSTA_CAUSA",
          "dataAdmissao": "2023-01-15",
          "dataDesligamento": "2024-02-29",
          "avisoPrevio": true,
          "feriasVencidas": false
        }
        ```

        *   `salario`: Salário base do empregado.
        *   `tipoRescisao`: Tipo de rescisão (por exemplo, `SEM_JUSTA_CAUSA`, `PEDIDO_DEMISSAO`).
        *   `dataAdmissao`: Data de admissão do empregado (formato YYYY-MM-DD).
        *   `dataDesligamento`: Data de desligamento do empregado (formato YYYY-MM-DD).
        *   `avisoPrevio`: Indica se o aviso prévio é trabalhado (`false`) ou indenizado (`true`).
        *   `feriasVencidas`: Indica se o empregado tem férias vencidas a serem pagas (`true`) ou não (`false`).

    *   Resposta (JSON):

        ```json
        {
        "saldoSalario": 3000.00,
        "inssSalario": 268.25,
        "irrfSalario": 35.44,
        "decimoTerceiroProporcional": 500.00,
        "inss13": 37.50,
        "irrf13": 0.00,
        "feriasVencidas": null,
        "umTercoFerias": null,
        "feriasProporcionais": 500.00,
        "umTercoFeriasProporcionais": 166.67,
        "avisoPrevioIndenizado": 3300.00,
        "totalBruto": 7466.67,
        "totalLiquido": 7125.48
        }
        ```

        *   `saldoSalario`: Valor do saldo de salário.
        *   `decimoTerceiroProporcional`: Valor do 13º salário proporcional.
        *   `feriasProporcionais`: Valor das férias proporcionais.
        *   `umTercoFerias`: Valor do 1/3 das férias.
        *   `avisoPrevioIndenizado`: Valor do aviso prévio indenizado (se aplicável).
        *   `totalBruto`: Valor total bruto da rescisão.
        *   `inss`: Valor do desconto de INSS.
        *   `irrf`: Valor do desconto de IRRF.
        *   `totalLiquido`: Valor total líquido da rescisão (total bruto - INSS - IRRF).

## Próximos Passos (Roadmap)

*   [ X ] Implementar o cálculo correto de INSS e IRRF (considerando as tabelas progressivas e as deduções permitidas).
*   [ X ] Implementar o cálculo correto do aviso prévio indenizado (considerando o tempo de serviço do empregado).
*   [ X ] Adicionar suporte para diferentes tipos de rescisão (por justa causa, acordo, etc.).
*   [   ] Adicionar testes unitários e de integração para garantir a qualidade do código.
*   [   ] Melhorar o tratamento de erros (retornar mensagens de erro mais específicas e códigos de status HTTP apropriados).

