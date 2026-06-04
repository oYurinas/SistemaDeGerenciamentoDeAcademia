# Sistema de Gerenciamento de Academia

Este é um **Sistema de Gerenciamento de Academia** completo, desenvolvido em **Java** utilizando os conceitos de **Programação Orientada a Objetos (POO)** e a arquitetura **MVC (Model-View-Controller)**.

O sistema possui controle de acesso seguro com senhas criptografadas, gerenciamento completo de alunos, personal trainers, planos de assinatura, pagamentos e vinculação de treinos, além de persistência local em banco de dados relacional.

---

## 🛠️ Tecnologias Utilizadas

- **Linguagem**: Java 17
- **Gerenciador de Dependências**: Maven 3.x
- **Banco de Dados**: SQLite (via JDBC)
- **Segurança**: jBCrypt (para hashing criptográfico de senhas)

---

## 📋 Pré-requisitos

Para compilar e rodar o projeto, você precisará ter instalado em sua máquina:

1.  **Java JDK 17** ou superior instalado e configurado nas variáveis de ambiente.
2.  **Apache Maven 3.x** instalado.

---

## 🚀 Como Executar o Projeto

Você pode rodar o projeto de duas formas principais: diretamente via Maven ou gerando o executável JAR.

### Opção 1: Executando diretamente pelo Maven (Recomendado para desenvolvimento)

Abra o terminal na raiz do projeto e execute o comando abaixo. O Maven irá baixar as dependências, compilar o código e inicializar a aplicação:

```bash
mvn compile exec:java
```

### Opção 2: Gerando o executável JAR

Para empacotar o projeto em um único arquivo JAR executável que contém todas as dependências embutidas (_Fat JAR_), execute:

```bash
mvn clean package
```

Isso gerará o arquivo `academia-full.jar` na pasta `target/`. Para iniciar a aplicação a partir dele, execute:

```bash
java -jar target/academia-full.jar
```

---

## 🔐 Credenciais Padrão do Administrador

Ao executar o sistema pela primeira vez, o banco de dados `academia.db` é criado automaticamente e um usuário administrador padrão é inserido com as seguintes credenciais de acesso:

- **E-mail**: `admin@academia.com`
- **Senha**: `admin123`

_Nota: A senha é criptografada em hash seguro (BCrypt) no banco de dados na inicialização._

---

## 👥 Equipe Desenvolvedora

Este projeto foi desenvolvido como requisito de avaliação da disciplina de Programação Orientada a Objetos.

- **Yuri Nascimento de Souza** (oYurinas)
- **Pedro Daniel da Silva Paiva** (pedaniiel)
- **José Matheus Gomes Silva** (matheusgomess1)
- **Aluizio Aires da Silva Junior** (AluizioJr007)
