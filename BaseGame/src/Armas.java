public enum Armas {
    // ─── Espadas ───────────────────────────────────────────────────────────────
    DAGGER("Adaga",WeaponType.SWORD, 4, 2,18,1.0),
    SHORT_SWORD("Espada Curta",WeaponType.SWORD,6, 4, 16, 1.5),
    LONG_SWORD("Espada Longa",WeaponType.SWORD,8, 6, 14, 2.5),
    GREATSWORD("Montante",WeaponType.SWORD,14, 10, 9, 6.0),

    // ─── Machados ─────────────────────────────────────────────────────────────
    HAND_AXE("Machadinha", WeaponType.AXE,6,  3, 15, 1.5),
    BATTLE_AXE("Machado de Guerra", WeaponType.AXE,10,  7, 11, 4.0),

    // ─── Lanças / Cajados ─────────────────────────────────────────────────────
    SPEAR("Lança", WeaponType.POLEARM,8, 5, 14, 2.0),
    STAFF("Cajado", WeaponType.POLEARM, 4, 2, 13, 2.0),

    // ─── Armas de Impacto ─────────────────────────────────────────────────────
    CLUB("Clava", WeaponType.BLUNT,4,  2, 15, 1.5),
    MACE("Maça", WeaponType.BLUNT,8,  6, 13, 3.0),
    WAR_HAMMER("Martelo de Guerra",WeaponType.BLUNT,12, 10, 10, 5.0),

    // ─── Armas à Distância ────────────────────────────────────────────────────
    SHORT_BOW("Arco Curto", WeaponType.RANGED,6, 4, 15, 1.0),
    LONG_BOW("Arco Longo", WeaponType.RANGED,9, 7, 13, 1.5),
    CROSSBOW("Besta", WeaponType.RANGED,12, 8, 11, 3.0),

    // ─── Armas Mágicas ────────────────────────────────────────────────────────
    WAND("Varinha", WeaponType.MAGIC, 5, 3, 15, 0.5),
    TOME("Grimório", WeaponType.MAGIC,8, 5, 12, 1.5),
    ORB("Orbe Arcano", WeaponType.MAGIC,12, 9, 10, 1.0);

    // ─── Campos ───────────────────────────────────────────────────────────────

    private final String displayName;
    private final WeaponType type;
    private final int maxDamage;
    private final int minDamage;
    /** Velocidade de ataque (menor = mais rápido) */
    private final int attackSpeed;
    private final double weight;

    // ─── Construtor ───────────────────────────────────────────────────────────

    Armas(String displayName, WeaponType type, int maxDamage, int minDamage, int attackSpeed, double weight) {
       this.displayName = displayName;
       this.type = type;
       this.maxDamage = maxDamage;
       this.minDamage = minDamage;
       this.attackSpeed = attackSpeed;
       this.weight = weight;
}

    // ─── Getters ──────────────────────────────────────────────────────────────

    public String getDisplayName() {return displayName;}
    public WeaponType getType() {return type;}
    public int getMaxDamage() {return maxDamage;}
    public int getMinDamage() {return minDamage;}
    public int getAttackSpeed() {return attackSpeed;}
    public double getWeight() {return weight;}

    // ─── Utilitários ──────────────────────────────────────────────────────────

    /** Calcula o dano médio da arma e retorna */
    public double getAverageDamage() {return (minDamage + maxDamage) / 2.0;}

    /** Descrição formatada */
    @Override
    public String toString() {
        return String.format(
                "%-20s | Tipo: %-8s | Dano: %2d-%2d | Velocidade: %2d | Peso: %.1fkg",
                displayName, type.getLabel(),
                minDamage, maxDamage,
                attackSpeed, weight);
}

    // ─── Enum Interno: Tipo de Arma ───────────────────────────────────────────

    public enum WeaponType {
        SWORD("Espada"),
        AXE("Machado"),
        POLEARM("Haste"),
        BLUNT("Impacto"),
        RANGED("Distância"),
        MAGIC("Mágica");

        private final String label;
        WeaponType(String label) {this.label = label;}
        public String getLabel() {return label;}
    }
}