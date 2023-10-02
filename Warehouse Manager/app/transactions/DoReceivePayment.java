package ggc.app.transactions;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.app.exception.UnknownTransactionKeyException;
import ggc.core.WarehouseManager;
import ggc.core.exception.NonExistentTransaction;

/**
 * Receive payment for sale transaction.
 */
public class DoReceivePayment extends Command<WarehouseManager> {

  public DoReceivePayment(WarehouseManager receiver) {
    super(Label.RECEIVE_PAYMENT, receiver);
    addIntegerField("id transacao", Message.requestTransactionKey());
  }

  @Override
  public final void execute() throws CommandException {
    int idTransacao = integerField("id transacao");

    try{
      _receiver.pagaVendaWarehouse(idTransacao);
    }
    catch(NonExistentTransaction net){
      throw new UnknownTransactionKeyException(idTransacao);
    }

  }

}
