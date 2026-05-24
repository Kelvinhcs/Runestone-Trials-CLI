@echo off
set "SRC=%~dp0..\.."
set "OUT=%SRC%..\..\out"
cd /d "%SRC%"

if not exist "%OUT%\Main.class" (
    echo Classes nao encontradas. Execute compilar.bat primeiro.
    pause
    exit /b 1
)

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
