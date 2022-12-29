package org.social.messaging.serializer;

import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;
import org.social.form.PostForm;

/**
 * @Author Mostafa
 * On 12/28/22
 */
public class PostsKafkaSerializer extends ObjectMapperDeserializer<PostForm> {
    public PostsKafkaSerializer() {
        super(PostForm.class);
    }
}
