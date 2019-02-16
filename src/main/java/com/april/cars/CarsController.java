package com.april.cars;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public class CarsController {

    private final CarsRepository carrepos;
    private final RabbitTemplate rt;

    public CarsController(CarsRepository carrepos, RabbitTemplate rt)
    {
        this.carrepos = carrepos;
        this.rt = rt;
    }
    @GetMapping("/cars")
    public List<Cars> all()
    {
        return carrepos.findAll();
    }

    @GetMapping("/cars/{id}")
    public Cars findOne(@PathVariable Long id)
    {
        return carrepos.findById(id)
                .orElseThrow(() -> new CarsNotFoundException(id));
    }
    @GetMapping("/cars/year")
    public ObjectNode sumPops()
    {
        /
        List<Cars> cars = carrepos.findAll();

        Long total = 0L;
        for (Cars l : Cars)
        {
            total = total + l.getYear();
        }

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode totalPops = mapper.createObjectNode();
        totalPops.put("id", 1);
        totalPops.put("year", "2010");
        totalPops.put("brand","BMW");
        totalPops.put("Model", "1 Series");

        CarsLog message = new CarsLog("Looked up cars");
        rt.convertAndSend(WorldlangsApplication.QUEUE_NAME, message.toString());
        log.info("Message sent");
        return totalPops;
    }
}


