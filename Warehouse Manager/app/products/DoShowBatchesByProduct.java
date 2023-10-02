package ggc.app.products;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.Lote;
import ggc.core.WarehouseManager;
import ggc.core.exception.NonExistentProduct;
import ggc.app.exception.UnknownProductKeyException;

/**
 * Show all products.
 */
class DoShowBatchesByProduct extends Command<WarehouseManager> {

  DoShowBatchesByProduct(WarehouseManager receiver) {
    super(Label.SHOW_BATCHES_BY_PRODUCT, receiver);
    addStringField("key", Message.requestProductKey());
  }

  @Override
  public final void execute() throws CommandException {

    String key = stringField("key");
    try{
      for (Lote lote : _receiver.getLotesProduto(key)){
        _display.popup(lote);
      }
    }
    catch (NonExistentProduct nep){
      throw new UnknownProductKeyException(key);
    }
  }
}
