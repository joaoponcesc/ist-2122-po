package ggc.core;

import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

import ggc.core.exception.*;

public class Parser {

  private Warehouse _store;

  public Parser(Warehouse w) {
    _store = w;
  }

  void parseFile(String filename) throws IOException, BadEntryException, DuplicatePartnerInserted, NonExistentPartner, NonExistentProduct{
    try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
      String line;

      while ((line = reader.readLine()) != null)
        parseLine(line);
    }
  }

  private void parseLine(String line) throws BadEntryException, DuplicatePartnerInserted, NonExistentPartner, NonExistentProduct{
    String[] components = line.split("\\|");

    switch (components[0]) {
      case "PARTNER":
        parsePartner(components, line);
        break;
      case "BATCH_S":
        parseSimpleProduct(components, line);
        break;

      case "BATCH_M":
        parseAggregateProduct(components, line);
        break;
        
      default:
        throw new BadEntryException("Invalid type element: " + components[0]);
    }
  }

  //PARTNER|id|nome|endereço
  private void parsePartner(String[] components, String line) throws BadEntryException, DuplicatePartnerInserted {
    if (components.length != 4)
      throw new BadEntryException("Invalid partner with wrong number of fields (4): " + line);
    String id = components[1];
    String name = components[2];
    String address = components[3];   
    _store.addParceiro(name, id, address);
  }

  //BATCH_S|idProduto|idParceiro|prec ̧o|stock-actual
  private void parseSimpleProduct(String[] components, String line) throws BadEntryException, NonExistentProduct, NonExistentPartner {
    if (components.length != 5)
      throw new BadEntryException("Invalid number of fields (4) in simple batch description: " + line);
    
    String idProduct = components[1];
    String idPartner = components[2];
    double price = Double.parseDouble(components[3]);
    int stock = Integer.parseInt(components[4]);
    if(!_store.existeProduto(idProduct)){
      _store.addProdutoSimples(idProduct);
    }
    Produto produto = _store.getProdutoByKey(idProduct.toLowerCase());
    Parceiro parceiro = _store.getParceiroByKey(idPartner);
    Lote lote = new Lote(price, stock, parceiro, produto);
    produto.addLote(lote);
    parceiro.addLote(lote);
  }
 
  //BATCH_M|idProduto|idParceiro|prec ̧o|stock-actual|agravamento|componente-1:quantidade-1#...#componente-n:quantidade-n
  private void parseAggregateProduct(String[] components, String line) throws BadEntryException, NonExistentPartner, NonExistentProduct{
    if (components.length != 7)
      throw new BadEntryException("Invalid number of fields (7) in aggregate batch description: " + line);
    
    String idProduct = components[1];
    String idPartner = components[2];
    if (!_store.existeProduto(idProduct)) {
      List<Produto> products = new ArrayList<>();
      List<Integer> quantities = new ArrayList<>();
      for (String component : components[6].split("#")) {
        String[] recipeComponent = component.split(":");
        products.add(_store.getProdutoByKey(recipeComponent[0]));     
        quantities.add(Integer.parseInt(recipeComponent[1]));
      }
      double aggravation = Double.parseDouble(components[5]);
      _store.addProdutoDerivado(new Receita(aggravation, quantities, products), idProduct);
    }   
    Produto produto = _store.getProdutoByKey(idProduct);
    Parceiro parceiro = _store.getParceiroByKey(idPartner);
    double price = Double.parseDouble(components[3]);
    int stock = Integer.parseInt(components[4]);
    Lote lote = new Lote(price, stock, parceiro, produto);
    produto.addLote(lote);
    parceiro.addLote(lote);
  }
}
