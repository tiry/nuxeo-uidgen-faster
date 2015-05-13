/*
 * (C) Copyright 2014 Nuxeo SA (http://nuxeo.com/) and contributors.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser General Public License
 * (LGPL) version 2.1 which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl.html
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * Contributors:
 *     Tiry
 *
 */
package org.nuxeo.uidgen.es;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.nuxeo.ecm.platform.uidgen.UIDSequencer;
import org.nuxeo.ecm.platform.uidgen.service.UIDGeneratorService;
import org.nuxeo.elasticsearch.test.RepositoryElasticSearchFeature;
import org.nuxeo.runtime.api.Framework;
import org.nuxeo.runtime.test.runner.Deploy;
import org.nuxeo.runtime.test.runner.Features;
import org.nuxeo.runtime.test.runner.FeaturesRunner;
import org.nuxeo.runtime.test.runner.LocalDeploy;

@Deploy({ "org.nuxeo.ecm.platform.uidgen.core", "org.nuxeo.uidgen.faster" })
@RunWith(FeaturesRunner.class)
@Features({ RepositoryElasticSearchFeature.class })
@LocalDeploy({ "org.nuxeo.uidgen.faster:elasticsearch-test-contrib.xml" })
public class TestESSeq {

    @Test
    public void testSeq() {
        UIDGeneratorService service = Framework.getService(UIDGeneratorService.class);
        UIDSequencer seq = service.getSequencer("esSequencer");

        Assert.assertNotNull(seq);
        Assert.assertTrue(seq.getClass().isAssignableFrom(ESUIDSequencer.class));

        Assert.assertEquals(1, seq.getNext("A"));
        Assert.assertEquals(2, seq.getNext("A"));
        Assert.assertEquals(1, seq.getNext("B"));

    }
}
