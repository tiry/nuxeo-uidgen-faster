package org.nuxeo.uidgen.redis;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.nuxeo.ecm.core.redis.RedisAdmin;
import org.nuxeo.ecm.core.redis.RedisCallable;
import org.nuxeo.ecm.core.redis.RedisExecutor;
import org.nuxeo.ecm.platform.uidgen.UIDSequencer;
import org.nuxeo.runtime.api.Framework;

import redis.clients.jedis.Jedis;

public class RedisUIDSequencer implements UIDSequencer {

    protected static final Log log = LogFactory.getLog(RedisUIDSequencer.class);

    protected RedisExecutor executor;

    @Override
    public void dispose() {
        executor = null;
    }

    @Override
    public int getNext(String key) {
        String namespace = Framework.getService(RedisAdmin.class).namespace("counters", key);
        try {
            return getExecutor().execute(new RedisCallable<Long>() {
                @Override
                public Long call(Jedis jedis) throws IOException {
                    return jedis.incrBy(namespace, 1);
                }
            }).intValue();
        } catch (Exception e) {
            log.error("Error while accesing Redis", e);
            return 0;
        }
    }

    protected RedisExecutor getExecutor() {
        if (executor == null) {
            executor = Framework.getService(RedisExecutor.class);
        }
        return executor;
    }

    @Override
    public void init() {
        getExecutor();
    }

}
