import java.util.ArrayList;
import java.math.BigDecimal;

public class Product {
    private String name;
    private BigDecimal price;
    private ArrayList<ProductArticle> articles;

    public Product() { articles = new ArrayList<>();}

    public String getName() {return name;}

    public void setName(String name) { this.name = name;}

    public BigDecimal getPrice() {return price;}

    public void setPrice(BigDecimal price) {this.price = price;}

    public ArrayList<ProductArticle> getArticles()  {return articles;}

    public void setArticles(ArrayList<ProductArticle> articles)  {this.articles = articles;}

    @Override
    public String toString() {
        return "Product [name=" + name + ", articles=" + articles + "]";
    }

}
