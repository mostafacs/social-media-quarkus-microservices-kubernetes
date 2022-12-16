package org.social.post.form;

import com.hazelcast.nio.serialization.DataSerializableFactory;
import com.hazelcast.nio.serialization.IdentifiedDataSerializable;

/**
 * @Author Mostafa
 * On 12/16/22
 */
public class PostFormDataSerializableFactory
        implements DataSerializableFactory {

    public static final int FACTORY_ID = 1;

    public static final int EMPLOYEE_TYPE = 1;

    @Override
    public IdentifiedDataSerializable create(int typeId) {
        if ( typeId == EMPLOYEE_TYPE ) {
            return new PostFormForCache();
        } else {
            return null;
        }
    }
}