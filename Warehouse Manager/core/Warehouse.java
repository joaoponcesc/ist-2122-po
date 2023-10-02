package ggc.core;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Collections;
import java.io.IOException;
import ggc.core.exception.*;

import java.util.List;
import java.util.Map;

/**
 * Class Warehouse implements a warehouse.
 */
public class Warehouse implements Serializable {

  /** Serial number for serialization. */
  private static final long serialVersionUID = 202109192006L;

  private Date _days;
  private Map<String,Parceiro> _parceiros = new HashMap<>();
  private Map<String,Produto> _produtos = new HashMap<>() ;
  private Map<Integer, Transacao> _transacoes = new HashMap<>();
  private double _saldoDisponivel;
  private int _numeroTransacoes;

  public Warehouse(){
    _days = new Date(0);
  }

/********************************************DATA***************************************************/

  public int getCurrentDate() {
      return _days.getDays();
  }

  public void aumentaDataAtual(int days) throws InvalidDateInsertion{
    if(days <= 0){
      throw new InvalidDateInsertion("Invalid date");
    }
    _days.increaseDays(days);
    atualizaMultasDescontos();
  } 

  void atualizaMultasDescontos(){
    int n = 5;
    for(Transacao t : _transacoes.values()){
      if(t instanceof VendaACredito){
        VendaACredito venda = (VendaACredito)t;
        if(!venda.isPaid()){
          if(venda.getProduto() instanceof ProdutoDerivado)
            n = 3;
          venda.setPrecoAPagar(venda.getParceiro().calculaValorAPagar(venda.getValorBase(), _days, 
          venda.getDataLimite(), n));
        }
      }
    }
  }

/***************************************PARCEIROS***************************************************/

  public void addParceiro(String nome, String key, String morada) throws DuplicatePartnerInserted{
    Parceiro parceiro = new Parceiro(nome, key, morada);
    if(_parceiros.containsKey(key.toLowerCase()))
      throw new DuplicatePartnerInserted("Duplicate Key Inserted");
    _parceiros.put(key.toLowerCase(), parceiro);
    for(Produto p : _produtos.values()){
      p.ligaDesligaObserver(parceiro);
    }
  }

  public List<Parceiro> getParceirosOrdered(){
    List<Parceiro> sorted = new ArrayList<>();
    
    for(String key : _parceiros.keySet()){
      sorted.add(_parceiros.get(key.toLowerCase()));
    }
    sorted.sort(new ParceiroComparator());
    return sorted;
  }

  Parceiro getParceiroByKey(String key) throws NonExistentPartner{
    if(!_parceiros.containsKey(key.toLowerCase()))
      throw new NonExistentPartner("Key Does not Exist");
    return _parceiros.get(key.toLowerCase());
  }

  public List<Notificacao> getNotificacoesParceiro(String idParceiro) throws NonExistentPartner{
    List<Notificacao> auxiliar = new ArrayList<>();
    Parceiro parceiro = getParceiroByKey(idParceiro);
    auxiliar.addAll(parceiro.getNotificacoes());
    parceiro.getNotificacoes().clear();
    return auxiliar;
  }

  public List<Compra> getComprasParceiro(String idParceiro) throws NonExistentPartner{
    Parceiro parceiro = getParceiroByKey(idParceiro);
    return parceiro.getCompras();
  }

  public List<Venda> getVendasParceiro(String idParceiro) throws NonExistentPartner{
    Parceiro parceiro = getParceiroByKey(idParceiro);
    return parceiro.getVendas();
  }

  public List<Parceiro> getAllParceiros(){
    List<Parceiro> parceiros = new ArrayList<>(_parceiros.values());
    return Collections.unmodifiableList(parceiros);
  }

  public List<Venda> getVendasPagasParceiro(String idParceiro) throws NonExistentPartner{
    Parceiro parceiro = getParceiroByKey(idParceiro);
    List<Venda> vendasPagas = new ArrayList<>();
    for(Venda v : parceiro.getVendas()){
      if(v.isPaid()){
        vendasPagas.add(v);
      }
    }
    return vendasPagas;
  }

/****************************************PRODUTOS***************************************************/

