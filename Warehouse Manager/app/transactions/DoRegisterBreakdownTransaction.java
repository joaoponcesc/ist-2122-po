package ggc.app.transactions;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.app.exception.UnavailableProductException;
import ggc.app.exception.UnknownPartnerKeyException;
import ggc.app.exception.UnknownProductKeyException;
import ggc.core.WarehouseManager;
import ggc.core.exception.NonExistentPartner;
import ggc.core.exception.NonExistentProduct;
import ggc.core.exception.NotEnoughQuantity;

/**
 * Register order.
 */
public class DoRegisterBreakdownTransaction extends Command<WarehouseManager> {

  public DoRegisterBreakdownTransaction(WarehouseManager receiver) {
    super(Label.REGISTER_BREAKDOWN_TRANSACTION, receiver);
    addStringField("Id Parceiro", Message.requestPartnerKey());
    addStringField("Id Produto", Message.requestProductKey());
    addIntegerField("quantidade", Message.requestAmount());
  }

  @Override
  public final void execute() throws CommandException {
    String idParceiro = stringField("Id Parceiro");
    String idProduto = stringField("Id Produto");
    int quantidade = integerField("quantidade");
    try{
      _receiver.registaDesagregacaoWarehouse(idParceiro, idProduto, quantidade);
    }
    catch(NonExistentPartner nep){
      throw new UnknownPartnerKeyException(idParceiro);
    }
    catch(NonExistentProduct nep){
      throw new UnknownProductKeyException(idProduto);
    }
    catch(NotEnoughQuantity neq){
      throw new UnavailableProductException(idProduto, quantidade, neq.getDisponivel());
    }
    
  }

}
