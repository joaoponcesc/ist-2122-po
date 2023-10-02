package ggc.app.lookups;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.app.exception.UnknownPartnerKeyException;
import ggc.core.Venda;
import ggc.core.WarehouseManager;
import ggc.core.exception.NonExistentPartner;

/**
 * Lookup payments by given partner.
 */
public class DoLookupPaymentsByPartner extends Command<WarehouseManager> {

  public DoLookupPaymentsByPartner(WarehouseManager receiver) {
    super(Label.PAID_BY_PARTNER, receiver);
    addStringField("id parceiro", Message.requestPartnerKey());
  }

  @Override
  public void execute() throws CommandException {
    String idParceiro = stringField("id parceiro");
    try{
      for(Venda v : _receiver.getVendasPagasParceiroWarehouse(idParceiro)){
        _display.popup(v);
      }
    }
    catch(NonExistentPartner nep){
      throw new UnknownPartnerKeyException(idParceiro);
    }
  }

}
