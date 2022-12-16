package org.social.post.form;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.DataSerializable;
import com.hazelcast.nio.serialization.IdentifiedDataSerializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.IOException;

/**
 * @Author Mostafa
 * On 12/16/22
 */

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class PostFormForCache implements IdentifiedDataSerializable {

    PostForm postForm;


    @Override
    public void writeData(ObjectDataOutput objectDataOutput) throws IOException {
        objectDataOutput.writeInt(postForm.getPriority());
    }

    @Override
    public void readData(ObjectDataInput objectDataInput) throws IOException {
        postForm = new PostForm();
        postForm.setPriority(objectDataInput.readInt());
    }

    @Override
    public int getFactoryId() {
        return 1;
    }

    @Override
    public int getClassId() {
        return 1;
    }
}
