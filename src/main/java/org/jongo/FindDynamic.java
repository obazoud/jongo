package org.jongo;

import com.mongodb.DBCollection;
import com.mongodb.ReadPreference;
import org.jongo.marshall.Unmarshaller;
import org.jongo.query.QueryFactory;

import java.util.ArrayList;

public class FindDynamic extends Find {

    public FindDynamic(DBCollection collection, ReadPreference readPreference, Unmarshaller unmarshaller, QueryFactory queryFactory, String query, Object... parameters) {
        this.readPreference = readPreference;
        this.unmarshaller = unmarshaller;
        this.collection = collection;
        this.queryFactory = queryFactory;
        this.query = this.queryFactory.createDynamicQuery(query, parameters);
        this.modifiers = new ArrayList<QueryModifier>();
    }

}
