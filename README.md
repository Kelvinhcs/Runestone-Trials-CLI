# 🪨 Runestone Trials CLI 🪨
![Java](https://img.shields.io/badge/Java-21-blue)
![Status](https://img.shields.io/badge/Status-Em%20Desenvolvimento-yellow)
![Interface](https://img.shields.io/badge/Interface-CLI-lightgrey)
![Paradigma](https://img.shields.io/badge/Paradigma-POO-purple)

O **Runestone Trials CLI** é um sistema interativo via terminal que permite ao usuário criar seu próprio personagem, testar e evoluir ele em um ambiente inspirado na mitologia nórdica.

O jogador pode personalizar seu próprio nome de personagem e escolher entre as classes e suas respectivas armas disponíveis, cada uma com atributos e comportamentos distintos. Após a criação, é possível visualizar a ficha do personagem e simular combates contra diversos inimigos distintos.



## ⚙️ Funcionalidades ⚙️

* 🧙 Criação de personagem customizada.

* 📊 Exibição de ficha do personagem:

  * Nome.
  * Classe escolhida.
  * Arma escolhida.
  * Pontos de vida totais.
  * Ataque.
  * Defesa.

* ⚔️ Sistema de combate:

  * PvP entre personagens criados pelo próprio jogador.
  * Sistema de masmoras para exploração aleatória.
  * Comportamentos diferentes com base na escolha das armas e classes.

* 📋 Menu interativo via terminal:

  * Navegação simples e intuitiva para trazer a melhor diversão possível.

---

## 🏗️ Estrutura do Projeto  🏗️

```bash
BaseGame/src/
├── personagens/     # package personagens (Personagem, Guerreiro, Mago, Arqueiro)
├── inimigos/        # package inimigos (Goblin, Orc, Esqueleto, Lobo, Troll)
├── equipamento/     # package equipamento (Armas, Armaduras)
├── util/            # package util (Log)
├── iniciarJogo/
│   ├── windows/     # compilar.bat, jogar.bat
│   └── linux/       # compilar.sh, jogar.sh
├── Main.java
└── … (menu, batalha, etc.)
```

## 🚀 Como executar 🚀

### Pré-requisitos

- [JDK](https://www.oracle.com/java/technologies/downloads/) instalado (recomendado: Java 21 ou superior).
- `javac` e `java` disponíveis no PATH do sistema.
- Para conferir no terminal: `javac -version` e `java -version`.

Os scripts de inicialização ficam em `BaseGame/src/iniciarJogo/`:

| Arquivo | Função |
|---------|--------|
| `compilar.bat` / `compilar.sh` | Compila o projeto e inicia o jogo |
| `jogar.bat` / `jogar.sh` | Apenas inicia o jogo (exige compilação prévia) |

As classes compiladas são geradas na pasta `out/` na raiz do repositório.

---

### Windows

#### Opção 1 — Clicar no arquivo

1. Abra a pasta `BaseGame\src\iniciarJogo\windows\` no Explorador de Arquivos.
2. Dê **duplo clique** em:
   - **`compilar.bat`** — compila e abre o jogo no terminal (use na primeira vez ou após alterar o código).
   - **`jogar.bat`** — abre o jogo sem recompilar (use quando já tiver compilado antes).

O terminal permanece aberto enquanto você joga. Para sair do jogo, escolha a opção **5** no menu e pressione uma tecla quando o script pedir.

#### Opção 2 — Terminal (PowerShell ou CMD)

**Usando os scripts:**

```powershell
cd BaseGame\src\iniciarJogo\windows
.\compilar.bat
```

Para jogar sem recompilar:

```powershell
cd BaseGame\src\iniciarJogo\windows
.\jogar.bat
```

**Compilando e executando manualmente:**

```powershell
cd BaseGame\src
javac -encoding UTF-8 -d ..\..\out Main.java
java -Dfile.encoding=UTF-8 -cp ..\..\out Main
```

---

### Linux / macOS

#### Opção 1 — Executar o arquivo (gerenciador de arquivos)

1. Abra a pasta `BaseGame/src/iniciarJogo/linux/`.
2. Na **primeira vez**, torne os scripts executáveis pelo terminal (veja abaixo) ou pelas propriedades do arquivo, se o seu ambiente permitir.
3. Execute **`compilar.sh`** (compila e inicia) ou **`jogar.sh`** (só inicia).

Em muitas distribuições, o clique direto em `.sh` abre o editor em vez de executar; nesse caso, use a opção 2.

#### Opção 2 — Terminal

**Primeira vez** — dar permissão de execução:

```bash
cd BaseGame/src/iniciarJogo/linux
chmod +x compilar.sh jogar.sh
```

**Usando os scripts:**

```bash
cd BaseGame/src/iniciarJogo/linux
./compilar.sh
```

Para jogar sem recompilar:

```bash
cd BaseGame/src/iniciarJogo/linux
./jogar.sh
```

**Compilando e executando manualmente:**

```bash
cd BaseGame/src
javac -encoding UTF-8 -d ../../out Main.java
java -Dfile.encoding=UTF-8 -cp ../../out Main
```

---

## 📌 Observações 📌

* Desenvolvido em **Java** - Versão 25.0.2 (LTS).
* Execução completa via terminal (CLI).
* Até o momento o projeto não possui persistência dedados ou interface gráfica (conforme requisitos acadêmicos).

## ⚖️ Aviso Legal ⚖️

*Este projeto foi desenvolvido exclusivamente para fins **acadêmicos e educacionais**, não possuindo finalidade comercial. Todo o conteúdo, incluindo código-fonte, estrutura e documentação, foi elaborado com o objetivo de demonstrar conceitos técnicos e boas práticas de desenvolvimento de software. Qualquer semelhança com sistemas, jogos, marcas, nomes, personagens ou propriedades intelectuais existentes é meramente coincidental ou utilizada apenas como referência conceitual, sem intenção de infringir direitos autorais ou de propriedade intelectual. Este projeto não coleta, armazena ou processa dados pessoais de usuários, não estando sujeito a legislações como a Lei Geral de Proteção de Dados (LGPD). O uso, cópia, modificação e distribuição deste código são de responsabilidade exclusiva do usuário, sendo recomendado citar a autoria original quando aplicável. O autor não se responsabiliza por quaisquer danos diretos ou indiretos decorrentes do uso deste software, incluindo, mas não se limitando, a falhas de execução, incompatibilidades de ambiente ou interpretações equivocadas do código. Este projeto é fornecido "no estado em que se encontra", sem garantias de qualquer tipo, expressas ou implícitas.*

## 👨‍💻 Autor 👨‍💻
Feito com ❤️ por [Kelvinhcs](https://github.com/Kelvinhcs).
- 🎓 Projeto Demonstrativo para fins acadêmicos 
> 🌱 *" Mudar, crescer, superar."*
