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
//
//		String mensagem = "Tipo Residência = " + tipo_residencia + "\n" 
//				+ "Município: " + municipio + "\n"
//				+ "Uf: " + uf + "\n"
//				+ "Valor: " + valorMinimo;
//		System.out.println(mensagem);

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
					mensagemRecebida = arrayMensagem[0] 
							+ "\n" + arrayMensagem[1]
							+ "\n" + arrayMensagem[2]
							+ "\n" + arrayMensagem[3] 
							+ "\n" + arrayMensagem[4];

					// exibe mensagem
					JOptionPane.showMessageDialog(null, mensagemRecebida, "Divulgação",
							JOptionPane.INFORMATION_MESSAGE);

				}

				@Override
				public void presence(PubNub pubnub, PNPresenceEventResult presence) {
				}
			});
			pubnub.subscribe().channels(Arrays.asList(channelName)).execute();
		} catch (Exception e) {
			System.out.println("Erro no Subscribe");
		}
	}

}
