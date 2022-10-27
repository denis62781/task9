@Table(title = "Cats")
public class Cat {
    @Column
    int id;
    @Column
    String name;
    @Column
    int score;
    //@Column
    int field;
    int rendel;

    public Cat(int id, String name, int score, int field, int rendel) {
        this.id = id;
        this.name = name;
        this.score = score;
        this.field = field;
        this.rendel = rendel;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public int getField() {
        return field;
    }

    public int getRendel() {
        return rendel;
    }
}