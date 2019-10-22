package pub_sub;

import com.pubnub.api.*;
import com.pubnub.api.callbacks.PNCallback;
import com.pubnub.api.models.consumer.PNPublishResult;
import com.pubnub.api.models.consumer.PNStatus;
import com.pubnub.api.models.consumer.presence.PNSetStateResult;
import com.pubnub.api.callbacks.SubscribeCallback;
import com.pubnub.api.enums.PNStatusCategory;
import com.pubnub.api.models.consumer.pubsub.PNMessageResult;
import com.pubnub.api.models.consumer.pubsub.PNPresenceEventResult;

import java.util.Arrays;

import javax.swing.JOptionPane;

public class Subscriber {
	
	//variaveis de configuração do pubnub
    final String PubKey = "pub-c-29ffe5d9-2643-4b78-8bb3-29d9b066c99b";
    final String SubKey = "sub-c-55c41470-ead1-11e9-bdee-36080f78eb20";	
    final String channelName = "divulgacao";
    PubNub pubnub;
    PNConfiguration config;
    
    public Subscriber() {
        config = new PNConfiguration();
        config.setSubscribeKey(SubKey);
        pubnub = new PubNub(config);
    }    
    
    public void subscribe(String tipo_residencia, String municipio, String valorMinimo) {
    	//Filtros
        if(!tipo_residencia.equals("null")) {
        	System.out.println("tipo_residencia");
        	config.setFilterExpression("tipo_residencia == " + tipo_residencia);
        }
        if(!municipio.equals("null")) {
        	config.setFilterExpression("municipio == " + municipio);
       }
       if(!valorMinimo.equals("null")) {
        	config.setFilterExpression("valor_minimo >= " + valorMinimo);        	
       }
        
		String mensagem =  
				"Tipo Residência = " + tipo_residencia + "\n" +
				"Município: " + municipio  + "\n" +
				"Valor: " + valorMinimo;
		System.out.println(mensagem);	    	
    	
        try {
            pubnub.addListener(new SubscribeCallback() {
                @Override
                public void status(PubNub pubnub, PNStatus status) {
                    if (status.getCategory() == PNStatusCategory.PNConnectedCategory) {
                        pubnub.setPresenceState()
                                .channels(Arrays.asList(channelName)) // subscribe to channels
                                .async(new PNCallback<PNSetStateResult>() {

                                    @Override
                                    public void onResponse(PNSetStateResult result, PNStatus status) {

                                    }
                                });
                    }
                }

                @Override
                public void message(PubNub pubnub, PNMessageResult message) {
                    

                    String mensagemRecebida = message.getMessage().toString();
                    
                    System.out.println("sfafssaf: " + mensagemRecebida);
                    
                    //split na mensagem recebida.
                    //indice 0 -> tipo residencia
                    //indice 1 -> municipio
                    //indice 2 -> valor
                    //indice 3 -> mensagem
                    String arrayMensagem[] = mensagemRecebida.split("@");
                    //formata mensagem para exibição
                    mensagemRecebida = "Tipo Residência: " + arrayMensagem[0] + "\n" +
                    				   "Município: " + arrayMensagem[1] + "\n" +
                    				   "Valor: " + arrayMensagem[2] + "\n" +
                    				   "Mensagem: " + arrayMensagem[3] + "\n";
                    
                    //exibe mensagem
            		JOptionPane.showMessageDialog(null, mensagemRecebida ,"Divulgação",
            				JOptionPane.INFORMATION_MESSAGE);	
                    
                }

                @Override
                public void presence(PubNub pubnub, PNPresenceEventResult presence) {
                }
            });
            pubnub.subscribe()
                    .channels(Arrays.asList(channelName))
                    .execute();
        } catch (Exception e) {
            System.out.println("Erro no Subscribe");
        }
    }    

}
