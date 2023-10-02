package ggc.app.lookups;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.Lote;
import ggc.core.WarehouseManager;

/**
 * Lookup products cheaper than a given price.
 */
public class DoLookupProductBatchesUnderGivenPrice extends Command<WarehouseManager> {

  public DoLookupProductBatchesUnderGivenPrice(WarehouseManager receiver) {
    super(Label.PRODUCTS_UNDER_PRICE, receiver);
    addIntegerField("preco limite", Message.requestPriceLimit());
  }

  @Override
  public void execute() throws CommandException {
    Integer i = integerField("preco limite");
    for(Lote lote : _receiver.getLotesWarehouseAbaixoPreco(i)){
      _display.popup(lote);
    }
  }

}
