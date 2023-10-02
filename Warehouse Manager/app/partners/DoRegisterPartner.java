package ggc.app.partners;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.app.exception.DuplicatePartnerKeyException;
import ggc.core.WarehouseManager;
import ggc.core.exception.*;

/**
 * Register new partner.
 */
class DoRegisterPartner extends Command<WarehouseManager> {

  DoRegisterPartner(WarehouseManager receiver) {
    super(Label.REGISTER_PARTNER, receiver);
    addStringField("key", Message.requestPartnerKey());
    addStringField("nome", Message.requestPartnerName());
    addStringField("morada", Message.requestPartnerAddress());
  }

  @Override
  public void execute() throws CommandException {

    String key = stringField("key");
    String nome = stringField("nome");
    String morada = stringField("morada");
    try{
    _receiver.addParceiro(nome, key, morada);
    }
    catch (DuplicatePartnerInserted dpi){
      throw new DuplicatePartnerKeyException(key);
    }
  }

}
