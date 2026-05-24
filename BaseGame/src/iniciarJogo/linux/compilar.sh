#!/usr/bin/env bash
set -e

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
SRC="$(cd "$SCRIPT_DIR/../.." && pwd)"
OUT="$(cd "$SRC/../.." && pwd)/out"

cd "$SRC"

if ! command -v javac >/dev/null 2>&1; then
    echo "ERRO: javac nao encontrado. Instale o JDK e adicione-o ao PATH."
    exit 1
fi

if ! command -v java >/dev/null 2>&1; then
    echo "ERRO: java nao encontrado. Instale o JDK e adicione-o ao PATH."
    exit 1
fi

mkdir -p "$OUT"
echo "Compilando..."
javac -encoding UTF-8 -d "$OUT" Main.java
echo "Compilacao concluida."
echo
echo "Iniciando Runestone Trials..."
echo
java -Dfile.encoding=UTF-8 -cp "$OUT" Main
