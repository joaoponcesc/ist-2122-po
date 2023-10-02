package ggc.app.transactions;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import java.util.ArrayList;
import java.util.List;

import ggc.app.exception.UnknownPartnerKeyException;
import ggc.app.exception.UnknownProductKeyException;
import ggc.core.WarehouseManager;
import ggc.core.exception.NonExistentPartner;
import ggc.core.exception.NonExistentProduct;
import pt.tecnico.uilib.forms.Form;

/**
 * Register order.
 */
public class DoRegisterAcquisitionTransaction extends Command<WarehouseManager> {

  public DoRegisterAcquisitionTransaction(WarehouseManager receiver) {
    super(Label.REGISTER_ACQUISITION_TRANSACTION, receiver);
    addStringField("idParceiro", Message.requestPartnerKey());
    addStringField("idProduto", Message.requestProductKey());
    addRealField("preco", Message.requestPrice());
    addIntegerField("quantidade", Message.requestAmount());
  }

  @Override
  public final void execute() throws CommandException {
    
    String id = "";
    List<String> produtos = new ArrayList<>();
    List<Integer> quantidades = new ArrayList<>();
    String idParceiro = stringField("idParceiro");
    String idProduto = stringField("idProduto");
    Double preco = realField("preco");
    Integer quantidade = integerField("quantidade");

    try{
      if(!_receiver.existeProdutoWarehouse(idProduto)){
        String temReceita = Form.requestString(Message.requestAddRecipe());
        if(!temReceita.equals("s") && !temReceita.equals("S"))
          _receiver.registaCompraWarehouse(idParceiro, quantidade, preco, idProduto);
        else{
          Integer num = Form.requestInteger(Message.requestNumberOfComponents()); 
          Double alpha = Form.requestReal(Message.requestAlpha());
          for(int i = 0; i < num; i++){
            id = Form.requestString(Message.requestProductKey());
            int q = Form.requestInteger(Message.requestAmount());
            produtos.add(id);
            quantidades.add(q);
          }
          _receiver.registaCompraWarehouse(idParceiro, quantidade, preco, idProduto, produtos, quantidades, alpha);
        }
      }
      else{
        _receiver.registaCompraWarehouse(idParceiro, quantidade, preco, idProduto);
      }  
    }
    catch(NonExistentPartner nep){
      throw new UnknownPartnerKeyException(idParceiro);
    }
    catch(NonExistentProduct nep){
      throw new UnknownProductKeyException(nep.getValue()); // esta a fazer mal a mensagem!!!!!!!!!!!!
    }
  }
}