package ggc.app.main;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.app.exception.FileOpenFailedException;
import ggc.core.WarehouseManager;
import ggc.core.exception.UnavailableFileException;

/**
 * Open existing saved state.
 */
class DoOpenFile extends Command<WarehouseManager> {

private String _filename;

  /** @param receiver */
  DoOpenFile(WarehouseManager receiver){
    super(Label.OPEN, receiver);
    addStringField("filename", Message.openFile());
  }

  @Override
  public final void execute() throws CommandException{
    try {
      _filename = stringField("filename");
      _receiver.load(_filename);
    } catch (UnavailableFileException ufe){
      throw new FileOpenFailedException(_filename);
    } catch (ClassNotFoundException e){
      e.printStackTrace();
    }
  }
}
