package com.example.questem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Linmo
 * @create 2020/5/14 19:48
 */

@Configuration
public class RabbitMQConfiguration {

//    @Bean
//    public DirectExchange exchange(){
//        return new DirectExchange("ques.bank");
//    }
//
//    @Bean
//    public Queue queue(){
//       return new Queue("ques.info.update");
//    }
//
//    @Bean
//    public Binding exchangeBuilder(DirectExchange exchange,Queue queue ){
//        return  BindingBuilder.bind(queue).
//                to(exchange).with("ques.update");
//    }

//    @Bean
//    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory){
//        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
//        return rabbitTemplate;
//    }
}
