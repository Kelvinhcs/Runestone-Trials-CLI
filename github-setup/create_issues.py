from __future__ import annotations

import json
import os
import subprocess
import sys
import unicodedata
from pathlib import Path

BASE_DIR = Path(__file__).resolve().parent
REPO = os.environ.get("REPO", "").strip()
DRY_RUN = os.environ.get("DRY_RUN", "0").strip() == "1"

REMOVED_LABELS = {"good-first-issue", "value:portfolio", "value:academic"}


def fail(message: str, code: int = 1) -> None:
    print(f"ERROR: {message}", file=sys.stderr)
    raise SystemExit(code)


def ensure_repo() -> None:
    if not REPO:
        fail(
            "Defina a variável de ambiente REPO. "
            "Exemplo no PowerShell: $env:REPO='seu-usuario/runestone-trials-cli'"
        )


def normalize(text: str) -> str:
    text = text.strip().lower()
    return "".join(
        c
        for c in unicodedata.normalize("NFD", text)
        if unicodedata.category(c) != "Mn"
    )


def run_gh(args: list[str]) -> subprocess.CompletedProcess[str]:
    cmd = ["gh", *args]

    if DRY_RUN:
        print("[DRY_RUN]", " ".join(cmd))
        return subprocess.CompletedProcess(cmd, 0, "", "")

    print("[RUN]", " ".join(cmd))
    return subprocess.run(
        cmd,
        text=True,
        capture_output=True,
        encoding="utf-8",
        errors="replace",
    )


def load_issues() -> list[dict]:
    path = BASE_DIR / "issues.json"
    if not path.exists():
        fail(f"Arquivo não encontrado: {path}")

    with path.open("r", encoding="utf-8") as f:
        data = json.load(f)

    if not isinstance(data, list):
        fail("issues.json deve conter uma lista de objetos.")

    return data


def get_existing_issue_titles() -> set[str]:
    result = subprocess.run(
        [
            "gh",
            "issue",
            "list",
            "--repo",
            REPO,
            "--limit",
            "500",
            "--state",
            "all",
            "--json",
            "title",
        ],
        text=True,
        capture_output=True,
        encoding="utf-8",
        errors="replace",
    )

    if result.returncode != 0:
        fail(result.stderr.strip() or "Falha ao listar issues existentes.")

    try:
        items = json.loads(result.stdout or "[]")
    except json.JSONDecodeError:
        fail("Não foi possível interpretar a saída do GitHub CLI ao listar issues.")

    return {item["title"] for item in items}


def resolve_milestone_title(expected_title: str) -> str | None:
    result = subprocess.run(
        ["gh", "api", f"repos/{REPO}/milestones?state=all", "--paginate"],
        text=True,
        capture_output=True,
        encoding="utf-8",
        errors="replace",
    )

    if result.returncode != 0:
        fail(result.stderr.strip() or "Falha ao listar milestones.")

    try:
        items = json.loads(result.stdout or "[]")
    except json.JSONDecodeError:
        fail("Não foi possível interpretar a saída da API do GitHub ao listar milestones.")

    target = normalize(expected_title)

    for item in items:
        api_title = item.get("title", "")
        if normalize(api_title) == target:
            return api_title

    return None


def labels_arg(labels: list[str]) -> str:
    filtered = [label for label in labels if label not in REMOVED_LABELS]
    return ",".join(filtered)


def main() -> None:
    ensure_repo()
    issues = load_issues()
    existing_titles = get_existing_issue_titles()

    for item in issues:
        title = item["title"]
        body = item["body"]
        labels = item.get("labels", [])
        milestone_title = item.get("milestone")

        if title in existing_titles:
            print(f"[SKIP] Issue já existe: {title}")
            continue

        args = [
            "issue",
            "create",
            "--repo",
            REPO,
            "--title",
            title,
            "--body",
            body,
        ]

        label_string = labels_arg(labels)
        if label_string:
            args.extend(["--label", label_string])

        if milestone_title:
            resolved_title = resolve_milestone_title(milestone_title)
            if resolved_title is None:
                fail(f"Milestone não encontrada para issue '{title}': {milestone_title}")
            args.extend(["--milestone", resolved_title])

        result = run_gh(args)

        if not DRY_RUN and result.returncode != 0:
            fail(result.stderr.strip() or f"Falha ao criar issue: {title}")

        print(f"[OK] Issue criada: {title}")


if __name__ == "__main__":
    main()