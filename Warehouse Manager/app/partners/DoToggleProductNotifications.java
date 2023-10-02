package ggc.app.partners;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.app.exception.UnknownPartnerKeyException;
import ggc.app.exception.UnknownProductKeyException;
import ggc.core.WarehouseManager;
import ggc.core.exception.NonExistentPartner;
import ggc.core.exception.NonExistentProduct;

/**
 * Toggle product-related notifications.
 */
class DoToggleProductNotifications extends Command<WarehouseManager> {

  DoToggleProductNotifications(WarehouseManager receiver) {
    super(Label.TOGGLE_PRODUCT_NOTIFICATIONS, receiver);
    addStringField("idParceiro", Message.requestPartnerKey());
    addStringField("idProduto", Message.requestProductKey());
  }

  @Override
  public void execute() throws CommandException {
    String idParceiro = stringField("idParceiro");
    String idProduto = stringField("idProduto");
    try{
      _receiver.ligaDesligaNotificacoesProdutoWarehouse(idParceiro, idProduto);
    }
    catch(NonExistentPartner nep){
      throw new UnknownPartnerKeyException(idParceiro);
    }
    catch(NonExistentProduct nep){
      throw new UnknownProductKeyException(idProduto);
    }
  }

}
