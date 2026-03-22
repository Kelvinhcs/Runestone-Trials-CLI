
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

def load_milestones() -> list[dict]:
    path = BASE_DIR / "milestones.json"
    if not path.exists():
        fail(f"Arquivo não encontrado: {path}")
    with path.open("r", encoding="utf-8") as f:
        data = json.load(f)
    if not isinstance(data, list):
        fail("milestones.json deve conter uma lista de objetos.")
    return data

def milestone_exists(title: str) -> bool:
    result = subprocess.run(
        ["gh", "api", f"repos/{REPO}/milestones", "--paginate"],
        text=True,
        capture_output=True
    )
    if result.returncode != 0:
        fail(result.stderr.strip() or "Falha ao listar milestones.")
    try:
        data = json.loads(result.stdout or "[]")
    except json.JSONDecodeError:
        fail("Não foi possível interpretar a resposta da API do GitHub ao listar milestones.")
    return any(item.get("title") == title for item in data)

def find_milestone_number(title: str) -> int | None:
    result = subprocess.run(
        ["gh", "api", f"repos/{REPO}/milestones", "--paginate"],
        text=True,
        capture_output=True
    )
    if result.returncode != 0:
        fail(result.stderr.strip() or "Falha ao listar milestones.")
    try:
        data = json.loads(result.stdout or "[]")
    except json.JSONDecodeError:
        fail("Não foi possível interpretar a resposta da API do GitHub ao buscar milestones.")
    for item in data:
        if item.get("title") == title:
            return item.get("number")
    return None

def main() -> None:
    ensure_repo()
    milestones = load_milestones()

    for item in milestones:
        title = item["title"]
        description = item["description"]
        due_on = item["due_on"]

        if milestone_exists(title):
            number = find_milestone_number(title)
            if number is None:
                fail(f"Milestone encontrada, mas sem número resolvido: {title}")
            result = run_gh([
                "api", f"repos/{REPO}/milestones/{number}",
                "--method", "PATCH",
                "-f", f"title={title}",
                "-f", f"description={description}",
                "-f", f"due_on={due_on}",
            ])
            action = "updated"
        else:
            result = run_gh([
                "api", f"repos/{REPO}/milestones",
                "--method", "POST",
                "-f", f"title={title}",
                "-f", f"description={description}",
                "-f", f"due_on={due_on}",
            ])
            action = "created"

        if not DRY_RUN and result.returncode != 0:
            fail(result.stderr.strip() or f"Falha ao processar milestone: {title}")
        print(f"[OK] Milestone {action}: {title}")

if __name__ == "__main__":
    main()
