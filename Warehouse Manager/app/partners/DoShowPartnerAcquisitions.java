package ggc.app.partners;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.app.exception.UnknownPartnerKeyException;
import ggc.core.Compra;
import ggc.core.WarehouseManager;
import ggc.core.exception.NonExistentPartner;

/**
 * Show all transactions for a specific partner.
 */
class DoShowPartnerAcquisitions extends Command<WarehouseManager> {

  DoShowPartnerAcquisitions(WarehouseManager receiver) {
    super(Label.SHOW_PARTNER_ACQUISITIONS, receiver);
    addStringField("id parceiro", Message.requestPartnerKey());
  }

  @Override
  public void execute() throws CommandException {
    String idParceiro = stringField("id parceiro");
    try{
      for(Compra compra : _receiver.getComprasParceiroWarehouse(idParceiro)){
        _display.popup(compra);
      }
    }
    catch(NonExistentPartner nep){
      throw new UnknownPartnerKeyException(idParceiro);
    }
  }

}
