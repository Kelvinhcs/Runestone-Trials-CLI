public enum Armaduras {
    // ─── Sem Armadura ─────────────────────────────────────────────────────────
    NONE("Nenhuma", ArmorType.UNARMORED, 0, 0, 0.0),

    // ─── Armaduras Leves ──────────────────────────────────────────────────────
    CLOTH_ROBE("Manto de Tecido", ArmorType.LIGHT, 1, 0, 0.5),
    LEATHER_ARMOR("Couro", ArmorType.LIGHT, 2, 0, 3.0),
    STUDDED_LEATHER("Couro Cravejado", ArmorType.LIGHT, 3, 0, 4.5),

    // ─── Armaduras Médias ─────────────────────────────────────────────────────
    HIDE_ARMOR("Couro Bruto", ArmorType.MEDIUM, 4, -1, 8.0),
    CHAIN_SHIRT("Camisola de Malha", ArmorType.MEDIUM, 5, -1, 10.0),
    SCALE_MAIL("Escamas", ArmorType.MEDIUM, 6, -2, 14.0),
    BREASTPLATE("Peitoral", ArmorType.MEDIUM, 7, -1, 12.0),

    // ─── Armaduras Pesadas ────────────────────────────────────────────────────
    CHAIN_MAIL("Cota de Malha", ArmorType.HEAVY, 10, -3, 25.0),
    SPLINT_ARMOR("Laminar", ArmorType.HEAVY, 12, -4, 30.0),
    PLATE_ARMOR("Armadura de Placas", ArmorType.HEAVY, 15, -5, 35.0),

    // ─── Armaduras Mágicas ────────────────────────────────────────────────────
    ARCANE_ROBE("Manto Arcano", ArmorType.MAGICAL, 4, 2, 2.0),
    MITHRIL_CHAIN("Malha de Mithril", ArmorType.MAGICAL, 12, 0, 8.0),
    DRAGONSCALE("Escamas de Dragão", ArmorType.MAGICAL, 16, 0, 20.0);

    // ─── Campos ───────────────────────────────────────────────────────────────

    private final String displayName;
    private final ArmorType type;
    private final int defense;
    /** Modificador de agilidade (negativo = penalidade) */
    private final int agilityModifier;
    private final double weight;

    // ─── Construtor ───────────────────────────────────────────────────────────

    Armaduras(String displayName, ArmorType type, int defense, int agilityModifier, double weight) {
        this.displayName = displayName;
        this.type = type;
        this.defense = defense;
        this.agilityModifier = agilityModifier;
        this.weight = weight;
}

    // ─── Getters ──────────────────────────────────────────────────────────────

    public String getDisplayName() {return displayName;}
    public ArmorType getType() {return type;}
    public int getDefense() {return defense;}
    public int getAgilityModifier() {return agilityModifier;}
    public double getWeight() {return weight;}

    // ─── Utilitários ──────────────────────────────────────────────────────────

    /** Retorna se a armadura impõe penalidade de agilidade */
    public boolean hasPenalty() {
        return agilityModifier < 0;
    }

    /** Descrição formatada */
    @Override
    public String toString() {
        String agility = agilityModifier >= 0
                ? String.format("+%d", agilityModifier)
                : String.valueOf(agilityModifier);

        return String.format(
                "%-24s | Tipo: %-12s | Defesa: %2d | Agilidade: %3s | Peso: %.1fkg",
                displayName, type.getLabel(),
                defense, agility, weight);
}

    // ─── Enum Interno: Tipo de Armadura ───────────────────────────────────────

    public enum ArmorType {
        UNARMORED("Sem Armadura"),
        LIGHT("Leve"),
        MEDIUM("Média"),
        HEAVY("Pesada"),
        MAGICAL("Mágica");

        private final String label;
        ArmorType(String label) {this.label = label;}
        public String getLabel() {return label;}
    }
}