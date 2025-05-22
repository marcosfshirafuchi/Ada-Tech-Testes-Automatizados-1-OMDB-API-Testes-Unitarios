<p align="center">
  <img src="https://i.ibb.co/wNCRx9z/image-2025-01-04-T23-13-02-901-Z.png" alt="Desenvolva+" width="200"/>
</p>

# 🎬 Desenvolva+: API REST com Java e OMDb - Teste Unitários


# 📌 Desafio: Melhorando a qualidade de nossos projetos.

## 📍 Contexto

Temos a nossa aplicação com uma API Rest pública para nossos clientes. Mas, agora, precisamos melhorar a qualidade, precisamos garantir que nossas entregas sejam feitas sem bugs.

Temos o objetivo de criar vários testes para garantir que seja entregue um produto com qualidade ao nosso cliente. Lembre-se que precisamos respeitar a pirâmide de testes. 

Caso você não tenha uma aplicação sua, pode utilizar essa aqui https://github.com/facincani/estoque como base.
---

## 🎯 Requisitos

Construir testes para as classes do sistema (service, repository e controller) e garantir uma cobertura de teste de 50%. Usando a cobertura do IntelliJ como base.

### ✅ Testes das camadas
- Lembre-se de utilizar os testes corretos conforme a camada que esteja sendo validada.


## 🛠 Tecnologias e Conceitos Esperados

Para resolver esse desafio, recomenda-se o uso dos seguintes recursos:

📌 Spring Boot (start web e starter test), JUnit.


---

## 👨‍💻 Desenvolvido por

- [Marcos Shirafuchi](https://github.com/marcosfshirafuchi)

---

## 🚀 Sobre a aplicação

Esta aplicação permite:

- Buscar informações de filmes em tempo real via OMDb API;
- Persistir os dados localmente em um banco de dados H2;
- Consultar, cadastrar, listar e deletar filmes;
- Acessar a documentação interativa com Swagger UI.

Tudo isso com uma interface REST acessível por ferramentas como Postman ou qualquer cliente HTTP.

---

## 🌐 Destaque: Integração com a OMDb API

A [**OMDb API (Open Movie Database)**](https://www.omdbapi.com/) é uma API REST gratuita e pública que fornece dados sobre filmes e séries. Nesta aplicação, ela é usada para **buscar informações precisas e atualizadas** sobre filmes a partir do título fornecido pelo usuário.

<p align="center">
  <img src="https://i.ibb.co/rRxSYD23/OMDB-API.png" alt="OMDB API" width="400"/>
</p>



---

## 🧰 Tecnologias Utilizadas

| Ferramenta | Descrição |
|-----------|-----------|
| ![Java](https://cdn.jsdelivr.net/gh/devicons/devicon/icons/java/java-original-wordmark.svg) | **Java 21**: versão mais recente com suporte LTS |
| ![Spring](https://cdn.jsdelivr.net/gh/devicons/devicon/icons/spring/spring-original-wordmark.svg) | **Spring Boot 3.4.4**: framework para APIs modernas |
| ![IntelliJ](https://cdn.jsdelivr.net/gh/devicons/devicon/icons/intellij/intellij-original.svg) | **IntelliJ IDEA**: IDE utilizada no desenvolvimento |
| ![Postman](https://cdn.jsdelivr.net/gh/devicons/devicon/icons/postman/postman-original-wordmark.svg) | **Postman 11.40.4**: testamos todos os endpoints por aqui |
| ![H2](https://cdn.jsdelivr.net/gh/devicons/devicon/icons/azuresqldatabase/azuresqldatabase-original.svg) | **H2 Database**: banco de dados em memória |
| Swagger UI | Documentação interativa para testar e visualizar a API |
| <img src="https://cdn.jsdelivr.net/gh/devicons/devicon@latest/icons/junit/junit-plain-wordmark.svg" />|**JUnit5**: é o framework que realiza os teste unitários das classes: FilmeService, FilmeRepository e FilmeController|

---

## 📌 Endpoints disponíveis

| Operação                   | URL                                      | Método HTTP |
|---------------------------|------------------------------------------|-------------|
| Buscar filme por nome     | `/filme?titulo=the forge`               | `GET`       |
| Cadastrar filme           | `/filme`                                 | `POST`      |
| Listar todos os filmes    | `/filme/listarFilmes`                    | `GET`       |
| Buscar filme por ID       | `/filme/{id}`                            | `GET`       |
| Deletar filme por ID      | `/filme/{id}`                            | `DELETE`    |

### Exemplo de payload para POST:
```json
{
  "Title": "The Karate Kid Part II"
}
```

---

## 🧪 Como testar a aplicação

1. Clone o repositório:
   ```bash
   git clone https://github.com/marcosfshirafuchi/Ada-Tech-Programacao-Web-2.git
   ```
2. Rode o projeto no IntelliJ.
3. Importe o arquivo JSON da pasta `ArquivoJsonParaOPostman` no Postman.
4. Utilize os endpoints para testar as funcionalidades.

---

## 🛢️ Banco de Dados H2

Acesse:
```
http://localhost:8080/h2
```
Utilize para inspecionar os dados persistidos durante o uso da aplicação.

---

## 📖 Swagger UI

Acesse a documentação interativa em:
```
http://localhost:8080/swagger-ui/index.html
```

<p align="center">
  <img src="https://i.ibb.co/5Xtkhsmd/Swagger.png" alt="Swagger UI"/>
</p>

---

## 📌 Diagrama de Classes

<p align="center">
  <a href="https://ibb.co/svLCr2VC"><img src="https://i.ibb.co/bgpFnPKF/Ada-Tech-IMDB-Oficial.jpg" alt="Diagrama de Classes" border="0"></a>
</p>

---

## 🎯 Objetivo do Projeto

Este projeto teve como finalidade consolidar o aprendizado prático em Java e Spring Boot com consumo de APIs externas, modelagem de dados e boas práticas REST. Além disso, promoveu a colaboração em equipe, divisão de tarefas e entrega de um produto funcional, completo e documentado.

---

## 📅 Entrega

Projeto entregue no GitHub conforme diretrizes da **Formação Desenvolva+ - Ada Tech**.
