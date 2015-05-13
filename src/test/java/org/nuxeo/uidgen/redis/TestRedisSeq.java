package org.nuxeo.uidgen.redis;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.nuxeo.ecm.core.redis.RedisFeature;
import org.nuxeo.ecm.platform.uidgen.UIDSequencer;
import org.nuxeo.ecm.platform.uidgen.service.UIDGeneratorService;
import org.nuxeo.runtime.api.Framework;
import org.nuxeo.runtime.test.runner.Deploy;
import org.nuxeo.runtime.test.runner.Features;
import org.nuxeo.runtime.test.runner.FeaturesRunner;

@RunWith(FeaturesRunner.class)
@Features({ RedisFeature.class })
@Deploy({ "org.nuxeo.ecm.platform.uidgen.core", "org.nuxeo.uidgen.faster" })
public class TestRedisSeq {

    @Test
    public void testSeq() {
        UIDGeneratorService service = Framework.getService(UIDGeneratorService.class);
        UIDSequencer seq = service.getSequencer("redisSequencer");

        Assert.assertNotNull(seq);
        Assert.assertTrue(seq.getClass().isAssignableFrom(RedisUIDSequencer.class));

        Assert.assertEquals(1, seq.getNext("A"));
        Assert.assertEquals(2, seq.getNext("A"));
        Assert.assertEquals(1, seq.getNext("B"));

    }
}