  public List<Produto> getAllProdutos(){
    List<Produto> produtos = new ArrayList<>(_produtos.values());
    return Collections.unmodifiableList(produtos);
  }

  public List<Produto> getProdutosOrdered(){
    List<Produto> sorted = new ArrayList<>();
    
    for(String key : _produtos.keySet()){
      sorted.add(_produtos.get(key));
    }
    sorted.sort(new ProdutoComparator());
    return sorted;
  }

  public Produto getProdutoByKey(String key) throws NonExistentProduct{
    if(!_produtos.containsKey(key.toLowerCase()))
      throw new NonExistentProduct(key);
    return _produtos.get(key.toLowerCase());
  }

  void addProdutoSimples(String idProduto){
    ProdutoSimples produto = new ProdutoSimples(idProduto);
    _produtos.put(idProduto.toLowerCase(), produto);
    adicionaObservers(produto);
  }

  void addProdutoDerivado(Receita receita, String idProduto){
    ProdutoDerivado produtoDerivado = new ProdutoDerivado(idProduto, receita);
    _produtos.put(idProduto.toLowerCase(), produtoDerivado);
    adicionaObservers(produtoDerivado);
  }

  Componente criaComponente(int quantidade, String idProduto) throws NonExistentProduct{
    return new Componente(quantidade, getProdutoByKey(idProduto));
  }

  public boolean existeProduto(String idProduto){
    return _produtos.containsKey(idProduto.toLowerCase());
  }

  void adicionaObservers(Produto produto){
    for(Parceiro parceiro : _parceiros.values()){
      produto.ligaDesligaObserver(parceiro);
    }
  }

  public void ligaDesligaNotificacoesProduto(String idParceiro, String idProduto) throws NonExistentPartner,
  NonExistentProduct{
    Parceiro parceiro = getParceiroByKey(idParceiro);
    Produto produto = getProdutoByKey(idProduto);
    parceiro.turnOnOffNotificacao(produto);
  }

/*******************************************LOTES***************************************************/

  public List<Lote> getAllLotesSorted(){
    List<Lote> allLotes = new ArrayList<>();
    
    for(String p : _produtos.keySet()){
      for(Lote l : _produtos.get(p).getLotes()){
        allLotes.add(l);
      }
    }
    allLotes = getLotesSorted(allLotes);
    return allLotes;
  }

  public List<Lote> getLotesParceiro(String key) throws NonExistentPartner{
    List<Lote> lotesPareceiro = new ArrayList<>();
    if(!_parceiros.containsKey(key.toLowerCase()))
      throw new NonExistentPartner("Key Does Not Exist");
    lotesPareceiro = getLotesSorted(_parceiros.get(key.toLowerCase()).getLotes());
    return lotesPareceiro;
  }

  public List<Lote> getLotesProduto(String key) throws NonExistentProduct{
    List<Lote> lotesProduto = new ArrayList<>();
    if (! _produtos.containsKey(key.toLowerCase()))
      throw new NonExistentProduct("Key Does Not Exist");
    lotesProduto = getLotesSorted(_produtos.get(key.toLowerCase()).getLotes());
    return lotesProduto;
  }

  List<Lote> getLotesSorted(List<Lote> lotes){
    List<Lote> sorted = new ArrayList<>();
    sorted.addAll(lotes);
    sorted.sort(new LoteComparator());
    return sorted;
  }

  public List<Lote> getAllLotesUnderPrice(int preco){
    List<Lote> lotesAbaixoPreco = new ArrayList<>();

    for(String p : _produtos.keySet()){
      for(Lote l : _produtos.get(p).getLotes()){
        if(l.getPreco() < preco)
          lotesAbaixoPreco.add(l);
      }
    }
    lotesAbaixoPreco.sort(new LoteComparator());
    return lotesAbaixoPreco;
  }

