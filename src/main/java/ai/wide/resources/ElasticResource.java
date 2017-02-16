package ai.wide.resources;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import ai.wide.utils.HTMLHandler;

@Path("elastic")
public class ElasticResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getIt() {
        

        String r = HTMLHandler.restCall("POST", "http://api.wide-eyes.it/SearchByAttributes", 
                "{\"page\":0,\"attributes\":{\"included\":[],\"excluded\":[]},\"filters\":{},\"ranges\":{\"price\":{\"min\":0,\"max\":1000},\"discount\":{\"min\":0,\"max\":100}},\"maxNumResults\":50}", 
                "application/json", null, "28ecd9090551dbe8ca7614ada843a15d7fc9f751");
        System.out.println(r);
        return Response.status(200).entity(r).build();
    
    }
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response postIt(String req) {
        return Response.status(200).entity(fbResponse(req).toString()).build();
    }

    private JSONObject fbResponse(String req) {
        String category = "";
        
        JSONObject json = new JSONObject(req);
        
        JSONObject parameters = json.getJSONObject("result").getJSONObject("parameters");
        
        if (parameters.has("category_type")) {
            category = parameters.getString("category_type");
        }
        
        JSONObject result = new JSONObject();
        result.put("source", json.getJSONObject("result").get("source"));
        result.put("resolvedQuery", json.getJSONObject("result").get("resolvedQuery"));
        //result.put("speech", json.getJSONObject("result").get("speech"));
        //result.put("action", json.getJSONObject("result").get("action"));
        result.put("actionIncomplete", json.getJSONObject("result").get("actionIncomplete"));
        result.put("parameters", json.getJSONObject("result").getJSONObject("parameters"));
        
        
        JSONObject response = new JSONObject();
        
        response.put("speech", "Hola" + category);
        response.put("displayText", "HolaHola" + category);
        response.put("source", json.getJSONObject("result").get("source"));
        
        
        JSONObject button = new JSONObject();
        button.put("type", "web_url");
        button.put("url", "http://www.google.com");
        button.put("title", "View Details");
        JSONArray buttons = new JSONArray();
        buttons.put(button);
        
        JSONObject element = new JSONObject();
        element.put("title", "titulo");
        element.put("image_url", "https://s-media-cache-ak0.pinimg.com/564x/52/b0/16/52b01627386f9879fb6aac6cd563f735.jpg");
        element.put("subtitle", "subtitle");
        element.put("buttons", buttons);
        JSONArray elements = new JSONArray();
        elements.put(element);
        elements.put(element);
        
        JSONObject payload = new JSONObject();
        payload.put("template_type", "generic");
        payload.put("elements", elements);
        
        JSONObject attachment = new JSONObject();
        attachment.put("type", "template");
        attachment.put("payload", payload);
        
        JSONObject fbMessage = new JSONObject();
        fbMessage.put("attachment", attachment);
        
        JSONObject data = new JSONObject();
        data.put("facebook", fbMessage);
        response.put("data", data);
        
        return response;
    }
}



/* 
 * 
 *     facebook_message = {
        "attachment": {
            "type": "template",
            "payload": {
                "template_type": "generic",
                "elements": [
                    {
                        "title": channel.get('title'),
                        "image_url": "http://l.yimg.com/a/i/us/we/52/" + condition.get('code') + ".gif",
                        "subtitle": speech,
                        "buttons": [
                            {
                                "type": "web_url",
                                "url": channel.get('link'),
                                "title": "View Details"
                            }
                        ]
                    }
                ]
            }
        }
    }

    print(json.dumps(slack_message))

    return {
        "speech": speech,
        "displayText": speech,
        "data": {"slack": slack_message, "facebook": facebook_message},
        # "contextOut": [],
        "source": "apiai-weather-webhook-sample"
    }
    
    
    {
   "id":"6a673dd2-e33f-451b-a856-c14d5f9ca387",
   "timestamp":"2017-02-13T13:41:43.998Z",
   "lang":"en",
   "result":{
      "source":"agent",
      "resolvedQuery":"I need a red jacket",
      "speech":"",
      "action":"",
      "actionIncomplete":false,
      "parameters":{
         "date-period":""
      },
      "contexts":[
         {
            "name":"generic",
            "parameters":{
               "date-period.original":"",
               "facebook_sender_id":"1416930015047968",
               "date-period":""
            },
            "lifespan":4
         }
      ],
      "metadata":{
         "intentId":"0976239c-2801-4cd3-876f-2b974f415016",
         "webhookUsed":"true",
         "webhookForSlotFillingUsed":"false",
         "intentName":"jacketcoat"
      },
      "fulfillment":{
         "speech":"Look if this is interesting for you",
         "messages":[
            {
               "type":0,
               "speech":"Your words are orders for me...my lord"
            },
            {
               "title":"Jacket 389353",
               "imageUrl":"http://imagenpng.com/wp-content/uploads/img/jacket_PNG8031.png",
               "buttons":[
                  {
                     "text":"Buy",
                     "postback":"http://www.yoox.com"
                  }
               ],
               "type":1
            }
         ]
      },
      "score":0.87
   },
   "status":{
      "code":200,
      "errorType":"success"
   },
   "sessionId":"2e02589c-301c-4740-a522-e0fe7ef9de60",
   "originalRequest":{
      "source":"facebook",
      "data":{
         "sender":{
            "id":"1416930015047968"
         },
         "recipient":{
            "id":"139458133235368"
         },
         "message":{
            "mid":"mid.1486993303560:95afcf7b94",
            "text":"I need a red jacket",
            "seq":1602
         },
         "timestamp":1.48699330356E12
      }
   }
}
 */


/*
 * curl -X POST -H "apikey: 28ecd9090551dbe8ca7614ada843a15d7fc9f751" 
 * -H "Content-Type: application/json" 
 * -d '{"page":0,"attributes":{"included":[],"excluded":[]},"filters":{},
 * "ranges":{"price":{"min":0,"max":1000},"discount":{"min":0,"max":100}},"maxNumResults":50}' 
 * http://api.wide-eyes.it/SearchByAttributes
 */