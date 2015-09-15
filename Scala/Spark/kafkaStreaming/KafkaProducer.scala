import java.util.Properties

import kafka.producer.{KeyedMessage, ProducerConfig, Producer => KafkaProducer}
import java.util.Properties

case class Producer[A](topic: String) {

  val props = new Properties();
  props.put("metadata.broker.list", "10.106.9.157:9092")
  props.put("serializer.class", "kafka.serializer.StringEncoder")
  props.put("partitioner.class", "example.producer.SimplePartitioner")
  props.put("request.required.acks", "1")

  // ProducerConfig config = new ProducerConfig(props)
  // Producer<String, String> producer = new Producer<String, String>(config)

  protected val config = new ProducerConfig(props)
  private lazy val producer = new KafkaProducer[A, A](config)

  def send(message: A) = sendMessage(producer, keyedMessage(topic, message))

  def sendStream(stream: Stream[A]) = {
    val iter = stream.iterator
    while(iter.hasNext) {
      send(iter.next())
    }
  }

  private def keyedMessage(topic: String, message: A): KeyedMessage[A, A] = new KeyedMessage[A, A](topic, message)
  private def sendMessage(producer: KafkaProducer[A, A], message: KeyedMessage[A, A]) = producer.send(message)
}


object Producer {
  def apply[T](topic: String, props: Properties) = new Producer[T](topic) {
    override val config = new ProducerConfig(props)
  }
}