  void addLote(String idProduto, int quantidade, double preco, String idParceiro){
    Produto produto = _produtos.get(idProduto.toLowerCase());
    Parceiro parceiro = _parceiros.get(idParceiro.toLowerCase());
    Lote lote = new Lote(preco, quantidade, parceiro, produto);
    produto.addLote(lote);
    parceiro.addLote(lote);
  }

/***************************************TRANSACOES**************************************************/

  List<Transacao> getTransacoes(){
    ArrayList<Transacao> transacoes = new ArrayList<>(_transacoes.values());
    return Collections.unmodifiableList(transacoes);
  }

  int getNumeeroTransacoes(){
    return _numeroTransacoes;
  }

  public Transacao getTransacaoId(int id) throws NonExistentTransaction{
    if(id < 0 || id >= _numeroTransacoes)
      throw new NonExistentTransaction("Unknown Transaction Key");
    return _transacoes.get(id);
  }

  public void registaCompra(String idParceiro, int quantidade, double preco, String idProduto) throws NonExistentPartner,
  NonExistentProduct{
    Parceiro parceiro = getParceiroByKey(idParceiro);
    if (!_produtos.containsKey(idProduto.toLowerCase())){
      addProdutoSimples(idProduto);
      addInformacaoProdutoNovo(parceiro, quantidade, preco, idProduto);
    }
    else{
      Produto produto = getProdutoByKey(idProduto);
      addInformacaoProdutoExistente(produto, parceiro, preco, quantidade);
    }
  }

  public void registaCompra(String idParceiro,int quantidade,double preco,String idProduto,
    List<String> produtos,List<Integer> quantidades, double alpha) throws NonExistentPartner, NonExistentProduct{

    List<Componente> componentes = new ArrayList<>();
    Parceiro parceiro = getParceiroByKey(idParceiro);
    for(int i = 0; i<produtos.size(); i++){
      componentes.add(criaComponente(quantidades.get(i), produtos.get(i)));
    }
    Receita receita = new Receita(alpha, componentes);
    addProdutoDerivado(receita, idProduto);
    addInformacaoProdutoNovo(parceiro, quantidade, preco, idProduto);
  }

  void addCompraAuxiliar(double preco, int quantidade, String idProduto, Parceiro parceiro){
    Compra compra = new Compra(_produtos.get(idProduto.toLowerCase()), quantidade, parceiro, _numeroTransacoes);
    compra.setDataPagamento(_days);
    compra.setValorBase(preco*quantidade);
    _transacoes.put(_numeroTransacoes++, compra);
    parceiro.adicionaCompra(compra);
    atualizaSaldoDisponivelCompra(preco*quantidade);
  }

  void addInformacaoProdutoNovo(Parceiro parceiro, int quantidade, double preco, String idProduto){
    adicionaObservers(idProduto);
    addLote(idProduto, quantidade, preco, parceiro.getId());
    addCompraAuxiliar(preco, quantidade, idProduto, parceiro);
  }

  void addInformacaoProdutoExistente(Produto produto, Parceiro parceiro, double preco, int quantidade){
    if(produto.getLotes().isEmpty())
        produto.notificaObservers("NEW", produto, preco);
    else if(preco < produto.getMinPrice())
      produto.notificaObservers("BARGAIN", produto, preco);
    addLote(produto.getId(), quantidade, preco, parceiro.getId());
    addCompraAuxiliar(preco, quantidade, produto.getId(), parceiro);
  }


  void adicionaObservers(String idProduto){
    Produto produto = _produtos.get(idProduto.toLowerCase());
    Iterator<Parceiro> iter = getAllParceiros().iterator();
    while(iter.hasNext()){
      Parceiro parceiro = iter.next();
      produto.ligaDesligaObserver(parceiro);
    }
  }

