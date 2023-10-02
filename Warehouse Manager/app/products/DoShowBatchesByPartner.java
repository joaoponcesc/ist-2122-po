package ggc.app.products;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.Lote;
import ggc.core.WarehouseManager;
import ggc.core.exception.NonExistentPartner;
import ggc.app.exception.UnknownPartnerKeyException;

/**
 * Show batches supplied by partner.
 */
class DoShowBatchesByPartner extends Command<WarehouseManager> {

  DoShowBatchesByPartner(WarehouseManager receiver) {
    super(Label.SHOW_BATCHES_SUPPLIED_BY_PARTNER, receiver);
    addStringField("key", Message.requestPartnerKey());
  }

  @Override
  public final void execute() throws CommandException {
    String key = stringField("key");
    try{
      for(Lote lote : _receiver.getLotesParceiro(key)){
        _display.popup(lote);
      }
    }
    catch(NonExistentPartner nep){
      throw new UnknownPartnerKeyException(key);
    }
  }
}
