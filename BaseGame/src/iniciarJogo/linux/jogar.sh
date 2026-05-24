#!/usr/bin/env bash
set -e

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
SRC="$(cd "$SCRIPT_DIR/../.." && pwd)"
OUT="$(cd "$SRC/../.." && pwd)/out"

cd "$SRC"

if [[ ! -f "$OUT/Main.class" ]]; then
    echo "Classes nao encontradas. Execute ./compilar.sh primeiro."
    exit 1
fi

if ! command -v java >/dev/null 2>&1; then
    echo "ERRO: java nao encontrado. Instale o JDK e adicione-o ao PATH."
    exit 1
fi

echo "Iniciando Runestone Trials..."
echo
java -Dfile.encoding=UTF-8 -cp "$OUT" Main
