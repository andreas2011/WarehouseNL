
public class Article {
    private int Id;
    private String name;
    private int stock;

    public Article() {}

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "Article [Id=" + Id + ", name=" + name + ", stock=" + stock + "]";
    }

}
