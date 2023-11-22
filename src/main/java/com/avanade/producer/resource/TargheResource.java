package com.avanade.producer.resource;

import com.avanade.model.Rilevazione;
import com.avanade.producer.Sender;
import com.avanade.producer.SenderAsyncCallBack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author mirco.cennamo on 20/10/2023
 * @project spring-boot-kafka-producer
 */
@RestController
@RequestMapping("avanade")
public class TargheResource {

    @Value("${topic.name}")
    private  String TOPIC;
    @Autowired
    private Sender sender;
    @Autowired
    private SenderAsyncCallBack senderAsyncCallBack;

    @PostMapping(path = "/publish/newTarga")
    public ResponseEntity<Rilevazione> create(@RequestBody Rilevazione newRilevazione) {
        sender.sendMessage(newRilevazione,TOPIC);
        return new ResponseEntity<>(newRilevazione, HttpStatus.OK);
    }

    @PostMapping(path = "/publish/newTargaCallBack")
    public ResponseEntity<Rilevazione> createCallBack(@RequestBody Rilevazione newRilevazione) {
        senderAsyncCallBack.sendMessage(newRilevazione,TOPIC);
        return new ResponseEntity<>(newRilevazione, HttpStatus.OK);
    }


}
