package equipamento;

public enum Armas {
    // ─── Corpo a Corpo - Leves ────────────────────────────────────────────────
    DAGGER          ("Adaga", WeaponType.SWORD,  "1d4",19,  "Perfuração"),
    SHORT_SWORD     ("Espada Curta", WeaponType.SWORD,  "1d6",19,"Perfuração"),
    // ─── Corpo a Corpo - Uma Mão ──────────────────────────────────────────────
    LONG_SWORD      ("Espada Longa", WeaponType.SWORD,  "1d8", 19,  "Corte"),
    CLUB            ("Clava", WeaponType.BLUNT,  "1d6", 20, "Impacto"),
    MACE            ("Maça", WeaponType.BLUNT,  "1d8", 20,"Impacto"),
    // ─── Corpo a Corpo - Duas Mãos ────────────────────────────────────────────
    GREATSWORD      ("Montante", WeaponType.SWORD,"2d6",19,"Corte"),
    WAR_HAMMER      ("Martelo de Guerra", WeaponType.BLUNT,"1d8",19,"Impacto"),
    // ─── Ataque à Distância ───────────────────────────────────────────────────
    SHORT_BOW       ("Arco Curto",WeaponType.RANGED,"1d6",20,"Perfuração"),
    LONG_BOW        ("Arco Longo",WeaponType.RANGED,"1d8",20,"Perfuração"),
    CROSSBOW        ("Besta",WeaponType.RANGED,"1d8",19,"Perfuração");
    // ─── Campos ───────────────────────────────────────────────────────────────

    private final String displayName;
    private final WeaponType type;
    /** Dado de dano (ex.: "1d8", "2d6") */
    private final String damageDice;
    /** Limiar do crítico - acima ou igual a este valor na d20 é crítico */
    private final int criticalThreshold;
    private final String damageType;

    // ─── Construtor ───────────────────────────────────────────────────────────

    Armas(String displayName, WeaponType type, String damageDice,
          int criticalThreshold, String damageType) {
        this.displayName = displayName;
        this.type = type;
        this.damageDice = damageDice;
        this.criticalThreshold = criticalThreshold;
        this.damageType = damageType;
    }

    // ─── Getters ──────────────────────────────────────────────────────────────

    public String getDisplayName()    { return displayName; }
    public WeaponType getType()       { return type; }
    public String getDamageDice()     { return damageDice; }
    public int getCriticalThreshold() { return criticalThreshold; }
    public String getDamageType()     { return damageType; }

    // ─── Utilitários ──────────────────────────────────────────────────────────

    /** Rola o dado de dano da arma e retorna o resultado.
     *  Suporta notação "XdY" e "XdY/XdZ" (armas versáteis - rola o primeiro dado). */
    public int rolarDano(java.util.Random rng) {
        String dice = damageDice.contains("/") ? damageDice.split("/")[0] : damageDice;
        String[] parts = dice.split("d");
        int quantidade = Integer.parseInt(parts[0]);
        int faces = Integer.parseInt(parts[1]);
        int total = 0;
        for (int i = 0; i < quantidade; i++) {
            total += rng.nextInt(faces) + 1;
        }
        return total;
    }

    public boolean isCritical(int roll) {
        return roll >= criticalThreshold;
    }

    /** Descrição formatada */
    @Override
    public String toString() {
        return String.format(
                "%-22s | Tipo: %-10s | Dano: %-6s | Crítico: >=%d | Tipo de dano: %s",
                displayName, type.getLabel(), damageDice, criticalThreshold, damageType);
    }

    // ─── Enum Interno: Tipo de Arma ───────────────────────────────────────────

    public enum WeaponType {
        SWORD("Espada"),
        BLUNT("Impacto"),
        RANGED("Distância"),
        MAGIC("Mágica");
        private final String label;
        WeaponType(String label) { this.label = label; }
        public String getLabel() { return label; }
    }
}
