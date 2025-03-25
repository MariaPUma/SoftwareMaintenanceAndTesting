package purchase;

public interface Warehouse {
  public boolean contains(String name);
  public boolean thereAreProducts(String name, int amount);
  
  public void remove(String name, int amount);
  public int getAmount(String name);
}
