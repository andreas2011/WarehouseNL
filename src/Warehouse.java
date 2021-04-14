import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

public class Warehouse {
    private ArrayList<Product> products;
    private ArrayList<Article> articles;

    public Warehouse() {
        products = new ArrayList<>();
        articles = new ArrayList<>();

        read_inventory();
        read_product();
    }


    public void show() {
        System.out.println("[ARTICLES in WAREHOUSE]");
        for (int i = 0; i < articles.size(); i++) {
            System.out.println(articles.get(i));
        }
        System.out.println();
        System.out.println("[AVAILABLE PRODUCTS]");

        for (int i = 0; i < products.size(); i++) {
            Product prodcut = products.get(i);
            int max_num = -1;
            for (int j = 0; j < prodcut.getArticles().size(); j++) {
                ProductArticle art_simple = prodcut.getArticles().get(j);
                Article art = find_article_by_id(art_simple.getArt_id());
                if (max_num < 0) {
                    max_num = art.getStock() / art_simple.getAmount();
                } else {
                    max_num = Math.min(art.getStock() / art_simple.getAmount(), max_num);
                }
            }
            if (max_num <= 0)
                continue;
            System.out.println(max_num + " " + prodcut.getName() + ":");
            for (int j = 0; j < prodcut.getArticles().size(); j++) {
                ProductArticle art_simple = prodcut.getArticles().get(j);
                System.out.println("art_id=" + art_simple.getArt_id() + ", amount=" + art_simple.getAmount());
            }
        }
    }

    public Article find_article_by_id(int art_id) {
        for (int i = 0; i < articles.size(); i++) {
            if (articles.get(i).getId() == art_id) {
                return articles.get(i);
            }
        }
        return null;
    }

    public void sell_product(String product_name, int amount) {
        Product p = null;
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getName().compareTo(product_name) == 0) {
                p = products.get(i);
                break;
            }
        }
        if (p == null) {
            System.out.println(product_name + " not in warehourse.");
            return;
        }
        ArrayList<ProductArticle> comp_articles = p.getArticles();
        for (int i = 0; i < comp_articles.size(); i++) {
            ProductArticle as = comp_articles.get(i);
            Article art = this.find_article_by_id(as.getArt_id());
            if (art == null) {
                System.out.println("Warehourse has no article with id " + as.getArt_id());
                return;
            }
            if (art.getStock() < as.getAmount() * amount) {
                System.out.println("Warehourse has not enough article with id " + as.getArt_id());
                System.out.println("Need " + as.getAmount() * amount + ", but stock only have " + art.getStock());
                return;
            }
        }

        for (int i = 0; i < comp_articles.size(); i++) {
            ProductArticle as = comp_articles.get(i);
            Article art = this.find_article_by_id(as.getArt_id());
            art.setStock(art.getStock() - as.getAmount() * amount);
        }
        System.out.println(product_name + " sold success.");
    }




    private void read_inventory() {
        String filePath = "inventory.json";
        BufferedReader reader = null;
        String readJson = "";
        try {
            FileInputStream fileInputStream = new FileInputStream(filePath);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
            reader = new BufferedReader(inputStreamReader);
            String tempString = null;
            while ((tempString = reader.readLine()) != null) {
                readJson += tempString;
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                }
            }
        }
        try {
            JSONObject jsonObject = JSONObject.parseObject(readJson);
            JSONArray array = jsonObject.getJSONArray("inventory");
            for (int i = 0; i < array.size(); i++) {
                JSONObject article_json_object = (JSONObject) array.get(i);
                Article article = new Article();
                article.setId(article_json_object.getIntValue("art_id"));
                article.setName(article_json_object.getString("name"));
                article.setStock(article_json_object.getIntValue("stock"));
                this.articles.add(article);
            }
        } catch (JSONException e) {
            System.err.println(e.getMessage());
        }
    }

    private void read_product() {
        String filePath = "products.json";
        BufferedReader reader = null;
        String readJson = "";
        try {
            FileInputStream fileInputStream = new FileInputStream(filePath);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
            reader = new BufferedReader(inputStreamReader);
            String tempString = null;
            while ((tempString = reader.readLine()) != null) {
                readJson += tempString;
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                }
            }
        }
        try {
            JSONObject jsonObject = JSONObject.parseObject(readJson);
            JSONArray array = jsonObject.getJSONArray("products");
            for (int i = 0; i < array.size(); i++) {
                JSONObject article_json_object = (JSONObject) array.get(i);
                Product product = new Product();
                product.setName(article_json_object.getString("name"));
                ArrayList<ProductArticle> art_simple_list = new ArrayList<>();
                JSONArray product_array = article_json_object.getJSONArray("contain_articles");
                for (int j = 0; j < product_array.size(); j++) {
                    JSONObject artsimple_json_object = (JSONObject) product_array.get(j);
                    ProductArticle art_simple = new ProductArticle();
                    art_simple.setArt_id(artsimple_json_object.getIntValue("art_id"));
                    art_simple.setAmount(artsimple_json_object.getIntValue("amount_of"));
                    art_simple_list.add(art_simple);
                }
                product.setArticles(art_simple_list);
                this.products.add(product);
            }
        } catch (JSONException e) {
            System.err.println(e.getMessage());
        }
    }






}
