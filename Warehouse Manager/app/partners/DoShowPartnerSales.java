package ggc.app.partners;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.app.exception.UnknownPartnerKeyException;
import ggc.core.Venda;
import ggc.core.WarehouseManager;
import ggc.core.exception.NonExistentPartner;

/**
 * Show all transactions for a specific partner.
 */
class DoShowPartnerSales extends Command<WarehouseManager> {

  DoShowPartnerSales(WarehouseManager receiver) {
    super(Label.SHOW_PARTNER_SALES, receiver);
    addStringField("id parceiro", Message.requestPartnerKey());
  }

  @Override
  public void execute() throws CommandException {
    String idParceiro = stringField("id parceiro");
    try{
      for(Venda venda : _receiver.getVendasParceiroWarehouse(idParceiro)){
        _display.popup(venda);
      }
    }
    catch(NonExistentPartner nep){
      throw new UnknownPartnerKeyException(idParceiro);
    }
  }

}
