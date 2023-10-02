package ggc.app.partners;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.app.exception.UnknownPartnerKeyException;
import ggc.core.Notificacao;
import ggc.core.WarehouseManager;
import ggc.core.exception.NonExistentPartner;

/**
 * Show partner.
 */
class DoShowPartner extends Command<WarehouseManager> {

  DoShowPartner(WarehouseManager receiver) {
    super(Label.SHOW_PARTNER, receiver);
    addStringField("idParceiro", Message.requestPartnerKey());
  }

  @Override
  public void execute() throws CommandException {

    String idParceiro = stringField("idParceiro");
    try{
      _display.popup(_receiver.getParceiroByKey(idParceiro));
      for(Notificacao n : _receiver.getNotificacoesParceiroWarehouse(idParceiro)){
        _display.popup(n);
      }
    }
    catch ( NonExistentPartner nep){
      throw new UnknownPartnerKeyException(idParceiro);
    }
  }

}
