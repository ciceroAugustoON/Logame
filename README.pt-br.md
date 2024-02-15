# Logame

[![Language](https://img.shields.io/badge/lang-en-blue)]() [![Language](https://img.shields.io/badge/lang-pt_br-green)]()

Logame é um aplicativo feito para gerenciar seus jogos, organizando-os em 4 "estados": Jogando, Próximos, Pendentes e Finalizados.

O projeto se originou do fato de que além do HowLongToBeat, não há nenhuma outra plataforma com este propósito. O projeto então tenta eliminar a necessidade de planilhas ou ter que usar uma plataforma online como a mencionada anteriormente.
## Features
- Gerenciar os jogos que você está jogando, pretende jogar e já finalizou.
- Sistema de progresso onde poderá medir o quanto você progrediu no jogo. [Em desenvolvimento]
- Não necessita de conexão com a Internet.
- Exportação dos dados em planilhas. [Em desenvolvimento]

## Tech

Logame é contruído utilizando as seguintes tecnologias:

- [Java](https://www.java.com/en/download/help/whatis_java.html) - Linguagem base para a contrução do sistema.
- [JavaFX](https://openjfx.io/) - Framework para contrução de interfaces mais requintadas em java.
- [SQLite](https://www.sqlite.org/index.html) - Biblioteca para criação de banco de dados integrado ao sistema.
- [Jackson](https://github.com/FasterXML/jackson) - Biblioteca para manipulação de arquivos JSON.
- [Gradle](https://gradle.org/) - Build Tool.

## Instalação

Atualmente não há versões prontas disponível. Mas você pode buildar do código-fonte, como explicado no próximo tópico.

#### Building for source
Primeiro, verifique se você tem Java (preferencialmente a versão 21 LTS) e Gradle (preferencialmente a versão 8.5 ou maior) instalado com os comandos:
```sh
java --version
```
```sh
gradle --version
```
Clone o projeto utilizando git no diretório desejado:

```sh
git clone https://github.com/ciceroAugustoON/Logame.git
```
Ou baixe o zip clicando no botão "Code".
Em Seguida, execute o comando no mesmo diretório:
```sh
gradle build run
```
Agora é só testar!
