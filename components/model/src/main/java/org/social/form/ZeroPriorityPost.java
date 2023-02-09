package org.social.form;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Author Mostafa
 * On 12/28/22
 */
@Getter
@Setter
@NoArgsConstructor @AllArgsConstructor
public class ZeroPriorityPost {
    Long userId;
    PostFeedCache postFeedCache;
}
