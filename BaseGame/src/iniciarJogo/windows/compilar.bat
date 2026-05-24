@echo off
set "SRC=%~dp0..\.."
set "OUT=%SRC%..\..\out"
cd /d "%SRC%"

where javac >nul 2>&1
if errorlevel 1 (
    echo ERRO: javac nao encontrado. Instale o JDK e adicione-o ao PATH.
    pause
    exit /b 1
)

if not exist "%OUT%" mkdir "%OUT%"

echo Compilando...
javac -encoding UTF-8 -d "%OUT%" Main.java
if errorlevel 1 (
    echo ERRO na compilacao.
    pause
    exit /b 1
)

echo Compilacao concluida.
echo.

where java >nul 2>&1
if errorlevel 1 (
    echo ERRO: java nao encontrado. Instale o JDK e adicione-o ao PATH.
    pause
    exit /b 1
)

echo Iniciando Runestone Trials...
echo.
java -Dfile.encoding=UTF-8 -cp "%OUT%" Main

echo.
echo Jogo encerrado.
pause
