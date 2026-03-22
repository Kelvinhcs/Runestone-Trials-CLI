
from __future__ import annotations
import json
import os
import subprocess
import sys
from pathlib import Path

BASE_DIR = Path(__file__).resolve().parent
REPO = os.environ.get("REPO", "").strip()
DRY_RUN = os.environ.get("DRY_RUN", "0").strip() == "1"

def fail(message: str, code: int = 1) -> None:
    print(f"ERROR: {message}", file=sys.stderr)
    raise SystemExit(code)

def run_gh(args: list[str]) -> subprocess.CompletedProcess[str]:
    cmd = ["gh", *args]
    if DRY_RUN:
        print("[DRY_RUN]", " ".join(cmd))
        return subprocess.CompletedProcess(cmd, 0, "", "")
    print("[RUN]", " ".join(cmd))
    return subprocess.run(cmd, text=True, capture_output=True)

def ensure_repo() -> None:
    if not REPO:
        fail("Defina a variável de ambiente REPO. Exemplo no Windows PowerShell: $env:REPO='seu-usuario/runestone-trials-cli'")

def load_labels() -> list[dict]:
    path = BASE_DIR / "labels.json"
    if not path.exists():
        fail(f"Arquivo não encontrado: {path}")
    with path.open("r", encoding="utf-8") as f:
        data = json.load(f)
    if not isinstance(data, list):
        fail("labels.json deve conter uma lista de objetos.")
    return data

def label_exists(name: str) -> bool:
    result = subprocess.run(
        ["gh", "label", "list", "--repo", REPO, "--limit", "200", "--search", name],
        text=True,
        capture_output=True
    )
    if result.returncode != 0:
        fail(result.stderr.strip() or "Falha ao listar labels.")
    for line in result.stdout.splitlines():
        if line.split("\t", 1)[0].strip() == name:
            return True
    return False

def main() -> None:
    ensure_repo()
    labels = load_labels()

    for item in labels:
        name = item["name"]
        color = item["color"]
        description = item["description"]

        if label_exists(name):
            result = run_gh([
                "label", "edit", name,
                "--repo", REPO,
                "--color", color,
                "--description", description,
            ])
            action = "updated"
        else:
            result = run_gh([
                "label", "create", name,
                "--repo", REPO,
                "--color", color,
                "--description", description,
            ])
            action = "created"

        if not DRY_RUN and result.returncode != 0:
            fail(result.stderr.strip() or f"Falha ao processar label: {name}")
        print(f"[OK] Label {action}: {name}")

if __name__ == "__main__":
    main()
