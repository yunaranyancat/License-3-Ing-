package modele;

public class DemandeARejouer {
    private String pseudo;
    private Integer idQuestionnaire;

    public DemandeARejouer(String pseudo, Integer idQuestionnaire) {
        this.pseudo = pseudo;
        this.idQuestionnaire = idQuestionnaire;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public Integer getIdQuestionnaire() {
        return idQuestionnaire;
    }

    public void setIdQuestionnaire(Integer idQuestionnaire) {
        this.idQuestionnaire = idQuestionnaire;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DemandeARejouer that = (DemandeARejouer) o;

        if (!pseudo.equals(that.pseudo)) return false;
        return idQuestionnaire.equals(that.idQuestionnaire);
    }

    @Override
    public int hashCode() {
        int result = pseudo.hashCode();
        result = 31 * result + idQuestionnaire.hashCode();
        return result;
    }
}
