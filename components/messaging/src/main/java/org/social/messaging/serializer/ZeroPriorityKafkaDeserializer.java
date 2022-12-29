package org.social.messaging.serializer;

import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;
import org.social.form.ZeroPriorityPost;

/**
 * @Author Mostafa
 * On 12/29/22
 */
public class ZeroPriorityKafkaDeserializer extends ObjectMapperDeserializer<ZeroPriorityPost> {
    public ZeroPriorityKafkaDeserializer() {
        super(ZeroPriorityPost.class);
    }
}