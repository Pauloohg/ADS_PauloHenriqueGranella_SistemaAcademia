package br.upf.trabalhofinalacademia.enumeration;

public enum EspecialidadeEnum {
    MUSCULACAO("Musculação"),
    PILATES("Pilates"),
    CARDIO("Cardio"),
    CROSSFIT("Crossfit"),
    YOGA("Yoga");

    private final String value;

    private EspecialidadeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}