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

public class Subscriber {
	
	//variaveis de configuração do pubnub
    final String PubKey = "pub-c-29ffe5d9-2643-4b78-8bb3-29d9b066c99b";
    final String SubKey = "sub-c-55c41470-ead1-11e9-bdee-36080f78eb20";	
    final String channelName = "divulgacao";
    PubNub pubnub;
    
    public Subscriber() {
        PNConfiguration config = new PNConfiguration();
        //config.setFilterExpression("Temperatura > 50");
        config.setSubscribeKey(SubKey);
        pubnub = new PubNub(config);
    }    
    
    public void subscribe() {
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
                    System.out.println(message.getMessage());
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
