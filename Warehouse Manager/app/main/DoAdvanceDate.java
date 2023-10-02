package ggc.app.main;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.app.exception.InvalidDateException;
import ggc.core.WarehouseManager;
import ggc.core.exception.InvalidDateInsertion;

/**
 * Advance current date.
 */
class DoAdvanceDate extends Command<WarehouseManager> {

  DoAdvanceDate(WarehouseManager receiver) {
    super(Label.ADVANCE_DATE, receiver);
    addIntegerField("days", Message.requestDaysToAdvance());
  }

  @Override
  public final void execute() throws CommandException {
    Integer i = integerField("days");
    try{
    _receiver.aumentaDataWarehouse(i);
    }
    catch(InvalidDateInsertion idi){
      throw new InvalidDateException(i);
    }
  }
}
