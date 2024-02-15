# Logame

[![Language](https://img.shields.io/badge/lang-en-blue)]() [![Language](https://img.shields.io/badge/lang-pt_br-green)]()

Logame is an application designed to manage your games, organizing them into 4 "states": Playing, Next, Backlog, and Finished.

The project originated from the fact that besides HowLongToBeat, there is no other platform with this purpose. The project then attempts to eliminate the need for spreadsheets or having to use an online platform like the one mentioned earlier.

## Features

- Manage the games you are playing, intend to play, and have already finished.
- Progress system where you can measure how much you have progressed in the game. [In development]
- Does not require an Internet connection.
- Data export to spreadsheets. [In development]

## Tech

Logame is built using the following technologies:

- [Java](https://www.java.com/en/download/help/whatis_java.html) - Base language for system construction.
- [JavaFX](https://openjfx.io/) - Framework for building more sophisticated interfaces in Java.
- [SQLite](https://www.sqlite.org/index.html) - Library for creating an integrated database system.
- [Jackson](https://github.com/FasterXML/jackson) - Library for JSON file manipulation.
- [Gradle](https://gradle.org/) - Build Tool.

## Installation

Currently, there are no ready-to-use versions available. But you can build from the source code, as explained in the next topic.

#### Building for source

First, make sure you have Java (preferably version 21 LTS) and Gradle (preferably version 8.5 or higher) installed with the commands:
```sh
java --version
```
```sh
gradle --version
```

Clone the project using git into your desired directory:

```sh
git clone https://github.com/ciceroAugustoON/Logame.git
```
Or download the zip by clicking on the "Code" button.
Then, execute the command in the same directory:

```sh
gradle build run
```
Now, it's ready for testing!
