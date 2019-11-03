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
import com.pubnub.api.models.consumer.pubsub.PNSignalResult;
import com.pubnub.api.models.consumer.pubsub.message_actions.PNMessageActionResult;
import com.pubnub.api.models.consumer.pubsub.objects.PNMembershipResult;
import com.pubnub.api.models.consumer.pubsub.objects.PNSpaceResult;
import com.pubnub.api.models.consumer.pubsub.objects.PNUserResult;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.Arrays;

import javax.swing.JOptionPane;

public class Subscriber {

	// variaveis de configuração do pubnub
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

	public void subscribe(String tipo_residencia, String municipio, String uf, String valorMinimo) {
		config.setFilterExpression("null");
		int i = 0;
		String filtro="";
		// Filtros
		if (!tipo_residencia.equals("null")) {
			if(i == 1) {
				filtro = filtro + " && tipo_residencia == " + "'" + tipo_residencia + "'";
			}else {
				filtro = "tipo_residencia == " + "'" + tipo_residencia + "'";
				i = 1;
			}
		}
		if (!municipio.equals("null")) {
				if(i == 1) {
					filtro = filtro + " && municipio == " + "'" + municipio + "'";
				}else {
					filtro = "municipio == " + "'" + municipio + "'";
					i = 1;
				}		
		}
		if (!uf.equals("null")) {
			if(i == 1) {
				filtro = filtro + " && uf == " + "'" + uf + "'";
			}else {
				filtro = "uf == " + "'" + uf + "'";
				i = 1;
			}		
		}
		if (!valorMinimo.equals("null")) {
			if(i == 1) {
				filtro = filtro + " && valor_minimo >= " +  valorMinimo;
			}else {
				filtro = "valor_minimo >= " + valorMinimo;
				i = 1;
			}		
		}
		
		config.setFilterExpression(filtro);
//		config.setFilterExpression("tipo_residencia == 'casa' && municipio == 'AÃ§ailÃ¢ndia' && uf == 'AC' && valor_minimo >= 100");
		System.out.println("Filtro string: " + filtro);
		System.out.println("Filtro get: " + config.getFilterExpression());

		try {
			pubnub.addListener(new SubscribeCallback() {
				@Override
				public void status(PubNub pubnub, PNStatus status) {
					if (status.getCategory() == PNStatusCategory.PNConnectedCategory) {
						pubnub.setPresenceState().channels(Arrays.asList(channelName)) // subscribe to channels
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

					// split na mensagem recebida.
					// indice 0 -> tipo residencia
					// indice 1 -> municipio
					// indice 2 -> valor
					// indice 3 -> mensagem
					String arrayMensagem[] = mensagemRecebida.split("@");
					// formata mensagem para exibição
					mensagemRecebida = 
							
							"\n" + arrayMensagem[0] 
							+ "\n" + arrayMensagem[1]
							+ "\n" + arrayMensagem[2]
							+ "\n" + arrayMensagem[3] 
							+ "\n" + arrayMensagem[4];
					
					String serializarMensagem = 
							mensagemRecebida 
							+ "\n" + "/////////////////////////////";
					try {
						serializar(serializarMensagem);
					}catch(Exception e) {
						System.out.println("Erro ao serializar arquivo");
					}
					
					// exibe mensagem
					JOptionPane.showMessageDialog(null, mensagemRecebida, "Divulgação",
							JOptionPane.INFORMATION_MESSAGE);
					
					pubnub.unsubscribe();

				}

				@Override
				public void presence(PubNub pubnub, PNPresenceEventResult presence) {
				}

				@Override
				public void signal(PubNub pubnub, PNSignalResult pnSignalResult) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void user(PubNub pubnub, PNUserResult pnUserResult) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void space(PubNub pubnub, PNSpaceResult pnSpaceResult) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void membership(PubNub pubnub, PNMembershipResult pnMembershipResult) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void messageAction(PubNub pubnub, PNMessageActionResult pnMessageActionResult) {
					// TODO Auto-generated method stub
					
				}
			});
			pubnub.subscribe().channels(Arrays.asList(channelName)).execute();
		} catch (Exception e) {
			System.out.println("Erro no Subscribe");
		}
	}
	
    public void serializar(String mensagem) throws Exception {
            File arquivo = new File("arquivo.txt");
            try {
                FileWriter grava = new FileWriter(arquivo, true);
                PrintWriter escreve = new PrintWriter(grava);
                escreve.println(mensagem);
                escreve.close();
                grava.close();
            } catch (IOException ex) {
                System.out.println("Erro de arquivo");
            }
    }	

}