  public void registaVenda(String idParceiro, int dataLimite, String idProduto, int quantidade) throws NonExistentPartner, 
  NonExistentProduct, NotEnoughQuantity{

    Parceiro parceiro = getParceiroByKey(idParceiro);
    Produto produto = getProdutoByKey(idProduto);
    int disponivel = produto.getQuantidade();
    if(disponivel < quantidade && produto instanceof ProdutoSimples)
      throw new NotEnoughQuantity(idProduto, disponivel, quantidade);
    else if(disponivel < quantidade && produto instanceof ProdutoDerivado){
      int quantidadeEmFalta = quantidade - produto.getQuantidade();
      agregaProdutos(produto, quantidadeEmFalta, parceiro);
    }
    adicionaVenda(parceiro, produto, dataLimite, quantidade);
  }

void agregaProdutos(Produto produto, int quantidadeEmFalta, Parceiro parceiro) throws NotEnoughQuantity{
    ProdutoDerivado produtoDerivado = (ProdutoDerivado)produto;
    double alpha = produtoDerivado.getReceita().getAlpha();
    for(int i = 0; i < quantidadeEmFalta; i++){
      double preco = 0;
      for(Componente c : produtoDerivado.getReceita().getComponentes()){
        int quantidadeNecessaria = c.getQuantidade();
        if(c.getProduto().getQuantidade() < quantidadeNecessaria * quantidadeEmFalta){
          if(c.getProduto() instanceof ProdutoDerivado)
            agregaProdutos(c.getProduto(), quantidadeEmFalta*(quantidadeNecessaria - c.getProduto().getQuantidade()), parceiro);
          else
            throw new NotEnoughQuantity(c.getProduto().getId(), c.getProduto().getQuantidade(), quantidadeNecessaria * quantidadeEmFalta);
        }
        preco += getPrecoInicialVenda(c.getProduto(), quantidadeNecessaria, parceiro); 
      }
      Lote lote = new Lote((1+alpha) * preco, 1, parceiro, produto);
      produto.addLote(lote);
      parceiro.addLote(lote);
    }
  }

  void adicionaVenda(Parceiro parceiro, Produto produto, int dataLimite, int quantidade){
    int N = 5;
    if(produto instanceof ProdutoDerivado)
      N = 3;
    double valorInicial = 0;
    VendaACredito venda = new VendaACredito(dataLimite, produto, quantidade, parceiro, _numeroTransacoes);
    _transacoes.put(_numeroTransacoes++, venda);
    parceiro.adicionaVenda(venda);
    valorInicial = getPrecoInicialVenda(produto, quantidade, parceiro);
    venda.setValorBase(valorInicial);
    venda.setPrecoAPagar(parceiro.calculaValorAPagar(valorInicial, _days, dataLimite, N));
  }

  double getPrecoInicialVenda(Produto produto, int quantidade, Parceiro parceiro){
    List<Lote> lotes = produto.getLotes();
    lotes.sort(new LotePorPrecoComparator());
    Iterator<Lote> iter = lotes.iterator();
    double preco = 0;
    int num = quantidade;
    while(iter.hasNext() && num != 0){
      Lote l = iter.next();
      if(num - l.getQuantidade() >= 0){
        num -= l.getQuantidade();
        preco += l.getPreco()*l.getQuantidade();
        iter.remove();
        parceiro.getLotes().remove(l);
      }
      else{
        preco += l.getPreco() * num;
        l.diminuiQuantidade(num);
        num = 0;
      }
    }
    return preco;
  }

  public void pagaVenda(int idTransacao) throws NonExistentTransaction{
    Transacao transacao = getTransacaoId(idTransacao);
    double valorFinal;
    int n = 5;
    if(transacao instanceof VendaACredito){
      if(!transacao.isPaid()){
        VendaACredito vendaCredito = (VendaACredito)transacao;
        Parceiro parceiro = transacao.getParceiro();
        if(vendaCredito.getProduto() instanceof ProdutoDerivado)
          n = 3;
        valorFinal = parceiro.calculaValorAPagar(vendaCredito.getValorBase(), _days, vendaCredito.getDataLimite(), n);
        vendaCredito.pagaVenda(valorFinal);
        parceiro.atualizaPontosParceiro(valorFinal, _days, vendaCredito.getDataLimite());
        vendaCredito.setDataPagamento(_days);
        autualizaSaldoDisponivelVenda(valorFinal);
      }
    }  
  }

