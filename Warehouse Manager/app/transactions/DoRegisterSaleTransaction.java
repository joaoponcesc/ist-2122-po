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
 * Register sale.
 */
public class DoRegisterSaleTransaction extends Command<WarehouseManager> {

  public DoRegisterSaleTransaction(WarehouseManager receiver) {
    super(Label.REGISTER_SALE_TRANSACTION, receiver);
    addStringField("id parceiro", Message.requestPartnerKey());
    addIntegerField("data limite", Message.requestPaymentDeadline());
    addStringField("id produto", Message.requestProductKey());
    addIntegerField("quantidade", Message.requestAmount());
  }

  @Override
  public final void execute() throws CommandException {

    String idParceiro = stringField("id parceiro");
    int dataLimite = integerField("data limite");
    String idProduto = stringField("id produto");
    int quantidade = integerField("quantidade");

    try{
      _receiver.registaVendaWarehouse(idParceiro, dataLimite, idProduto, quantidade);
    }
    catch(NonExistentPartner nep){
      throw new UnknownPartnerKeyException(idParceiro);
    }
    catch(NonExistentProduct nep){
      throw new UnknownProductKeyException(idProduto);
    }
    catch(NotEnoughQuantity neq){
      throw new UnavailableProductException(neq.getId(), neq.getPedido(), neq.getDisponivel());
    }
  }
}
