package br.com.caelum.jms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.naming.InitialContext;

import br.com.caelum.jms.modelo.Pedido;
import br.com.caelum.jms.modelo.PedidoFactory;

public class TesteProdutorTopico {

    public static void main(String[] args) throws Exception {

        InitialContext context = new InitialContext();
        ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");

        Connection connection = factory.createConnection("admin", "admin");
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Destination topico = (Destination) context.lookup("loja");
        MessageProducer producer = session.createProducer(topico);
        
        Pedido pedido = new PedidoFactory().geraPedidoComValores();
        
        Message message = session.createObjectMessage(pedido);

//        StringWriter writer = new StringWriter();
//
//        JAXB.marshal(pedido, writer);
//
//        String xml = writer.toString();

//        Message message = session.createTextMessage(xml);
        
        //Message message = session.createTextMessage("<pedido><id>222</id></pedido>");
        //message.setBooleanProperty("ebook", false);
        producer.send(message);

        session.close();
        connection.close();
        context.close();
    }
}