  public void registaDesagregacao(String idParceiro, String idProduto, int quantidade)throws NonExistentPartner, 
  NonExistentProduct, NotEnoughQuantity{

    Parceiro parceiro = getParceiroByKey(idParceiro);
    Produto produto = getProdutoByKey(idProduto);
    if(produto.getQuantidade() < quantidade)
      throw new NotEnoughQuantity(idProduto, produto.getQuantidade(), quantidade);
    if (!(produto instanceof ProdutoSimples)){
      double precoDerivado = getPrecoInicialVenda(produto, quantidade, parceiro);
      ProdutoDerivado derivado = (ProdutoDerivado)produto;
      VendaDesagregacao desagregacao = new VendaDesagregacao(produto, quantidade, parceiro, _numeroTransacoes);
      double precoComponentes = adicionaComponentesDerivado(parceiro, derivado, quantidade, desagregacao);
      double diferenca = precoDerivado - precoComponentes;
      desagregacao.setValorBase(diferenca);
      if(diferenca < 0)
        desagregacao.setPrecoAPagar(0);
      else{
        desagregacao.setPrecoAPagar(diferenca);
        parceiro.atualizaPontosParceiro(diferenca, _days, _days.getDays());
      }
      autualizaSaldoDisponivelVenda(diferenca);
      desagregacao.setDataPagamento(_days);
      _transacoes.put(_numeroTransacoes++, desagregacao);
      parceiro.adicionaVenda(desagregacao);
    }
  }

  double adicionaComponentesDerivado(Parceiro parceiro, ProdutoDerivado derivado, 
  int quantidade, VendaDesagregacao desagregacao){

    double precoTotalComponentes = 0;
    for(Componente c : derivado.getReceita().getComponentes()){
      double preco = calculaPrecoInsercao(c.getProduto());
      Lote lote = new Lote(preco, c.getQuantidade() * quantidade, parceiro, c.getProduto());
      c.getProduto().addLote(lote);
      parceiro.addLote(lote);
      desagregacao.addLoteDesagregacao(lote);
      precoTotalComponentes += (preco * quantidade * c.getQuantidade());
    }
    return precoTotalComponentes;
  }

  double calculaPrecoInsercao(Produto produto){
    if(produto.getQuantidade() > 0){
      List<Lote> lotes = produto.getLotes();
      lotes.sort(new LotePorPrecoComparator());
      return lotes.get(0).getPreco();
    }
    else{
      produto.notificaObservers("NEW", produto, produto.getMaxMrice());
      return produto.getMaxMrice();
    } 
  }

/*****************************************SALDO*****************************************************/

  void atualizaSaldoDisponivelCompra(double valor){
    _saldoDisponivel -= valor;
  }

  void autualizaSaldoDisponivelVenda(double valor){
    _saldoDisponivel += valor;
  }

  public double getSaldoDisponivel(){
    return Math.round(_saldoDisponivel);
  }

  public double calculaSaldoContabilistico(){
    double valorCompras = 0;
    double valorVendasPagas = 0;
    for(Transacao t : _transacoes.values()){
      if(t instanceof Compra){
        Compra compra = (Compra)t;
        valorCompras += compra.getValorBase();
      }
      else{
        Venda venda = (Venda)t;
        valorVendasPagas += venda.getPrecoAPagar();
      }
    }
    return Math.round(valorVendasPagas - valorCompras);
  }

  /**
   * @param txtfile filename to be loaded.
   * @throws IOException
   * @throws BadEntryException
   */
  void importFile(String txtfile) throws IOException, BadEntryException, DuplicatePartnerInserted, 
  NonExistentPartner, NonExistentProduct{
    Parser parser = new Parser(this);
    parser.parseFile(txtfile);
  }
}