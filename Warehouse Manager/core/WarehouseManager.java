package ggc.core;

import java.io.*;
import java.util.List;

import ggc.core.exception.*;

/** Façade for access. */
public class WarehouseManager {

  /** Name of file storing current warehouse. */
  private String _filename = "";

  /** The wharehouse itself. */
  private Warehouse _warehouse;

  public WarehouseManager(){
    _warehouse = new Warehouse();
  }

  public Warehouse getWarehouse(){
    return _warehouse;
  }

  public int getDateWarehouse(){
    return _warehouse.getCurrentDate();
  }

  public List<Parceiro> getParceirosWarehouse(){
    return _warehouse.getParceirosOrdered();
  }

  public List<Produto> getProdutosWarehouse(){
    return _warehouse.getProdutosOrdered();
  }

  public void aumentaDataWarehouse(int days) throws InvalidDateInsertion{
    _warehouse.aumentaDataAtual(days);
  }

  public void addParceiro(String nome, String key, String morada) throws DuplicatePartnerInserted{
    _warehouse.addParceiro(nome, key, morada);
  }

  public Parceiro getParceiroByKey(String key) throws NonExistentPartner{
    return _warehouse.getParceiroByKey(key);
  }

  public Produto getProdutoByKey(String key) throws NonExistentProduct{
    return _warehouse.getProdutoByKey(key);
  }

  public Transacao getTransacaoId(int id) throws NonExistentTransaction{
    return  _warehouse.getTransacaoId(id);
  }

  public List<Lote> getLotesWarehouse(){
    return _warehouse.getAllLotesSorted();
  }

  public List<Lote> getLotesParceiro(String key) throws NonExistentPartner{
    return _warehouse.getLotesParceiro(key);
  }

  public List<Lote> getLotesProduto(String key) throws NonExistentProduct{
    return _warehouse.getLotesProduto(key);
  }

  public List<Lote> getLotesWarehouseAbaixoPreco(int preco){
    return _warehouse.getAllLotesUnderPrice(preco);
  }

  public List<Produto> getAllProdutosWarehouse(){
    return _warehouse.getAllProdutos();
  }

  public void registaCompraWarehouse(String idParceiro, int quantidade, double preco, String idProduto) throws NonExistentPartner,
   NonExistentProduct{
    _warehouse.registaCompra(idParceiro, quantidade, preco, idProduto);
  }

  public void registaCompraWarehouse(String idParceiro,int quantidade,double preco,String idProduto,
  List<String> produtos,List<Integer> quantidades,double alpha) throws NonExistentPartner, NonExistentProduct{
    _warehouse.registaCompra(idParceiro, quantidade, preco, idProduto, produtos, quantidades, alpha);
  }

  public List<Compra> getComprasParceiroWarehouse(String idParceiro) throws NonExistentPartner{
    return _warehouse.getComprasParceiro(idParceiro);
  }

  public List<Venda> getVendasParceiroWarehouse(String idParceiro) throws NonExistentPartner{
    return _warehouse.getVendasParceiro(idParceiro);
  }

  public List<Parceiro> getAllParceirosWarehouse(){
    return _warehouse.getAllParceiros();
  }

  public boolean existeProdutoWarehouse(String idProduto){
    return _warehouse.existeProduto(idProduto);
  }

  public void registaVendaWarehouse(String idParceiro, int dataLimite, String idProduto, int quantidade) throws NonExistentPartner, 
  NonExistentProduct, NotEnoughQuantity{
    _warehouse.registaVenda(idParceiro, dataLimite, idProduto, quantidade);
  }

  public void registaDesagregacaoWarehouse(String idParceiro, String idProduto, int quantidade) throws NonExistentPartner,
  NonExistentProduct, NotEnoughQuantity{
    _warehouse.registaDesagregacao(idParceiro, idProduto, quantidade);
  }

  public void pagaVendaWarehouse(int idTransacao) throws NonExistentTransaction{
    _warehouse.pagaVenda(idTransacao);
  }

  public List<Venda> getVendasPagasParceiroWarehouse(String idParceiro) throws NonExistentPartner{
    return _warehouse.getVendasPagasParceiro(idParceiro);
  }

  public double getSaldoDisponivelWarehouse(){
    return _warehouse.getSaldoDisponivel();
  }

  public double getSaldoContabilisticoWarehouse(){
    return _warehouse.calculaSaldoContabilistico();
  }

  public void ligaDesligaNotificacoesProdutoWarehouse(String idParceiro, String idProduto) throws NonExistentPartner,
  NonExistentProduct{
    _warehouse.ligaDesligaNotificacoesProduto(idParceiro, idProduto);
  }

  public List<Notificacao> getNotificacoesParceiroWarehouse(String idParceiro) throws NonExistentPartner{
    return _warehouse.getNotificacoesParceiro(idParceiro);
  }

  /**
   * @@throws IOException
   * @@throws FileNotFoundException
   * @@throws MissingFileAssociationException
   */
  public void save() throws IOException , FileNotFoundException, MissingFileAssociationException{
    if(_filename.equals(""))
      throw new MissingFileAssociationException();
    try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(_filename));){
      out.writeObject(_warehouse);
    }
  }

  /**
   * @@param filename
   * @@throws MissingFileAssociationException
   * @@throws IOException
   * @@throws FileNotFoundException
   */
  public void saveAs(String filename) throws MissingFileAssociationException, FileNotFoundException, IOException {
    _filename = filename;
    save();
  }

  /**
   * @@param filename
   * @@throws UnavailableFileException
   */
  public void load(String filename) throws UnavailableFileException, ClassNotFoundException{
    try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))){
      _warehouse = (Warehouse) ois.readObject();
      _filename = filename;
    } catch(IOException a){
      throw new UnavailableFileException(filename);
    }
  }

  /**
   * @param textfile
   * @throws ImportFileException
   */
  public void importFile(String textfile) throws ImportFileException, DuplicatePartnerInserted, NonExistentPartner, NonExistentProduct{
    try {
      _warehouse.importFile(textfile);
    } catch (IOException | BadEntryException e) {
      throw new ImportFileException(textfile, e);
    }
  }
}
