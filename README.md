
# 🚗 Atividade Continuada - 2025.1 (Java)

Este projeto é uma aplicação Java desenvolvida para a disciplina de Programação Orientada a Objetos, cujo foco principal é simular o **cadastro e gerenciamento de segurados**, utilizando conceitos como orientação a objetos, persistência de dados, interface gráfica com SWT e testes automatizados com JUnit.

---

## 📋 Sumário

- [📚 Sobre o Projeto](#-sobre-o-projeto)
- [🧱 Tecnologias e Bibliotecas](#-tecnologias-e-bibliotecas)
- [📁 Estrutura do Projeto](#-estrutura-do-projeto)
- [🚀 Como Executar](#-como-executar)
- [✅ Funcionalidades](#-funcionalidades)
- [🧪 Testes Automatizados](#-testes-automatizados)
- [🖥️ Telas e Interface Gráfica](#-telas-e-interface-gráfica)
- [📦 Organização em Camadas](#-organização-em-camadas)
- [🧠 Regras de Negócio Implementadas](#-regras-de-negócio-implementadas)
- [🛠️ Desenvolvido com](#-desenvolvido-com)

---

## 📚 Sobre o Projeto

O projeto simula um sistema de gestão de apólices de seguros, onde é possível:

- Cadastrar segurados (pessoa física ou empresa).
- Vincular veículos a segurados.
- Calcular prêmios e franquias de apólices com base em regras de negócio.
- Persistir dados em arquivos utilizando serialização de objetos.
- Usar uma **interface gráfica com SWT** para interação com o usuário.

---

## 🧱 Tecnologias e Bibliotecas

| Tecnologia        | Descrição                                   |
|-------------------|----------------------------------------------|
| `Java 21`         | Linguagem principal                         |
| `SWT`             | Interface gráfica (GUI)                     |
| `JUnit 5`         | Testes automatizados                        |
| `Lombok`          | Redução de boilerplate (getters/setters)   |
| `PersistenciaObjetos.jar` | Serialização e leitura de objetos       |

---

## 📁 Estrutura do Projeto

```bash
ac2025.1/
├── lib/                        # Bibliotecas externas (JARs)
├── src/
│   └── br.edu.cs.poo.ac/
│       ├── entidades/         # Classes de negócio (Segurado, Apolice, etc)
│       ├── dao/               # Persistência de dados
│       ├── service/           # Regras de negócio
│       ├── telas/             # Interface gráfica com SWT
│       └── testes/            # Testes JUnit
├── out/                       # Arquivos compilados
└── README.md                  # Este arquivo
```

---

## 🚀 Como Executar

### Pré-requisitos:

- Java 21+ instalado
- IntelliJ IDEA ou outro IDE compatível
- `libs/` com todos os `.jar` corrigidos (veja [aqui](#-tecnologias-e-bibliotecas))

### Passos:

```bash
git clone https://github.com/seu-usuario/ac2025.1.git
cd ac2025.1
```

1. Abra o projeto no IntelliJ.
2. Vá em `File > Project Structure > Libraries` e adicione todos os `.jar` da pasta `lib/`.
3. Execute a classe `TelaExemploCadastro` ou `TelaAdicao` na pasta `telas`.

---

## ✅ Funcionalidades

- [x] Cadastro de segurado pessoa física e empresa
- [x] Cálculo automático de apólice (prêmio e franquia)
- [x] Associação de veículos a segurados
- [x] Interface gráfica responsiva via SWT
- [x] Persistência em arquivo via serialização
- [x] Testes automatizados com JUnit 5
- [x] Projeto estruturado em camadas (DAO, Service, GUI)
- [x] Validação de dados (CPF, CNPJ, ano do veículo, placa etc)
- [x] Apólices com bônus para segurados sem sinistro
- [x] Controle visual de fluxo por janelas (telas em SWT)

---

## 🧪 Testes Automatizados

Os testes estão localizados em:

```bash
src/br/edu/cs/poo/ac/testes/
```

Utilizam JUnit 5 e cobrem:

- Inclusão e exclusão de segurados
- Validações de campos obrigatórios
- Cálculo de valores de apólice
- Comportamento esperado em falhas

---

## 🖥️ Telas e Interface Gráfica

O sistema possui uma interface gráfica desenvolvida com SWT, com as seguintes janelas:

- Tela de cadastro de segurado
- Tela de cadastro de veículo
- Tela de emissão de apólice
- Tela de listagem e busca
- Tela de exclusão e confirmação

As janelas são abertas dinamicamente com `Shell` e `Display`, utilizando `Text`, `Label`, `Button`, `Combo`, `Table`, entre outros componentes.

---

## 📦 Organização em Camadas

O projeto está organizado seguindo a arquitetura MVC adaptada:

- **Entidades**: modelos de dados (POJOs)
- **DAO**: camada de persistência (salva e carrega dados em arquivos)
- **Service / Mediator**: regras de negócio e validações
- **Telas (GUI)**: interface do usuário
- **Testes**: testes automatizados de todas as camadas

---

## 🧠 Regras de Negócio Implementadas

- Validação de CPF/CNPJ obrigatória
- Apenas veículos com ano > 2000 podem ser segurados
- Bonificação de 20% para segurado sem sinistro anterior
- Cálculo de prêmio e franquia com base no tipo e ano do veículo
- Não é permitido cadastrar dois segurados com mesmo documento

---

## 🛠️ Desenvolvido com

- 💻 **IntelliJ IDEA**
- 🎓 **CESAR School - POO 2025.1**
- 👨‍💻 **Antonio Laprovitera** (adaptação do projeto original)

---

> ⚠️ Este projeto foi adaptado a partir de uma base pública com ajustes feitos para fins acadêmicos.  
> Reutilize com responsabilidade e aprenda com o código!
