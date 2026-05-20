public enum Armaduras {
    // ─── Armaduras Leves ───
    NONE             ("Sem Armadura", ArmorType.UNARMORED,0,0),
    ACOLCHOADA       ("Armadura Acolchoada", ArmorType.LIGHT,1,0),
    COURO            ("Armadura de Couro", ArmorType.LIGHT,2,0),
    COURO_BATIDO     ("Couro Batido", ArmorType.LIGHT,3,-1),
    GIBAO_DE_PELES   ("Gibão de Peles", ArmorType.LIGHT,4,-3),
    COURACA          ("Couraça", ArmorType.LIGHT,5,-4),
    // ─── Armaduras Pesadas ───
    BRUNEA           ("Brunea", ArmorType.HEAVY,5,-2),
    COTA_DE_MALHA    ("Cota de Malha", ArmorType.HEAVY,6,-2),
    LORIGA_SEGMENTADA("Loriga Segmentada", ArmorType.HEAVY,7,-3),
    MEIA_ARMADURA    ("Meia Armadura", ArmorType.HEAVY,8,-4),
    ARMADURA_COMPLETA("Armadura Completa", ArmorType.HEAVY, 10,-5),
    // ─── Escudos ───
    ESCUDO_LEVE      ("Escudo Leve",ArmorType.SHIELD,1,-1),
    ESCUDO_PESADO    ("Escudo Pesado",ArmorType.SHIELD,2,-2);
    // ─── Campos ───

    private final String displayName;
    private final ArmorType type;
    private final int defenseBonus;
    /** Penalidade de armadura (negativo = penalidade) */
    private final int armorPenalty;

    // ─── Construtor ───

    Armaduras(String displayName, ArmorType type, int defenseBonus, int armorPenalty) {
        this.displayName  = displayName;
        this.type         = type;
        this.defenseBonus = defenseBonus;
        this.armorPenalty = armorPenalty;}

    // ─── Getters ───

    public String getDisplayName() { return displayName; }
    public ArmorType getType()     { return type; }
    public int getDefenseBonus()   { return defenseBonus; }
    public int getArmorPenalty()   { return armorPenalty; }

    // ─── Utilitários ───

    public boolean temPenalidade() {
        return armorPenalty < 0;}

    @Override
    public String toString() {
        String penalty = armorPenalty == 0
                ? "0"
                : String.valueOf(armorPenalty);

        return String.format(
                "%-24s | Tipo: %-8s | Defesa: +%d | Penalidade: %s",
                displayName, type.getLabel(), defenseBonus, penalty);}

    // ─── Enum Interno ───

    public enum ArmorType {
        UNARMORED("Sem Armadura"),
        LIGHT  ("Leve"),
        HEAVY  ("Pesada"),
        SHIELD ("Escudo");

        private final String label;
        ArmorType(String label) { this.label = label; }
        public String getLabel() { return label; }
    }
}