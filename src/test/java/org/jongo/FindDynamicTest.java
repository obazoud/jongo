package org.jongo;

import org.bson.types.ObjectId;
import org.jongo.model.Friend;
import org.jongo.util.JongoTestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;

import static org.assertj.core.api.Assertions.assertThat;

public class FindDynamicTest extends JongoTestCase {

    private MongoCollection collection;

    @Before
    public void setUp() throws Exception {
        collection = createEmptyCollection("friends");
    }

    @After
    public void tearDown() throws Exception {
        dropCollection("friends");
    }

    @Test
    public void canFindDynamic() throws Exception {
        /* given */
        Friend friend = new Friend(new ObjectId(), "John");
        collection.save(friend);

        /* when */
        Iterator<Friend> friends = collection.findDynamic("{name:'John'}").as(Friend.class);

        /* then */
        assertThat(friends.hasNext()).isTrue();
        assertThat(friends.next().getName()).isEqualTo("John");
        assertThat(friends.hasNext()).isFalse();
    }

    @Test
    public void canFindDynamicWithNullValue() throws Exception {
        /* given */
        collection.save(new Friend(new ObjectId(), "John"));
        collection.save(new Friend(new ObjectId(), "Jack"));
        collection.save(new Friend(new ObjectId(), "James"));
        collection.save(new Friend(new ObjectId(), null));

        /* when */
        MongoCursor<Friend> friends = collection.findDynamic("{name:#}", null).as(Friend.class);

        /* then */
        assertThat(friends.count()).isEqualTo(4);
    }

    @Test
    public void canFindDynamicWithNotValue() throws Exception {
        /* given */
        collection.save(new Friend(new ObjectId(), "John"));
        collection.save(new Friend(new ObjectId(), "Jack"));
        collection.save(new Friend(new ObjectId(), "James"));
        collection.save(new Friend(new ObjectId(), null));

        /* when */
        MongoCursor<Friend> friends = collection.findDynamic("{name:#}", "John").as(Friend.class);

        /* then */
        assertThat(friends.count()).isEqualTo(1);
    }

}
