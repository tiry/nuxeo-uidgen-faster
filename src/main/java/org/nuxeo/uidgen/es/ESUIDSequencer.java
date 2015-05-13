package org.nuxeo.uidgen.es;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.nuxeo.ecm.platform.uidgen.UIDSequencer;
import org.nuxeo.elasticsearch.api.ElasticSearchAdmin;
import org.nuxeo.runtime.api.Framework;

public class ESUIDSequencer implements UIDSequencer {

    public static final String IDX_NAME = "uidgen";

    public static final String IDX_TYPE = "seqId";

    protected Client esClient = null;

    protected Client getClient() {
        if (esClient == null) {
            ElasticSearchAdmin esa = Framework.getService(ElasticSearchAdmin.class);
            esClient = esa.getClient();
        }
        return esClient;
    }

    @Override
    public void dispose() {
        if (esClient != null) {
            esClient.close();
        }
    }

    @Override
    public int getNext(String sequenceName) {
        String source = "{ \"ts\" : " + System.currentTimeMillis() + "}";
        IndexResponse res = getClient().prepareIndex(IDX_NAME, IDX_TYPE, sequenceName).setSource(source).execute().actionGet();
        return (int) res.getVersion();
    }

    @Override
    public void init() {
        getClient();
    }

}
